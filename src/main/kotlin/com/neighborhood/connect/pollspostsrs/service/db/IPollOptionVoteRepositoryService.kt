package com.neighborhood.connect.pollspostsrs.service.db

import com.neighborhood.connect.pollspostsrs.entities.PollOptionVote

interface IPollOptionVoteRepositoryService {
    fun getUserVoteForPost(userId: Int, postId: Int): PollOptionVote?

    fun save(userVote: PollOptionVote)
}
