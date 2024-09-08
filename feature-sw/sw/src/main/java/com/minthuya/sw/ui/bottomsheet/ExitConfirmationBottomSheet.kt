package com.minthuya.sw.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.minthuya.sw.databinding.SwExitConfirmBottomSheetBinding

class ExitConfirmationBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: SwExitConfirmBottomSheetBinding

    var onPositiveAction: (() -> Unit)? = null
    var onNegativeAction: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SwExitConfirmBottomSheetBinding.inflate(inflater, container, false)
        binding.btnPositive.setOnClickListener {
            onPositiveAction?.invoke()
        }
        binding.btnNegative.setOnClickListener {
            onNegativeAction?.invoke()
        }
        return binding.root
    }
    companion object {
        const val TAG = "ExitConfirmationBottomSheet"
    }
}