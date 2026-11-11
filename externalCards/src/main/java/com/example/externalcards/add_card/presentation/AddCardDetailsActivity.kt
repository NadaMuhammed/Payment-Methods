package com.example.externalcards.add_card.presentation

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.base.BaseActivity
import com.example.externalcards.R
import com.example.externalcards.add_card.domain.models.CardType
import com.example.externalcards.add_card.presentation.watchers.CardNumberTextWatcher
import com.example.externalcards.databinding.ActivityAddCardDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCardDetailsActivity : BaseActivity<ActivityAddCardDetailsBinding>(
    ActivityAddCardDetailsBinding::inflate
) {

    private val viewModel: AddCardDetailsViewModel by viewModels()

    override fun ActivityAddCardDetailsBinding.initializeUI() {
        etCardNumber.addTextChangedListener(
            CardNumberTextWatcher(
                tvCardMask.text.toString()
            ) { cardNumber, cardMask ->
                viewModel.setEvent(
                    AddCardDetailsEvent.ValidateCardNumber(
                        cardNumber = cardNumber,
                        cardMask = cardMask.toString()
                    )
                )
            }
        )
    }

    override fun handleState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    with(binding) {
                        tvCardMask.text = state.cardMask ?: getString(
                            R.string.card_number_mask
                        )

                        ivCard.setImageResource(
                            when (state.cardType) {
                                CardType.Mastercard -> R.drawable.ic_card_mastercard
                                CardType.Meeza -> R.drawable.ic_card_meeza
                                CardType.Visa -> R.drawable.ic_card_visa
                                null -> R.drawable.ic_card_generic
                            }
                        )
                    }
                }
            }
        }
    }
}
