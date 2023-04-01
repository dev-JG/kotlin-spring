package com.jg.redis.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jg.redis.model.Member
import com.jg.redis.repository.RedisRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit


@Service
class RedisService (
    var redisRepository: RedisRepository
){
    private val log = LoggerFactory.getLogger(javaClass)
    val PRE_FIX = "member:"
    val objectMapper = ObjectMapper().registerKotlinModule()
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

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

    fun addMemberTemplate(member: Member): String {
        val valueOperations = redisTemplate.opsForValue()
        var json = objectMapper.writeValueAsString(member)
        valueOperations.set(PRE_FIX + member.id, json, 1L, TimeUnit.MINUTES)
        log.info("##### addMemberTemplate = {}", json)

        return json
    }

    fun getMemberTemplate(id: String): Member? {
        val valueOperations = redisTemplate.opsForValue()
        val json = valueOperations.get(PRE_FIX + id)
        json?.let {
            val member = objectMapper.readValue<Member>(it)
            log.info("##### getMemberTemplate = {}", member)

            return member
        }

        return null
    }
}