package com.Kcolis.android.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatDialogFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimerPickerFragment : AppCompatDialogFragment(), TimePickerDialog.OnTimeSetListener {
    private lateinit var calendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val hour = calendar!!.get(Calendar.HOUR_OF_DAY)
        val minute = calendar!!.get(Calendar.MINUTE)
        return TimePickerDialog(
            activity,
            this,
            hour,
            minute,true
        )

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
        calendar.set(Calendar.MINUTE,minute)

        val format  = SimpleDateFormat("HH:mm",Locale.FRENCH)
        val selectedDate =  format.format(calendar.time)

        targetFragment!!.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            activity!!.intent.putExtra("selectedTime",selectedDate)
        )
    }
}