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


    var fieldName = ""

    private fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        //
    }

    override fun setHeading(heading: String) {
        binding.headingView.text = heading
    }

    override fun validate(): Boolean {
        return binding.radioGroup.checkedRadioButtonId != -1
    }

    override fun getValue(): String {
        if (!validate()) return ""
        return binding.root.findViewById<MaterialRadioButton>(binding.radioGroup.checkedRadioButtonId).text.toString()
    }

    override fun getName(): String {
        return fieldName
    }

    fun setOptions(options: Array<String>) {
        this.options = options
        renderOptions()
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


}