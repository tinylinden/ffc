package pl.tinylinden.ffc.core.model

import java.math.BigDecimal

data class Price(
    val currency: String,
    val amount: BigDecimal
)
