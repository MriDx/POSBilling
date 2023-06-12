package dev.mridx.dynamic_form.components.mobile_number

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import dev.mridx.common.common_utils.utils.isPhoneNumber
import dev.mridx.dynamic_form.components.simple_text_input.SimpleTextInput
import dev.mridx.dynamic_form.components.simple_text_input.SimpleTextInputType

class MobileNumberField : SimpleTextInput {


    constructor(context: Context) : super(context) {
        render(context = context, attrs = null, -1)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        render(context, attrs, -1)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        render(context, attrs, defStyleAttr)
    }


    override fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        setPrefix("+91 ")
        errorMessage = "Mobile number is invalid."
        setMaxLength(length = 10)
        countedEnabled(maxCounter = 10)
        setInputType(inputType = SimpleTextInputType.NUMBER_SIGNED)
        setActionKey(actionKey = EditorInfo.IME_ACTION_DONE)

    }

    override fun validate(): Boolean {
        return validateField()
    }

    override fun validateField(): Boolean {
        val value = getValue()
        if (value.isNotEmpty() || required) {
            val valid = value.isPhoneNumber()
            if (!valid) {
                showErrorMessage(errorMessage = errorMessage)
            }
            return valid
        }
        return true
    }


}