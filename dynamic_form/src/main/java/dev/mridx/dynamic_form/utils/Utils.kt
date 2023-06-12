package dev.mridx.dynamic_form.utils

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt



fun dpToInt(context: Context, size: Int) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    size.toFloat(),
    context.resources.displayMetrics
).roundToInt()
