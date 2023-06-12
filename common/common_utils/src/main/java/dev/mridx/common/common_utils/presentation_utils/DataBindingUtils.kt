package dev.mridx.common.common_utils.presentation_utils

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView


@set:BindingAdapter("visible")
var View.visible
    get() = this.isVisible
    set(value) {
        this.isVisible = value
    }


@set:BindingAdapter("drawableResource")
var AppCompatImageView.drawableResource
    get() = 0
    set(value) {
        setImageResource(value)
    }


@set:BindingAdapter("buttonIcon")
var MaterialButton.buttonIcon
    get() = 0
    set(value) {
        icon = ContextCompat.getDrawable(context, value)
    }


@BindingAdapter("android:valueAttrChanged")
fun setSliderListeners(slider: Slider, attrChange: InverseBindingListener) {
    slider.addOnChangeListener { _, _, _ ->
        attrChange.onChange()
    }
}

@set:BindingAdapter("pipeLength")
var MaterialTextView.pipeLength
    get() = 0f
    set(value) {
        text = "${value.toInt()} meter"
    }

@set:BindingAdapter("textResource")
var MaterialTextView.textResource
    get() = 0
    set(value) {
        text = resources.getString(value)
    }


@BindingAdapter("removeError")
fun TextInputLayout.removeError(removeError: Boolean = true) {
    editText?.doBeforeTextChanged { text, start, count, after ->
        this.error = null
    }
}