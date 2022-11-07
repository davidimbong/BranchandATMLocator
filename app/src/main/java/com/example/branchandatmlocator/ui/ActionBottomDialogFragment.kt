package com.example.branchandatmlocator.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.databinding.ApiCallingDialogBinding
import com.example.branchandatmlocator.databinding.FragmentBottomSheetDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

enum class VALUES {
    BOTH, BRANCH, ATM
}

class ActionBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetDialogFragmentBinding
    private var selected = VALUES.BOTH.ordinal.toString()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.typeBoth.setOnClickListener {
            selected = VALUES.BOTH.ordinal.toString()
        }

        binding.typeATM.setOnClickListener {
            selected = VALUES.ATM.ordinal.toString()
        }

        binding.typeBranch.setOnClickListener {
            selected = VALUES.BRANCH.ordinal.toString()
        }
    }

    fun getSelected(): String {
        return selected
    }

//    fun getSelected(type: String): String{
//        selected = type
//        return selected
//    }
}