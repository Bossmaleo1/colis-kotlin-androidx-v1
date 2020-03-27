package com.Kcolis.android.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.Kcolis.android.R
import com.Kcolis.android.appviews.AnnoncesList
import com.Kcolis.android.appviews.SearchTown
import com.Kcolis.android.model.Const
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Recherche : Fragment() {

    private var rechercher : MaterialButton? = null
    private var dateannonce : TextInputEditText? = null
    private var depart : TextInputEditText? = null
    private var arrivee : TextInputEditText? = null
    private var dateannonce_error : TextInputLayout? = null
    private var arrivee_error : TextInputLayout? = null
    private var selectedDate : String? = null
    private var idaeroportdepart : Int? = null
    private var idaeroportarrivee : Int? = null
    private var depart_error : TextInputLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val inflatedView: View =  inflater.inflate(R.layout.recherche,container,false)

        val fm : FragmentManager = activity!!.supportFragmentManager
        rechercher = inflatedView.findViewById(R.id.rechercher)
        depart = inflatedView.findViewById(R.id.depart)
        arrivee = inflatedView.findViewById(R.id.arrivvee)
        dateannonce = inflatedView.findViewById(R.id.dateannonce) as TextInputEditText

        depart_error = inflatedView.findViewById(R.id.text_input_layout_depart) as TextInputLayout
        arrivee_error = inflatedView.findViewById(R.id.text_input_layout_password) as TextInputLayout
        dateannonce_error = inflatedView.findViewById(R.id.text_input_layout_dateannonce) as TextInputLayout



        arrivee!!.setOnClickListener{
            val intent = Intent(activity,SearchTown::class.java)
            intent.putExtra("title","Ville d'arrivée")
            startActivityForResult(intent,Const.REQUEST_CODE_ARRIVEE)
        }

        depart!!.setOnClickListener{
            val intent = Intent(activity, SearchTown::class.java)
            intent.putExtra("title","Ville départ")
            startActivityForResult(intent,Const.REQUEST_CODE_DEPART)
        }

        depart!!.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            val intent = Intent(activity, SearchTown::class.java)
            intent.putExtra("title","Ville départ")
            startActivityForResult(intent,Const.REQUEST_CODE_DEPART)
        }

        rechercher!!.setOnClickListener {
            if(validate()){
                val intent = Intent(activity,AnnoncesList::class.java)
                intent.putExtra("depart",idaeroportdepart.toString())
                intent.putExtra("arrivee",idaeroportarrivee.toString())
                intent.putExtra("date",dateannonce!!.text.toString())
                startActivity(intent)
            }
        }

        dateannonce!!.setOnClickListener{
            val newFragment : AppCompatDialogFragment = DatePickerFragment()
            newFragment.setTargetFragment(this,Const.REQUEST_CODE)
            newFragment.show(fm,"selectedDate")
        }

        return inflatedView
    }

    fun validate() : Boolean {

        var valid : Boolean = true

        val _depart_voyage = depart!!.text.toString()
        val  _arrivee_voyage = arrivee!!.text.toString()
        val _date_voyage = dateannonce!!.text.toString()

        if (_depart_voyage.isEmpty()) {
            depart_error!!.error = "La ville de départ doit être renseigner"
            valid = false
        } else {
            depart_error!!.error = null
        }

        if (_arrivee_voyage.isEmpty()) {
            arrivee_error!!.error = "La ville d'arrivée doit être renseigner"
            valid = false
        } else {
            arrivee_error!!.error = null
        }

        if (_date_voyage.isEmpty()) {
            dateannonce_error!!.error = "La date de l'annonce doit être renseigner"
            valid = false
        } else {
            dateannonce_error!!.error = null
        }

        return valid
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Const.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedDate = data!!.getStringExtra("selectedDate")
            dateannonce!!.setText(selectedDate)
        } else if (requestCode == Const.REQUEST_CODE_ARRIVEE && resultCode == Activity.RESULT_OK) {
            arrivee!!.setText(data!!.getStringExtra("ville"))
            idaeroportarrivee = data!!.getIntExtra("id",0)
        } else if (requestCode == Const.REQUEST_CODE_DEPART && resultCode == Activity.RESULT_OK) {
                depart!!.setText(data!!.getStringExtra("ville"))
                idaeroportdepart = data!!.getIntExtra("id",0)
        }
    }

}