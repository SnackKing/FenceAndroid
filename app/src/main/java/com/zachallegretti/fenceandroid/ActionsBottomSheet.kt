package com.zachallegretti.fenceandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActionsBottomSheet constructor(val mainActivityPresenter: MainActivityPresenter) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.actions_bottom_sheet, container, false)

        val changeTimeRemaining = view.findViewById<TextView>(R.id.change_time_remaining)
        val medicalBreak = view.findViewById<TextView>(R.id.medical_break)
        val nextBout = view.findViewById<TextView>(R.id.next_bout)
        val penaltyChart = view.findViewById<TextView>(R.id.penalty_chart)
        val done = view.findViewById<Button>(R.id.done)

        changeTimeRemaining.setOnClickListener {

        }

        medicalBreak.setOnClickListener {

        }
        nextBout.setOnClickListener {

        }
        penaltyChart.setOnClickListener {

        }

        done.setOnClickListener {
            dismiss()
        }

        return view
    }

    companion object {
        const val TAG = "ActionsBottomSheet"
    }
}