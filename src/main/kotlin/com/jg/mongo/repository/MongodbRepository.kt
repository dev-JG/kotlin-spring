package com.jg.mongo.repository

import com.jg.mongo.model.Review
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MongodbRepository : MongoRepository<Review, String> {

}