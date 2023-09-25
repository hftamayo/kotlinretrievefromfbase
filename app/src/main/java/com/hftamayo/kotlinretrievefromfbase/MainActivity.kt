package com.hftamayo.kotlinretrievefromfbase

import android.app.ProgressDialog
import android.content.ClipData.Item
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter : UserDataAdapter
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showProgressBar()
        initRecyclerView()
        getData()
    }

    private fun showProgressBar(){
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Fetching data...")
        progressDialog!!.show()
    }

    private fun initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getData(){
        val db = FirebaseFirestore.getInstance()
        //the database was binded during app's auth, we just need to refer to collection's name
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<User>()
                for(document in result){
                    val id = document.id
                    val firstName = document.getString("firstName") ?: ""
                    val lastName = document.getString("lastName") ?: ""
                    val age = document.getString("age") ?: ""
                    itemList.add(User(firstName, lastName, age))
                }
                userAdapter = UserDataAdapter(itemList)
                recyclerView.adapter = userAdapter
                if(progressDialog?.isShowing == true){
                    progressDialog!!.dismiss()
                }
            }
            .addOnFailureListener{
                exception -> Log.d(TAG, "Error getting documents", exception)
                if(progressDialog?.isShowing == true){
                    progressDialog!!.dismiss()
                }
            }
    }
}