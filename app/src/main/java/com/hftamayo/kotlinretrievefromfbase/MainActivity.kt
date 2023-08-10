package com.hftamayo.kotlinretrievefromfbase

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var usersArrayList: ArrayList<User>
    private var userAdapter : UserDataAdapter? = null
    private var db: FirebaseFirestore? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Fetching data...")
        progressDialog!!.show()

        initView()
        initRecyclerView()
    }

    private fun initView(){
        recyclerView = findViewById(R.id.recyclerView)
        db = FirebaseFirestore.getInstance()
    }

    private fun initRecyclerView(){
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserDataAdapter()
        recyclerView.adapter = userAdapter

    }

    private fun getData(){
        val dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<Post>()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost: onCancelled", databaseError.toException())
            }
        }
    }

}