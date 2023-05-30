package com.neighborhood.connect.pollspostsrs.repositories

import com.neighborhood.connect.pollspostsrs.entities.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface IPostRepository : JpaRepository<Post, Long> {
    @Modifying
    @Query("UPDATE Post post SET post.deleted = true WHERE post.id = :postId")
    fun deletePost(@Param("postId") postId: Int): Int

    fun findByIdAndUserCredentialId(postId: Int, userId: Int): List<Post>

    @Modifying
    @Query("SELECT new com.neighborhood.connect.pollspostsrs.entities.PostPollOptionAndVote(post, pollOption, pollOptionVote) " +
            "FROM Post post " +
            "LEFT JOIN PollOption pollOption " +
            "ON post.id = pollOption.postId " +
            "LEFT JOIN PollOptionVote pollOptionVote " +
            "ON pollOption.id = pollOptionVote.pollOptionId " +
            "WHERE post.userCredentialId = :userId")
    fun getPostsWithOptionsAndVotes(userId: Int): List<Any>
}
