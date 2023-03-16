package com.jg.mongo.service

import com.jg.mongo.model.Review
import com.jg.mongo.repository.MongodbRepository
import org.springframework.stereotype.Service

@Service
class MongodbService (
    var mongodbRepository: MongodbRepository
) {

    fun addReview(mongoDocument: Review) : Review {
        return mongodbRepository.save(mongoDocument)
    }

    fun getReview() : MutableList<Review>{
        return mongodbRepository.findAll()
    }
}