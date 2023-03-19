package com.jg.mongo.controller

import com.jg.mongo.model.Review
import com.jg.mongo.service.MongodbService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/mongo")
class MongoController(
    var mongodbService: MongodbService
) {

    @GetMapping("/add/review")
    fun addReview() : Review {
        return mongodbService.addReview(Review.getTempDocument())
    }

    @GetMapping("/reviews")
    fun getReviews() : MutableList<Review>{
        return mongodbService.getReviews()
    }

    @GetMapping("/review/{id}")
    fun getReview(@PathVariable id: String) : Review{
        return mongodbService.getReview(id)
    }

    @GetMapping("/review/status/{status}")
    fun getReviewFindStatus(@PathVariable status: String) : MutableList<Review> {
        return mongodbService.getReviewFindStatus(status)
    }
}