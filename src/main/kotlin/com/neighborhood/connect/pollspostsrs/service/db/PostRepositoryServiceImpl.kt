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

    override fun deletePost(postId: Int): Int {
        return postRepository.deletePost(postId)
    }

    override fun existsById(postId: Int): Boolean {
        return postRepository.existsById(postId.toLong())
    }

    override fun isUserOwnerOfThePost(userId: Int, postId: Int): Boolean {
        return postRepository.findByIdAndUserCredentialId(postId, userId).isNotEmpty()
    }

    override fun getPostsWithOptionsAndVotes(userId: Int?, postId: Int?): List<Any> {
        return postRepository.getPostsWithOptionsAndVotes(userId, postId)
    }
}
