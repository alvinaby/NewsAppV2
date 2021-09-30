package com.alvinaby.newsappv2.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate

class ThemeUtils(context: Context) {
    private val themePreferences: SharedPreferences = context.getSharedPreferences("ThemeMode", Context.MODE_PRIVATE)
    private val themeKey = "theme_type"
    private val themeDialog = AlertDialog.Builder(context)
    private var darkMode = themePreferences.getInt(themeKey, 0)
    set(value) = themePreferences.edit().putInt(themeKey, value).apply()

    fun checkTheme() {
        when (darkMode) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun changeTheme() {
        val theme = arrayOf("System default", "Light", "Dark")
        val chooseItem = darkMode

        themeDialog.setTitle("Theme")
        themeDialog.setSingleChoiceItems(theme, chooseItem) { dialog, item ->
            when (item) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    darkMode = 0
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    darkMode = 1
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    darkMode = 2
                }
            }
            dialog.dismiss()
        }
        themeDialog.create().show()
    }
}