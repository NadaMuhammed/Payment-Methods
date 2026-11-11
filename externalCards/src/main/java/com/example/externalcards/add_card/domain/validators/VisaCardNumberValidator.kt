package com.example.externalcards.add_card.domain.validators

class VisaCardNumberValidator : CardNumberValidator {

    override val prefixRanges: List<IntRange> = listOf(4..4)

    override val validLengths: Set<Int> = setOf(13, 16, 19)
}
