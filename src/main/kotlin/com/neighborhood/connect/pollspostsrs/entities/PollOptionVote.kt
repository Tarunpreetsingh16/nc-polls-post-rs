package com.neighborhood.connect.pollspostsrs.entities

import jakarta.persistence.*
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@Data
@NoArgsConstructor
@Table(name = "poll_option_vote")
data class PollOptionVote(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(name = "pollOptionId", nullable = false)
    var pollOptionId: Int?,

    @Column(name = "voterCredentialId", nullable = false)
    var voterCredentialId: Int?,

    @Column(name = "anonymousVote", nullable = false)
    var anonymousVote: Boolean?,

    @Column(name = "postId", nullable = false)
    var postId: Int?

) {
    constructor(): this(null, null, null, null, null)
}
