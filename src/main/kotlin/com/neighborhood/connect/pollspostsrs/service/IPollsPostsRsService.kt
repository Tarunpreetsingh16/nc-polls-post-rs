package com.neighborhood.connect.pollspostsrs.service

import org.springframework.http.ResponseEntity

interface IPollsPostsRsService {
    fun createPost(): ResponseEntity<Any>
}