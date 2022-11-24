package com.example.branchandatmlocator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.branchandatmlocator.databinding.FragmentBottomSheetDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

enum class Values {
    BOTH, BRANCH, ATM
}

class ActionBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetDialogFragmentBinding
    private var selected = Values.BOTH.ordinal.toString()
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
            selected = Values.BOTH.ordinal.toString()
        }

        binding.typeATM.setOnClickListener {
            selected = Values.ATM.ordinal.toString()
        }

        binding.typeBranch.setOnClickListener {
            selected = Values.BRANCH.ordinal.toString()
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