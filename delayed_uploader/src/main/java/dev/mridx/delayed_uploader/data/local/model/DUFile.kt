package dev.mridx.delayed_uploader.data.local.model

import androidx.annotation.Keep

@Keep
data class DUFile(
    val parameter: String = "file",
    var filePath: String,
)
