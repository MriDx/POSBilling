package dev.mridx.common.common_presentation.utils

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import dev.mridx.common.common_utils.utils.parseException

fun LoadState.isLoading() = this is LoadState.Loading

fun LoadState.isError() = this is LoadState.Error

@BindingAdapter("errorState")
fun TextView.errorState(state: LoadState) {
    val exception = (state as? LoadState.Error)?.error ?: return
    isVisible = !exception.message.isNullOrEmpty()
    text = parseException(e = exception)
    //text = exception?.message
}