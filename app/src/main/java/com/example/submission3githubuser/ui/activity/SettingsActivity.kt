package com.example.submission3githubuser.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission3githubuser.R
import com.example.submission3githubuser.databinding.ActivitySettingsBinding
import com.example.submission3githubuser.ui.activity.MainActivity.Companion.SETTINGS
import com.example.submission3githubuser.ui.viewmodel.SettingsViewModel
import com.example.submission3githubuser.ui.viewmodel.ViewModelFactoryDarkMode
import com.example.submission3githubuser.utils.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SETTINGS)

class SettingsActivity : AppCompatActivity() {
    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.settings)

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, ViewModelFactoryDarkMode(pref))[SettingsViewModel::class.java]
        viewModel.getThemeSettings().observe( this) { isDarkModeActive: Boolean ->
            binding.apply {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    switchTheme.isChecked = false
                }
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}