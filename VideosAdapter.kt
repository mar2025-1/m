package com.example.mard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VideosAdapter(
    private var videos: List<VideoMeta>,
    private val onItemClick: (VideoMeta) -> Unit,
    private val onDeleteClick: (VideoMeta) -> Unit
) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    // تحديث البيانات
    fun updateVideos(newVideos: List<VideoMeta>) {
        this.videos = newVideos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount() = videos.size

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvVideoTitle)
        private val tvDuration: TextView = itemView.findViewById(R.id.tvVideoDuration)
        private val tvQuality: TextView = itemView.findViewById(R.id.tvVideoQuality)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteVideo)

        fun bind(video: VideoMeta) {
            tvTitle.text = video.title
            tvDuration.text = video.duration
            tvQuality.text = video.quality

            itemView.setOnClickListener { onItemClick(video) }
            btnDelete.setOnClickListener { onDeleteClick(video) }
        }
    }
}