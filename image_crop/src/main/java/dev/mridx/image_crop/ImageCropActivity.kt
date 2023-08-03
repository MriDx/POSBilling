package dev.mridx.image_crop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.canhub.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_utils.bitmap.compressBitmap
import dev.mridx.common.common_utils.bitmap.storeImage
import dev.mridx.common.common_utils.bitmap.toBitmap
import dev.mridx.image_crop.databinding.ImageCropActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Date


@AndroidEntryPoint
class ImageCropActivity : AppCompatActivity(), CropImageView.OnSetImageUriCompleteListener,
    CropImageView.OnCropImageCompleteListener {

    open class ImageCropActivityContract : ActivityResultContract<Uri, Uri>() {

        override fun createIntent(context: Context, input: Uri): Intent {
            val intent = Intent(context, ImageCropActivity::class.java)
            intent.putExtras(
                bundleOf(
                    "image_uri" to input.toString()
                )
            )
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri {
            val imageUri = intent?.extras?.getString("image_uri") ?: ""
            return Uri.parse(imageUri)
        }

    }


    override fun onCropImageComplete(view: CropImageView, result: CropImageView.CropResult) {
        if (result.error != null) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        } else {

            val uri = result.getUriFilePath(this)
//            val intent = Intent()
//            intent.putExtras(Bundle().apply {
//                putString("image_uri", uri)
//            })
//            setResult(Activity.RESULT_OK, intent)
            compressCroppedImage(uri)
        }
    }

    override fun onSetImageUriComplete(view: CropImageView, uri: Uri, error: Exception?) {

    }

    private lateinit var binding: ImageCropActivityBinding

    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ImageCropActivityBinding?>(
            this,
            R.layout.image_crop_activity
        ).apply {
            lifecycleOwner = this@ImageCropActivity
        }

        val filePath = intent?.extras?.getString("image_uri")

        imageUri = Uri.parse(filePath)

        binding.cropImageView.setImageUriAsync(imageUri)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.image_crop_activity_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveBtn -> {
                //
                handleSave()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun handleSave() {
        //
        val outputFile = createOutputFile()
        binding.cropImageView.croppedImageAsync(
            customOutputUri = outputFile.toUri()
        )
    }

    private fun createOutputFile(): File {
        val parentDir = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "cropped"
        )
        if (parentDir.exists()) {
            parentDir.deleteRecursively()
        }
        parentDir.mkdirs()

        val file = File(
            parentDir,
            "${Date().time}.jpg"
        )

        file.createNewFile()

        return file
    }

    public override fun onStart() {
        super.onStart()
        binding.cropImageView.setOnSetImageUriCompleteListener(this)
        binding.cropImageView.setOnCropImageCompleteListener(this)
    }

    public override fun onStop() {
        super.onStop()
        binding.cropImageView.setOnSetImageUriCompleteListener(null)
        binding.cropImageView.setOnCropImageCompleteListener(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("tmp_uri", imageUri.toString())
    }

    private fun compressCroppedImage(uri: String?) {
        lifecycleScope.launch(Dispatchers.IO) {

            val file = File(uri)

            val savedFile = compressAndStoreImage(file = file, saveFile = null)

            val intent = Intent()
            intent.putExtras(Bundle().apply {
                putString("image_uri", savedFile.path)
            })
            setResult(Activity.RESULT_OK, intent)

            finish()

        }
    }

    private suspend fun compressAndStoreImage(file: File, saveFile: File? = null): File {
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
        val parentDir = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "images/media/tmpStore"
        )

        parentDir.mkdirs()

        val file = File(parentDir, "${Date().time}.jpg")

        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return file
    }


}