package com.hftamayo.kotlinretrievefromfbase

import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class UserDataAdapter(private val itemList: List<User>): RecyclerView.Adapter<UserDataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val firstName: TextView = itemView.findViewById(R.id.tvFirstName)
        val lastName: TextView = itemView.findViewById(R.id.tvLastName)
        val age: TextView = itemView.findViewById(R.id.tvAge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val userItem = itemList[position]
        holder.firstName.text = userItem.firstName
        holder.lastName.text = userItem.lastName
        holder.age.text = userItem.age.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}
