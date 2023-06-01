package com.neighborhood.connect.pollspostsrs.controller

import com.neighborhood.connect.pollspostsrs.models.CreatePostRequest
import com.neighborhood.connect.pollspostsrs.models.GetPostsWithinRadiusRequest
import com.neighborhood.connect.pollspostsrs.models.VoteRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface IPollsPostsRsController {
    @GetMapping("/heartbeat", produces = ["application/json"])
    fun heartBeat(): String

    @PostMapping("", produces = ["application/json"])
    fun createPost(@RequestBody createPostRequest: CreatePostRequest): ResponseEntity<Any>

    @DeleteMapping("/{postId}", produces = ["application/json"])
    fun deletePost(@PathVariable(name = "postId") postId: Int): ResponseEntity<Any>

    @GetMapping("", produces = ["application/json"])
    fun getPosts(): ResponseEntity<Any>

    @GetMapping("/postsWithinRadius", produces = ["application/json"])
    fun getPostsWithinRadius(@RequestBody getPostsWithinRadiusRequest: GetPostsWithinRadiusRequest): ResponseEntity<Any>

    @PostMapping("/vote", produces = ["application/json"])
    fun vote(@RequestBody voteRequest: VoteRequest): ResponseEntity<Any>
}
