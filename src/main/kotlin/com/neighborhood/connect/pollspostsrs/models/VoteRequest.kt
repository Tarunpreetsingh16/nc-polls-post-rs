package com.neighborhood.connect.pollspostsrs.models

data class VoteRequest (
    val postId: Int,
    val pollOptionId: Int,
    val anonymous: Boolean
)
