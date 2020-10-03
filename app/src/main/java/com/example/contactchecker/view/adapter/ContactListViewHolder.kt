package com.example.contactchecker.view.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.utils.Utils
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactListViewHolder(
    itemView: View,
    private val listener: ContactListAdapter.ContactItemClickListener
) : BaseViewHolder(itemView) {

    override fun bind(item: Any, itemCount: Int) {
        item as ContactModel
        itemView.tv_name.text = item.nickName?:item.name
        itemView.tv_phone_number.text = item.number
        setIcon(item.name?:item.number)
        itemView.setOnClickListener {
            listener.onContactItemClick(item, adapterPosition)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setIcon(name: String) {
        val splitNameArr = name.split(" ")
        if (splitNameArr.size>1) {
            itemView.name_icon.text = splitNameArr[0].substring(0, 1) + splitNameArr[1].substring(0, 1)
        }else{
            itemView.name_icon.text = splitNameArr[0].substring(0, 1)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            itemView.name_icon.backgroundTintList =  Utils.getMaterialColor(itemView.context)
        }
    }

}
