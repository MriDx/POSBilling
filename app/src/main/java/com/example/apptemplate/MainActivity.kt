package com.example.apptemplate

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.example.apptemplate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.data.constants.ACCESS_TOKEN
import dev.mridx.common.common_data.di.qualifier.ApiBaseUrl
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.mridx.common.common_utils.bitmap.compressBitmap
import dev.mridx.common.common_utils.bitmap.storeImage
import dev.mridx.common.common_utils.bitmap.toBitmap
import dev.mridx.delayed_uploader.accessor.DelayedUploader
import dev.mridx.delayed_uploader.data.local.model.DUFile
import dev.mridx.delayed_uploader.data.local.model.DUFileParameter
import dev.mridx.delayed_uploader.data.local.model.DUJob
import dev.mridx.delayed_uploader.data.local.model.DUNetworkConfiguration
import dev.mridx.delayed_uploader.data.local.model.DUNotificationConfiguration
import dev.mridx.dynamic_form.components.simple_text_input.SimpleTextInputType
import dev.mridx.image_crop.ImageCropActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.text.DateFormat
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri ->
            imageCroper.launch(uri)
        }

    }

    private val imageCroper =
        registerForActivityResult(ImageCropActivity.ImageCropActivityContract()) {
            onImageCroped(it)
        }

    private fun onImageCroped(fileUri: Uri?) {
        if (fileUri != null) {
            val file = File(fileUri!!.path)
            compressImage(file)
        }
    }

    private fun compressImage(file: File) {
        lifecycleScope.launch(Dispatchers.IO) {

            val compressedFile = compressAndStoreImage(file, createImageFile())

            imageFilePath = compressedFile.path

            withContext(Dispatchers.Main) {
                Glide.with(this@MainActivity)
                    .asBitmap()
                    .load(compressedFile)
                    .into(binding.imageView)
            }

        }
    }


    suspend fun compressAndStoreImage(file: File, saveFile: File? = null): File {
        return withContext(Dispatchers.IO) {
            val bitmap = file.toBitmap(720f, 720f) ?: throw Exception("")

            val compressedBitmap =
                compressBitmap(bitmap = bitmap, maxHeight = 720f, maxWidth = 720f) ?: bitmap

            val savedFile =
                storeImage(image = compressedBitmap, pictureFile = saveFile ?: createImageFile())
                    ?: throw Exception("could not save file")

            savedFile
        }
    }

    /**
     * This will create a file to store the file after cropping.
     * everytime it runs, it will remove all existing files under the directory.
     */
    private fun createImageFile(): File {
        val parentDir = File(filesDir, "images/media/tmpStore")

        parentDir.deleteRecursively()
        parentDir.mkdirs()

        val file = File(parentDir, "${Date().time}.jpg")
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return file
    }

    private var imageFilePath = ""


    @ApiBaseUrl
    @Inject
    lateinit var apiBaseUrl: String

    @Inject
    lateinit var delayedUploader: DelayedUploader

    @Inject
    @AppPreference
    lateinit var appSharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.imageView.setOnClickListener {
            imagePicker.launch("image/*")
        }

        binding.submitBtn.setOnClickListener {
            handleSubmit()
        }

        binding.jobsBtn.setOnClickListener {
            //getAllJobs()
            getCheckedValue()
        }


        binding.radioInputField.apply {
            setOptions(arrayOf("1", "2", "3", "4", "5"))
            setRadioOrientation(LinearLayout.VERTICAL)
            setValues(arrayOf("01", "02", "03", "04", "05"))
        }

        binding.inputField.apply {
            setHeading("Write something !")
            setInputType(SimpleTextInputType.NUMBER_SIGNED)
        }


    }

    private fun getCheckedValue() {
        Log.d("mridx", "getCheckedValue: ${binding.radioInputField.getValue()}")
        Log.d("mridx", "getCheckedValue: ${binding.inputField.getValue()}")


        /*binding.parentLayout.children.forEach {
            Log.d("mridx", "getCheckedValue: ${it.javaClass.name}")
            if (it is RadioInput) {
                Log.d("mridx", "getCheckedValue: ${(it as RadioInput).getValue()}")
            }
        }
*/
    }

    private fun getAllJobs() {
        lifecycleScope.launch(Dispatchers.IO) {
            delayedUploader.getAllJobs().forEachIndexed { index, duJobDataModel ->
                Log.d("mridx", "getAllJobs: index  $index")
                Log.d("mridx", "getAllJobs: $duJobDataModel")
                Log.d("mridx", "getAllJobs: Ends")
            }
        }
    }

    private fun handleSubmit() {
        if (imageFilePath.isEmpty()) {
            Toast.makeText(this, "No file yet", Toast.LENGTH_SHORT).show()
            return
        }

        processStart()
    }


    private fun processStart() {
        lifecycleScope.launch(Dispatchers.IO) {
//            val imageBitmap = makeBitmapImage()
//            //showImage(imageBitmap)
//            val imageFile = createFile()
//            storeImage(image = imageBitmap, pictureFile = imageFile)


            appSharedPreferences.edit {
                putString(ACCESS_TOKEN, "Bearer 191|vRgtimW6bL2ryrd3n5ZitBV2a1gHmDcHzcwjTrZQ")
            }



            delayedUploader.scheduleNewJob(
                this@MainActivity,
                duJob = DUJob(
                    finalParameters = JSONObject().apply {
                        put("remarks", "This is remarks")
                        put("user_id", "01h3pq8w0aqg2hn5q0e45wj9z3")
                        put("answer", "This is answer !")
                    },
                    fileParameters = listOf(
                        DUFileParameter(
                            parameter = "images",
                            files = listOf(
                                DUFile(
                                    parameter = "file",
                                    filePath = imageFilePath
                                )
                            ),
                        ),
                    ),
                    networkConfiguration = DUNetworkConfiguration(
                        uploadUrl = "${apiBaseUrl}v1/uploads",
                        submissionUrl = "${apiBaseUrl}v1/subtasks/01h3khwfp1h6pb4bck8yxj3y0x/report",
                        headers = mapOf(
                            "Content-Type" to "application/json",
                            "Accept" to "application/json",
                            "Authorization" to "Bearer 191|vRgtimW6bL2ryrd3n5ZitBV2a1gHmDcHzcwjTrZQ",
                        )
                    ),
                    clearAfter = true,
                    notificationConfiguration = DUNotificationConfiguration(
                        notificationId = Date().time.toInt(),
                        title = "Progress !",
                        body = "Work is being progress.",
                        autoCancel = true
                    )
                )
            )

        }


    }


    private suspend fun makeBitmapImage(): Bitmap {
        return withContext(Dispatchers.IO) {
            val height = 720
            val width = 1280

            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            val tmpBmp = bmp.copy(bmp.config, true)
            val canvas = Canvas(tmpBmp)

            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), Paint().apply {
                color = Color.RED
            })

            val text =
                DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(Date())
            val textPaint = Paint().apply {
                color = Color.BLACK
                textSize = 40f
            }
            val textWidth = textPaint.measureText(text)

            canvas.drawText(text, (width - textWidth) / 2, height / 2f, textPaint)

            tmpBmp
        }

    }


}