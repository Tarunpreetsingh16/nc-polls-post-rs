package com.neighborhood.connect.pollspostsrs.entities

import jakarta.persistence.*
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.geo.Point
import java.sql.Timestamp

@Entity
@Data
@NoArgsConstructor
@Table(name = "post")
data class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(name = "timestamp", nullable = false)
    val timestamp: Timestamp?,

    @Column(name = "archived", nullable = false)
    val archived: Boolean?,

    @Column(name = "postalCode", nullable = false)
    val postalCode: String?,

    @Column(name = "deleted", nullable = false)
    val deleted: Boolean?,

    @Column(name = "coordinate", nullable = false)
    val coordinate: Point?,

    @Column(name = "city", nullable = false)
    val city: String?,

    @Column(name = "anonymous", nullable = false)
    val anonymous: Boolean?,

    @Column(name = "userCredentialId", nullable = false)
    val userCredentialId: Int?,

    @Column(name = "title", nullable = false)
    val title: String?,

    @Column(name = "description", nullable = false)
    val description: String?,

    @Column(name = "postTypeId", nullable = false)
    val postTypeId: Int?,
)
