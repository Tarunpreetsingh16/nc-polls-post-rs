package com.neighborhood.connect.pollspostsrs.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

interface IPollsPostsRsController {
    @GetMapping("/heartbeat", produces = ["application/json"])
    fun heartBeat(): String

    @PostMapping("/post", produces = ["application/json"])
    fun createPost(): ResponseEntity<Any>
}
