package dev.mridx.dynamic_form.components.dynamic_field

interface DynamicField {

    fun setHeading(heading: String)

    fun validate(): Boolean

    fun getValue(): String

    fun getName(): String


}