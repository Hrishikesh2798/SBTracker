package com.ctlb.sbtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ctlb.sbtracker.ui.login.LoginActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_bus.*

/**
 * This activity adds a new bus to the database. The activity contains different fields to add data
 * and an add bus button to store that data onto the DB
 */
class AddBusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bus)

        //setting action on click of add bus button
        addBusButton.setOnClickListener {
            Toast.makeText(this,"Add Bus Button Clicked", Toast.LENGTH_SHORT).show()
            val phn : String = phoneNumber.text.toString()
            val bus : String = busno.text.toString()
            val drvname : String = driverName.text.toString()

            // CHecking errors in the entered data
            if(phn.isEmpty())
            {
                phoneNumber.setError("Phone number is required")
                phoneNumber.requestFocus()
                return@setOnClickListener
            }
            if(bus.isEmpty())
            {
                busno.setError("Bus Number is required")
                busno.requestFocus()
                return@setOnClickListener
            }
            else {
                if(drvname.isEmpty())
                {
                    driverName.setError("Driver name is required")
                    driverName.requestFocus()
                    return@setOnClickListener
                }
            }

            //Linking database and inserting values into the db
            var database = FirebaseDatabase.getInstance().reference
            var buses = Bus(phoneNumber.text.toString(),busno.text.toString(),driverName.text.toString(),0.0,0.0,"I")
            database.child(phoneNumber.text.toString()).setValue(buses)

            // going back to the viewbus activity
            val intent = Intent(this@AddBusActivity, ViewBusActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}