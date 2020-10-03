package com.example.contactchecker.view.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.utils.setIcon
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactListViewHolder(
    itemView: View,
    private val listener: ContactListAdapter.ContactItemClickListener
) : BaseViewHolder(itemView) {

    override fun bind(item: Any, itemCount: Int) {
        item as ContactModel
        binding.setVariable(BR.contact, item)
        binding.setVariable(BR.itemClickListener, onItemClickListener )
        itemView.name_icon.setIcon(item.name?:item.number)
    }

    private val onItemClickListener = View.OnClickListener {
        listener.onContactItemClick(it.tag as ContactModel, adapterPosition)
    }

    private val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

}
