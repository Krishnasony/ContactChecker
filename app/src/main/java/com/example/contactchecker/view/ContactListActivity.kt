package com.example.contactchecker.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.contactchecker.R
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.utils.ProgressUtils
import com.example.contactchecker.utils.Status
import com.example.contactchecker.view.adapter.ContactListAdapter
import com.example.contactchecker.viewmodel.ContactListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.app_bar_home_list.*


@AndroidEntryPoint
class ContactListActivity : AppCompatActivity(),
    ContactListAdapter.ContactItemClickListener, AddDialogFragment.DialogFragmentCallback {

    private val mViewModel: ContactListViewModel by viewModels()
    private var mAdapter: ContactListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)
        setSupportActionBar(toolbar)
        getContactList()
    }

    private fun getContactList() {
        if (hasContactReadPermission()) {
            mViewModel.getContactDetails()
            mViewModel._contacts.observe(this, {
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
        } else requestPermission()
    }

    private fun setRecyclerData(data: List<ContactModel>?) {
        data?.apply {
            if (mAdapter == null) {
                mAdapter = ContactListAdapter(data, this@ContactListActivity)
                rv_list.adapter = mAdapter
            } else {
                mAdapter?.mList = data
                mAdapter?.notifyDataSetChanged()
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onContactItemClick(contactModel: ContactModel, position: Int) {
        AddDialogFragment.newInstance(contactModel).apply {
            isCancelable = false
            show(supportFragmentManager, null)
        }

    }

    override fun onSaveClick(contactModel: ContactModel) {
        mViewModel.addContactToDataBase(contactModel)
        getContactList()
    }

    private fun hasContactReadPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContactList()
            } else {
                requestPermission()
            }
        }
    }

    companion object {

        private const val PERMISSION_REQUEST_CODE = 101

        fun Context.startContactListActivity() = startActivity(
            Intent(this, ContactListActivity::class.java)
        )

    }

}
