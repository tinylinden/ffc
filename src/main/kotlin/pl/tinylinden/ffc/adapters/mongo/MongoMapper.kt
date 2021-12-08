package pl.tinylinden.ffc.adapters.mongo

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import pl.tinylinden.ffc.core.model.Showing
import pl.tinylinden.ffc.core.model.ShowingsForDate

@Mapper(componentModel = "spring")
interface MongoMapper {

    fun toDocument(source: ShowingsForDate): MongoShowingsForDate

    fun fromDocument(source: MongoShowingsForDate): ShowingsForDate

    @Mappings(
        Mapping(target = "movieId", source = "movie.imdbId")
    )
    fun toDocument(source: Showing): MongoShowing

    @InheritInverseConfiguration
    fun fromDocument(source: MongoShowing): Showing
}