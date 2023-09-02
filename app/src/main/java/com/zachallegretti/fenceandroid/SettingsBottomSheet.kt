package com.zachallegretti.fenceandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.get
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var boutPreferencesDataStore: BoutPreferencesDataStore

    private lateinit var boutType: TextInputLayout
    private lateinit var weaponType: TextInputLayout
    private lateinit var practiceLength: TextInputLayout

    private lateinit var timerTapToggle: SwitchMaterial
    private lateinit var timerBuzzToggle: SwitchMaterial
    private lateinit var timerSoundToggle: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_bottom_sheet, container, false)
        boutPreferencesDataStore = BoutPreferencesDataStore(requireContext().applicationContext)

        boutType = view.findViewById<TextInputLayout>(R.id.bout_type_menu)
        val adapter = (ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_item, BOUT_TYPE_LIST))
        (boutType.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (boutType.editText as? AutoCompleteTextView)?.setText(getSelectedBoutType(), false)

        weaponType = view.findViewById<TextInputLayout>(R.id.weapon_menu)
        val weaponAdapter = (ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_item, WEAPON_LIST))
        (weaponType.editText as? AutoCompleteTextView)?.setAdapter(weaponAdapter)
        (weaponType.editText as? AutoCompleteTextView)?.setText(getSelectedWeaponType(), false)

        practiceLength = view.findViewById<TextInputLayout>(R.id.practice_length_menu)
        val practiceAdapter = (ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_item, PRACTICE_LIST))
        (practiceLength.editText as? AutoCompleteTextView)?.setAdapter(practiceAdapter)
        (practiceLength.editText as? AutoCompleteTextView)?.setText(getSelectedPracticeLength(), false)

        timerTapToggle = view.findViewById(R.id.timer_tap_mode_switch)
        timerBuzzToggle = view.findViewById(R.id.timer_buzz_switch)
        timerSoundToggle = view.findViewById(R.id.timer_sound_switch)

        (boutType.editText as AutoCompleteTextView).onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                CoroutineScope(Dispatchers.IO).launch {
                    boutPreferencesDataStore.updateBoutMode(Bout.BoutType.fromInt(position))
                }
            }

        (weaponType.editText as AutoCompleteTextView).onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                CoroutineScope(Dispatchers.IO).launch {
                    boutPreferencesDataStore.updateWeapon(Bout.WeaponType.fromInt(position))
                }
            }

        (practiceLength.editText as AutoCompleteTextView).onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                CoroutineScope(Dispatchers.IO).launch {
                    boutPreferencesDataStore.updatePracticeScore(PRACTICE_LIST[position].toInt())
                }
            }

        timerTapToggle.setOnCheckedChangeListener { compoundButton, checked ->
            CoroutineScope(Dispatchers.IO).launch {
                boutPreferencesDataStore.setTimerTapModeEnabled(checked)
            }
        }

        timerBuzzToggle.setOnCheckedChangeListener { compoundButton, checked ->
            CoroutineScope(Dispatchers.IO).launch {
                boutPreferencesDataStore.setTimerBuzzEnabled(checked)
            }
        }

        timerSoundToggle.setOnCheckedChangeListener { compoundButton, checked ->
            CoroutineScope(Dispatchers.IO).launch {
                boutPreferencesDataStore.setTimerSoundEnabled(checked)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            boutPreferencesDataStore.boutSettingsFlow.collect {
                activity?.runOnUiThread {

                    (boutType.editText as? AutoCompleteTextView)?.setText(
                        Bout.BoutType.fromInt(it.boutSettingsKey).name,
                        false
                    )
                    (weaponType.editText as? AutoCompleteTextView)?.setText(
                        Bout.WeaponType.fromInt(
                            it.weaponKey
                        ).name, false
                    )
                    if (it.practiceScore != 0) {
                        (practiceLength.editText as? AutoCompleteTextView)?.setText(
                            it.practiceScore.toString(),
                            false
                        )
                    }

                    timerTapToggle.isChecked = it.timerTapModeEnabled
                    timerBuzzToggle.isChecked = it.timerBuzzEnabled
                    timerSoundToggle.isChecked = it.timerSoundEnabled
                }
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
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
        val BOUT_TYPE_LIST = Bout.BoutType.values().map { it.name }
        val WEAPON_LIST = Bout.WeaponType.values().map { it.name }
        val PRACTICE_LIST = listOf("5", "10", "15", "99")
    }

}