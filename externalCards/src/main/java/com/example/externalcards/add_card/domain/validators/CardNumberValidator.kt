package com.example.externalcards.add_card.domain.validators

interface CardNumberValidator {

    val prefixRanges: List<IntRange>

    val validLengths: Set<Int>

    fun matchesPrefix(
        cardNumber: String
    ): Boolean = prefixRanges.any { range ->
        val prefixLength = range.first.toString().length
        cardNumber.length >= prefixLength && cardNumber.take(prefixLength).toInt() in range
    }

    fun isValid(
        cardNumber: String
    ): Boolean = cardNumber.all(Char::isDigit) &&
            matchesPrefix(cardNumber) &&
            cardNumber.length in validLengths
}
