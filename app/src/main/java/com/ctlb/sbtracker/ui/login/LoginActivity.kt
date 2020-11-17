package com.ctlb.sbtracker.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ctlb.sbtracker.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.registryType
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        var phn = ""
        var pwd = ""
        var found = 0
        var usertype = ""
        var database = FirebaseDatabase.getInstance().reference


        val typeAdapter: ArrayAdapter<String>
        val types = arrayOf("Organisation", "Driver", "Parent/Student")
        var type = "O"

        typeAdapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, types)
        registryType.adapter = typeAdapter

        registryType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                setContentView(R.layout.activity_login)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 1) {
                    type = "D"
                }
                if (position == 2) {
                    type = "P"
                }
            }
        }

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            Log.e("above found","inside found == 2")
            if(found ==2)
            {
                Log.e("found","inside found == 2")
                setResult(Activity.RESULT_OK)

                //Complete and destroy login activity once successful
                finish()
            }
            Log.e("below found","inside found == 2")
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
            phn = username.text.toString()
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
                pwd = password.text.toString()
            }



            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
                var getdata = object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        for(i in p0.children)
                        {
                            if(phn == i.child("phn").getValue().toString())
                            {
                                Log.e("found","inside found == 1")
                                found = 1
                                pwd = i.child("pwd").getValue().toString()
                                name = i.child("name").getValue().toString()
                                usertype = i.child("type").getValue().toString()
                                Log.e("phn","$pwd  $name   $usertype  $found")
                            }
                        }

                    }

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                }

                database.addValueEventListener(getdata)
                Log.e("between","add 1 and 2")
                database.addListenerForSingleValueEvent(getdata)
                Log.e("found is","$found")
                if(found == 0)
                {
                    username.setError("Invalid username")
                    username.requestFocus()
                    return@setOnClickListener
                }
                if(pwd != password.text.toString())
                {
                    password.setError("Incorrect password")
                    password.requestFocus()
                    return@setOnClickListener
                }
                if(usertype != type)
                {
                    return@setOnClickListener
                }
                found = 2
                pwd = ""
                name = ""
                phn = ""
                if(type == "O")
                {
                    val intent = Intent(this@LoginActivity, OrganisationHomeActivity::class.java)
                    startActivity(intent)
                }
                else if(type == "D")
                {
                    val intent = Intent(this@LoginActivity, DriverHomeActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    val intent = Intent(this@LoginActivity, ParentHomeActivity::class.java)
                    intent.putExtra("phone",username.text.toString())
                    startActivity(intent)
                }

            }

            signup.setOnClickListener {
                    val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                    startActivity(intent)

            }

        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = name
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                "$welcome $name",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}