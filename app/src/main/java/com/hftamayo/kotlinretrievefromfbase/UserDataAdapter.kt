package com.hftamayo.kotlinretrievefromfbase

import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserDataAdapter: RecyclerView.Adapter<UserDataAdapter.UserViewHolder>() {
    private var userArrayList: ArrayList<User> = ArrayList()

    fun addItems(items: ArrayList<User>){
        this.userArrayList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = UserViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int){
        val userItem = userArrayList[position]
        holder.bindView(userItem)
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private var firstName = view.findViewById<TextView>(R.id.tvFirstName)
        private var lastName = view.findViewById<TextView>(R.id.tvLastName)
        private var age = view.findViewById<TextView>(R.id.tvAge)
        fun bindView(std:User){
            firstName.text = std.firstName
            lastName.text = std.lastName
            age.text = std.age.toString()
        }
    }

}
