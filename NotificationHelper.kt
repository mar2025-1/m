@AndroidEntryPoint
class NotificationHelper @Inject constructor(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager

    private val channelId = "download_channel"
    private val notificationId = 1

    init {
        createNotificationChannel()
    }

    fun showProgressNotification(
        downloadId: Long,
        title: String,
        progress: Int,
        total: Int
    ) {
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Downloading: $title")
            .setSmallIcon(R.drawable.ic_download)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(total, progress, false)
            .build()

        notificationManager.notify(downloadId.hashCode(), notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Downloads",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Download progress notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}