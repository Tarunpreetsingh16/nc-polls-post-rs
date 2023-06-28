package com.neighborhood.connect.pollspostsrs.controller

import com.neighborhood.connect.errorhandlerlib.customExceptions.FieldsValidationException
import com.neighborhood.connect.pollspostsrs.models.CreatePostRequest
import com.neighborhood.connect.pollspostsrs.models.GetPostsWithinRadiusRequest
import com.neighborhood.connect.pollspostsrs.models.VoteRequest
import com.neighborhood.connect.pollspostsrs.service.PollsPostsRsServiceImpl
import com.neighborhood.connect.pollspostsrs.validators.CreatePostRequestValidator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/poll")
class PollsPostsRsController(
    private val pollsPostsRsServiceImpl: PollsPostsRsServiceImpl,
): IPollsPostsRsController {
    override fun heartBeat(): String {
        return "Service up and running"
    }

    override fun createPost(createPostRequest: CreatePostRequest): ResponseEntity<Any> {
        val createPostRequestValidationResult = CreatePostRequestValidator().validate(createPostRequest = createPostRequest)
        if (createPostRequestValidationResult.errors.size > 0) {
            throw FieldsValidationException(createPostRequestValidationResult)
        }
        return pollsPostsRsServiceImpl.createPost(createPostRequest)
    }

    override fun deletePost(postId: Int): ResponseEntity<Any> {
        return pollsPostsRsServiceImpl.deletePost(postId)
    }

    override fun getPosts(): ResponseEntity<Any> {
        return pollsPostsRsServiceImpl.getPosts()
    }

    override fun getPostsWithinRadius(getPostsWithinRadiusRequest: GetPostsWithinRadiusRequest): ResponseEntity<Any> {
        return pollsPostsRsServiceImpl.getPostsWithinRadius(getPostsWithinRadiusRequest)
    }

    override fun vote(voteRequest: VoteRequest): ResponseEntity<Any> {
        return pollsPostsRsServiceImpl.vote(voteRequest)
    }
}
