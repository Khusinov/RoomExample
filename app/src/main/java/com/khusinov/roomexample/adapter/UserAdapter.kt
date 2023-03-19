package com.khusinov.roomexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khusinov.roomexample.database.UserDatabase
import com.khusinov.roomexample.databinding.ItemUsersBinding
import com.khusinov.roomexample.entity.User


class UserAdapter(var list: List<User>, var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.VH>() {

    lateinit var userDatabase: UserDatabase

    inner class VH(var itemUsersBinding: ItemUsersBinding) :
        RecyclerView.ViewHolder(itemUsersBinding.root) {

        fun onBind(user: User, position: Int) {
            itemUsersBinding.firstName.text = user.firstName
            itemUsersBinding.lastName.text = user.lastName

            itemUsersBinding.editBtn.setOnClickListener {
                onItemClickListener.onItemEdit(user, position)
            }

            itemUsersBinding.deleteBtn.setOnClickListener {
                onItemClickListener.onItemDelete(user, position)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    interface OnItemClickListener {
        fun onItemDelete(user: User, position: Int)
        fun onItemEdit(user: User, position: Int)
    }
}