package com.Kcolis.android.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.Kcolis.android.R
import com.Kcolis.android.appviews.SearchTown
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.Const.REQUEST_CODE
import com.Kcolis.android.model.Const.REQUEST_CODE12
import com.Kcolis.android.model.Const.REQUEST_CODE13
import com.Kcolis.android.model.Const.REQUEST_CODE14
import com.Kcolis.android.model.Const.REQUEST_CODE_ARRIVEE
import com.Kcolis.android.model.Const.REQUEST_CODE_DEPART
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.User
import com.Kcolis.android.utils.MyApplication
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.recherche.*
import org.json.JSONException
import org.json.JSONObject

class AddAnnonce : Fragment() {

    private var day : Int? = null
    private var month : Int? = null
    private var year : Int? = null
    private var date_annonce : EditText? = null
    private var date_annonce2 : EditText? = null
    private var ville_depart : EditText? = null
    private var ville_arrivee : EditText? = null
    private var heure_depart : EditText? = null
    private var heure_darrivee : EditText? = null
    private var prix_transaction : EditText? = null
    private var poids : EditText? = null
    private var Ajouter : Button? = null
    private var Suivant : Button? = null
    private var Lieux_rdv1 : EditText? = null
    private var Lieux_rdv2 : EditText? = null
    private var date_annonce_error : TextInputLayout? = null
    private var date_annonce2_error : TextInputLayout? = null
    private var ville_depart_error : TextInputLayout? = null
    private var ville_darrivee_error : TextInputLayout? = null
    private var prix_transaction_error : TextInputLayout? = null
    private var poids_error : TextInputLayout? = null
    private var Lieux_rdv1_error : TextInputLayout? = null
    private var Lieux_rdv2_error : TextInputLayout? = null
    private var Block1 : RelativeLayout? = null
    private var Block2 : RelativeLayout? = null
    private var selectedDate : String? = null
    private var selectedDate1 : String? = null
    private var selectedDate2 : String? = null
    private var selectedDate14 : String? = null
    private var snackbar : Snackbar? = null
    private var reponse : JSONObject? = null
    private var data : JSONObject? = null
    private var succes : Int? = null
    private var coordinatorLayout : CoordinatorLayout? = null
    private var idaeroportdepart : Int? = null
    private var idaeroportarrivee : Int? = null
    private var database : DatabaseHandler? = null
    private var session : SessionManager? = null
    private var user : User? = null
    private var anim : Animation? = null
    private var heure_depart_error : TextInputLayout? = null
    private var heure_darrivee_error : TextInputLayout? = null
    private var fm : FragmentManager? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView: View =  inflater.inflate(R.layout.addannonce,container,false)

        fm = activity!!.supportFragmentManager

        database = activity?.let { DatabaseHandler(it,null) }
        session = activity?.let { SessionManager(it) }
        user = database!!.getUser((session!!.getUserDetail()[Const.Key_ID] ?: error("")).toInt())

        coordinatorLayout = inflatedView.findViewById(R.id.coordinatorLayout)
        date_annonce = inflatedView.findViewById(R.id.dateannonce)
        date_annonce2 = inflatedView.findViewById(R.id.dateannonce_arrivee)
        ville_depart = inflatedView.findViewById(R.id.depart)
        ville_arrivee = inflatedView.findViewById(R.id.arrivvee)
        heure_depart = inflatedView.findViewById(R.id.heure_depart)
        heure_darrivee = inflatedView.findViewById(R.id.heure_arrivee)
        prix_transaction = inflatedView.findViewById(R.id.prix)
        poids = inflatedView.findViewById(R.id.kilo)
        Suivant = inflatedView.findViewById(R.id.ajouter1)
        Ajouter = inflatedView.findViewById(R.id.ajouter)
        Block1 = inflatedView.findViewById(R.id.block)
        Block2 = inflatedView.findViewById(R.id.block2)
        Lieux_rdv1 = inflatedView.findViewById(R.id.lieux_rdv1)
        Lieux_rdv2 = inflatedView.findViewById(R.id.lieux_rdv2)

        date_annonce_error = inflatedView.findViewById(R.id.text_input_layout_dateannonce)
        date_annonce2_error = inflatedView.findViewById(R.id.text_input_layout_dateannonce_arrivee)
        ville_depart_error = inflatedView.findViewById(R.id.text_input_layout_depart)
        ville_darrivee_error = inflatedView.findViewById(R.id.text_input_layout_arrivvee)
        heure_depart_error = inflatedView.findViewById(R.id.text_input_layout_heure_depart)
        heure_darrivee_error = inflatedView.findViewById(R.id.text_input_layout_dheure_arrivee)
        prix_transaction_error = inflatedView.findViewById(R.id.text_input_layout_prix)
        poids_error = inflatedView.findViewById(R.id.text_input_layout_kilo)
        Lieux_rdv1_error = inflatedView.findViewById(R.id.text_input_layout_lieux_rdv1)
        Lieux_rdv2_error = inflatedView.findViewById(R.id.text_input_layout_lieux_rdv2)

