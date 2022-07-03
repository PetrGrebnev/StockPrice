package com.example.stockprice.screen

import android.app.Activity
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.stockprice.R
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var controller: NavController
    private lateinit var appBar: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        loadSettings()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller = findNavController(R.id.fragment_container_view)
        appBar = AppBarConfiguration(controller.graph)
        setupActionBarWithNavController(controller, appBar)
    }

    private fun loadSettings(){
        val sharedPreferences = getSharedPreferences("Setting", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_lang", PREFERENCE_LANGUAGE)
        val theme = sharedPreferences.getBoolean("My_theme", PREFERENCE_THEME)
        setLocale(language!!)
        setTheme(theme)
    }

    private fun setTheme(enableNightTheme: Boolean) {
        when (enableNightTheme) {
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    override fun onSupportNavigateUp(): Boolean {
        return controller.navigateUp(appBar) || super.onSupportNavigateUp()
    }

    companion object {
        private const val PREFERENCE_THEME = false
        private const val PREFERENCE_LANGUAGE = "en"
    }

}