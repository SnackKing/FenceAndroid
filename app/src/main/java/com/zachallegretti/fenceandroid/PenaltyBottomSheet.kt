package com.zachallegretti.fenceandroid

import android.app.AlertDialog
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PenaltyBottomSheet constructor(val mainActivityPresenter: MainActivityPresenter) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.penalty_bottom_sheet, container, false)

        val leftYellow  = view.findViewById<View>(R.id.left_yellow)
        val leftRed  = view.findViewById<View>(R.id.left_red)
        val leftBlack  = view.findViewById<View>(R.id.left_black)

        val rightYellow  = view.findViewById<View>(R.id.right_yellow)
        val rightRed  = view.findViewById<View>(R.id.right_red)
        val rightBlack  = view.findViewById<View>(R.id.right_black)

        val done = view.findViewById<View>(R.id.done)

        leftYellow.setOnClickListener {
            showCard(ColorDrawable(requireContext().getColor(R.color.timer_yellow)), container)
        }
        leftRed.setOnClickListener {
            showCard(ColorDrawable(requireContext().getColor(R.color.score_red)), container) {
                mainActivityPresenter.increaseRightScore()
            }
        }
        leftBlack.setOnClickListener {
            showCard(ColorDrawable(requireContext().getColor(R.color.black)), container)
        }

        rightYellow.setOnClickListener {
            showCard(ColorDrawable(requireContext().getColor(R.color.timer_yellow)), container)
        }
        rightRed.setOnClickListener {
            showCard(ColorDrawable(requireContext().getColor(R.color.score_red)), container) {
                mainActivityPresenter.increaseLeftScore()
            }
        }
        rightBlack.setOnClickListener {
            showCard(ColorDrawable(requireContext().getColor(R.color.black)), container)
        }

        done.setOnClickListener {
            this.dialog?.cancel()
        }



        return view
    }

    private fun showCard(colorDrawable: ColorDrawable, container: ViewGroup?, onCloseCallback: () -> Unit = {}) {
        val alert = AlertDialog.Builder(requireContext(), android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.card_dialog_layout, container, false)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        dialogView.minimumWidth = width
        dialogView.minimumHeight = height
        dialogView.background = colorDrawable
        alert.setView(dialogView)

        val dialog = alert.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.findViewById<View>(R.id.card_bg).setOnClickListener {
            dialog.cancel()
            onCloseCallback.invoke()
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    companion object {
        const val TAG = "SettingsBottomSheet"
    }
}