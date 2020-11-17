package com.ctlb.sbtracker

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class RowBusActivity(private val context: Activity, private val busno: ArrayList<String>, private val drivername: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.row_viewbuses, busno) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.row_viewbuses, null, true)

        val titleText = rowView.findViewById(R.id.bus_number) as TextView
        val subtitleText = rowView.findViewById(R.id.driver_name) as TextView

        titleText.text = busno.get(position)
        subtitleText.text = drivername.get(position)

        return rowView
    }
}