package com.neighborhood.connect.pollspostsrs.service

import com.neighborhood.connect.jwtlib.model.CustomUserDetails
import com.neighborhood.connect.pollspostsrs.config.SecurityConfig
import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.entities.Post
import com.neighborhood.connect.pollspostsrs.models.CreatePostRequest
import com.neighborhood.connect.pollspostsrs.service.db.PollOptionRepositoryServiceImpl
import com.neighborhood.connect.pollspostsrs.service.db.PostRepositoryServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class PollsPostsRsServiceImpl(
    private val postRepositoryServiceImpl: PostRepositoryServiceImpl,
    private val pollOptionRepositoryServiceImpl: PollOptionRepositoryServiceImpl
) : IPollsPostsRsService {
    @Transactional
    override fun createPost(createPostRequest: CreatePostRequest): ResponseEntity<Any> {
        kotlin.runCatching {
            val post = createPostRequest.post
            post.userCredentialId = getUserId()

            val savedPost: Post = postRepositoryServiceImpl.createPost(post)

            val pollOptions = linkPostAndPollOptions(createPostRequest.pollOptions, savedPost.id!!)

            pollOptionRepositoryServiceImpl.createOptions(pollOptions)
            return ResponseEntity.ok(null)
        }.getOrElse {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, it.message)
        }
    }

    fun getUserId(): Int? {
        val customerUserDetails = SecurityContextHolder.getContext().authentication.principal as CustomUserDetails
        return  customerUserDetails.getUserId()
    }

    fun linkPostAndPollOptions(pollOptions: List<PollOption>, id: Int): List<PollOption> {
        val pollOptionslocal = pollOptions
        pollOptionslocal.forEach{ pollOption ->
            pollOption.postId = id
        }
        return pollOptionslocal
    }
}
