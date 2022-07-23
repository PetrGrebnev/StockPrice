package com.example.stockprice.screen.activity

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.stockprice.R
import java.util.*

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        PreferenceManager.getDefaultSharedPreferences(this@SettingsActivity)
            .unregisterOnSharedPreferenceChangeListener(this@SettingsActivity)
        this.finish()
        super.onBackPressed()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "language") {
            when (sharedPreferences?.getString(key, PREFERENCE_LANGUAGE)) {
                "ru" -> setLocale("ru")
                "en" -> setLocale("en")
            }
        }
        if (key == "dark_theme") {
            when (sharedPreferences?.getBoolean(key, PREFERENCE_THEME)) {
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    saveTheme(false)
                }
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    saveTheme(true)
                }
                null -> Toast.makeText(
                    this,
                    "Oops, you're too kind",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun saveTheme(nightTheme: Boolean) {
        val editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit()
        editor.putBoolean("My_theme", nightTheme)
        editor.apply()
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        recreate()

        val editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit()
        editor.putString("My_lang", lang)
        editor.apply()
    }

    companion object {
        private const val PREFERENCE_THEME = false
        private const val PREFERENCE_LANGUAGE = "en"
    }
}