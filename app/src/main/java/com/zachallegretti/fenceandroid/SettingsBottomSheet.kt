package com.zachallegretti.fenceandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
        val adapter = (ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_item, BOUT_TYPE_LIST))
        (boutType.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (boutType.editText as? AutoCompleteTextView)?.setText(getSelectedBoutType(), false)

        val weaponType = view.findViewById<TextInputLayout>(R.id.weapon_menu)
        val weaponAdapter = (ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_item, WEAPON_LIST))
        (weaponType.editText as? AutoCompleteTextView)?.setAdapter(weaponAdapter)
        (weaponType.editText as? AutoCompleteTextView)?.setText(getSelectedWeaponType(), false)

        val practiceLength = view.findViewById<TextInputLayout>(R.id.practice_length_menu)
        val practiceAdapter = (ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_item, PRACTICE_LIST))
        (practiceLength.editText as? AutoCompleteTextView)?.setAdapter(practiceAdapter)
        (practiceLength.editText as? AutoCompleteTextView)?.setText(getSelectedPracticeLength(), false)

        return view
    }

    private fun getSelectedBoutType(): String {
        //TODO: Add real implementation
        return "${BOUT_TYPE_LIST.first()}"
    }

    private fun getSelectedWeaponType(): String {
        //TODO: Add real implementation
        return WEAPON_LIST.first()
    }

    private fun getSelectedPracticeLength(): String {
        //TODO: Add real implementation
        return PRACTICE_LIST.first()
    }

    companion object {
        const val TAG = "SettingsBottomSheet"
        val BOUT_TYPE_LIST = listOf("Practice", "Pool", "Team", "DE", "DE (VET)", "DE(Y10)")
        val WEAPON_LIST = listOf("Epee", "Foil", "Saber")
        val PRACTICE_LIST = listOf("5", "10", "15", "99")
    }

}