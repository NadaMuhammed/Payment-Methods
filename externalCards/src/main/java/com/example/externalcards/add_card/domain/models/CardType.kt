package com.example.externalcards.add_card.domain.models

import com.example.externalcards.R
import com.example.externalcards.add_card.domain.validators.CardNumberValidator
import com.example.externalcards.add_card.domain.validators.MastercardCardNumberValidator
import com.example.externalcards.add_card.domain.validators.MeezaCardNumberValidator
import com.example.externalcards.add_card.domain.validators.VisaCardNumberValidator

sealed interface CardType {

    val icon: Int

    val validator: CardNumberValidator

    data object Visa : CardType {
        override val icon: Int = R.drawable.ic_card_visa
        override val validator: CardNumberValidator = VisaCardNumberValidator()
    }

    data object Mastercard : CardType {
        override val icon: Int = R.drawable.ic_card_mastercard
        override val validator: CardNumberValidator = MastercardCardNumberValidator()
    }

    data object Meeza : CardType {
        override val icon: Int = R.drawable.ic_card_meeza
        override val validator: CardNumberValidator = MeezaCardNumberValidator()
    }

    companion object {
        val all: List<CardType> = listOf(Visa, Mastercard, Meeza)
    }
}
