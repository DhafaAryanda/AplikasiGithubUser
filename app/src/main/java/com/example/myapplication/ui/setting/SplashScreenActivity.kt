package com.example.myapplication.ui.setting

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.databinding.ActivitySplashScreenBinding
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.ui.main.dataStore
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel by viewModels<SettingViewModel> { SettingViewModelFactory(pref) }
        settingViewModel.getThemeSettings(isDarkModeOn()).observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        activityScope.launch {
            val delaySplashScreen = 3000L
            delay(delaySplashScreen)
            runOnUiThread {
                MainActivity.start(this@SplashScreenActivity)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.coroutineContext.cancelChildren()
    }
}