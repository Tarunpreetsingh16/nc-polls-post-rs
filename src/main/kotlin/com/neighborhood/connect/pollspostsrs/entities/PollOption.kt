package com.neighborhood.connect.pollspostsrs.entities

import jakarta.persistence.*
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@Data
@NoArgsConstructor
@Table(name = "poll_option")
data class PollOption(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(name = "postId", nullable = false)
    val postId: Int?,

    @Column(name = "pollOption", nullable = false)
    val pollOption: String?
)
