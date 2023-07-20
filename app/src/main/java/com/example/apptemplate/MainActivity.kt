package com.example.apptemplate

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.example.apptemplate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.ApiBaseUrl
import dev.mridx.common.common_utils.bitmap.storeImage
import dev.mridx.delayed_uploader.accessor.DelayedUploader
import dev.mridx.delayed_uploader.data.local.model.DUFile
import dev.mridx.delayed_uploader.data.local.model.DUFileParameter
import dev.mridx.delayed_uploader.data.local.model.DUJob
import dev.mridx.delayed_uploader.data.local.model.DUNetworkConfiguration
import dev.mridx.delayed_uploader.data.local.model.DUNotificationConfiguration
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


    @ApiBaseUrl
    @Inject
    lateinit var apiBaseUrl: String

    @Inject
    lateinit var delayedUploader: DelayedUploader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        processStart()


    }



    private fun createFile(): File {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images")
        file.mkdirs()
        val f = File(file, "${Date().time}.jpg")
        f.createNewFile()
        return f
    }

    private fun processStart() {
        lifecycleScope.launch(Dispatchers.IO) {
            val imageBitmap = makeBitmapImage()
            //showImage(imageBitmap)
            val imageFile = createFile()
            storeImage(image = imageBitmap, pictureFile = imageFile)


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
                                    filePath = imageFile.path
                                )
                            ),
                        ),
                    ),
                    networkConfiguration = DUNetworkConfiguration(
                        uploadUrl = "${apiBaseUrl}v1/uploads",
                        submissionUrl = "${apiBaseUrl}v1/subtasks/01h3khwfp1h6pb4bck8yxj3y0x/report",
                        headers = mapOf(
                            "content-type" to "application/json",
                            "accept" to "application/json",
                            "authorization" to "Bearer 191|vRgtimW6bL2ryrd3n5ZitBV2a1gHmDcHzcwjTrZQ"
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