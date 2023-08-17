package dev.mridx.dynamic_form.components.simple_file_field

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import dev.mridx.dynamic_form.R
import dev.mridx.dynamic_form.databinding.ImageUploadFieldBinding

class SimpleImageField : LinearLayoutCompat, SimpleFileField {


    var onCameraAction: (() -> Unit)? = null

    var onPickerAction: (() -> Unit)? = null


    private val binding =
        DataBindingUtil.inflate<ImageUploadFieldBinding>(
            LayoutInflater.from(context),
            R.layout.image_upload_field,
            this,
            true
        )

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


    fun setUploadHint(hint: String) {
        binding.uploadHint.text = hint
    }

    override fun setHeading(heading: String) {
        binding.headingView.text = heading
    }

    override fun setHint(hint: String) {
        binding.uploadHint.text = hint
    }

    override fun setValue(value: String) {
        //
    }


    private var imageUri: Uri? = null
    fun setImage(uri: Uri) {
        imageUri = uri
        Glide.with(context)
            .load(uri)
            .into(binding.uploadPreview)
        //binding.uploadPreview.setImageURI(uri)
        binding.uploadPreview.isVisible = true
        binding.uploadHint.isVisible = false
        binding.uploadIcon.isVisible = false
    }

    private fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        binding.fieldHolder.setOnClickListener {
            showPickerDialog()
        }


    }

    override fun validate(): Boolean {
        if (imageUri == null) {
            binding.errorView.apply {
                text = "Select or capture a photo."
            }.isVisible = true
            return false
        }
        return true
    }

    override fun showErrorMessage(errorMessage: String) {
        //
    }

    override fun getValue(): String {
        return imageUri?.path ?: ""
    }

    override fun getName(): String {
        return ""
    }

    fun setError(errorMessage: String) {
        if (errorMessage.isEmpty()) return
        binding.errorView.apply {
            text = errorMessage
        }.isVisible = true
    }

    enum class OPTIONS {
        CAMERA_ONLY, GALLERY_ONLY, BOTH
    }

    private var sourceOptions = arrayOf("Open Camera", "Select from Gallery")

    fun setOptions(option: OPTIONS) {
        when (option) {
            OPTIONS.CAMERA_ONLY -> {
                sourceOptions = arrayOf("Open Camera")
            }

            OPTIONS.GALLERY_ONLY -> {

            }

            else -> {

            }
        }
    }


    private fun showPickerDialog() {
        AlertDialog.Builder(context).apply {
            setTitle("Select source")
            setItems(
                sourceOptions
            ) { dialog, btnIndex ->
                dialog.dismiss()
                if (btnIndex == 0) {
                    //
                    onCameraAction?.invoke()
                } else {
                    //
                    onPickerAction?.invoke()
                }
            }
            setCancelable(true)
            setPositiveButton("Cancel") { dialog, index ->
                dialog.dismiss()
            }
        }.show()
    }

}