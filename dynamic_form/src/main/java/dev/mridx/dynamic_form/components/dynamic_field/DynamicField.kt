package dev.mridx.dynamic_form.components.dynamic_field

interface DynamicField {

    fun setHeading(heading: String)

    fun setHint(hint: String)

    fun setValue(value: String)

    fun getValue(): String

    fun getName(): String

    //fun setRequired(required: Boolean)

    fun validate(): Boolean

    //fun validateField(): Boolean

    fun showErrorMessage(errorMessage: String)


}