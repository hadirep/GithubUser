package com.example.submission3githubuser.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3githubuser.R
import com.example.submission3githubuser.databinding.FragmentFollowBinding
import com.example.submission3githubuser.ui.adapter.ListAdapter
import com.example.submission3githubuser.ui.viewmodel.FollowViewModel

class FollowersFragment : Fragment(R.layout.fragment_follow) {
    private lateinit var binding: FragmentFollowBinding
    private val viewModel by viewModels<FollowViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)

        val username = arguments?.getString(ARG_NAME).toString()
        viewModel.getFollowers(username)

        viewModel.itemItem.observe(viewLifecycleOwner) {
            val adapter = ListAdapter(it)
            binding.rvUser.adapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.apply{
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.rvUser.layoutManager = layoutManager
            binding.rvUser.setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_NAME = "FollowersFragment"
    }
}