package com.example.externalcards.add_card.presentation

import com.example.base.BaseContract
import com.example.externalcards.add_card.domain.models.CardType

sealed interface AddCardDetailsEvent : BaseContract.Event {

    data class ValidateCardNumber(
        val cardNumber: String,
        val cardMask: String
    ) : AddCardDetailsEvent
}

sealed interface AddCardDetailsSideEffect : BaseContract.SideEffect {

}

data class AddCardDetailsState(
    val cardNumber: String? = null,
    val cardMask: String? = null,
    val cardType: CardType? = null
) : BaseContract.State
