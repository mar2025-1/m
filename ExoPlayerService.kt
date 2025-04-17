@AndroidEntryPoint
class ExoPlayerService : LifecycleService() {

    private var exoPlayer: ExoPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                val videoUri = intent.getParcelableExtra<Uri>(EXTRA_VIDEO_URI)
                videoUri?.let { preparePlayer(it) }
            }
            ACTION_STOP -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun preparePlayer(uri: Uri) {
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            play()
        }
    }

    override fun onDestroy() {
        exoPlayer?.release()
        super.onDestroy()
    }

    companion object {
        const val ACTION_PLAY = "PLAY_VIDEO"
        const val ACTION_STOP = "STOP_PLAYER"
        const val EXTRA_VIDEO_URI = "VIDEO_URI"
    }
}