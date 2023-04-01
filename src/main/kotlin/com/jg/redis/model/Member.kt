package com.jg.redis.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.io.Serializable

@RedisHash("member")
data class Member (
    @Id
    var id : String? = null,
    var name : String? = null,
    var age: Int? = null,
    @TimeToLive
    val ttl : Long = 60
) : Serializable {

    companion object {
        fun getTempMember() : Member {
            val member = Member();
            member.id = (Math.random() * 1000 % 100).toInt().toString();
            member.name = "name" + (Math.random() * 1000).toInt().toString()
            member.age = (Math.random() * 100).toInt()
            return member;
        }
    }
}