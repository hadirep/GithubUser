package com.example.submission3githubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission3githubuser.R
import com.example.submission3githubuser.databinding.ActivityDetailBinding
import com.example.submission3githubuser.ui.viewmodel.DetailViewModel
import com.example.submission3githubuser.ui.viewmodel.ViewModelFactoryFavorite
import com.example.submission3githubuser.data.Result
import com.example.submission3githubuser.ui.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.detail)

        val factory: ViewModelFactoryFavorite = ViewModelFactoryFavorite.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        val username = intent.getStringExtra(EXTRA_DETAIL) as String
        detailViewModel.getDetailUser(username)

        detailViewModel.getDetailUser(username).observe(this) {
            if (it != null) {
                when(it) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.apply{
                            progressBar.visibility = View.GONE
                            val user = it.data
                            Glide.with(this@DetailActivity)
                                .load(user.avatarUrl)
                                .circleCrop()
                                .into(binding.ivAvatar)
                            tvName.text = user.name
                            tvUsername.text = StringBuilder("Username: ").append(user.username)
                            tvFollowers.text = resources.getString(R.string.followers, user.followers)
                            tvFollowing.text = resources.getString(R.string.following, user.following)

                            if (user.isFavorite) {
                                btnFavorite.setImageDrawable(ContextCompat.getDrawable(binding.btnFavorite.context, R.drawable.ic_favorite))
                            } else {
                                btnFavorite.setImageDrawable(ContextCompat.getDrawable(binding.btnFavorite.context, R.drawable.ic_favorite_border))
                            }

                            btnFavorite.setOnClickListener {
                                if (user.isFavorite) {
                                    detailViewModel.deleteFavorite(user)
                                } else {
                                    detailViewModel.insertFavorite(user)
                                }
                            }

                            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity)
                            sectionsPagerAdapter.username = user.username

                            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
                            viewPager.adapter = sectionsPagerAdapter

                            val tabs: TabLayout = findViewById(R.id.tabs)
                            TabLayoutMediator(tabs, viewPager) { tab, position ->
                                tab.text = resources.getString(TAB_TITLES[position])
                            }.attach()
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }
}