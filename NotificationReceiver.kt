class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            "OPEN_DOWNLOAD" -> {
                val fileUri = intent.getParcelableExtra<Uri>("FILE_URI")
                fileUri?.let { openFile(context, it) }
            }
            "CANCEL_DOWNLOAD" -> {
                // إلغاء التنزيل الجاري
            }
        }
    }

    private fun openFile(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "video/*")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)
    }
}