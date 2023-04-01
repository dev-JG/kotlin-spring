package com.jg.redis.service

import com.jg.redis.model.Member
import com.jg.redis.repository.RedisRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RedisService (
    var redisRepository: RedisRepository
){
    private val log = LoggerFactory.getLogger(javaClass)

    fun addMember(member: Member) : Member {
        redisRepository.save(member)
        return member
    }

    fun getMember(id: String): Member {
        val member = redisRepository.findById(id)
        log.info("##### getMember = {}", member)
        return member.get()
    }

    fun getMembers() : MutableList<Member> {
        val members = redisRepository.findAll()
        log.info("##### getMembers = {}", members)
        return members.toMutableList()
    }
}