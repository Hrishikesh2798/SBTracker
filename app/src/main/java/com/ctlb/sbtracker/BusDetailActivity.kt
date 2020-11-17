package com.ctlb.sbtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class BusDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))
        var database = FirebaseDatabase.getInstance().reference
        var share = findViewById<FloatingActionButton>(R.id.shareButton)
        var phn = ""
        var status = "I"
        var found = 0
        findViewById<FloatingActionButton>(R.id.shareButton).setOnClickListener { view ->
            val x = intent.getStringExtra(BusDetailFragment.ARG_ITEM_ID)
            Log.e("busDetail","$x")
            var database = FirebaseDatabase.getInstance().reference
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var drvname = ""
                        var lattitude = 0.0
                        var longitude = 0.0
                        for(i in snapshot.children)
                        {
                            if(i.child("busno").getValue().toString() == x)
                            {
                                phn = i.child("phn").getValue().toString()
                                status = i.child("status").getValue().toString()
                                drvname = i.child("drvname").getValue().toString()
                            }
                        }
                        if(found == 0)
                        {
                            found = 1
                            if(status == "I")
                            {

                                status = "A"
                                database.child(phn).child("status").setValue("A")
                                Snackbar.make(view, "Location started sharing", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show()

                                val intent = Intent(this@BusDetailActivity, ShareLocationActivity::class.java)
                                intent.putExtra("phn",phn)
                                intent.putExtra("busno",x.toString())
                                intent.putExtra("drvname",drvname)
                                startActivity(intent)

                            }
                            else{
                                Snackbar.make(view, "Location already being shared", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show()
                        }

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }

        findViewById<FloatingActionButton>(R.id.stopShareButton).setOnClickListener { view ->
            if(status != "I")
            {
                database.child(phn).child("status").setValue("I")
                found = 0
                status = "I"
                Snackbar.make(view, "Location stopped sharing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }

        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = BusDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        BusDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(BusDetailFragment.ARG_ITEM_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo(Intent(this, DriverHomeActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}