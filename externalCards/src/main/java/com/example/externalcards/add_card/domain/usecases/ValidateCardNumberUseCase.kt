package com.example.externalcards.add_card.domain.usecases

import com.example.base.BaseResult
import com.example.base.BaseUseCase
import com.example.externalcards.add_card.domain.models.CardType
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ValidateCardNumberUseCase @Inject constructor() : BaseUseCase<String, CardType>() {

    override suspend fun invoke(
        input: String
    ): BaseResult<CardType> = coroutineScope {
        val cardType = CardType.all.firstOrNull {
            it.validator.matchesPrefix(input)
        }

        if (cardType?.validator?.isValid(input) == true) {
            BaseResult.BaseSuccess(cardType)
        } else {
            BaseResult.BaseFailure
        }
    }
}
