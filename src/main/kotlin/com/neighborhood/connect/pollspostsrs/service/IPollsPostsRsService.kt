package com.neighborhood.connect.pollspostsrs.service

import com.neighborhood.connect.pollspostsrs.models.CreatePostRequest
import org.springframework.http.ResponseEntity

interface IPollsPostsRsService {
    fun createPost(createPostRequest: CreatePostRequest): ResponseEntity<Any>
    fun deletePost(postId: Int): ResponseEntity<Any>
}
