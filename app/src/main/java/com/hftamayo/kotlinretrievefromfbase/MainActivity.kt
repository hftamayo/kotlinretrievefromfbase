package com.hftamayo.kotlinretrievefromfbase

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var usersArrayList: ArrayList<User>
    private var userAdapter : UserDataAdapter? = null
    private var databaseReference: DatabaseReference? = null
    private var progressDialog: ProgressDialog? = null
    private var eventListener : ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Fetching data...")
        progressDialog!!.show()

        initView()
        initRecyclerView()
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

    private fun getData(){
        databaseReference = FirebaseDatabase.getInstance().getReference("todo")
        eventListener = databaseReference!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersArrayList.clear()
                for(itemSnapshot in snapshot.children){
                    val userClass = itemSnapshot.getValue(User::class.java)
                    if(userClass != null){
                        usersArrayList.add(userClass)
                    }
                }
                userAdapter?.notifyDataSetChanged()
                if(progressDialog?.isShowing() == true){
                    progressDialog!!.dismiss()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if(progressDialog?.isShowing() == true){
                    progressDialog!!.dismiss()
                }
            }
        })
    }
}