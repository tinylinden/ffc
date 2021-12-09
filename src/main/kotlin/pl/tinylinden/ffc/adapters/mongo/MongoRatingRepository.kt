package pl.tinylinden.ffc.adapters.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface MongoRatingRepository : MongoRepository<MongoRating, MongoRatingId> {

    @Query("{\"_id.movieId\": ?0}")
    fun findRatings(movieId: String): List<MongoRating>
}