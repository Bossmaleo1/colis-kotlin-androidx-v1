package com.Kcolis.android.fragments

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : AppCompatDialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var calendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val year = calendar!!.get(Calendar.YEAR)
        val month = calendar!!.get(Calendar.MONTH)
        val day = calendar!!.get(Calendar.DAY_OF_MONTH)

        return activity?.let { DatePickerDialog(it,this,year,month,day) }!!
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR,year)
        calendar.set(Calendar.MONTH,month)
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
        val selectedDate : String = SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH).format(calendar.time)

        targetFragment!!.onActivityResult(
            targetRequestCode,
            RESULT_OK,
            activity!!.intent.putExtra("selectedDate",selectedDate)
        )
    }

    private fun formatDate(year: Int, month: Int,day: Int) : String {
        calendar.set(year,month,day,0,0,0)
        val chosenDate = calendar.time
        //Format the date picker selected true
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }
}