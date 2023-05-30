package com.neighborhood.connect.pollspostsrs.entities

data class PostPollOptionAndVote (
    val post: Post,
    val pollOption: PollOption,
    val pollOptionVote: PollOptionVote? = PollOptionVote()
)
