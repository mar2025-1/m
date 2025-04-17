@AndroidEntryPoint
class DownloadService : LifecycleService() {

    @Inject lateinit var downloadManager: DownloadManager
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var workManager: WorkManager

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onCreate() {
        super.onCreate()
        startForeground(
            NotificationHelper.DOWNLOAD_NOTIFICATION_ID,
            notificationHelper.createDownloadNotification("", 0)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { handleCommand(it) }
        return START_STICKY
    }

    private fun handleCommand(intent: Intent) {
        when (intent.action) {
            ACTION_START_DOWNLOAD -> {
                val url = intent.getStringExtra(EXTRA_URL) ?: return
                val title = intent.getStringExtra(EXTRA_TITLE) ?: "ملف"
                startDownload(url, title)
            }
            ACTION_STOP_DOWNLOAD -> stopDownloads()
        }
    }

    private fun startDownload(url: String, title: String) {
        serviceScope.launch {
            try {
                val file = File(
                    getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    URLUtil.guessFileName(url, null, null)
                )

                val request = DownloadManager.Request(Uri.parse(url)).apply {
                    setTitle(title)
                    setDestinationUri(Uri.fromFile(file))
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    setAllowedOverMetered(true)
                    setAllowedOverRoaming(true)
                }

                val downloadId = downloadManager.enqueue(request)
                trackProgress(downloadId, title, file.path)
            } catch (e: Exception) {
                notificationHelper.showError("فشل التنزيل: ${e.localizedMessage}")
                stopSelf()
            }
        }
    }

    private fun trackProgress(downloadId: Long, title: String, path: String) {
        serviceScope.launch {
            var lastProgress = -1
            while (isActive) {
                val progress = getDownloadProgress(downloadId)
                if (progress != lastProgress) {
                    notificationHelper.updateProgress(title, progress, path)
                    lastProgress = progress
                }
                if (progress >= 100) break
                delay(1000)
            }
            stopSelf()
        }
    }

    private fun getDownloadProgress(downloadId: Long): Int {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)
        return if (cursor.moveToFirst()) {
            val bytesDownloaded = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            val bytesTotal = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            ((bytesDownloaded * 100) / bytesTotal).toInt()
        } else 0
    }

    private fun stopDownloads() {
        downloadManager.remove(DownloadManager.Request::class.java.getField("COLUMN_ID").get(null) as LongArray)
        stopSelf()
    }

    override fun onDestroy() {
        serviceJob.cancel()
        super.onDestroy()
    }

    companion object {
        const val ACTION_START_DOWNLOAD = "com.example.mard.START_DOWNLOAD"
        const val ACTION_STOP_DOWNLOAD = "com.example.mard.STOP_DOWNLOAD"
        const val EXTRA_URL = "DOWNLOAD_URL"
        const val EXTRA_TITLE = "DOWNLOAD_TITLE"

        fun startDownload(context: Context, url: String, title: String) {
            val intent = Intent(context, DownloadService::class.java).apply {
                action = ACTION_START_DOWNLOAD
                putExtra(EXTRA_URL, url)
                putExtra(EXTRA_TITLE, title)
            }
            ContextCompat.startForegroundService(context, intent)
        }
    }
}