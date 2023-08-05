package com.hftamayo.kotlinretrievefromfbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<Task> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.myViewHolder holder, int position) {
        User user = userArrayList.get(position);

        holder.firstName.setText(user.firstName);
        holder.lastName.setText(user.lastName);
        holder.age.setText(String.valueOf(user.age));

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastName, age;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.tvFirstName);
            lastName = itemView.findViewById(R.id.tvLastName);
            age = itemView.findViewById(R.id.tvAge);
        }
    }
}
