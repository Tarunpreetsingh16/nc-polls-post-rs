package com.neighborhood.connect.pollspostsrs.service.db

import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.models.VoteRequest
import com.neighborhood.connect.pollspostsrs.repositories.IPollOptionRepository
import org.springframework.stereotype.Service

@Service
class PollOptionRepositoryServiceImpl(private val pollOptionRepository: IPollOptionRepository) :
    IPollOptionRepositoryService {
    override fun createOptions(pollOptions: List<PollOption>) {
        pollOptions.forEach { pollOption ->
            pollOptionRepository.save(pollOption)
        }
    }

    override fun arePostIdAndPollOptionIdLinked(voteRequest: VoteRequest): Boolean {
        return pollOptionRepository.findByIdAndPostId(voteRequest.pollOptionId, voteRequest.postId) != null
    }
}
