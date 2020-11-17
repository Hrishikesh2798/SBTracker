package com.ctlb.sbtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class PopUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up)

        val button = findViewById<Button>(R.id.button)
        val phn = intent.getStringExtra("phone")
        Log.e("pop up","phn is $phn")
        var database = FirebaseDatabase.getInstance().getReference()


        button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle)
            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                database.child(phn.toString()).setValue(null)
                Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
                val intent = Intent(this@PopUpActivity, ViewBusActivity::class.java)
                startActivity(intent)
                finish()
            }
            //performing cancel action
            builder.setNeutralButton("Cancel"){dialogInterface , which ->
                Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
            }
            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->
                Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}