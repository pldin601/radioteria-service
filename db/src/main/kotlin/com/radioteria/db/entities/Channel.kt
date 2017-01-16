package com.radioteria.db.entities

import javax.persistence.*

object ChannelMeta {
    const val TABLE_NAME = "channels"
    const val NAME = "name"
    const val DESCRIPTION = "description"
    const val STARTED_AT = "started_at"
    const val USER_ID = "user_id"
    const val ARTWORK_FILE_ID = "artwork_file_id"
}

@Entity
@Table(name = ChannelMeta.TABLE_NAME)
class Channel(
        @ManyToOne(targetEntity = User::class)
        @JoinColumn(name = ChannelMeta.USER_ID, nullable = false)
        var userId: User,

        @Column(name = ChannelMeta.NAME, nullable = false)
        var name: String = "",

        @Column(name = ChannelMeta.DESCRIPTION, nullable = false)
        var description: String = "",

        @Column(name = ChannelMeta.STARTED_AT)
        var startedAt: Long? = null,

        @ManyToOne(targetEntity = File::class)
        @JoinColumn(name = ChannelMeta.ARTWORK_FILE_ID)
        var artworkFile: File? = null,

        @OneToMany(mappedBy = "channel", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        @OrderBy(TrackMeta.ORDER_ID + " ASC")
        var tracks: List<Track> = emptyList(),

        id: Long? = null
) : IdAwareEntity<Long>(id) {
    val isStarted: Boolean get() = startedAt != null
}