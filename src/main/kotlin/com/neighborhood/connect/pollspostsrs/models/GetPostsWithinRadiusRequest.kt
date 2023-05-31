package com.neighborhood.connect.pollspostsrs.models

data class GetPostsWithinRadiusRequest (
    val radius: Int,
    val latitude: Double,
    val longitude: Double
)
