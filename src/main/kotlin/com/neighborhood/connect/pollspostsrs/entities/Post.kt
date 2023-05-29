package com.neighborhood.connect.pollspostsrs.entities

import jakarta.persistence.*
import lombok.Data
import lombok.NoArgsConstructor
import java.sql.Timestamp

import java.time.Instant

@Entity
@Data
@NoArgsConstructor
@Table(name = "post")
data class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(name = "timestamp", nullable = false)
    val timestamp: Timestamp? = Timestamp.from(Instant.now()),

    @Column(name = "archived", nullable = false)
    val archived: Boolean? = false,

    @Column(name = "postalCode", nullable = false)
    val postalCode: String?,

    @Column(name = "deleted", nullable = false)
    val deleted: Boolean? = false,

    @Column(name = "longitude", nullable = false)
    val longitude: Double?,

    @Column(name = "latitude", nullable = false)
    val latitude: Double?,

    @Column(name = "city", nullable = false)
    val city: String?,

    @Column(name = "anonymous", nullable = false)
    val anonymous: Boolean?,

    @Column(name = "userCredentialId", nullable = false)
    var userCredentialId: Int?,

    @Column(name = "title", nullable = false)
    val title: String?,

    @Column(name = "description", nullable = false)
    val description: String?,

    @Column(name = "postTypeId", nullable = false)
    val postTypeId: Int?,
) {
    constructor(): this(null, null, null, null, null, null,null
    ,null, null, null, null, null, null)
}
