@AndroidEntryPoint
class DownloadCompleteReceiver : BroadcastReceiver() {

    @Inject lateinit var downloadRepository: DownloadRepository

    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                val downloadId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, -1
                )
                if (downloadId != -1L) {
                    handleDownloadComplete(context, downloadId)
                }
            }
        }
    }

    private fun handleDownloadComplete(context: Context, downloadId: Long) {
        val downloadManager = context.getSystemService(
            Context.DOWNLOAD_SERVICE
        ) as DownloadManager

        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)

        if (cursor.moveToFirst()) {
            val status = cursor.getInt(
                cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            )
            when (status) {
                DownloadManager.STATUS_SUCCESSFUL -> {
                    val uri = downloadManager.getUriForDownloadedFile(downloadId)
                    downloadRepository.saveDownload(uri)
                    showNotification(context, "تم التنزيل بنجاح")
                }
                DownloadManager.STATUS_FAILED -> {
                    showNotification(context, "فشل التنزيل")
                }
            }
        }
        cursor.close()
    }

    private fun showNotification(context: Context, message: String) {
        NotificationHelper(context).showDownloadNotification(message)
    }
}