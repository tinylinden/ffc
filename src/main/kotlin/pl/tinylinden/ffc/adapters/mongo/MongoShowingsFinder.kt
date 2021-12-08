package pl.tinylinden.ffc.adapters.mongo

import org.springframework.stereotype.Component
import pl.tinylinden.ffc.core.model.ShowingsForDate
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import java.time.LocalDate

@Component
class MongoShowingsFinder(
    private val repository: MongoShowingsRepository,
    private val mapper: MongoMapper
) : ShowingsFinder {

    override fun findShowings(from: LocalDate, to: LocalDate): List<ShowingsForDate> =
        repository.findShowings(from, to.plusDays(1))
            .map { mapper.fromDocument(it) }
}
