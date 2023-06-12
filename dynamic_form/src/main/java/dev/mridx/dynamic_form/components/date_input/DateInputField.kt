package dev.mridx.dynamic_form.components.date_input

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.mridx.dynamic_form.components.simple_text_input.SimpleTextInput
import dev.mridx.dynamic_form.utils.SimpleDatePicker

class DateInputField : SimpleTextInput {



    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    var datePickerTitle = "Select Date"

    private var fragment: Fragment? = null


    override fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        super.render(context, attrs, defStyleAttr)


        isFocusable = false
        isFocusableInTouchMode = false

        errorMessage = "Date is empty."

        binding.textInputView.setOnClickListener {
            Log.d("mridx", "render: open date picker")
            if (fragment != null) {
                SimpleDatePicker.showPickerRawValue(
                    fragmentManager = fragment!!.childFragmentManager,
                    title = datePickerTitle,
                    listener = { date, cancelled ->
                        if (!cancelled) {
                            binding.textInputView.setText(SimpleDatePicker.formatDateToShow(date!!.time))
                        }
                    }
                )
            } else if (context is AppCompatActivity) {
                SimpleDatePicker.showPickerRawValue(
                    fragmentManager = context.supportFragmentManager,
                    title = datePickerTitle,
                    listener = { date, cancelled ->
                        if (!cancelled) {
                            binding.textInputView.setText(SimpleDatePicker.formatDateToShow(date!!.time))
                        }
                    }
                )
            }
        }

    }

    fun setFragment(fragment: Fragment? = null) {
        this.fragment = fragment
    }

    override fun validate(): Boolean {
        return validateField()
    }

    override fun validateField(): Boolean {
        val value = getValue()
        if (required && value.isEmpty()) {
            showErrorMessage(errorMessage = errorMessage)
            return false
        }
        return true
    }


}