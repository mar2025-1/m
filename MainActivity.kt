package com.example.mard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // عناصر الواجهة كما في activity_main.xml
    private lateinit var etSearch: EditText
    private lateinit var btnDownload: Button
    private lateinit var btnSettings: Button
    private lateinit var rvVideos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ربط العناصر
        etSearch = findViewById(R.id.etSearch)
        btnDownload = findViewById(R.id.btnDownload)
        btnSettings = findViewById(R.id.btnSettings)
        rvVideos = findViewById(R.id.rvVideos)

        // تهيئة RecyclerView
        rvVideos.layoutManager = LinearLayoutManager(this)
        rvVideos.setHasFixedSize(true)

        // تفعيل الأزرار
        setupButtons()
    }

    private fun setupButtons() {
        // زر التنزيل
        btnDownload.setOnClickListener {
            val url = etSearch.text.toString()
            if (url.isNotEmpty()) {
                startDownload(url)
            } else {
                showToast("Please enter video URL")
            }
        }

        // زر الإعدادات
        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun startDownload(url: String) {
        // سيتم تنفيذها في VideoDownloader.kt
        showToast("Downloading: $url")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // سيتم استكمالها عند تنفيذ VideoDbHelper
    private fun loadDownloadedVideos() {
        // rvVideos.adapter = VideosAdapter(listOf())
    }
}