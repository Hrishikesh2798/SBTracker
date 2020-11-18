package com.ctlb.sbtracker

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
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
import com.ctlb.sbtracker.ui.login.LoginActivity

/**
 * It represents the Organisation home page which the organisation visits directly after logging in
 * the activity contains 3 buttons (for add, delete and view location of buses), a side menu and
 * testViews to display messages
 */
class OrganisationHomeActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val add_bus: FloatingActionButton = findViewById(R.id.add_bus)
        add_bus.setOnClickListener { view ->
            val intent = Intent(this@OrganisationHomeActivity, AddBusActivity::class.java)
            startActivity(intent)
        }

        val delete_bus: FloatingActionButton = findViewById(R.id.view_bus_oranisation)
        delete_bus.setOnClickListener { view ->
            val intent = Intent(this@OrganisationHomeActivity, ViewBusActivity::class.java)
            startActivity(intent)
        }

        val view_bus: FloatingActionButton = findViewById(R.id.location_bus)
        view_bus.setOnClickListener { view ->
            val intent = Intent(this@OrganisationHomeActivity, ViewBusLocationActivity::class.java)
            startActivity(intent)
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_organisation_layout)
        val navView: NavigationView = findViewById(R.id.nav_organisation_view)
        val navController = findNavController(R.id.nav_host_fragment_organisation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_organisation_home), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.organisation_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.logout_organisation)
        {
            val intent = Intent(this@OrganisationHomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        if(id == R.id.logout_parent)
        {
            val intent = Intent(this@OrganisationHomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_organisation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}