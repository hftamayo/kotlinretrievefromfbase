package com.hftamayo.kotlinretrievefromfbase

import android.app.ProgressDialog
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
    private var db : FirebaseFirestore? = null
    private var databaseReference : DatabaseReference? = null
    var eventListener : ValueEventListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersArrayList: ArrayList<User>
    private lateinit var userAdapter : UserDataAdapter
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initRecyclerView()
        showProgressBar()
        getData()
    }

    private fun initView(){
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun initRecyclerView(){
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserDataAdapter()
        recyclerView.adapter = userAdapter

    }

    private fun showProgressBar(){
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Fetching data...")
        progressDialog!!.show()
    }

    private fun getData2(){
        usersArrayList = ArrayList()
        db = FirebaseFirestore.getInstance()
        db!!.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        db!!
            .collection("users")
            .get()
            .addOnSuccessListener { data ->
                try{
                    if (data != null){
                        for(record in data) {
                            val fName = record["firstName"].toString()
                            val lName = record["lastName"].toString()
                            val age = record["age"].toString().toInt()
                            val user = User(firstName = fName, lastName = lName, age = age)
                            usersArrayList.add(user)
                        }
                        userAdapter.notifyDataSetChanged()
                        //Toast.makeText(context, "Data read successfully", Toast.LENGTH_LONG).show()
                    } else {
                        //Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show()
                    }
                }catch (ex: Exception) {
                    ex.message?.let { Log.e(TAG, it) }
                }
            }.addOnFailureListener{
                e -> Log.e(TAG, "Error in the connection process", e)
            }

    }

    private fun getData(){
        usersArrayList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        eventListener = databaseReference!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersArrayList.clear()
                for(itemSnapshot in snapshot.children){
                    val userClass = itemSnapshot.getValue(User::class.java)
                    if(userClass != null){
                        usersArrayList.add(userClass)
                    }
                }
                userAdapter.notifyDataSetChanged()
                if(progressDialog?.isShowing == true){
                    progressDialog!!.dismiss()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if(progressDialog?.isShowing == true){
                    progressDialog!!.dismiss()
                }
            }
        })
    }
}