package com.example.submission3githubuser.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3githubuser.R
import com.example.submission3githubuser.databinding.ActivityMainBinding
import com.example.submission3githubuser.ui.viewmodel.MainViewModel
import com.example.submission3githubuser.data.remote.ItemsItem
import com.example.submission3githubuser.ui.activity.MainActivity.Companion.SETTINGS
import com.example.submission3githubuser.ui.adapter.ListAdapter
import com.example.submission3githubuser.ui.viewmodel.SettingsViewModel
import com.example.submission3githubuser.ui.viewmodel.ViewModelFactoryDarkMode
import com.example.submission3githubuser.utils.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SETTINGS)

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModelDarkMode = ViewModelProvider(this, ViewModelFactoryDarkMode(pref))[SettingsViewModel::class.java]
        viewModelDarkMode.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            binding.apply {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }

        viewModel.itemsItem.observe(this@MainActivity) { item ->
            val adapter = ListAdapter(item)
            binding.rvUser.adapter = adapter
            adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    Intent(this@MainActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                        startActivity(it)
                    }
                }
            })
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.apply{
            val layoutAdapter = LinearLayoutManager(this@MainActivity)
            rvUser.layoutManager = layoutAdapter
            rvUser.setHasFixedSize(true)
        }

        binding.svSearch.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                viewModel.getSearchUser(q)
                binding.svSearch.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
                true
            }
            R.id.favorite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val SETTINGS = "settings"
    }
}