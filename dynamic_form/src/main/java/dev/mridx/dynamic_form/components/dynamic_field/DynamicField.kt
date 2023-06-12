package dev.mridx.dynamic_form.components.dynamic_field

interface DynamicField {

    fun validate(): Boolean


    fun getValue(): String

}