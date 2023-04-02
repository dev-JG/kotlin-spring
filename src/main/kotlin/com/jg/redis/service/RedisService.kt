package com.jg.redis.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jg.redis.model.Member
import com.jg.redis.model.User
import com.jg.redis.repository.RedisRepository
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.Random
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


@Service
class RedisService (
    var redisRepository: RedisRepository,
    var redisTemplate: RedisTemplate<String, String>
){
    private val log = LoggerFactory.getLogger(javaClass)
    val objectMapper = ObjectMapper().registerKotlinModule()
    val PRE_FIX_USER = "user:"

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

    fun addUser(user: User): String {
        val valueOperations = redisTemplate.opsForValue()
        val jsonData  = objectMapper.writeValueAsString(user)

        valueOperations.set(PRE_FIX_USER + user.id, jsonData , 60L, TimeUnit.MINUTES)
        log.info("##### addMemberTemplate = {}", jsonData )

        return jsonData
    }

    fun getUser(id: String): User? {
        val jsonData = redisTemplate.opsForValue().get(PRE_FIX_USER + id)
        val expireTime = redisTemplate.getExpire(PRE_FIX_USER + id)

        jsonData?.let {
            val user = objectMapper.readValue<User>(it)
            CompletableFuture.runAsync {
                cacheStampede(PRE_FIX_USER + id, expireTime)
            }
            log.info("##### getMemberTemplate expireTime = {}, member = {}", expireTime, user)

            return user
        }

        return null
    }

    /**
     * 캐시스탬피드 문제 해결
     * ttl 60분 남았을 경우 실행, 아래로직은 랜덤값이 0,1일 경우 갱신됨
     * 0..9 값이 랜덤으로 나오므로 확률은 20%
     * */
    fun cacheStampede(key: String, ttl: Long) {
        if (ttl <= 60 * 60L) {
            val random = Random().nextInt(10)
            val expiryGapMs = 2000;

            if (ttl - (expiryGapMs * random) > 0) {
                redisTemplate.expire(key, 60L, TimeUnit.MINUTES)
            }
        }
    }
}