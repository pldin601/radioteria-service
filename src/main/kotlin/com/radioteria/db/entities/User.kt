package com.radioteria.db.entities

import com.radioteria.db.entities.meta.UserMeta
import com.radioteria.db.enums.UserState
import javax.persistence.*

@Entity
@Table(name = UserMeta.TABLE_NAME)
@Access(AccessType.FIELD)
class User(
        @Column(name = UserMeta.EMAIL, unique = true, nullable = false)
        var email: String = "",

        @Column(name = UserMeta.PASSWORD, nullable = false)
        var password: String = "",

        @Column(name = UserMeta.NAME, nullable = false)
        var name: String = "",

        @Enumerated
        @Column(name = UserMeta.STATE, nullable = false)
        var state: UserState = UserState.INACTIVE,

        @ManyToOne(targetEntity = File::class)
        @JoinColumn(name = UserMeta.AVATAR_FILE_ID)
        var avatarFile: File? = null,

        @OneToMany(mappedBy = "user", orphanRemoval = true)
        var channels: List<Channel> = emptyList(),

        id: Long? = null
) : IdAwareEntity<Long>(id) {
    val hasAvatar: Boolean get() = avatarFile != null
    val hasChannels: Boolean get() = channels.isNotEmpty()
}