package com.neighborhood.connect.pollspostsrs.models.interfaces

import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.entities.Post

interface IPostPollOption {
    val post: Post
    val pollOptions: List<PollOption>
}
