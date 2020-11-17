package com.ctlb.sbtracker.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ctlb.sbtracker.OrganisationHomeActivity
import com.ctlb.sbtracker.R

class OrganisationHomeFragment : Fragment() {

    private lateinit var homeViewModel: OrganisationHomeViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(OrganisationHomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_organisation_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home_organisation)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}