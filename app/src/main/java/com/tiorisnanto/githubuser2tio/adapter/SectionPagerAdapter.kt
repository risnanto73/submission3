package com.tiorisnanto.githubuser2tio.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tiorisnanto.githubuser2tio.fragment.FollowersFragment
import com.tiorisnanto.githubuser2tio.fragment.FollowingsFragment

class SectionPagerAdapter(activity: AppCompatActivity, data: Bundle) :
    FragmentStateAdapter(activity) {

    private val fragmentBundle: Bundle = data

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingsFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}