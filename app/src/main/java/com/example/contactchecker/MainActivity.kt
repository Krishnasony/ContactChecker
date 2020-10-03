package com.example.contactchecker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.contactchecker.databinding.ActivityMainBinding
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.utils.ProgressUtils
import com.example.contactchecker.utils.Status
import com.example.contactchecker.view.ContactListActivity.Companion.startContactListActivity
import com.example.contactchecker.view.adapter.ContactListAdapter
import com.example.contactchecker.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_home_list.*
import kotlin.system.exitProcess

private const val REQUEST_ID_SYSTEM_WINDOW = 104

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ContactListAdapter.ContactItemClickListener {

    private lateinit var dataBinding: ActivityMainBinding
    private val mViewModel: ContactViewModel by viewModels()
    private lateinit var mAdapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkAndRequestPermissions()
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        dataBinding.onFabClick = onFabClickListener
    }

    private fun getContactList() {
        mViewModel.fetchContacts()
        mViewModel.contacts.observe(this, {
            when (it.status) {
                Status.LOADING -> {
                    ProgressUtils.showLoadingOverlay(this, cl_parent)
                }
                Status.ERROR -> {
                    ProgressUtils.removeLoadingOverlay(cl_parent)
                }
                Status.SUCCESS -> {
                    ProgressUtils.removeLoadingOverlay(cl_parent)
                    setRecyclerData(it.data)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getContactList()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkAndRequestPermissions(): Boolean {
        if (!Settings.canDrawOverlays(this)) {
            val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            alertBuilder.setCancelable(true)
            alertBuilder.setTitle("System Alert Window permission necessary")
            alertBuilder.setMessage(
                "User needs to manually set this permission from Settings to allow floating dialog."
            )
            alertBuilder.setPositiveButton(
                "Yes"
            ) { _, _ ->
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivityForResult(myIntent, REQUEST_ID_SYSTEM_WINDOW)
            }
            val alert: AlertDialog = alertBuilder.create()
            alert.show()
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ID_SYSTEM_WINDOW) {
            if (Settings.canDrawOverlays(this))
                Toast.makeText(this, "Hurry! permission granted", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Please allow app two draw overlay", Toast.LENGTH_LONG).show()
        }
    }

    private fun setRecyclerData(data: List<ContactModel>?) {
        data?.let {
            mAdapter = ContactListAdapter(it, this)
            rv_list.adapter = mAdapter
        }
    }

    override fun onContactItemClick(contactModel: ContactModel, position: Int) {
        //TODO("Not yet implemented")
    }

    private val onFabClickListener = View.OnClickListener {
        startContactListActivity()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
