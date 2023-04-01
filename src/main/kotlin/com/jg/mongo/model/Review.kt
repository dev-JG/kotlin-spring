package com.jg.mongo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable
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
) : Serializable {
    companion object {
        fun getTempDocument() : Review {
            val review = Review();
            review.id = (Math.random() * 1000 % 100).toInt().toString();
            review.reviewNo = (Math.random() * 1000).toInt()
            review.status = "AV"
            return review;
        }
    }
}