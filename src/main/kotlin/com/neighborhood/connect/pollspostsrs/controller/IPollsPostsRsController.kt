package com.neighborhood.connect.pollspostsrs.controller

import org.springframework.web.bind.annotation.GetMapping

interface IPollsPostsRsController {
    @GetMapping("/heartbeat", produces = ["application/json"])
    fun heartBeat(): String
}