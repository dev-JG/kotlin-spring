package com.jg.mongo.controller

import com.jg.mongo.model.Review
import com.jg.mongo.service.MongodbService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/mongo")
class MongoController(
    var mongodbService: MongodbService
) {

    @GetMapping("/add/review")
    fun addReview() : Review {
        var review = Review();

        review.id = Math.random().toString()
        review.reviewNo = (Math.random()*1000).toInt()
        review.status = "AV"

        return mongodbService.addReview(review)
    }

    @GetMapping("/reviews")
    fun getReviews() : MutableList<Review>{
        return mongodbService.getReview()
    }
}