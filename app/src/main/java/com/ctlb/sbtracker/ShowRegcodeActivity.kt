package com.ctlb.sbtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ShowRegcodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_regcode)
        val ref_number: TextView = findViewById(R.id.reference_number)
        ref_number.text="reference number"
    }
}
