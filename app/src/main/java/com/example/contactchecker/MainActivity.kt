package com.example.contactchecker

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
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


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ContactListAdapter.ContactItemClickListener {

    private lateinit var dataBinding: ActivityMainBinding
    private val mViewModel: ContactViewModel by viewModels()
    private lateinit var mAdapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun setRecyclerData(data: List<ContactModel>?) {
        data?.let {
            mAdapter = ContactListAdapter(it,this)
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
