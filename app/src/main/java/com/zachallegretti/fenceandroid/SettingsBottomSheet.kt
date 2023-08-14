package com.zachallegretti.fenceandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class SettingsBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_bottom_sheet, container, false)

        val boutType = view.findViewById<TextInputLayout>(R.id.bout_type_menu)
        val adapter = (ArrayAdapter<String>(requireContext(), R.layout.bout_type_list_item, BOUT_TYPE_LIST))
        (boutType.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (boutType.editText as? AutoCompleteTextView)?.setText(getSelectedBoutType(), false)


        return view
    }

    private fun getSelectedBoutType(): String {
        //TODO: Add real implementation
        return "${BOUT_TYPE_LIST.first()}"
    }

    companion object {
        const val TAG = "SettingsBottomSheet"
        val BOUT_TYPE_LIST = listOf("Practice", "Pool", "Team", "DE", "DE (VET)", "DE(Y10)")
    }

}