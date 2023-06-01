package com.neighborhood.connect.pollspostsrs.service

import com.neighborhood.connect.pollspostsrs.models.CreatePostRequest
import com.neighborhood.connect.pollspostsrs.models.GetPostsWithinRadiusRequest
import com.neighborhood.connect.pollspostsrs.models.VoteRequest
import org.springframework.http.ResponseEntity

interface IPollsPostsRsService {
    fun createPost(createPostRequest: CreatePostRequest): ResponseEntity<Any>
    fun deletePost(postId: Int): ResponseEntity<Any>

    fun getPosts(): ResponseEntity<Any>

    fun getPostsWithinRadius(getPostsWithinRadiusRequest: GetPostsWithinRadiusRequest): ResponseEntity<Any>

    fun vote(voteRequest: VoteRequest): ResponseEntity<Any>
}
