package dev.mridx.dynamic_form.components.simple_text_input

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import dev.mridx.dynamic_form.R
import dev.mridx.dynamic_form.components.dynamic_field.DynamicField
import dev.mridx.dynamic_form.databinding.SimpleTextInputBinding

open class SimpleTextInput : LinearLayoutCompat, DynamicField {

    val binding: SimpleTextInputBinding = DataBindingUtil.inflate<SimpleTextInputBinding>(
        LayoutInflater.from(context),
        R.layout.simple_text_input,
        this,
        true
    )


    constructor(context: Context) : super(context) {
        initialRender(context, null, -1)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialRender(context, attrs, -1)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialRender(context, attrs, defStyleAttr)
    }


    private var hint = ""
    var required: Boolean = false
    var errorMessage: String = "Field can not be blank."
    var fieldName: String = ""


    override fun setHeading(heading: String) {
        binding.headingView.text = heading
    }

    override fun setHint(hint: String) {
        binding.textInputView.hint = hint
    }

    override fun setValue(value: String) {
        binding.textInputView.setText(value)
    }

    override fun getValue(): String {
        return binding.textInputView.text.toString()
    }

    override fun getName(): String {
        return fieldName
    }


    override fun validate(): Boolean {
        return validateField()
    }


    override fun showErrorMessage(errorMessage: String) {
        binding.textInputLayout.error = errorMessage
    }


    fun setPrefix(prefix: String) {
        binding.textInputLayout.prefixText = prefix
    }

    fun setSuffix(suffix: String) {
        binding.textInputLayout.suffixText = suffix
    }

    fun setInputType(inputType: Int) {
        binding.textInputView.inputType = inputType
    }

    fun setActionKey(actionKey: Int) {
        binding.textInputView.imeOptions = actionKey
    }

    open fun validateField(): Boolean {
        val value = binding.textInputView.text.toString()
        if (required && value.isEmpty()) {
            binding.textInputLayout.error = errorMessage
            return false
        }
        return true
    }


    fun setMaxLength(length: Int) {
        binding.textInputView.filters = arrayOf(InputFilter.LengthFilter(length))
    }

    fun setMaxLines(maxLine: Int) {
        binding.textInputView.maxLines = maxLine
    }

    fun setMinLines(minLine: Int) {
        binding.textInputView.minLines = minLine
    }


    fun countedEnabled(maxCounter: Int) {
        binding.textInputLayout.isCounterEnabled = true
        binding.textInputLayout.counterMaxLength = maxCounter
    }

    fun showPrivateErrorMessage(errorMessage: String?) {
        binding.textInputLayout.error = errorMessage ?: this.errorMessage
    }


    open fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        //
    }

    private fun initialRender(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        render(context, attrs, defStyleAttr)
    }


}