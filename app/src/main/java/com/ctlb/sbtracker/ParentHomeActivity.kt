package com.ctlb.sbtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.fragment_parent_home.*
import com.ctlb.sbtracker.ui.login.LoginActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class ParentHomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        var phn = intent.getStringExtra("phone")
        Log.e("phn","phone is $phn")

        fun onOptionsItemSelected(item: MenuItem): Boolean {
            if(item.itemId == R.id.logout_parent)
            {
                val intent = Intent(this@ParentHomeActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            return super.onOptionsItemSelected(item)
        }

        var database = FirebaseDatabase.getInstance().reference
        var getdata= object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var name = ""
                for(i in snapshot.children)
                {
                    Log.e("key","key is $(i.key)")
                    if(i.child("phn").getValue().toString() == phn.toString())
                    {
                        Log.e("key in","key is $(i.key)")
                        name = i.child("name").getValue().toString()
                        Log.e("key in","Name :  $name  \n" +
                                "  Phone : $phn")

                    }
                }
                Log.e("outside","key is (i.key)")
                val text = findViewById<TextView>(R.id.text_home_parent)
                text.setText("Name : $name \n Phone : $phn")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
        Log.e("between","key is (i.key)")
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_parent_layout)
        val navView: NavigationView = findViewById(R.id.nav_parent_view)
        val navController = findNavController(R.id.nav_host_fragment_parent)

        val view_bus: FloatingActionButton = findViewById(R.id.view_bus_parent)
        view_bus.setOnClickListener { view ->
            val intent = Intent(this@ParentHomeActivity, ViewBusActivity::class.java)
            startActivity(intent)
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_parent_home), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.parent_home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_parent)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}