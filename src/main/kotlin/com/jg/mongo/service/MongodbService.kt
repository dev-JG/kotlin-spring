package com.jg.mongo.service

import com.jg.mongo.model.Review
import com.jg.mongo.repository.MongodbRepository
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service

@Service
class MongodbService (
    var mongodbRepository: MongodbRepository,
    var mongoTemplate: MongoTemplate
) {
    private val log = LoggerFactory.getLogger(javaClass)
    val collection: String = "review"

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

    fun getReviewFindStatus(status: String): MutableList<Review> {
        log.info("##### getReviewFindStatus")

        val query = Query()
        val criteria = Criteria()
        criteria.and("status").isEqualTo(status)
            .and("reviewNo").gte(600)
        query.addCriteria(criteria)

        return mongoTemplate.find(query, Review::class.java, collection)

//        return mongoTemplate.find(
//            Query().addCriteria(Criteria
//                .where("status")
//                .isEqualTo(status)
//            ),
//            Review::class.java,
//            collection
//        )
    }
}