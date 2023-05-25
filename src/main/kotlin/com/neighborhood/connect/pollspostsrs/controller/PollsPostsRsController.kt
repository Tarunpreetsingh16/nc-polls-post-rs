package com.neighborhood.connect.pollspostsrs.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/polls")
class PollsPostsRsController: IPollsPostsRsController {
    override fun heartBeat(): String {
        return "Service up and running"
    }
}