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

class ViewBusActivity : AppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bus)

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
                listView.adapter = RowBusActivity(this@ViewBusActivity, busnumbers, drivernames)  // this needs to be my custom adapter telling my list what to render
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
        Log.e("View bus Activity","after add value")
        val listView = findViewById<ListView>(R.id.Viewbuses_listview)

        listView.setOnItemClickListener() { adapterView, view, position, id ->
            val intent = Intent(this@ViewBusActivity, PopUpActivity::class.java)
            intent.putExtra("phone", phnnos.get(position))
            startActivity(intent)
            finish()

        }


    }

}