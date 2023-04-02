package com.jg.redis.model

import java.io.Serializable

data class User (
    var id : String? = null,
    var name : String? = null,
    var age: Int? = null
) : Serializable {

    companion object {
        fun getTempUser() : User {
            val user = User();
            user.id = (Math.random() * 1000 % 100).toInt().toString();
            user.name = "name" + (Math.random() * 1000).toInt().toString()
            user.age = (Math.random() * 100).toInt()
            return user;
        }
    }
}