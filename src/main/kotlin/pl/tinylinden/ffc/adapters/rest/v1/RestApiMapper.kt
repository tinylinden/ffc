package pl.tinylinden.ffc.adapters.rest.v1

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import pl.tinylinden.ffc.adapters.rest.v1.model.*
import pl.tinylinden.ffc.core.model.*

@Mapper(componentModel = "spring")
interface RestApiMapper {

    fun toDto(core: ShowingsForDate): ShowingsForDateDto

    fun fromDto(dto: ShowingsForDateDto): ShowingsForDate

    @Mappings(
        Mapping(target = "movieId", source = "movie.imdbId"),
        Mapping(target = "startAt", source = "startAt", dateFormat = "HH:mm")
    )
    fun toDto(core: Showing): ShowingDto

    @InheritInverseConfiguration
    fun fromDto(dto: ShowingDto): Showing

    fun toDto(core: MovieDetails): MovieDetailsDto

    @Mappings(
        Mapping(target = "movie.imdbId", source = "id")
    )
    fun fromDto(id: String, dto: RatingDto): Rating

    fun toDto(core: AverageRating): AverageRatingDto
}