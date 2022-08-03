package com.tiorisnanto.githubuser2tio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tiorisnanto.githubuser2tio.R
import com.tiorisnanto.githubuser2tio.databinding.ItemRowUserBinding
import com.tiorisnanto.githubuser2tio.model.ResponseUser
import com.tiorisnanto.githubuser2tio.viewmodel.FavoriteViewModel

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClick(data: ResponseUser)
    }

    private val listUsers = ArrayList<ResponseUser>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(user: ResponseUser) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions())
                    .into(imgItemPhoto)
                tvUsername.text = user.username
                itemView.setOnClickListener { onItemClickCallback?.onItemClick(user) }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListUser(users: ArrayList<ResponseUser>) {
        listUsers.clear()
        listUsers.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    override fun getItemCount(): Int = listUsers.size
}