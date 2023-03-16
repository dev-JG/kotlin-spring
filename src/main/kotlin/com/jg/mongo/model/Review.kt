package com.jg.mongo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "review")
data class Review (

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: String? = null,

    @Field(name="createDate")
    var createDate: LocalDateTime = LocalDateTime.now(),

    @Field(name="reviewNo")
    var reviewNo: Int = 0,

    @Field(name="status")
    var status: String? = null
)