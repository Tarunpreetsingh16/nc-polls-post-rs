package com.neighborhood.connect.pollspostsrs.service.db

import com.neighborhood.connect.pollspostsrs.entities.Post

interface IPostRepositoryService {
    fun createPost(post: Post): Post
}