        anim = AnimationUtils.loadAnimation(activity,R.anim.slide_in)

        ville_depart!!.setOnClickListener{
            val intent  = Intent(activity, SearchTown::class.java)
            intent.putExtra("title","D'où partez-vous ?!")
            startActivityForResult(intent, REQUEST_CODE_DEPART)
        }

        ville_arrivee!!.setOnClickListener{
            val intent = Intent(activity,SearchTown::class.java)
            intent.putExtra("title","Où allez-vous?!")
            startActivityForResult(intent, REQUEST_CODE_ARRIVEE)
        }

        date_annonce!!.setOnClickListener{
            val newFragment : AppCompatDialogFragment = DatePickerFragment()
            newFragment!!.setTargetFragment(this,Const.REQUEST_CODE)
            newFragment.show(fm!!,"datePicker")
        }

        date_annonce2!!.setOnClickListener{
            val newFragment : AppCompatDialogFragment = DatePickerFragment()
            newFragment!!.setTargetFragment(this,Const.REQUEST_CODE14)
            newFragment.show(fm!!,"datePicker")
        }

        heure_depart!!.setOnClickListener {
            val newFragment : AppCompatDialogFragment = TimerPickerFragment()
            newFragment!!.setTargetFragment(this,Const.REQUEST_CODE12)
            newFragment.show(fm!!,"selectedTime")
        }

        heure_darrivee!!.setOnClickListener{
            val newFragment : AppCompatDialogFragment = TimerPickerFragment()
            newFragment!!.setTargetFragment(this,Const.REQUEST_CODE13)
            newFragment.show(fm!!,"selectedTime")
        }

        Suivant!!.setOnClickListener{
            if(Validate1()){
                Block1!!.visibility = View.GONE
                Block2!!.visibility = View.VISIBLE
                Block2!!.startAnimation(anim)
            }
        }

        Ajouter!!.setOnClickListener{
            if(validate2()) {
                val mDialogView = LayoutInflater.from(activity).inflate(R.layout.progressdialogfragment,null)
                val dialogBuilder = AlertDialog.Builder(activity!!).setView(mDialogView)
                val alert = dialogBuilder.create()
                alert.setCancelable(false)
                alert.show()
                InsertAnnonce(alert)
            }
        }


        return inflatedView
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Toast.makeText(activity,"Test Maleo  !!",Toast.LENGTH_LONG).show()

        if(requestCode == Const.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedDate = data!!.getStringExtra("selectedDate")
            date_annonce!!.setText(selectedDate)

        } else if (requestCode == Const.REQUEST_CODE12 && resultCode == Activity.RESULT_OK) {
            selectedDate1 = data!!.getStringExtra("selectedTime")
            heure_depart!!.setText(selectedDate1+":00")

        } else if (requestCode == Const.REQUEST_CODE13 && resultCode == Activity.RESULT_OK) {
            selectedDate2 = data!!.getStringExtra("selectedTime")
            heure_darrivee!!.setText(data!!.getStringExtra("selectedTime")+":00")

        } else if (requestCode == Const.REQUEST_CODE_ARRIVEE && resultCode == Activity.RESULT_OK) {
            ville_arrivee!!.setText(data!!.getStringExtra("ville"))
            idaeroportarrivee = data!!.getIntExtra("id",0)

            Toast.makeText(activity,data!!.getStringExtra("ville")+" "+requestCode,Toast.LENGTH_LONG).show()
        } else if (requestCode == Const.REQUEST_CODE_DEPART && resultCode == Activity.RESULT_OK) {
            ville_depart!!.setText(data!!.getStringExtra("ville"))
            idaeroportdepart = data!!.getIntExtra("id",0)

            Toast.makeText(activity,data!!.getStringExtra("ville")+" "+requestCode,Toast.LENGTH_LONG).show()
        } else if (requestCode == Const.REQUEST_CODE14 && resultCode == Activity.RESULT_OK) {
            selectedDate14 = data!!.getStringExtra("selectedDate")
            date_annonce2!!.setText(selectedDate14)

        }
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun Validate1(): Boolean {
        var valid = true

        val _depart_voyage = ville_depart!!.text.toString()
        val _arrivee_voyage = ville_arrivee!!.text.toString()
        val _date_voyage = date_annonce!!.text.toString()
        val _date_voyage2 = date_annonce2!!.text.toString()
        val _heure_depart = heure_depart!!.text.toString()

        if(_depart_voyage.isEmpty()){
            ville_depart_error!!.error = "Veuillez remplir la ville de départ svp !"
            valid = false
        } else {
            ville_depart_error!!.error = null
        }

        if (_arrivee_voyage.isEmpty()) {
            ville_darrivee_error!!.error = "Veuillez remplir la ville d'arrivée svp !"
            valid = false
        } else {
            ville_darrivee_error!!.error = null
        }

        if(_date_voyage.isEmpty()) {
            date_annonce_error!!.error = "Veuillez remplir la date de départ svp !"
            valid = false
        } else {
            date_annonce_error!!.error = null
        }

        if(_date_voyage2.isEmpty()) {
            date_annonce2_error!!.error = "Veuillez renseigner la date d'arrivée svp !"
            valid = false
        } else {
            date_annonce2_error!!.error = null
        }

        if(_heure_depart.isEmpty()) {
            heure_depart_error!!.error = "Veuillez renseigner l'heure de départ svp !"
            valid = false
        } else {
            heure_depart!!.error = null
        }


        return valid
    }

