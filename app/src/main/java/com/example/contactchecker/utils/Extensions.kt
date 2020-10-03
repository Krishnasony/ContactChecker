package com.example.contactchecker.utils

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView

@SuppressLint("SetTextI18n")
fun TextView.setIcon(name: String) {
    val splitNameArr = name.split(" ")
    if (splitNameArr.size>1) {
       this.text = splitNameArr[0].substring(0, 1) + splitNameArr[1].substring(0, 1)
    }else{
        this.text = splitNameArr[0].substring(0, 1)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.backgroundTintList =  Utils.getMaterialColor(this.context)
    }
}
