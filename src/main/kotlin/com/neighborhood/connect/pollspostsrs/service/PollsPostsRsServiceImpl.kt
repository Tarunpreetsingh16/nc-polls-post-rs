package com.neighborhood.connect.pollspostsrs.service

import com.neighborhood.connect.jwtlib.model.CustomUserDetails
import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.entities.PollOptionVote
import com.neighborhood.connect.pollspostsrs.entities.Post
import com.neighborhood.connect.pollspostsrs.entities.PostPollOptionAndVote
import com.neighborhood.connect.pollspostsrs.models.*
import com.neighborhood.connect.pollspostsrs.service.db.PollOptionRepositoryServiceImpl
import com.neighborhood.connect.pollspostsrs.service.db.PostRepositoryServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import kotlin.math.*

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

    override fun getPostsWithinRadius(getPostsWithinRadiusRequest: GetPostsWithinRadiusRequest): ResponseEntity<Any> {
        kotlin.runCatching {
            val postsWithPollOptionsAndVotes = postRepositoryServiceImpl.getPostsWithOptionsAndVotes()
            val getPostsResponseWithinRadius = GetPostsResponse(mutableListOf())

            generateResponseForGetPosts(postsWithPollOptionsAndVotes)
                .postsWithPollOptionsAndVotes
                .forEach {
                    if (calculateDistance(
                            getPostsWithinRadiusRequest.latitude,
                            getPostsWithinRadiusRequest.longitude,
                            it.post.latitude!!,
                            it.post.longitude!!,
                        ) <= getPostsWithinRadiusRequest.radius
                    ) {
                        getPostsResponseWithinRadius.postsWithPollOptionsAndVotes.add(it)
                    }
                }

            return ResponseEntity.ok(getPostsResponseWithinRadius)
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

        // go through the data fetched from the database containing posts, polls options and option votes
        postsWithPollOptionsAndVotes.forEach {
            val postWithPollOptionAndVote = it as PostPollOptionAndVote

            val post = postWithPollOptionAndVote.post
            val pollOption = postWithPollOptionAndVote.pollOption
            val pollOptionVote = postWithPollOptionAndVote.pollOptionVote

            //if the post has not been seen yet in the data
            // add the post, add the option and its vote to the maps
            if (!posts.containsKey(post.id!!)) {
                posts[post.id] = post
                // If post was never seen in data, we could not have seen any option and vote too, so create new entry to the map
                pollOptions[post.id] = mutableListOf(pollOption)
                pollOptionVotes[pollOption.id!!] = mutableListOf(pollOptionVote)
            } else {
                //if the post was traversed before means we do not have to create a new entry for post
                // if we have traversed the option before we will find that in votes map as it is always done in the if block above,
                // so we just need to update th vote list
                if (pollOptionVotes.containsKey(pollOption.id!!)) {
                    pollOptionVotes[pollOption.id]?.add(pollOptionVote)
                } else {
                    //if we see a new option for the post, we add option and vote to their respective map
                    pollOptions[post.id]?.add(pollOption)
                    pollOptionVotes[pollOption.id] = mutableListOf(pollOptionVote)
                }
            }
        }

        val getPostsResponse = GetPostsResponse(mutableListOf())


        // posts object contains all the posts fetched from the database
        posts.forEach { post ->

            //add the post to the object as the post will not be repeated
            val postPollOptionsAndVotes = PostPollOptionsAndVotes(post.value, mutableListOf())

            // go through the options linked to the post
            pollOptions[post.key]?.forEach { pollOption ->
                // add the votes, linked to the option, to the object
                postPollOptionsAndVotes.pollOptionAndVotes.add(
                    PollOptionAndVotes(pollOption, pollOptionVotes[pollOption.id]?.toList() ?: listOf())
                )
            }

            //add the post with options and votes to the response object
            getPostsResponse.postsWithPollOptionsAndVotes.add(postPollOptionsAndVotes)
        }
        return getPostsResponse
    }

    fun calculateDistance(sourceLat: Double, sourceLong: Double, targetLat: Double, targetLong: Double): Double {
        val sourceLatRad = Math.toRadians(sourceLat)
        val sourceLongRad = Math.toRadians(sourceLong)
        val targetLatRad = Math.toRadians(targetLat)
        val targetLongRad = Math.toRadians(targetLong)

        val diffLong = targetLongRad - sourceLongRad
        val diffLat = targetLatRad - sourceLatRad

        val a = sin(diffLat / 2).pow(2) + cos(sourceLatRad) * cos(targetLatRad) * sin(diffLong / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return 6371 * c // 6371 being the Earth Radius
    }
}
