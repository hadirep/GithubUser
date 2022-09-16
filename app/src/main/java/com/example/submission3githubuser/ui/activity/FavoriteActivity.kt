package com.example.submission3githubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3githubuser.R
import com.example.submission3githubuser.data.remote.ItemsItem
import com.example.submission3githubuser.databinding.ActivityFavoriteBinding
import com.example.submission3githubuser.ui.adapter.ListAdapter
import com.example.submission3githubuser.ui.viewmodel.FavoriteViewModel
import com.example.submission3githubuser.ui.viewmodel.ViewModelFactoryFavorite

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.favorite)

        val factory: ViewModelFactoryFavorite = ViewModelFactoryFavorite.getInstance(this)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        viewModel.getFavoriteUser().observe(this) { item ->
            binding.progressBar.visibility = View.GONE
            val listUser = item.map {
                ItemsItem(it.username, it.avatarUrl)
            }

            val adapter = ListAdapter(listUser as ArrayList<ItemsItem>)
            binding.apply{
                rvUser.adapter = adapter
                rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                rvUser.setHasFixedSize(true)
            }
            adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                        startActivity(it)
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}