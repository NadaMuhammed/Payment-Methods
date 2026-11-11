package com.example.externalcards.add_card.presentation

import com.example.base.BaseViewModel
import com.example.externalcards.add_card.domain.usecases.ValidateCardNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class AddCardDetailsViewModel @Inject constructor(
    private val validateCardNumberUseCase: ValidateCardNumberUseCase
) : BaseViewModel<AddCardDetailsState, AddCardDetailsSideEffect, AddCardDetailsEvent>(
    AddCardDetailsState()
) {

    private var cardNumberJob: Job? = null

    override fun setEvent(newEvent: AddCardDetailsEvent) {
        when (newEvent) {
            is AddCardDetailsEvent.ValidateCardNumber -> {
                processCardNumber(
                    cardNumber = newEvent.cardNumber,
                    cardMask = newEvent.cardMask
                )
            }
        }
    }

    private fun processCardNumber(
        cardNumber: String,
        cardMask: String
    ) {
        updateState {
            it.copy(
                cardNumber = cardNumber,
                cardMask = cardMask
            )
        }

        cardNumberJob?.cancel()
        cardNumberJob = executeUseCase(
            useCase = {
                validateCardNumberUseCase.invoke(cardNumber)
            },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        cardType = result
                    )
                }
            },
            onError = {
                updateState {
                    it.copy(
                        cardType = null
                    )
                }
            }
        )
    }
}