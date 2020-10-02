package com.example.contactchecker.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.contactchecker.R


/**
 * Created by anurag on 29/10/17.
 */
object ProgressUtils {
    /**
     * To show custom loader in parent view.
     *
     * @param context context
     * @param parent  parentView
     */
    fun showLoadingOverlay(context: Context, parent: View) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.layout_loading_default, null)
        (parent as ViewGroup).addView(
            view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    /**
     * To show custom loader in parent view.
     *
     * @param context context
     * @param parent  parentView
     */
    fun showWrapLoadingOverlay(context: Context, parent: View) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.layout_loading_default, null)
        (parent as ViewGroup).addView(view, ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight())
    }

    /**
     * To hide custom loader in parent view.
     *
     * @param parent parentView
     */
    fun removeLoadingOverlay(parent: View) {
        val indicatorView = parent.findViewById<RelativeLayout>(R.id.layout_loading_default)
        (parent as ViewGroup).removeView(indicatorView)
    }
}
