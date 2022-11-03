package com.example.branchandatmlocator.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.ui.fragments.LocatorFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_fragment.*
import kotlinx.android.synthetic.main.fragment_locator.*

enum class VALUES{
    BOTH, BRANCH, ATM
}
class ActionBottomDialogFragment : BottomSheetDialogFragment() {

    private var selected = VALUES.BOTH.ordinal.toString()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        btnSubmit.setOnClickListener {
//            if (typeBoth.isSelected) {
//                selected = getSelected(VALUES.BOTH.toString())
//            } else if (typeATM.isSelected) {
//                selected = getSelected(VALUES.ATM.toString())
//            } else if (typeBranch.isSelected) {
//                selected = getSelected(VALUES.BRANCH.toString())
//            }
//            Toast.makeText(context, selected, Toast.LENGTH_SHORT).show()
//            this.dismiss()
//        }

//        btnSubmit.setOnClickListener {
//            if (typeBoth.isSelected) {
//                selected = VALUES.BOTH.toString()
//            } else if (typeATM.isSelected) {
//                selected = VALUES.ATM.toString()
//            } else if (typeBranch.isSelected) {
//                selected = VALUES.BRANCH.toString()
//            }
//
//            this.dismiss()
//        }
//
//        btnCancel.setOnClickListener {
//            this.dismiss()
//        }

        typeBoth.setOnClickListener {
            selected = VALUES.BOTH.ordinal.toString()
        }

        typeATM.setOnClickListener {
            selected = VALUES.ATM.ordinal.toString()
        }

        typeBranch.setOnClickListener {
            selected = VALUES.BRANCH.ordinal.toString()
        }
    }

    fun getSelected(): String{
        return selected
    }

//    fun getSelected(type: String): String{
//        selected = type
//        return selected
//    }
}