    fun validate2() : Boolean {
        var valid = true

        val _heur_darrivee = heure_darrivee!!.text.toString()
        val _prix_transaction = prix_transaction!!.text.toString()
        val _poids = poids!!.text.toString()
        val _lieux_rdv1 = Lieux_rdv1!!.text.toString()
        val _lieux_rdv2 = Lieux_rdv2!!.text.toString()

        if(_heur_darrivee.isEmpty()) {
            heure_darrivee_error!!.error = "Veuillez renseigner l'heure d'arrivée svp !"
            valid = false
        } else {
            heure_darrivee_error!!.error = null
        }

        if(_prix_transaction.isEmpty()) {
            prix_transaction_error!!.error = "Veuillez indiquer le prix de la transaction svp !"
            valid = false
        } else {
            prix_transaction_error!!.error = null
        }

        if(_poids.isEmpty()) {
            poids_error!!.error = "Veuillez indiquer le poids par kilo svp !"
            valid = false
        } else {
            poids_error!!.error = null
        }

        if (_lieux_rdv1.isEmpty()) {
            Lieux_rdv1_error!!.error = "Veuillez indiquer le lieux du Rdv de départ"
            valid = false
        } else {
            Lieux_rdv1_error!!.error = null
        }

        if(_lieux_rdv2.isEmpty()) {
            Lieux_rdv2_error!!.error = "Veuillez indiquer le lieux du RDV d'arrivée"
            valid = false
        } else {
            Lieux_rdv2_error!!.error = null
        }

        return valid
    }

