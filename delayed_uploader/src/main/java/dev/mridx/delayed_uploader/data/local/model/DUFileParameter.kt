package dev.mridx.delayed_uploader.data.local.model

import androidx.annotation.Keep

@Keep
data class DUFileParameter(
    val parameter: String,
    val files: List<DUFile>,
)
