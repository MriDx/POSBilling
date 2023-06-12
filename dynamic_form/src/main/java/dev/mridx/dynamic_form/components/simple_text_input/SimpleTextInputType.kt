package dev.mridx.dynamic_form.components.simple_text_input

import android.text.InputType

object SimpleTextInputType {

    const val NUMBER = InputType.TYPE_CLASS_NUMBER

    const val NUMBER_SIGNED = NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED

    const val NUMBER_DECIMAL = NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL

    const val TEXT = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_FLAG_AUTO_CORRECT

    const val TEXT_MULTILINE = TEXT + InputType.TYPE_TEXT_FLAG_MULTI_LINE

    const val EMAIL = TEXT + InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

    const val DATE = InputType.TYPE_NULL

    const val DROP_DOWN = InputType.TYPE_NULL

}