package com.ctlb.sbtracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ctlb.sbtracker.R

class ParentHomeFragment : Fragment() {

    private lateinit var homeViewModel: ParentHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(ParentHomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_parent_home, container, false)
        return root
    }

}