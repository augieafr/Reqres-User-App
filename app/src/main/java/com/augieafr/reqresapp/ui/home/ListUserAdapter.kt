package com.augieafr.reqresapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.augieafr.reqresapp.databinding.UserItemBinding
import com.augieafr.reqresapp.ui.model.UserUiModel
import com.bumptech.glide.Glide

class ListUserAdapter : PagingDataAdapter<UserUiModel, ListUserViewHolder>(
    object : DiffUtil.ItemCallback<UserUiModel>() {
        override fun areItemsTheSame(oldItem: UserUiModel, newItem: UserUiModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserUiModel, newItem: UserUiModel) =
            oldItem == newItem
    }
) {
    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserViewHolder(binding)
    }
}

class ListUserViewHolder(
    private val binding: UserItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserUiModel) = with(binding) {
        val fullName = "${user.firstName} ${user.lastName}"
        Glide.with(root.context)
            .load(user.avatar)
            .circleCrop()
            .into(imgProfile)
        tvName.text = fullName
        tvEmail.text = user.email
    }
}
