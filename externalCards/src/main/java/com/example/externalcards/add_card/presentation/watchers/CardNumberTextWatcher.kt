package com.example.externalcards.add_card.presentation.watchers

import android.graphics.Color
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import androidx.core.text.buildSpannedString
import androidx.core.text.color

class CardNumberTextWatcher(
    private val cardMask: String,
    private val onCardNumberChanged: (cardNumber: String, cardMask: CharSequence) -> Unit
) : TextWatcher {

    private val maxDigits = cardMask.count(Char::isDigit)

    private var isFormatting = false

    override fun afterTextChanged(editable: Editable?) {
        if (editable == null || isFormatting) return

        val digits = editable.filter(Char::isDigit).take(maxDigits).toString()
        val formatted = format(digits)

        if (formatted != editable.toString()) {
            val cursor = Selection.getSelectionEnd(editable).coerceIn(0, editable.length)
            val digitsBeforeCursor = editable.subSequence(0, cursor).count(Char::isDigit)

            isFormatting = true
            editable.replace(0, editable.length, formatted)
            isFormatting = false

            Selection.setSelection(
                editable,
                cursorAfterDigits(
                    formatted,
                    digitsBeforeCursor
                )
            )
        }

        onCardNumberChanged(digits, buildMask(formatted))
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // no-op
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // no-op
    }

    private fun format(digits: String): String = buildString {
        var index = 0
        for (maskChar in cardMask) {
            if (index == digits.length) break
            if (maskChar.isDigit()) append(digits[index++]) else append(maskChar)
        }
    }

    private fun buildMask(formatted: String): CharSequence = buildSpannedString {
        color(Color.TRANSPARENT) { append(formatted) }
        append(cardMask.substring(formatted.length))
    }

    private fun cursorAfterDigits(text: String, digitCount: Int): Int {
        if (digitCount == 0) return 0
        var seen = 0
        text.forEachIndexed { index, char ->
            if (char.isDigit() && ++seen == digitCount) return index + 1
        }
        return text.length
    }
}
