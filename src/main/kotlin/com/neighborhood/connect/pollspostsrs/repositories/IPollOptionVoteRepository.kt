package com.neighborhood.connect.pollspostsrs.repositories

import com.neighborhood.connect.pollspostsrs.entities.PollOptionVote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPollOptionVoteRepository : JpaRepository<PollOptionVote, Long> {
    fun findByVoterCredentialIdAndPostId(voterCredentialId: Int, postId: Int): PollOptionVote?
}
