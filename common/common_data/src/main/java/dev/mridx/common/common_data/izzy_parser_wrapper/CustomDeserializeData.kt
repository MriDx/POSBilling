package dev.mridx.common.common_data.izzy_parser_wrapper

import com.undabot.izzy.models.DataPool
import com.undabot.izzy.models.IzzyResource
import com.undabot.izzy.parser.ATTRIBUTES
import com.undabot.izzy.parser.DATA
import com.undabot.izzy.parser.DeserializeLinks
import com.undabot.izzy.parser.DeserializeMeta
import com.undabot.izzy.parser.DeserializeRelationships
import com.undabot.izzy.parser.ID
import com.undabot.izzy.parser.IzzyJsonParser
import com.undabot.izzy.parser.JsonElements
import com.undabot.izzy.parser.TYPE

class CustomDeserializeData(private val izzyJsonParser: IzzyJsonParser) {


    private val deserializeLinks = DeserializeLinks()
    private val customDeserializeLinks = CustomDeserializeLinks()
    private val deserializeMeta = DeserializeMeta()
    private val deserializeRelationships =
        DeserializeRelationships(izzyJsonParser, deserializeLinks, deserializeMeta)

    fun <T : IzzyResource> forSingleResource(jsonTree: JsonElements): T? {
        val data = jsonTree.jsonElement(DATA)
        val resource: T? = resourceFrom(data)
        resource?.links = deserializeLinks.from(data)
        resource?.meta = deserializeMeta.from(data)
        deserializeRelationships.forGiven(DataPool(), resource, data, jsonTree)
        return resource
    }

    fun <T : IzzyResource> forResourceCollection(jsonTree: JsonElements): List<T>? {
        val data = jsonTree.jsonElementsArray(DATA)
        val pool = DataPool()
        val unfilteredList: List<T?> = data.map { currentJsonObject ->
            val resource: T? = resourceFrom(currentJsonObject)
            //resource?.links = deserializeLinks.from(currentJsonObject)
            resource?.links = customDeserializeLinks.from(currentJsonObject)
            resource?.meta = deserializeMeta.from(currentJsonObject)
            deserializeRelationships.forGiven(pool, resource, currentJsonObject, jsonTree)
            resource
        }
        return unfilteredList.filterNotNull()
    }

    private fun <T : IzzyResource> resourceFrom(data: JsonElements): T? {
        val resType = data.stringFor(TYPE)!!
        if (!isRegistered(resType)) {
            return null
        }

        val id = data.stringFor(ID)
        val attributes = data.jsonElement(ATTRIBUTES)
        val attributesString = if (attributes.isNull()) {
            "{}"
        } else {
            attributes.asJsonString()
        }
        val res: T = izzyJsonParser.parse(attributesString, typeFor(resType))
        res.id = id
        return res
    }

    private fun typeFor(resType: String) = izzyJsonParser.izzyConfiguration().typeFor(resType)

    private fun isRegistered(resType: String) = izzyJsonParser.izzyConfiguration().isRegistered(resType)


}