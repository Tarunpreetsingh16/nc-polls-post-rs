package com.neighborhood.connect.pollspostsrs.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException

class PollsPostsRsServiceImpl: IPollsPostsRsService {
    override fun createPost(): ResponseEntity<Any> {
        kotlin.runCatching {
            return ResponseEntity.ok(null)
        }.getOrElse {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, it.message)

        }
    }
}
