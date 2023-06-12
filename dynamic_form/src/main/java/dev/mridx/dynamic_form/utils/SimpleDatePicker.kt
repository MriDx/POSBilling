package dev.mridx.dynamic_form.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object SimpleDatePicker {

    private val sdf by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.US)
    }

    fun formatTimeStampToDate(s: Long): String {
        return sdf.format(s)
    }

    fun formatTimeStampToDate(s: Long, format: SimpleDateFormat): String {
        return format.format(s)
    }

    fun formatDateToShow(s: Long): String {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(s))
    }


    private fun formatToDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    fun showPickerRawValue(
        fragmentManager: FragmentManager,
        title: String? = null,
        listener: (date: Date?, cancelled: Boolean) -> Unit
    ) {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(title ?: "Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker.addOnPositiveButtonClickListener {
            it?.let { s ->
                val date = formatToDate(timestamp = s)
                listener.invoke(date, false)
                datePicker.dismiss() //dismiss
            }
        }
        datePicker.addOnNegativeButtonClickListener {
            //
            listener.invoke(null, true)
            datePicker.dismiss() //dismiss
        }
        datePicker.show(fragmentManager, "MATERIAL_DATE_PICKER")
    }


}