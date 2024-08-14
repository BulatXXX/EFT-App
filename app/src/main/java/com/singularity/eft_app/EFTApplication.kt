package com.singularity.eft_app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EFTApplication: Application() {
    var darkTheme = false
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        sharedPreferences = applicationContext.getSharedPreferences("MODE" , Context.MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean("DarkThemeEnabled",false) == true
        switchTheme(darkTheme)
        super.onCreate()
    }
    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled

        sharedPreferences.edit()?.putBoolean("DarkThemeEnabled" , darkThemeEnabled)?.apply()

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}