package pl.tinylinden.ffc.core.model

data class AverageRating(
    val movie: MovieId,
    val votesCount: Int,
    val averageRating: Double
)
