package pl.tinylinden.ffc.adapters.mongo

import org.springframework.stereotype.Component
import pl.tinylinden.ffc.core.model.ShowingsForDate
import pl.tinylinden.ffc.core.ports.ShowingsManager

@Component
class MongoShowingsManager(
    private val repository: MongoShowingsRepository,
    private val mapper: MongoMapper
) : ShowingsManager {

    override fun setOrReplaceShowings(showings: List<ShowingsForDate>) {
        val documents = showings.map { mapper.toDocument(it) }
        repository.saveAll(documents)
    }
}
