package com.example.mard

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * نموذج بيانات الفيديو مع حقل [id] تلقائي الإضافة.
 * يتوافق مع جدول قاعدة البيانات ويحتوي على معلومات أساسية عن الفيديو.
 */
@Entity(tableName = "videos")
data class VideoMeta(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,          // عنوان الفيديو
    val duration: String,       // المدة (بتنسيق "00:00:00")
    val quality: String,        // الجودة (مثال: "720p")
    val path: String,           // مسار الملف في التخزين
    val sourceUrl: String,      // رابط المصدر الأصلي
    val downloadDate: Date = Date()  // تاريخ التنزيل التلقائي
) {
    /**
     * دالة مساعدة لعرض معلومات الفيديو
     */
    fun getFormattedInfo(): String {
        return "$title ($quality) - $duration"
    }
}