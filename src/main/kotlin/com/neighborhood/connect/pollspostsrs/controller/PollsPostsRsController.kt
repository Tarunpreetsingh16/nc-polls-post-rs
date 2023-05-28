package com.neighborhood.connect.pollspostsrs.controller

import com.neighborhood.connect.pollspostsrs.service.PollsPostsRsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/polls")
class PollsPostsRsController(
    private val pollsPostsRsServiceImpl: PollsPostsRsServiceImpl,
): IPollsPostsRsController {
    override fun heartBeat(): String {
        return "Service up and running"
    }

    override fun createPost(): ResponseEntity<Any> {
        return pollsPostsRsServiceImpl.createPost()
    }
}
