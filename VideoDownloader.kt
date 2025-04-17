package com.example.mard

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object VideoDownloader {

    // دالة رئيسية للتنزيل
    suspend fun downloadVideo(
        context: Context,
        url: String,
        quality: String,
        title: String
    ): Long {
        return withContext(Dispatchers.IO) {
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val request = DownloadManager.Request(url.toUri()).apply {
                setTitle("Downloading: $title")
                setDescription("Quality: $quality")
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "MARD/${sanitizeFileName(title)}_$quality.mp4"
                )
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setAllowedOverMetered(true)
                setAllowedOverRoaming(true)
            }

            downloadManager.enqueue(request)
        }
    }

    // دالة مساعدة لتنظيف اسم الملف
    private fun sanitizeFileName(name: String): String {
        return name.replace("[^a-zA-Z0-9-_.]".toRegex(), "_")
    }

    // دالة للحصول على مسار الملف المحفوظ
    fun getDownloadedFilePath(context: Context, title: String, quality: String): String? {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "MARD/${sanitizeFileName(title)}_$quality.mp4"
        )
        return if (file.exists()) file.absolutePath else null
    }
}