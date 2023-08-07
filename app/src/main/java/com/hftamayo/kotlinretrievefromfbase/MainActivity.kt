package com.hftamayo.kotlinretrievefromfbase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    private var myAdapter: MyAdapter? = null
    private var db: FirebaseFirestore? = null
    private var progressDialog? = ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Fetching data...")
        progressDialog.show()

        recyclerView = findViewById<R.id.recyclerView>()
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(new LinearLayoutManager(this))

        db = FirebaseFirestore.getInstance()
        userArrayList = new ArrayList<User>()
        myAdapter = new MyAdapter(MainActivity.this.userArrayList)

        recyclerView.setAdapter(myAdapter)

        EventChangeListener()

    }

    private void EventChangeListener() {
        db.collection("users").orderBy("firstName", Query.Direction.ASCENDING)
            .addSnapshotListener(new EventListener<QuerySnapshot>(){
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error){
                    if(error != null){
                        if(progressDialog.isShowing())
                            progressDialog.dismiss()
                        Log.e("firestore error", error.getMessage())
                        return
                    }
                    for (DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            userArrayList.add(dc.getDocument().toObject(User.class))
                        }
                    myAdapter.notifyDataSetChanged()
                    if(progressDialog.isShowing())
                        progressDialog.dismiss()
                }

                }
            });
    }
}