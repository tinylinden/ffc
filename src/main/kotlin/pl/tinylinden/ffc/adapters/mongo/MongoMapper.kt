package pl.tinylinden.ffc.adapters.mongo

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import pl.tinylinden.ffc.core.model.Rating
import pl.tinylinden.ffc.core.model.Showing
import pl.tinylinden.ffc.core.model.ShowingsForDate

@Mapper(componentModel = "spring")
interface MongoMapper {

    fun toDocument(core: ShowingsForDate): MongoShowingsForDate

    fun fromDocument(document: MongoShowingsForDate): ShowingsForDate

    @Mappings(
        Mapping(target = "movieId", source = "movie.imdbId")
    )
    fun toDocument(core: Showing): MongoShowing

    @InheritInverseConfiguration
    fun fromDocument(document: MongoShowing): Showing

    @Mappings(
        Mapping(target = "id", expression = "java(new MongoRatingId(authorFingerprint, core.getMovie().getImdbId()))"),
        Mapping(target = "rating", source = "core.rating")
    )
    fun toDocument(authorFingerprint: String, core: Rating): MongoRating
}