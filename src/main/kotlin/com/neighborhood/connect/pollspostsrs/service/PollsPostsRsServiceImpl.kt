package com.neighborhood.connect.pollspostsrs.service

import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.entities.Post
import com.neighborhood.connect.pollspostsrs.models.CreatePostRequest
import com.neighborhood.connect.pollspostsrs.service.db.PollOptionRepositoryServiceImpl
import com.neighborhood.connect.pollspostsrs.service.db.PostRepositoryServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
            val savedPost: Post = postRepositoryServiceImpl.createPost(createPostRequest.post)
            val pollOptions: List<PollOption> = createPostRequest.pollOptions
            pollOptions.forEach{pollOption ->
                pollOption.postId = savedPost.id
            }
            pollOptionRepositoryServiceImpl.createOptions(pollOptions)
            return ResponseEntity.ok(null)
        }.getOrElse {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, it.message)
        }
    }
}
