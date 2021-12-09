package pl.tinylinden.ffc.core.ports

import pl.tinylinden.ffc.core.model.Rating

interface RatingManager {

    fun setOrReplaceRating(authorFingerprint: String, rating: Rating)
}