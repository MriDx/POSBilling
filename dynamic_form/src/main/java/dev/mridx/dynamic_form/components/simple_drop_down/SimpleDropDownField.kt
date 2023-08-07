package dev.mridx.dynamic_form.components.simple_drop_down

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import dev.mridx.dynamic_form.R
import dev.mridx.dynamic_form.components.dynamic_field.DynamicField
import dev.mridx.dynamic_form.components.simple_text_input.SimpleTextInputType
import dev.mridx.dynamic_form.databinding.SimpleDropDownFieldBinding

open class SimpleDropDownField : LinearLayoutCompat, DynamicField {

    private val binding: SimpleDropDownFieldBinding =
        DataBindingUtil.inflate<SimpleDropDownFieldBinding>(
            LayoutInflater.from(context),
            R.layout.simple_drop_down_field,
            this,
            true
        )

    constructor(context: Context) : super(context) {
        render(context, null, -1)
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

    private fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        binding.dropdownField.apply {
            inputType = SimpleTextInputType.DROP_DOWN
            isFocusable = false
            isFocusableInTouchMode = false
        }
    }


    var required: Boolean = false
    private var selectedOptionIndex = -1
    var errorMessage: String = "Select an option."
    var fieldName: String = ""
    private var optionValues = arrayOf<String>()


    override fun setHint(hint: String) {
        binding.dropdownField.hint = hint
    }

    override fun setHeading(heading: String) {
        binding.headingView.text = heading
    }

    override fun setValue(value: String) {
        binding.dropdownField.setText(value)
    }

    override fun getValue(): String {
        //return binding.dropdownField.text.toString()
        if (selectedOptionIndex == -1) return ""
        return if (optionValues.isEmpty()) binding.dropdownField.text.toString() else optionValues[selectedOptionIndex]
    }


    override fun validate(): Boolean {
        val value = getValue()
        if (required && value.isEmpty()) {
            showErrorMessage(errorMessage = errorMessage)
            return false
        }
        return true
    }

    fun setPrefix(prefix: String) {
        binding.dropdownFieldLayout.prefixText = prefix
    }

    fun setSuffix(suffix: String) {
        binding.dropdownFieldLayout.suffixText = suffix
    }

    open fun validateField(): Boolean {
        val value = binding.dropdownField.text.toString()
        if (required && value.isEmpty()) {
            showPrivateErrorMessage(errorMessage)
            return false
        }
        return true
    }

    private fun showPrivateErrorMessage(errorMessage: String?) {
        binding.dropdownFieldLayout.error = errorMessage ?: this.errorMessage
    }

    fun setOptionValues(values: Array<String>) {
        optionValues = values
    }

    fun setOptions(options: Array<String>) {
        binding.dropdownField.apply {
            setSimpleItems(options)
        }
    }

    override fun getName(): String {
        return fieldName
    }

    override fun showErrorMessage(errorMessage: String) {
        binding.dropdownFieldLayout.error = errorMessage
    }


}