package com.neighborhood.connect.pollspostsrs.service.db

import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.models.VoteRequest

interface IPollOptionRepositoryService {
    fun createOptions(pollOptions: List<PollOption>)

    fun arePostIdAndPollOptionIdLinked(voteRequest: VoteRequest): Boolean
}
