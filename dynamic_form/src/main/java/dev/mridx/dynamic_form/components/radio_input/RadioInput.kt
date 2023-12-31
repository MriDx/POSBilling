package dev.mridx.dynamic_form.components.radio_input

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.radiobutton.MaterialRadioButton
import dev.mridx.dynamic_form.R
import dev.mridx.dynamic_form.components.dynamic_field.DynamicField
import dev.mridx.dynamic_form.databinding.RadioInputBinding

class RadioInput : LinearLayoutCompat, DynamicField {


    private val binding: RadioInputBinding =
        DataBindingUtil.inflate<RadioInputBinding>(
            LayoutInflater.from(context),
            R.layout.radio_input,
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

    private var options =
        arrayOf("Mithicher", "Fardeen", "Partha", "Vaibhav", "Moni Kongkan", "Manash")

    private var optionValues =
        arrayOf("Mithicher", "Fardeen", "Partha", "Vaibhav", "Moni Kongkan", "Manash")


    var fieldName = ""

    private fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        //
    }

    override fun setHeading(heading: String) {
        binding.headingView.text = heading
    }

    override fun setHint(hint: String) {
        //
    }

    override fun setValue(value: String) {
        //
    }

    override fun validate(): Boolean {
        return binding.radioGroup.checkedRadioButtonId != -1
    }

    override fun getValue(): String {
        if (!validate()) return ""
        val selectedIndex = binding.radioGroup.indexOfChild(
            binding.radioGroup.findViewById<MaterialRadioButton>(binding.radioGroup.checkedRadioButtonId)
        )
        return if (optionValues.isEmpty()) binding.radioGroup.findViewById<MaterialRadioButton>(
            binding.radioGroup.checkedRadioButtonId
        ).text.toString() else optionValues[selectedIndex]
        //return binding.radioGroup.findViewById<MaterialRadioButton>(binding.radioGroup.checkedRadioButtonId).text.toString()
    }

    override fun getName(): String {
        return fieldName
    }



    /**
     * set radio options
     *
     * NOTE: may not exceed 3 options, use dropdown for more options
     *
     * @param options
     *
     */
    fun setOptions(options: Array<String>) {
        this.options = options
        renderOptions()
    }

    /**
     * set values to represent the selected options
     *
     * NOTE: values length must be same as options.
     *
     * USE CASE: UI options -> Yes, No --> value to post -> true, false
     *
     * @param values
     *
     */
    fun setOptionValues(values: Array<String>) {
        optionValues = values
    }


    fun setRadioOrientation(orientation: Int) {
        binding.radioGroup.orientation = orientation
    }

    private fun renderOptions() {
        //invalidate()
        options.forEachIndexed { index, key ->
            val radioBtn = MaterialRadioButton(context).apply {
                text = key
                layoutParams = LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }
            binding.radioGroup.addView(radioBtn)
            id = ViewCompat.generateViewId()
        }

        val firstItem = binding.radioGroup.getChildAt(0) as MaterialRadioButton

        binding.radioGroup.check(firstItem.id)
    }


    override fun showErrorMessage(errorMessage: String) {
        //
    }

}