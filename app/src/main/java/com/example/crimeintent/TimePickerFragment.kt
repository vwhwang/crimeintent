package com.example.crimeintent

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import java.util.Calendar

class TimePickerFragment : DialogFragment() {
    private val args: TimePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val timeListener = TimePickerDialog.OnTimeSetListener() { _, hour, minute ->
            val calendar = Calendar.getInstance()
            calendar.time = args.crimeTime
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            setFragmentResult(REQUEST_KEY_TIME, bundleOf(BUNDLE_KEY_TIME to calendar.time))
        }
        val calendar = Calendar.getInstance()
        calendar.time = args.crimeTime
        val initialHour = calendar.get(Calendar.HOUR)
        val initialMinute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireContext(),
            timeListener,
            initialHour,
            initialMinute,
            false
        )
    }

    companion object {
        const val REQUEST_KEY_TIME = "REQUEST_KEY_TIME"
        const val BUNDLE_KEY_TIME = "BUNDLE_KEY_TIME"
    }
}