package com.ctlb.sbtracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_view_bus.*

class ViewBusLocationActivity : AppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bus_location)

        var busnumbers = ArrayList<String>()
        var drivernames = ArrayList<String>()
        Log.e("Viewbus Activity", "started")
        var phnnos = ArrayList<String>()
        var database = FirebaseDatabase.getInstance().reference
        var getdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("View bus activity", "data change")
                for (i in snapshot.children) {
                    if (i.child("type").getValue().toString() == "B") {
                        busnumbers.add(i.child("busno").getValue().toString())
                        phnnos.add(i.child("phn").getValue().toString())
                        drivernames.add(i.child("drvname").getValue().toString())
                    }
                }
                val listView = findViewById<ListView>(R.id.Viewbuses_listview)
                listView.adapter = RowBusActivity(this@ViewBusLocationActivity, busnumbers, drivernames)  // this needs to be my custom adapter telling my list what to render
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(getdata)
        Log.e("View bus Activity","after add value")
        val listView = findViewById<ListView>(R.id.Viewbuses_listview)

        listView.setOnItemClickListener() { adapterView, view, position, id ->
            var db = FirebaseDatabase.getInstance().reference
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var status = ""
                            Log.e("View bus activity", "data change")
                            for (i in snapshot.children) {
                                if (i.child("phn").getValue().toString() == phnnos.get(position)) {
                                    status = i.child("status").getValue().toString()
                                }
                            }
                            if(status != "I")
                            {
                                val intent = Intent(this@ViewBusLocationActivity, ViewLocationActivity::class.java)
                                intent.putExtra("phone", phnnos.get(position))
                                startActivity(intent)
                                finish()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

        }


    }

}