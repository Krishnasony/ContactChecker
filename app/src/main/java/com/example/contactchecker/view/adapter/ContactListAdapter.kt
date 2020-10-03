package com.example.contactchecker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactchecker.R
import com.example.contactchecker.model.ContactModel

class ContactListAdapter(
    var mList: List<ContactModel>,
    private val listener: ContactItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        LayoutInflater.from(parent.context).apply {
            return ContactListViewHolder(
                inflate(R.layout.item_contact, parent, false), listener
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(mList[position], mList.size)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface ContactItemClickListener {

        fun onContactItemClick(contactModel: ContactModel, position: Int)

    }

}
