package com.example.submission3githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission3githubuser.ui.tab.FollowersFragment
import com.example.submission3githubuser.ui.tab.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = Bundle().apply{
            putString(FollowersFragment.ARG_NAME, username)
            putString(FollowingFragment.ARG_NAME, username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}