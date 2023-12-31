package dev.mridx.common.common_data.izzy_parser_wrapper

import com.undabot.izzy.models.IzzyResource
import com.undabot.izzy.models.JsonDocument
import okhttp3.ResponseBody
import retrofit2.Converter

class MyIzzyResponseBodyConverter<T : IzzyResource>(private val izzy: MyIzzy) :
    Converter<ResponseBody, JsonDocument<T>> {

    override fun convert(value: ResponseBody): JsonDocument<T>? =
        izzy.deserializeToDocument(json = value.charStream().readText())
}