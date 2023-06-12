package dev.mridx.dynamic_form.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DynamicFieldModel(
    var viewId: Int = -1,
    val type: String,
    val heading: String,
    val name: String? = null,
    val required: Boolean = true,
    val hint: String? = null,
    val prefix: String? = null,
    val suffix: String? = null,
    val max_length: Int? = null,
    val min_length: Int? = null,
    val options: List<String>? = null,
) : Parcelable
