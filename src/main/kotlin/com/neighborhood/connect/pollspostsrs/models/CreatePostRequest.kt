package com.neighborhood.connect.pollspostsrs.models

import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.entities.Post

data class CreatePostRequest (
    val post: Post,
    val pollOptions: List<PollOption>
)
