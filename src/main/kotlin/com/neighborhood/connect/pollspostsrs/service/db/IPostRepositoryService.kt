package com.neighborhood.connect.pollspostsrs.service.db

import com.neighborhood.connect.pollspostsrs.entities.Post

interface IPostRepositoryService {
    fun createPost(post: Post): Post
    fun deletePost(postId: Int): Int

    fun existsById(postId: Int): Boolean

    fun isUserOwnerOfThePost(userId: Int, postId: Int): Boolean

    fun getPostsWithOptionsAndVotes(userId: Int): List<Any>
}
