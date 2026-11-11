package com.example.externalcards.add_card.domain.validators

class MastercardCardNumberValidator : CardNumberValidator {

    override val prefixRanges: List<IntRange> = listOf(51..55, 2221..2720)

    override val validLengths: Set<Int> = setOf(16)
}
