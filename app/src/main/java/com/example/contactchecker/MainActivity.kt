package com.example.contactchecker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.contactchecker.databinding.ActivityMainBinding
import com.example.contactchecker.view.ContactListActivity.Companion.startContactListActivity
import kotlinx.android.synthetic.main.app_bar_home_list.*


class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        dataBinding.onFabClick = onFabClickListener
    }

    private val onFabClickListener = View.OnClickListener {
        startContactListActivity()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
