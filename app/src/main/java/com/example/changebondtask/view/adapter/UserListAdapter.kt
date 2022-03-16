package com.example.changebondtask.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.changebondtask.R
import com.example.changebondtask.databinding.ItemUsersBinding
import com.example.changebondtask.view.model.Users

class UserListAdapter( var itemClickListener : (model : Users) -> Unit) : ListAdapter<Users, UserListAdapter.ViewHolder>(diffCallBack) {

    companion object{
        private val diffCallBack = object : DiffUtil.ItemCallback<Users>() {
            override fun areItemsTheSame(
                oldItem: Users,
                newItem: Users
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Users,
                newItem: Users
            ): Boolean {
                return isContentSame(oldItem,newItem)
            }

        }
        fun isContentSame(oldItem : Users, newItem : Users) : Boolean{

            return (oldItem.id == newItem.id)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemUsersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_users,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {

            val tempModel = currentList[position]

            holder.bindView(tempModel)


        } catch (e: Exception) {
        }


    }



    inner class ViewHolder(itemView: ItemUsersBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding: ItemUsersBinding = itemView

        fun bindView(model: Users) {

            binding.model = model
            itemView.setOnClickListener {
                itemClickListener(model)
            }


        }


    }

    override fun submitList(list: List<Users>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}