package pl.tinylinden.ffc.adapters.rest.v1

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import pl.tinylinden.ffc.adapters.rest.v1.model.ShowingDto
import pl.tinylinden.ffc.adapters.rest.v1.model.ShowingsForDateDto
import pl.tinylinden.ffc.core.model.Showing
import pl.tinylinden.ffc.core.model.ShowingsForDate

@Mapper(componentModel = "spring")
interface ApiMapper {

    fun toDto(source: ShowingsForDate): ShowingsForDateDto

    fun fromDto(source: ShowingsForDateDto): ShowingsForDate

    @Mappings(
        Mapping(target = "movieId", source = "movie.imdbId"),
        Mapping(target = "startAt", source = "startAt", dateFormat = "HH:mm")
    )
    fun toDto(source: Showing): ShowingDto

    @InheritInverseConfiguration
    fun fromDto(source: ShowingDto): Showing
}