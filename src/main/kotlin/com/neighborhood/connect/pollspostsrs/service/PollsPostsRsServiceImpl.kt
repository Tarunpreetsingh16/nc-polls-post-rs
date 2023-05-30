package com.neighborhood.connect.pollspostsrs.service

import com.neighborhood.connect.jwtlib.model.CustomUserDetails
import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.entities.PollOptionVote
import com.neighborhood.connect.pollspostsrs.entities.Post
import com.neighborhood.connect.pollspostsrs.entities.PostPollOptionAndVote
import com.neighborhood.connect.pollspostsrs.models.CreatePostRequest
import com.neighborhood.connect.pollspostsrs.models.GetPostsResponse
import com.neighborhood.connect.pollspostsrs.models.PollOptionAndVotes
import com.neighborhood.connect.pollspostsrs.models.PostPollOptionsAndVotes
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

    @Transactional
    override fun deletePost(postId: Int): ResponseEntity<Any> {

        if (!postRepositoryServiceImpl.existsById(postId)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Post does not exist")
        }

        if (!postRepositoryServiceImpl.isUserOwnerOfThePost(getUserId() ?: -1, postId)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Account eligibility mismatch")
        }

        kotlin.runCatching {
            val rowsAffected = postRepositoryServiceImpl.deletePost(postId)
            return ResponseEntity.ok("{\"items_updated\": ${rowsAffected}}")
        }.getOrElse {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, it.message)
        }
    }

    override fun getPosts(): ResponseEntity<Any> {
        kotlin.runCatching {
            val postsWithPollOptionsAndVotes = postRepositoryServiceImpl.getPostsWithOptionsAndVotes(getUserId() ?: -1)

            return ResponseEntity.ok(generateResponseForGetPosts(postsWithPollOptionsAndVotes))
        }.getOrElse {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, it.message)
        }
    }

    fun getUserId(): Int? {
        val customerUserDetails = SecurityContextHolder.getContext().authentication.principal as CustomUserDetails
        return customerUserDetails.getUserId()
    }

    fun linkPostAndPollOptions(pollOptions: List<PollOption>, id: Int): List<PollOption> {
        val pollOptionslocal = pollOptions
        pollOptionslocal.forEach { pollOption ->
            pollOption.postId = id
        }
        return pollOptionslocal
    }

    fun generateResponseForGetPosts(postsWithPollOptionsAndVotes: List<Any>): GetPostsResponse {
        val posts = HashMap<Int, Post>()
        val pollOptions = HashMap<Int, MutableList<PollOption>>()
        val pollOptionVotes = HashMap<Int, MutableList<PollOptionVote?>>()
        postsWithPollOptionsAndVotes.forEach {
            val postWithPollOptionAndVote = it as PostPollOptionAndVote
            val post = postWithPollOptionAndVote.post
            val pollOption = postWithPollOptionAndVote.pollOption
            val pollOptionVote = postWithPollOptionAndVote.pollOptionVote
            if (!posts.containsKey(post.id!!)) {
                posts[post.id] = post
                pollOptions[post.id] = mutableListOf(pollOption)
                pollOptionVotes[pollOption.id!!] = mutableListOf(pollOptionVote)
            } else {
                if (pollOptionVotes.containsKey(pollOption.id!!)) {
                    pollOptionVotes[pollOption.id]?.add(pollOptionVote)
                } else {
                    pollOptions[post.id]?.add(pollOption)
                    pollOptionVotes[pollOption.id] = mutableListOf(pollOptionVote)
                }
            }
        }

        val getPostsResponse = GetPostsResponse(mutableListOf())

        posts.forEach { post ->

            val postPollOptionsAndVotes = PostPollOptionsAndVotes(post.value, mutableListOf())

            pollOptions[post.key]?.forEach { pollOption ->
                postPollOptionsAndVotes.pollOptionAndVotes.add(
                    PollOptionAndVotes(pollOption, pollOptionVotes[pollOption.id]?.toList() ?: listOf())
                )
            }
            getPostsResponse.postsWithPollOptionsAndVotes.add(postPollOptionsAndVotes)
        }
        return  getPostsResponse
    }
}
