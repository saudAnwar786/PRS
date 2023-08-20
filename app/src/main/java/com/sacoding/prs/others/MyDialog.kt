package com.sacoding.prs.others

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.sacoding.prs.R
import com.sacoding.prs.data.models.ProductCategory
import com.sacoding.prs.ui.fragments.Fragment_User

class MyDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val items = arrayOf("Garment Upper body","Underwear","Garment Lower body","Accessories","Items","Nightwear","Unknown","Underwear or nightwear",
            "Shoes","Swimwear","Garment Full body","Cosmetic","Interior textile","Bags","Furniture","Garment and Shoe care","Fun","Stationery")
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val selectedItems = ArrayList<String>()
            builder.setTitle("Select Categories")
                .setMultiChoiceItems(
                   items, null,
                    DialogInterface.OnMultiChoiceClickListener { dialog, position, isChecked ->
                        if (isChecked) {
                            selectedItems.add(items[position])
                        } else if (selectedItems.contains(items[position])) {
                            selectedItems.remove(items[position])
                        }
                    })
                .setPositiveButton(
                    "Recommend Me",
                    DialogInterface.OnClickListener { dialog, id ->
                        val listener = targetFragment as? DataListener
                        listener?.onDataReceived(selectedItems)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
interface DataListener {
    fun onDataReceived(data: ArrayList<String>)
}