package com.neighborhood.connect.pollspostsrs.repositories

import com.neighborhood.connect.pollspostsrs.entities.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPostRepository : JpaRepository<Post, Long> {}
