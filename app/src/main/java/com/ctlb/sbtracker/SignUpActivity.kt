package com.ctlb.sbtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import com.ctlb.sbtracker.ui.login.LoginActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.registryType
import kotlinx.android.synthetic.main.activity_sign_up.signUpButton

/**
 * This activity adds a new user to the database. The activity contains different fields to add data
 * and a sign up button to store that data onto the DB
 */
class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // getting DB instance
        var database = FirebaseDatabase.getInstance().reference

        // Creating spinner
        val typeAdapter: ArrayAdapter<String>
        val types = arrayOf("Organisation", "Driver", "Parent/Student")
        var type : String = "O"
        var reg = 0


        // getting registration code
        var getdata = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children)
                {
                    if(i.child("Name").getValue() == "Reg")
                    {
                        reg = i.child("Value").getValue().toString().toInt()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        // adding value listener to DB
        database.addValueEventListener(getdata)

        val reg1 = (reg + 1)
        database.child("Reg").setValue(reg1.toString())
        regCode.isEnabled = false

        // adding list to the spinner
        typeAdapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, types)
        registryType.adapter = typeAdapter

        registryType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                setContentView(R.layout.activity_sign_up)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if(position == 0)
                {
                    regCode.setText(reg.toString())
                    regCode.isEnabled = false
                    type = "O"
                }
                else if(position == 1)
                {
                    regCode.setText("")
                    regCode.isEnabled = true
                    type = "D"

                }
                else
                {
                    regCode.setText("")
                    regCode.isEnabled = true
                    type = "P"
                }
            }
        }

        signUpButton.setOnClickListener {
            Toast.makeText(this,"Sign Up Button Clicked",Toast.LENGTH_SHORT).show()
            val phn : String = phoneNumber.text.toString()
            val pwd : String = password.text.toString()
            val etname : String = name.text.toString()
            var reg : String = regCode.text.toString()


            // Validating the data entered
            if(phn.isEmpty())
            {
                phoneNumber.setError("Phone number is required")
                phoneNumber.requestFocus()
                return@setOnClickListener
            }
            if(pwd.isEmpty())
            {
                password.setError("Password is required")
                password.requestFocus()
                return@setOnClickListener
            }
            if(password.length() < 6)
            {
                password.setError("Password should be atleast 6 characters")
                password.requestFocus()
                return@setOnClickListener
            }
            if(type == "O")
            {
                if(etname.isEmpty()) {
                    name.setError("Name is required")
                    name.requestFocus()
                    return@setOnClickListener
                }
            }
            else {
                if(reg.isEmpty())
                {
                    regCode.setError("Registration Code is required")
                    regCode.requestFocus()
                    return@setOnClickListener
                }
            }

            // Inserting details to the DB
            var user = User(phoneNumber.text.toString(),password.text.toString(),name.text.toString(),type,regCode.text.toString())
            database.child(phoneNumber.text.toString()).setValue(user)

            //Going back to the login activity
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}
