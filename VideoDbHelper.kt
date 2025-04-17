package com.example.mard

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [VideoMeta::class],
    version = 1,
    exportSchema = false
)
abstract class VideoDbHelper : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {
        @Volatile
        private var INSTANCE: VideoDbHelper? = null

        fun getDatabase(context: Context): VideoDbHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VideoDbHelper::class.java,
                    "videos_database"
                )
                    .fallbackToDestructiveMigration() // لتجنب أخطاء الترقية
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// واجهة عمليات قاعدة البيانات
@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: VideoMeta)

    @Delete
    suspend fun deleteVideo(video: VideoMeta)

    @Query("SELECT * FROM videos ORDER BY downloadDate DESC")
    fun getAllVideos(): List<VideoMeta>

    @Query("SELECT * FROM videos WHERE title LIKE :query")
    fun searchVideos(query: String): List<VideoMeta>
}