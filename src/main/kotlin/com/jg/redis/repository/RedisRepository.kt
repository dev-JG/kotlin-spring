package com.jg.redis.repository

import com.jg.redis.model.Member
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RedisRepository : CrudRepository<Member, String> {

}