package com.neighborhood.connect.pollspostsrs.models

import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.entities.Post
import com.neighborhood.connect.pollspostsrs.models.interfaces.IPostPollOption

data class CreatePostRequest (
    override val post: Post,
    override val pollOptions: List<PollOption>
): IPostPollOption
