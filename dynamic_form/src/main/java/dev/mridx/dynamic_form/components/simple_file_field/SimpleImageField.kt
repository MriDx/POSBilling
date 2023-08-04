package dev.mridx.dynamic_form.components.simple_file_field

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import dev.mridx.common.common_utils.file_utils.GetUriOfFiles
import dev.mridx.dynamic_form.R
import dev.mridx.dynamic_form.databinding.ImageUploadFieldBinding
import dev.mridx.image_crop.ImageCropActivity
import java.io.File

class SimpleImageField : LinearLayoutCompat, SimpleFileField {


    private fun getActivityReference(): AppCompatActivity {
        return (context as AppCompatActivity)
    }


    private val imagePickerLauncher =
        getActivityReference().registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                imageCropActivityLauncher.launch(it)
            }
        }

    private var imageCaptureFileUri: Uri? = null
    private val imageCaptureLauncher =
        getActivityReference().registerForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
            if (captured && imageCaptureFileUri != null) {
                imageCropActivityLauncher.launch(imageCaptureFileUri)
            }
        }


    private val imageCropActivityLauncher =
        (context as AppCompatActivity).registerForActivityResult(ImageCropActivity.ImageCropActivityContract()) {
            onCroppedResult(it)
        }


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

    override fun getValue(): String {
        return imageUri?.path ?: ""
    }


    private fun showPickerDialog() {
        AlertDialog.Builder(context).apply {
            setTitle("Select source")
            setItems(arrayOf("Open Camera", "Select from Gallery"),
                DialogInterface.OnClickListener { dialog, btnIndex ->
                    dialog.dismiss()
                    if (btnIndex == 0) {
                        //
                        openCamera()
                    } else {
                        //
                        imagePickerLauncher.launch("image/*")
                    }
                }
            )
            setCancelable(true)
            setPositiveButton("Cancel") { dialog, index ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun openCamera() {

        imageCaptureFileUri = getTmpFileUri()

        imageCaptureLauncher.launch(imageCaptureFileUri)


    }


    /**
     * use this function to generate uri for capture image
     */
    private fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", getActivityReference().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return GetUriOfFiles.getUri(context, tmpFile)
    }


    private fun onCroppedResult(fileUri: Uri?) {
        if (fileUri != null) {
            val file = File(fileUri.path)
            setImage(uri = file.toUri())
        }
    }

    override fun getName(): String {
        return ""
    }

}