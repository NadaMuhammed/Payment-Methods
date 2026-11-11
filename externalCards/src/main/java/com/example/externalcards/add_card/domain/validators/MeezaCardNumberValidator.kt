package com.example.externalcards.add_card.domain.validators

class MeezaCardNumberValidator : CardNumberValidator {

    override val prefixRanges: List<IntRange> = listOf(5078..5078)

    override val validLengths: Set<Int> = setOf(16)
}
