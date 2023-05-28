package com.neighborhood.connect.pollspostsrs.service.db

import com.neighborhood.connect.pollspostsrs.entities.Post
import com.neighborhood.connect.pollspostsrs.repositories.IPostRepository
import org.springframework.stereotype.Service

@Service
class PostRepositoryServiceImpl(private val postRepository: IPostRepository) :
    IPostRepositoryService {
    override fun createPost(post: Post): Post {
        return postRepository.save(post)
    }
}