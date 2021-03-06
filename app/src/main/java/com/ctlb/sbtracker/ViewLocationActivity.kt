package com.ctlb.sbtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * A google maps activity which starts shows the location of the bus. It updates the location
 * continuously and periodically in some interval of time
 */
class ViewLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_location)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // getting phone no from the previuos activity to recognize the clicked bus
        var phn = intent.getStringExtra("phone").toString()

        // getiing db instance and adding a listener to it
        var database = FirebaseDatabase.getInstance().reference
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var long = 0.0
                        var lat = 0.0
                        for(i in snapshot.children)
                        {
                            if(i.child("phn").getValue().toString() == phn)
                            {
                                // if status is inactive finish the app
                                if(i.child("status").getValue().toString() == "I")
                                {
                                    finish()
                                }

                                // If status is active get the coordinates from db
                                else{
                                    long = i.child("long").getValue().toString().toDouble()
                                    lat = i.child("lat").getValue().toString().toDouble()
                                }
                            }
                        }
                        val your_location = LatLng(lat, long)

                        // viewing the location on map
                        mMap.addMarker(MarkerOptions().position(your_location).title("Your Location"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(your_location))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
    }
}