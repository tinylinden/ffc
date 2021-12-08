package pl.tinylinden.ffc.adapters.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.math.BigDecimal
import java.time.LocalDate

@Document(collection = "showings_for_date")
@TypeAlias("ShowingsForDate")
data class MongoShowingsForDate(
    @Id @MongoId val date: LocalDate,
    val showings: List<MongoShowing>
)

data class MongoShowing(
    val movieId: String,
    val startAt: String,
    val ticketPrice: MongoPrice
)

data class MongoPrice(
    val currency: String,
    val amount: BigDecimal
)
