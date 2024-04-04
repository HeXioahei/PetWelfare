package com.example.petwelfare.logic.model

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

    //@RequiresApi(Build.VERSION_CODES.Q)
    fun getImageFileFromUri(context: Context, imageUri: Uri): File? {
        // 检查Android版本，对于Android 10及以上版本，使用新的 MediaStore API
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getImageFileFromUriAboveQ(context, imageUri)
        } else {
            getImageFileFromUriUnderQ(context, imageUri)
        }
    }

    private fun getImageFileFromUriAboveQ(context: Context, imageUri: Uri): File? {
        val inputStream: InputStream = context.contentResolver.openInputStream(imageUri)
            ?: return null
        /*使用 context.contentResolver.openInputStream(imageUri) 打开与 imageUri 关联的输入流。
        如果输入流为 null（即无法打开输入流），函数将返回一个空字符串对应的 File 对象
        （这实际上是一个错误处理，应该返回一个更有意义的值或抛出异常）。*/

        // 创建一个临时文件来保存图片内容
        val tempFile = File(context.cacheDir, "temp_image.jpg")
        /*使用 context.cacheDir 获取应用的缓存目录。
        在缓存目录中创建一个名为 temp_image.jpg 的临时文件。*/
        val outputStream: OutputStream = FileOutputStream(tempFile)
        /*创建一个 FileOutputStream （输出流），用于将图片内容写入临时文件。*/

        // 将图片内容从输入流复制到输出流（临时文件）（还不是很明白）
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)     // 创建一个用于储存输入流数据的数组
        var bytesRead: Int
        try {
            bytesRead = inputStream.read(buffer)      // 将输入流中的数据储存到数组中并产生片段数据
            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bytesRead)  // 输出流一点一点地读取片段数据
                bytesRead = inputStream.read(buffer)
            }
            /*使用一个循环，从输入流中读取数据，然后写入到输出流（即临时文件）中，直到读取完所有数据。*/
        } catch (e: Exception) {
            e.printStackTrace()
            inputStream.close()
            outputStream.close()
            return null
            /*使用 try-catch 块捕获可能出现的异常。
            如果出现异常，打印堆栈跟踪，关闭输入流和输出流，并返回一个空字符串对应的 File 对象
            （同样，这这种处理方式不太好）。*/
        } finally {
            inputStream.close()
            outputStream.close()
            /* finally 块确保无论是否出现异常，输入流和输出流都会被关闭。*/
        }

        return tempFile
        /*
        * 总结：
        * 这个函数提供了一个从 URI 读取图片内容并保存到临时文件的方法，这对于处理从媒体库或其他来源获取的图片非常有用。
        * 然而，需要注意的是，函数在出现错误时返回了一个空字符串对应的 File 对象，这可能不是最佳的错误处理策略。
        * 更好的做法可能是抛出一个异常或返回一个 null 值，并让调用者决定如何处理错误。
        * 此外，代码中没有对临时文件的删除操作，这可能会导致缓存目录中的文件堆积，需要在使用完毕后手动删除或通过其他机制进行管理。
        * */
    }

    //@RequiresApi(Build.VERSION_CODES.Q)   （这里的注释是个错误）
    private fun getImageFileFromUriUnderQ(context: Context, imageUri: Uri): File? {

        // 查询图片路径
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        /* projection : 这是一个字符串数组，用于指定从 MediaStore 查询哪些列。
        这里只查询了 DATA 列，它通常包含文件的路径。*/
        val cursor = context.contentResolver.query(imageUri, projection, null, null, null)
        /* cursor : 通过 contentResolver.query 方法执行查询，并返回一个 Cursor （光标） 对象，这个对象包含了查询结果。*/

        // 处理查询结果
        if (cursor != null) {
            // 如果cursor不为null，则尝试将光标移动到第一行数据。
            if (cursor.moveToFirst()) {
                // 通过 getColumnIndexOrThrow 获取 DATA 列的索引。
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                // 使用getString方法从当前行中获取该列的值，即图片的路径。
                val imagePath = cursor.getString(columnIndex)
                Log.d("pathname", imagePath.toString())
                return File(imagePath)
            }
            cursor.close()  // 关闭 cursor
        }

        return null
    }

    /*
    总结：
    这个函数提供了一个在Android Q以下版本中从Uri获取图片文件路径的方法。

    然而，它有几个问题需要注意：
        * @RequiresApi注解似乎不正确，应该与函数实际支持的API级别相匹配。
        * 返回空字符串对应的File对象不是好的错误处理策略。
        * 在Android Q及更高版本中，直接访问文件路径的方式不再推荐，因此这个函数可能在更高版本的Android上不起作用。

    为了改进这个函数，可以考虑：
        * 修改@RequiresApi注解以反映正确的API级别。
        * 返回null或抛出一个异常来表示错误情况。
        * 对于Android Q及更高版本，使用像getImageFileFromUriAboveQ函数那样的输入/输出流方式来处理图片。
     */
}