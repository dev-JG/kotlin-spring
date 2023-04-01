package com.jg.redis.controller

import com.jg.redis.model.Member
import com.jg.redis.service.RedisService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/redis")
class RedisController(
    var redisService: RedisService
) {

    @GetMapping("/add/member")
    fun addMember() : Member {
        return redisService.addMember(Member.getTempMember())
    }

    @GetMapping("/member/{id}")
    fun getMember(@PathVariable(value = "id") id: String) : Member {
        return redisService.getMember(id)
    }

    @GetMapping("/members")
    fun getMembers() : MutableList<Member> {
        return redisService.getMembers()
    }
}