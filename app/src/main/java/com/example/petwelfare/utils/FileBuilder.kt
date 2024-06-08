package com.example.petwelfare.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object FileBuilder {

    fun getImageFileFromUri(context: Context, imageUri: Uri, number: Int): File? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getImageFileFromUriAboveQ(context, imageUri, number)
        } else {
            getImageFileFromUriUnderQ(context, imageUri, number)
        }
    }

    private fun getImageFileFromUriAboveQ(context: Context, imageUri: Uri, number: Int): File? {
        val inputStream: InputStream = context.contentResolver.openInputStream(imageUri)
            ?: return null

        val tempFile = File(context.cacheDir, "temp_image$number.jpg")

        val outputStream: OutputStream = FileOutputStream(tempFile)

        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var bytesRead: Int

        try {
            bytesRead = inputStream.read(buffer)
            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bytesRead)
                bytesRead = inputStream.read(buffer)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            inputStream.close()
            outputStream.close()
            return null

        } finally {
            inputStream.close()
            outputStream.close()
        }

        return tempFile
    }

    private fun getImageFileFromUriUnderQ(context: Context, imageUri: Uri, number: Int): File? {

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(imageUri, projection, null, null, null)


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                val imagePath = cursor.getString(columnIndex)
                Log.d("pathname", imagePath.toString())
                return File(imagePath)
            }
            cursor.close()
        }

        return null
    }

}