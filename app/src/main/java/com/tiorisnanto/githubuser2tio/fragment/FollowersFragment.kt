package com.tiorisnanto.githubuser2tio.fragment

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiorisnanto.githubuser2tio.activity.DetailActivity
import com.tiorisnanto.githubuser2tio.adapter.ListUserAdapter
import com.tiorisnanto.githubuser2tio.databinding.FragmentFollowersBinding
import com.tiorisnanto.githubuser2tio.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var followerViewModel: FollowersViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollower.layoutManager = LinearLayoutManager(activity)
            rvFollower.setHasFixedSize(true)
            rvFollower.adapter = adapter
        }

        showLoading(true)
        followerViewModel = FollowersViewModel()
        followerViewModel.setListFollower(username)
        followerViewModel.getListFollower().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setListUser(it)
                showLoading(false)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }
}