package com.neighborhood.connect.pollspostsrs.service.db

import com.neighborhood.connect.pollspostsrs.entities.PollOptionVote
import com.neighborhood.connect.pollspostsrs.repositories.IPollOptionVoteRepository
import org.springframework.stereotype.Service

@Service
class PollOptionVoteRepositoryServiceImpl(private val pollOptionVoteRepository: IPollOptionVoteRepository) :
    IPollOptionVoteRepositoryService {
    override fun getUserVoteForPost(userId: Int, postId: Int): PollOptionVote? {
        return pollOptionVoteRepository.findByVoterCredentialIdAndPostId(userId, postId)
    }

    override fun save(userVote: PollOptionVote) {
        pollOptionVoteRepository.save(userVote)
    }
}
