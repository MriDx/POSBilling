package dev.mridx.delayed_uploader.utils

import androidx.work.WorkerParameters
import org.json.JSONException
import org.json.JSONObject

fun <DataType> WorkerParameters.getValueFromKey(key: String): DataType {
    return this.inputData.keyValueMap[key] as DataType
}


fun String.toHeadersMap(): Map<String, String> {
    val headers = mutableMapOf<String, String>()
    return try {
        val obj = JSONObject(this)
        obj.keys().forEach {
            headers[it] = obj.getString(it)
        }
        headers
    } catch (e: Exception) {
        e.printStackTrace()
        emptyMap()
    }

}

fun Map<String, String>.toHeaderString(): String {
    val obj = JSONObject()
    this.entries.forEach {
        obj.put(it.key, it.value)
    }
    return obj.toString()
}


@Throws(JSONException::class)
fun String?.toJSON(): JSONObject {
    return if (this.isNullOrEmpty()) JSONObject() else JSONObject(this)
}
