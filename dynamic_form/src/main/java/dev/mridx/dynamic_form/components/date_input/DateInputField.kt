package dev.mridx.dynamic_form.components.date_input

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.mridx.dynamic_form.components.simple_text_input.SimpleTextInput
import dev.mridx.dynamic_form.utils.SimpleDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    private var dateToSend = ""
    private var dateFormat_ = "yyyy-MM-dd"


    override fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        super.render(context, attrs, defStyleAttr)

        isFocusable = false
        isFocusableInTouchMode = false

        errorMessage = "Date is empty."

        binding.textInputView.setOnClickListener {
            openAndHandleDatePickerDialog()
        }

    }

    /**
     * set fragment to show dialog inside fragment,
     * No need to use or pass anything in case of activity.
     *
     * @param fragment
     */
    fun setFragment(fragment: Fragment? = null) {
        this.fragment = fragment
    }


    /**
     * This function validates the field
     *
     * First gets the value
     */
    override fun validateField(): Boolean {
        val value = getValue()
        if (required && value.isEmpty()) {
            showPrivateErrorMessage(errorMessage = errorMessage)
            return false
        }
        return true
    }

    /**
     * store date format to format the selected date and return
     * Default format is yyyy-MM-dd
     *
     * @param dateFormat
     *
     */
    fun setDateFormat(dateFormat: String = "yyyy-MM-dd") {
        dateFormat_ = dateFormat
    }

    override fun getValue(): String {
        return dateToSend
    }


    /**
     * opens the date picker dialog
     */
    private fun openAndHandleDatePickerDialog() {
        if (fragment != null) {
            SimpleDatePicker.showPickerRawValue(
                fragmentManager = fragment!!.childFragmentManager,
                title = datePickerTitle,
                listener = { date, cancelled ->
                    handleSelectedValue(date = date, cancelled = cancelled)
                }
            )
        } else if (context is AppCompatActivity) {
            SimpleDatePicker.showPickerRawValue(
                fragmentManager = (context as AppCompatActivity).supportFragmentManager,
                title = datePickerTitle,
                listener = { date, cancelled ->
                    handleSelectedValue(date = date, cancelled = cancelled)
                }
            )
        }
    }

    private fun handleSelectedValue(date: Date?, cancelled: Boolean) {
        if (!cancelled) {
            dateToSend = try {
                SimpleDatePicker.formatTimeStampToDate(
                    s = date!!.time,
                    format = SimpleDateFormat(dateFormat_, Locale.US)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                SimpleDatePicker.formatTimeStampToDate(
                    s = date!!.time,
                    format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                )
            }

            binding.textInputView.setText(SimpleDatePicker.formatDateToShow(date!!.time))
        }
    }


}