package com.example.myapplication.ui.setting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySettingsBinding
import com.example.myapplication.ui.main.dataStore

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel by viewModels<SettingViewModel> { SettingViewModelFactory(pref) }

        settingViewModel.getThemeSettings(isDarkModeOn()).observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.apply {
            setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                settingViewModel.saveThemeSetting(isChecked)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SettingsActivity::class.java)
            context.startActivity(starter)
        }
    }
}
