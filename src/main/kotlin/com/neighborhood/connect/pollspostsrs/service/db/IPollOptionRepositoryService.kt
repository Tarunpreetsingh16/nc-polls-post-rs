package com.neighborhood.connect.pollspostsrs.service.db

import com.neighborhood.connect.pollspostsrs.entities.PollOption

interface IPollOptionRepositoryService {
    fun createOptions(pollOptions: List<PollOption>)
}
