package dev.mridx.common.common_utils.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun File.toBitmap(maxHeight: Float, maxWidth: Float): Bitmap? {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    var bmp = BitmapFactory.decodeFile(this.path, options)

    var actualHeight = options.outHeight
    var actualWidth = options.outWidth

    var imgRatio = actualWidth.toFloat() / actualHeight.toFloat()
    val maxRatio = maxWidth / maxHeight

    if (actualHeight > maxHeight || actualWidth > maxWidth) {
        when {
            imgRatio < maxRatio -> {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            }

            imgRatio > maxRatio -> {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            }

            else -> {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }
    }
    options.apply {
        inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        inJustDecodeBounds = false
        inDither = false
        inPurgeable = true
        inInputShareable = true
        inTempStorage = ByteArray(16 * 1024)
    }

    try {
        bmp = BitmapFactory.decodeFile(this.path, options)
    } catch (e: OutOfMemoryError) {
        e.printStackTrace()
        return null
    }
    return bmp
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}


fun compressBitmap(bitmap: Bitmap, maxHeight: Float, maxWidth: Float): Bitmap? {

    val scaledBitmap: Bitmap?
    val bmp = bitmap.copy(bitmap.config, true)


    var actualHeight = /*options.outHeight*/ bmp.height
    var actualWidth = /*options.outWidth*/ bmp.width

    var imgRatio = actualWidth.toFloat() / actualHeight.toFloat()
    val maxRatio = maxWidth / maxHeight

    if (actualHeight > maxHeight || actualWidth > maxWidth) {
        when {
            imgRatio < maxRatio -> {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            }

            imgRatio > maxRatio -> {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            }

            else -> {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }
    }

    try {
        scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

    val ratioX = actualWidth / bmp.width.toFloat()
    val ratioY = actualHeight / bmp.height.toFloat()
    val middleX = actualWidth / 2.0f
    val middleY = actualHeight / 2.0f

    val scaleMatrix = Matrix()
    scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

    val canvas = Canvas(scaledBitmap)
    canvas.setMatrix(scaleMatrix)
    canvas.drawBitmap(
        bmp,
        middleX - bmp.width / 2,
        middleY - bmp.height / 2,
        Paint(Paint.FILTER_BITMAP_FLAG)
    )
    bmp.recycle()

    return scaledBitmap
}


fun storeImage(image: Bitmap, pictureFile: File): File? {
    if (!pictureFile.exists()) {
        pictureFile.createNewFile()
    }
    try {
        val fos = FileOutputStream(pictureFile)
        image.compress(Bitmap.CompressFormat.PNG, 90, fos)
        fos.close()
        return pictureFile
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}