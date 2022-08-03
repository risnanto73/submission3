package com.tiorisnanto.githubuser2tio.fragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiorisnanto.githubuser2tio.R
import com.tiorisnanto.githubuser2tio.activity.DetailActivity
import com.tiorisnanto.githubuser2tio.adapter.ListUserAdapter
import com.tiorisnanto.githubuser2tio.databinding.FragmentFollowingsBinding
import com.tiorisnanto.githubuser2tio.viewmodel.FollowingsViewModel

class FollowingsFragment : Fragment() {

    private var _binding: FragmentFollowingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingsViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }

        showLoading(true)
        followingViewModel = FollowingsViewModel()
//        followingViewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.NewInstanceFactory()
//        ).get(FollowingsViewModel::class.java)
        followingViewModel.setListFollowing(username)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, {
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