package com.radioteria.db.repositories

import com.radioteria.db.entities.IdAwareEntity
import org.springframework.beans.PropertyAccessorFactory
import java.io.Serializable
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaQuery

abstract class JpaEntityRepository<K : Serializable, E : IdAwareEntity<K>>(
        open val entityClass: Class<E>,
        open val idClass: Class<K>) : EntityRepository<K, E> {

    @PersistenceContext open lateinit var entityManager: EntityManager

    override fun persist(entity: E) {
        entityManager.persist(entity)
    }

    override fun remove(entity: E) {
        entityManager.remove(entity)
    }

    override fun findById(id: K): E? {
        return entityManager.find(entityClass, id)
    }

    open internal fun findByQueryProvider(cqProvider: () -> CriteriaQuery<E>): E? {
        return try { queryByProvider(cqProvider).singleResult } catch (e: NoResultException) { null }
    }

    override fun <T> findByPropertyValue(propertyName: String, propertyValue: T): E? {
       return try { queryByPropertyValue(propertyName, propertyValue).singleResult } catch (e: NoResultException) { null }
    }

    override fun list(): List<E> {
        return listByQueryProvider {
            with(entityManager.criteriaBuilder.createQuery(entityClass)) {
                this.select(this.from(entityClass))
            }
        }
    }

    override fun <T> listByPropertyValue(propertyName: String, propertyValue: T): List<E> {
        return queryByPropertyValue(propertyName, propertyValue).resultList
    }

    open internal fun listByQueryProvider(cqProvider: () -> CriteriaQuery<E>): List<E> {
        return queryByProvider(cqProvider).resultList
    }

    open internal fun queryByProvider(cqProvider: () -> CriteriaQuery<E>): TypedQuery<E> {
        return entityManager.createQuery(cqProvider.invoke())
    }

    open internal fun <T> queryByPropertyValue(propertyName: String, propertyValue: T): TypedQuery<E> {
        val cb = entityManager.criteriaBuilder
        val criteriaQuery = cb.createQuery(entityClass)
        val root = criteriaQuery.from(entityClass)

        return entityManager.createQuery(
                criteriaQuery
                        .select(root)
                        .where(cb.equal(root.get<T>(propertyName), propertyValue))
        )
    }

    override fun flush() {
        entityManager.flush()
    }

    override fun refresh(entity: E) {
        entityManager.refresh(entity)
    }

    override fun refreshProperty(entity: E, propertyName: String) {
        val entityId = entity.id ?: throw IllegalStateException()
        findById(entityId)?.let {
            val src = PropertyAccessorFactory.forBeanPropertyAccess(it)
            val dst = PropertyAccessorFactory.forBeanPropertyAccess(entity)

            dst.setPropertyValue(propertyName, src.getPropertyValue(propertyName))
        }
    }

    override fun <A : Number> incrementAndRefresh(entity: E, propertyName: String, by: A) {
        val cb = entityManager.criteriaBuilder
        val criteriaUpdate = cb.createCriteriaUpdate(entityClass)
        val root = criteriaUpdate.from(entityClass)
        val property = root.get<A>(propertyName)

        criteriaUpdate.set(property, cb.sum(property, by))

        entityManager.createQuery(criteriaUpdate).executeUpdate()

        refreshProperty(entity, propertyName)
    }
}