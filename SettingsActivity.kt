package com.example.mard

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    // عناصر الواجهة كما في activity_settings.xml
    private lateinit var switchDarkMode: SwitchCompat
    private lateinit var spinnerLanguage: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // تهيئة SharedPreferences
        prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // ربط العناصر
        switchDarkMode = findViewById(R.id.switchDarkMode)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)

        // تهيئة الإعدادات
        setupDarkMode()
        setupLanguageSelector()
    }

    private fun setupDarkMode() {
        // تعيين الحالة الحالية
        switchDarkMode.isChecked = isDarkModeEnabled()

        // تفعيل التغيير
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            setDarkMode(isChecked)
        }
    }

    private fun setupLanguageSelector() {
        // تحميل قائمة اللغات من arrays.xml
        ArrayAdapter.createFromResource(
            this,
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerLanguage.adapter = adapter
        }

        // تحديد اللغة الحالية
        spinnerLanguage.setSelection(
            if (getCurrentLanguage() == "ar") 1 else 0
        )

        // تغيير اللغة عند الاختيار
        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val langCode = if (position == 1) "ar" else "en"
                if (getCurrentLanguage() != langCode) {
                    setAppLanguage(langCode)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun isDarkModeEnabled(): Boolean {
        return prefs.getBoolean("DARK_MODE", false)
    }

    private fun setDarkMode(enabled: Boolean) {
        // تطبيق الوضع
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        // حفظ التفضيل
        prefs.edit().putBoolean("DARK_MODE", enabled).apply()
    }

    private fun getCurrentLanguage(): String {
        return prefs.getString("APP_LANGUAGE", Locale.getDefault().language) ?: "en"
    }

    private fun setAppLanguage(langCode: String) {
        // تغيير لغة التطبيق
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        // حفظ اللغة المفضلة
        prefs.edit().putString("APP_LANGUAGE", langCode).apply()

        // إعادة تشغيل النشاط
        recreate()
    }
}