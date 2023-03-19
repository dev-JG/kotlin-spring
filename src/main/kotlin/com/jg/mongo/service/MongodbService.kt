package com.jg.mongo.service

import com.jg.mongo.model.Review
import com.jg.mongo.repository.MongodbRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MongodbService (
    var mongodbRepository: MongodbRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun addReview(mongoDocument: Review) : Review {
        log.info("##### addReview")
        return mongodbRepository.save(mongoDocument)
    }

    fun getReviews() : MutableList<Review> {
        log.info("##### getReviews")
        return mongodbRepository.findAll()
    }

    fun getReview(id: String) : Review {
        log.info("##### getReview")
        return mongodbRepository.findById(id).get()
    }
}