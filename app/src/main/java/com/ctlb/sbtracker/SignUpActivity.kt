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
import com.ctlb.schoolbustracking.ViewBusActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.registryType
import kotlinx.android.synthetic.main.activity_sign_up.signUpButton

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val typeAdapter: ArrayAdapter<String>
        val types = arrayOf("Organisation", "Driver", "Parent/Student")
        var type : String = "O"

        typeAdapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, types)
        registryType.adapter = typeAdapter

        registryType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                setContentView(R.layout.activity_sign_up)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val  param = signUpButton.layoutParams as ViewGroup.MarginLayoutParams
                param.topMargin = 1250
                signUpButton.layoutParams = param
                if(position == 0)
                {
                    regCode.visibility = View.INVISIBLE
                    name.visibility = View.VISIBLE
                    type = "O"
                }
                else if(position == 1)
                {
                    name.visibility = View.INVISIBLE
                    regCode.visibility = View.VISIBLE
                    type = "D"
                }
                else
                {
                    name.visibility = View.INVISIBLE
                    regCode.visibility = View.VISIBLE
                    type = "P"
                }
            }
        }

        signUpButton.setOnClickListener {
            Toast.makeText(this,"Sign Up Button Clicked",Toast.LENGTH_SHORT).show()
            val phn : String = phoneNumber.text.toString()
            val pwd : String = password.text.toString()
            val name : String = name.text.toString()
            if(type == "O")
            {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(this@SignUpActivity, ViewBusActivity::class.java)
                startActivity(intent)
            }
        }



    }
}
