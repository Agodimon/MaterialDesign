package com.bignerdranch.android.materialdesign.ui.picture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bignerdranch.android.materialdesign.R
import com.bignerdranch.android.materialdesign.ui.api.ApiActivity
import com.bignerdranch.android.materialdesign.ui.collapsingtoolbar.CollapsingToolbarActivity
import com.bignerdranch.android.materialdesign.util.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> startActivity(Intent(context, CollapsingToolbarActivity::class.java))
                R.id.navigation_two -> toast("2")
            }
            true
        }
    }

}