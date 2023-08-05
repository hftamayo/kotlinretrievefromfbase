package com.hftamayo.kotlinretrievefromfbase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    private var myAdapter: MyAdapter? = null
    private var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<R.id.recyclerView>()
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(new LinearLayoutManager(this))

        db = FirebaseFirestore.getInstance()
        userArrayList = new ArrayList<User>()
        myAdapter = new MyAdapter(MainActivity.this.userArrayLst)

        EventChangeListener()

    }

    private void EventChangeListener() {

    }
}