    private fun InsertAnnonce(alert:AlertDialog) {

        val dateannonce_string = dateannonce!!.text.toString().split("-")[2]+"-"+dateannonce!!.text.toString().split("-")[1]+"-"+dateannonce!!.text.toString().split("-")[0]

        val stringRequest = object : StringRequest(
            Request.Method.GET,Const.dns+"/colis/ColisApi/public/api/InsertAnnonce?heure_depart="
                    +heure_depart!!.text.toString()+"&heure_arrivee="
                    +heure_darrivee!!.text.toString()+"&max_kilo="+poids!!.text.toString()+"&lieux_rdv1="+Lieux_rdv1!!.text.toString()
                    +"&lieux_rdv2="+Lieux_rdv2!!.text.toString()
                    +"&dateannonce="+dateannonce_string+"&id_user="+user!!.ID+"&id_aeroport1="+idaeroportdepart.toString()
                    +"&id_aeroport2="+idaeroportarrivee.toString()+"&prix="+prix_transaction!!.text.toString()+"&dateannonce2="+selectedDate14,
            Response.Listener<String> { response ->
                try {
                    showJSON(response)
                    Block2!!.visibility = View.GONE
                    Block1!!.visibility = View.VISIBLE
                    Block1!!.startAnimation(anim)
                    selectedDate = null
                    selectedDate1 = null
                    selectedDate2 = null
                    selectedDate14 = null
                    date_annonce!!.text = null
                    date_annonce2!!.text = null
                    ville_depart!!.text = null
                    ville_arrivee!!.text = null
                    heure_depart!!.text = null
                    heure_darrivee!!.text = null
                    prix_transaction!!.text = null
                    poids!!.text = null
                    alert.dismiss()

                }catch (e: JSONException){
                    e.printStackTrace()
                    Toast.makeText(activity,"Hello Wordld2", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(activity,"Une erreur réseaux, veuillez revoir votre connexion internet",
                    Toast.LENGTH_LONG).show()
                val snack = coordinatorLayout?.let { it1 -> Snackbar.make(it1,activity!!.getString(R.string.error_volley_servererror),Snackbar.LENGTH_LONG) }
                snack!!.show()
                alert.dismiss()
            }){
            @Throws(AuthFailureError::class)
            override fun getParams():Map<String, String> {
                val params = HashMap<String, String>()
                return params
            }
        }

        MyApplication.instance?.addToRequestQueue(stringRequest)
    }

    fun showJSON(response:String) {
        try {
            reponse = JSONObject(response)
            succes = reponse!!.getInt("succes")
            if(succes==1) {
                Toast.makeText(activity,"Votre Annonce vient d'etre publier avec succes !", Toast.LENGTH_LONG).show()
                ville_depart!!.text = null
                ville_arrivee!!.text = null
                date_annonce!!.text = null
                prix_transaction!!.text = null
                poids!!.text = null
                heure_depart!!.text = null
                heure_darrivee!!.text = null
                Lieux_rdv1!!.text = null
                Lieux_rdv2!!.text = null

            }
        }catch (e: JSONException){
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Toast.makeText(activity, requestCode.toString()+" "+data!!.getStringExtra("selectedTime"),Toast.LENGTH_LONG).show()

        if (requestCode == Const.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedDate = data!!.getStringExtra("selectedDate")
            dateannonce!!.setText(selectedDate)

        } else if (requestCode == Const.REQUEST_CODE_ARRIVEE && resultCode == Activity.RESULT_OK && data!!.getStringExtra("ville")!=null) {
            ville_arrivee!!.setText(data!!.getStringExtra("ville"))
            idaeroportarrivee = data!!.getIntExtra("id",0)

        } else if (requestCode == Const.REQUEST_CODE_DEPART && resultCode == Activity.RESULT_OK && data!!.getStringExtra("ville")!= null) {
            ville_depart!!.setText(data!!.getStringExtra("ville"))
            idaeroportdepart = data!!.getIntExtra("id",0)

        } /*else if (requestCode == REQUEST_CODE12 && resultCode == Activity.RESULT_OK) {
            selectedDate1 = data!!.getStringExtra("selectedTime")
            heure_depart!!.setText(selectedDate1+":00")

        }*/

        if(requestCode == REQUEST_CODE13 && resultCode == Activity.RESULT_OK && data!!.getStringExtra("selectedTime") != null) {
            selectedDate2 = data!!.getStringExtra("selectedTime")
            heure_darrivee!!.setText(data!!.getStringExtra("selectedTime")+":00")
        }


        if (requestCode == Const.REQUEST_CODE12 && resultCode == Activity.RESULT_OK && data!!.getStringExtra("selectedTime") != null) {
            selectedDate1 = data!!.getStringExtra("selectedTime")
            heure_depart!!.setText(selectedDate1 + ":00")
        }

        if(requestCode == REQUEST_CODE14 && resultCode == Activity.RESULT_OK) {
            selectedDate14 = data!!.getStringExtra("selectedDate")
            date_annonce2!!.setText(data!!.getStringExtra("selectedDate"))
        }



        /*if(requestCode == Const.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedDate = data!!.getStringExtra("selectedDate")
            date_annonce!!.setText(selectedDate)

        }*/



        /*else if (requestCode == Const.REQUEST_CODE12 && resultCode == Activity.RESULT_OK) {
            selectedDate1 = data!!.getStringExtra("selectedTime")
            heure_depart!!.setText(selectedDate1+":00")

        } else if (requestCode == Const.REQUEST_CODE13 && resultCode == Activity.RESULT_OK) {
            selectedDate2 = data!!.getStringExtra("selectedTime")
            heure_darrivee!!.setText(data!!.getStringExtra("selectedTime")+":00")

        } else if(requestCode == Const.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedDate = data!!.getStringExtra("selectedDate")
            date_annonce!!.setText(selectedDate)

        }  else if (requestCode == Const.REQUEST_CODE13 && resultCode == Activity.RESULT_OK) {
            selectedDate2 = data!!.getStringExtra("selectedTime")
            heure_darrivee!!.setText(data!!.getStringExtra("selectedTime")+":00")

        }*/
    }

}