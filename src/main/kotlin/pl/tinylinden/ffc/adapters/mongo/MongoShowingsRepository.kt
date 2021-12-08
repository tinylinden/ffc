package pl.tinylinden.ffc.adapters.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDate

interface MongoShowingsRepository : MongoRepository<MongoShowingsForDate, LocalDate> {

    @Query("{date: {\$gte: ?0, \$lt: ?1}}")
    fun findShowings(fromInclusive: LocalDate, toExclusive: LocalDate): List<MongoShowingsForDate>
}