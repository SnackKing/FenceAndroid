package com.zachallegretti.fenceandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PenaltyBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.penalty_bottom_sheet, container, false)
        return view
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    companion object {
        const val TAG = "SettingsBottomSheet"
    }
}