package com.neighborhood.connect.pollspostsrs.repositories

import com.neighborhood.connect.pollspostsrs.entities.PollOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPollOptionRepository : JpaRepository<PollOption, Long>
