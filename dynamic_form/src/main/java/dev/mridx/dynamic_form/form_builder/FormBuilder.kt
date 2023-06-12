package dev.mridx.dynamic_form.form_builder

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.view.updateMargins
import androidx.fragment.app.Fragment
import dev.mridx.dynamic_form.components.date_input.DateInputField
import dev.mridx.dynamic_form.components.dynamic_field.DynamicField
import dev.mridx.dynamic_form.components.mobile_number.MobileNumberField
import dev.mridx.dynamic_form.components.simple_drop_down.SimpleDropDownField
import dev.mridx.dynamic_form.components.simple_file_field.SimpleImageField
import dev.mridx.dynamic_form.components.simple_text_input.SimpleTextInput
import dev.mridx.dynamic_form.components.simple_text_input.SimpleTextInputType
import dev.mridx.dynamic_form.data.model.DynamicFieldModel
import dev.mridx.dynamic_form.utils.dpToInt
import org.json.JSONArray
import org.json.JSONObject

class FormBuilder : LinearLayoutCompat {


    constructor(context: Context) : super(context) {
        render(context, null, -1)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        render(context, attrs, -1)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        render(context, attrs, defStyleAttr)
    }

    private var fields: List<DynamicFieldModel> = emptyList()


    private fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        orientation = LinearLayoutCompat.VERTICAL


    }


    fun validateFields(): Boolean {
        if (children.toList().isEmpty()) return true
        val validated = children.map {
            (it as DynamicField).validate()
        }.toList()
        //Log.d("mridx", "validateFields: $validated - ${validated.any { it }}")
        return validated.any { it }
        /*return children.all {
            (it as DynamicField).validate()
        }*/
    }

    fun getValues(): List<JSONObject> {

        return children.mapIndexed { index, view ->
            JSONObject().apply {
                put("heading", fields[index].heading)
                put("answer", (view as DynamicField).getValue())
            }
        }.toList()

    }


    fun buildFromJson(jsonArray: JSONArray): FormBuilder {


        fields = buildFieldsFromJson(jsonArray = jsonArray)


        buildUIFromFields(fragment = null, fields)

        return this

    }

    fun buildFromFieldList(fragment: Fragment, fields: List<DynamicFieldModel>): FormBuilder {
        this.fields = fields
        buildUIFromFields(fragment = fragment, fields = fields)

        return this

    }

    fun buildFromFieldList(fields: List<DynamicFieldModel>): FormBuilder {
        this.fields = fields
        buildUIFromFields(fields = fields)

        return this

    }


    private fun buildFieldsFromJson(jsonArray: JSONArray): List<DynamicFieldModel> {

        val fieldList = mutableListOf<DynamicFieldModel>()

        for (i in 0 until jsonArray.length()) {

            fieldList.add(buildFieldModelFromJson(jsonObj = jsonArray.getJSONObject(i)))

        }

        return fieldList

    }

    private fun buildFieldModelFromJson(jsonObj: JSONObject): DynamicFieldModel {

        val type = jsonObj.getString("type")
        val heading = jsonObj.getString("heading")
        val name = jsonObj.getString("name")
        val required = jsonObj.getBoolean("required")
        val hint = jsonObj.getString("hint")
        val prefix = jsonObj.getString("prefix")
        val suffix = jsonObj.getString("suffix")
        val max_length = jsonObj.getString("max_length").toIntOrNull()
        val min_length = jsonObj.getString("min_length").toIntOrNull()
        val options = optionListFromJson(jsonArray = jsonObj.getJSONArray("options"))

        return DynamicFieldModel(
            type = type,
            heading = heading,
            name = name,
            required = required,
            hint = hint,
            prefix = prefix,
            suffix = suffix,
            max_length = max_length,
            min_length = min_length,
            options = options
        )

    }

    private fun optionListFromJson(jsonArray: JSONArray?): List<String> {
        val options = mutableListOf<String>()
        repeat(jsonArray?.length() ?: 0) {
            options.add(jsonArray!!.getString(it))
        }
        return options
    }

    private fun buildUIFromFields(fragment: Fragment? = null, fields: List<DynamicFieldModel>) {

        fields.forEachIndexed { index, dynamicFieldModel ->
            when (dynamicFieldModel.type) {
                "text_input" -> {
                    val field = SimpleTextInput(context).apply {
                        setHeading(heading = dynamicFieldModel.heading)
                        fieldName = dynamicFieldModel.name ?: ""
                        setHint(hint = dynamicFieldModel.hint ?: "")
                        required = dynamicFieldModel.required
                        setPrefix(dynamicFieldModel.prefix ?: "")
                        setSuffix(dynamicFieldModel.suffix ?: "")
                        setInputType(inputType = SimpleTextInputType.TEXT)
                        setActionKey(actionKey = EditorInfo.IME_ACTION_NEXT)
                        layoutParams = LinearLayoutCompat.LayoutParams(
                            ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            ).apply {
                                updateMargins(
                                    left = dpToInt(context, size = 0),
                                    right = dpToInt(context, size = 0),
                                    top = dpToInt(context, size = 16),
                                    bottom = 0
                                )
                            }
                        )
                        id = ViewCompat.generateViewId()
                    }
                    dynamicFieldModel.viewId = field.id
                    addView(field)
                }

                "mobile_input" -> {
                    val field = MobileNumberField(context).apply {
                        setHeading(heading = dynamicFieldModel.heading)
                        fieldName = dynamicFieldModel.name ?: ""
                        setHint(hint = dynamicFieldModel.hint ?: "")
                        required = dynamicFieldModel.required
                        setPrefix(dynamicFieldModel.prefix ?: "")
                        setSuffix(dynamicFieldModel.suffix ?: "")
                        setActionKey(actionKey = EditorInfo.IME_ACTION_NEXT)
                        layoutParams = LinearLayoutCompat.LayoutParams(
                            ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            ).apply {
                                updateMargins(
                                    left = dpToInt(context, size = 0),
                                    right = dpToInt(context, size = 0),
                                    top = dpToInt(context, size = 16),
                                    bottom = 0
                                )
                            }
                        )
                        id = ViewCompat.generateViewId()
                    }
                    dynamicFieldModel.viewId = field.id
                    addView(field)
                }

                "choice_field" -> {
                    val field = SimpleDropDownField(context).apply {
                        setHeading(heading = dynamicFieldModel.heading)
                        fieldName = dynamicFieldModel.name ?: ""
                        setHint(hint = dynamicFieldModel.hint ?: "Select")
                        required = dynamicFieldModel.required
                        setPrefix(dynamicFieldModel.prefix ?: "")
                        setSuffix(dynamicFieldModel.suffix ?: "")
                        setOptions(options = dynamicFieldModel.options?.toTypedArray() ?: arrayOf())
                        layoutParams = LinearLayoutCompat.LayoutParams(
                            ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            ).apply {
                                updateMargins(
                                    left = dpToInt(context, size = 0),
                                    right = dpToInt(context, size = 0),
                                    top = dpToInt(context, size = 16),
                                    bottom = 0
                                )
                            }
                        )
                        id = ViewCompat.generateViewId()
                    }
                    dynamicFieldModel.viewId = field.id
                    addView(field)
                }

                "date_input" -> {
                    val field = DateInputField(context).apply {
                        setFragment(fragment = fragment)
                        setHeading(heading = dynamicFieldModel.heading)
                        fieldName = dynamicFieldModel.name ?: ""
                        setHint(hint = dynamicFieldModel.hint ?: "")
                        required = dynamicFieldModel.required
                        setPrefix(dynamicFieldModel.prefix ?: "")
                        setSuffix(dynamicFieldModel.suffix ?: "")
                        setActionKey(actionKey = EditorInfo.IME_ACTION_NEXT)
                        layoutParams = LinearLayoutCompat.LayoutParams(
                            ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            ).apply {
                                updateMargins(
                                    left = dpToInt(context, size = 0),
                                    right = dpToInt(context, size = 0),
                                    top = dpToInt(context, size = 16),
                                    bottom = 0
                                )
                            }
                        )
                        id = ViewCompat.generateViewId()
                    }
                    dynamicFieldModel.viewId = field.id
                    addView(field)
                }

                "multiline_input" -> {
                    val field = SimpleTextInput(context).apply {
                        setHeading(heading = dynamicFieldModel.heading)
                        fieldName = dynamicFieldModel.name ?: ""
                        setHint(hint = dynamicFieldModel.hint ?: "")
                        required = dynamicFieldModel.required
                        setPrefix(dynamicFieldModel.prefix ?: "")
                        setSuffix(dynamicFieldModel.suffix ?: "")
                        setInputType(SimpleTextInputType.TEXT_MULTILINE)
                        setActionKey(actionKey = EditorInfo.IME_ACTION_NEXT)
                        setMinLines(3)
                        layoutParams = LinearLayoutCompat.LayoutParams(
                            ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            ).apply {
                                updateMargins(
                                    left = dpToInt(context, size = 0),
                                    right = dpToInt(context, size = 0),
                                    top = dpToInt(context, size = 16),
                                    bottom = 0
                                )
                            }
                        )
                        id = ViewCompat.generateViewId()
                    }
                    dynamicFieldModel.viewId = field.id
                    addView(field)
                }

                "image_field" -> {
                    val field = SimpleImageField(context).apply {
                        layoutParams = LinearLayoutCompat.LayoutParams(
                            ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToInt(context, 164)
                            ).apply {
                                updateMargins(
                                    left = dpToInt(context, size = 0),
                                    right = dpToInt(context, size = 0),
                                    top = dpToInt(context, size = 16),
                                    bottom = 0
                                )
                            }
                        )
                        setHeading(heading = dynamicFieldModel.heading)
                        id = ViewCompat.generateViewId()
                    }
                    dynamicFieldModel.viewId = field.id
                    addView(field)
                }
            }

        }
        //text_input, mobile_input, choice_field, date_input, multiline_input,

    }


}