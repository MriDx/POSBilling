package dev.mridx.delayed_uploader.data.local.model

import androidx.annotation.Keep

@Keep
data class DUNetworkConfiguration(
    val uploadUrl: String,
    val submissionUrl: String,
    val headers: Map<String, String>
)