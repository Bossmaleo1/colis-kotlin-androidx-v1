package com.Kcolis.android.appviews

import android.app.Activity
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.Annonce
import com.Kcolis.android.model.data.User
import com.Kcolis.android.utils.MyApplication
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.connexion.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*


class Validation_Annonce : AppCompatActivity() {

    private var session : SessionManager? = null
    private var database : DatabaseHandler? = null
    private var user : User? = null
    private var validation: Button? = null
    private var nomproprietaire: EditText? = null
    private var dateexpiration: EditText? = null
    private var numero_carte_derriere: EditText? = null
    private var numero_carte: EditText? = null
    private var poids: EditText? = null
    private var Description: EditText? = null
    private var nomproprietaire_error: TextInputLayout? = null
    private var dateexpiration_error: TextInputLayout? = null
    private var numero_carte_derriere_error: TextInputLayout? = null
    private var numero_carte_error: TextInputLayout? = null
    private var poids_error: TextInputLayout? = null
    private var Description_error: TextInputLayout? = null
    private var validation_image: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var block: RelativeLayout? = null
    private var annonce : Annonce? = null
    private var day: Int? = null
    private var month: Int? = null
    private var year: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.validation_annonce)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "PAIEMENT"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        database = DatabaseHandler(applicationContext,null)
        session = SessionManager(applicationContext)

        validation = findViewById<Button>(R.id.send)
        validation_image = findViewById<ImageView>(R.id.validation_maleo)
        nomproprietaire = findViewById<EditText>(R.id.nomproprietaire)
        dateexpiration = findViewById<EditText>(R.id.dateexpiration)
        numero_carte_derriere = findViewById<EditText>(R.id.numero_carte_derriere)
        numero_carte = findViewById<EditText>(R.id.numero_carte)
        poids = findViewById<EditText>(R.id.poids)
        Description = findViewById<EditText>(R.id.description)
        progressBar = findViewById<ProgressBar>(R.id.progressbar)
        block = findViewById<RelativeLayout>(R.id.block)
        nomproprietaire_error = findViewById<TextInputLayout>(R.id.text_input_layout_nomproprietaire)
        dateexpiration_error = findViewById<TextInputLayout>(R.id.text_input_layout_dateexpiration)
        numero_carte_derriere_error = findViewById<TextInputLayout>(R.id.text_input_layout_numero_carte_derriere)
        numero_carte_error = findViewById<TextInputLayout>(R.id.text_input_layout_numero_carte)
        poids_error = findViewById<TextInputLayout>(R.id.text_input_layout_poids)
        Description_error = findViewById<TextInputLayout>(R.id.text_input_layout_description)
        annonce = intent.getParcelableExtra("annonce")
        dateexpiration!!.setText("09-2019")
        numero_carte_derriere!!.setText("854")
        numero_carte!!.setText("5860-4024-5627-1523")

         user = database!!.getUser(session!!.getUserDetail().get(Const.Key_ID)!!.toInt())

        validation!!.setOnClickListener{
            if(validate()) {
                VALIDATION_ANNONCE()
            }
        }

        addListenerOnButton()
        setCurrentDateOnView()
    }

    fun validate():Boolean {
        var valid: Boolean = true

        val _nom_proprietaire = nomproprietaire!!.text.toString()
        val _dateexpiration = dateexpiration!!.text.toString()
        val _numero_carte_derriere = numero_carte_derriere!!.text.toString()
        val _numero_carte = numero_carte!!.text.toString()
        val _poids = poids!!.text.toString()
        val _description = Description!!.text.toString()

        if (_nom_proprietaire.isEmpty()) {
            nomproprietaire_error!!.error = "Veuillez preciser le nom du proprietaire de la carte"
            valid = false
        } else {
            nomproprietaire_error!!.error = null
        }

        if(_dateexpiration.isEmpty()) {
            dateexpiration_error!!.error = "Veuillez preciser la date d'expiration de la carte"
            valid = false
        } else {
            dateexpiration_error!!.error = null
        }

        if(_numero_carte_derriere.isEmpty()) {
            numero_carte_derriere_error!!.error = "Veuillez preciser le numero de la carte"
            valid = false
        } else {
            numero_carte_derriere_error!!.error = null
        }

        if(_poids.isEmpty()) {
            poids_error!!.error = "Veuillez preciser le poids de votre colis"
            valid = false
        } else {
            poids_error!!.error = null
        }

        if (_description.isEmpty()) {
            Description_error!!.error = "Veuillez preciser la description de votre colis"
            valid = false
        } else {
            Description_error!!.error = null
        }


        return valid
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                /*val intent = Intent()
                setResult(Activity.RESULT_OK,intent)
                finish()*/
                val intent = Intent(applicationContext, Home::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    fun addListenerOnButton() {
        dateexpiration!!.setOnClickListener { showDialog(Const.DATE_DIALOG_ID) }
    }


    private val datePickerListener =
        OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            // when dialog box is closed, below method will be called.
            year = selectedYear
            month = selectedMonth + 1
            day = selectedDay
            //dateexpiration.setText(day);
            if (month!! > 9 && day!! > 9) {
                dateexpiration!!.setText(day.toString() + "-" + month + "-" + year)
            } else if (day!! < 9 && month!! > 9) {
                dateexpiration!!.setText("0$day-$month-$year")
            } else if (month!! < 9 && day!! > 9) {
                dateexpiration!!.setText(day.toString() + "-0" + month + "-" + year)
            } else if (month!! < 9 && day!! < 9) {
                dateexpiration!!.setText("0$day-0$month-$year")
            }
        }



    fun setCurrentDateOnView() {
        val c: Calendar = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)
    }

    private fun volley_de_sms_notification() {
        val message = "L'utilisateur "+user!!.PRENOM+" "+user!!.NOM+" vous éffectuez une demande d'expedition de colis"
        val url = "https://api.smsbox.fr/api.php?apikey=pub-ad1746a3c1fa0266937010c56e18e0b0-7dd7c66d-e54b-4b17-9c1d-73c4215482c1&msg="+ message+"&dest=" +
                annonce!!.PHONE_USER+"&mode=Expert"

        val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

            },
            Response.ErrorListener {

            }){
            @Throws(AuthFailureError::class)
            override fun getParams():Map<String, String> {
                val params = HashMap<String, String>()
                return params
            }
        }

        MyApplication.instance?.addToRequestQueue(stringRequest)
    }

    private fun VALIDATION_ANNONCE() {

        val message = "L'utilisateur "+user!!.PRENOM+" "+user!!.NOM+" Vous avez effectuez une demande d'expedition de colis"
        block!!.visibility = View.GONE
        progressBar!!.visibility
        val url = Const.dns+"/colis/ColisApi/public/api/InsertValidation?nombrekilo="+poids!!.text.toString()+"&description="+Description!!.text.toString()+"&id_annonce="+annonce!!.ID+"&id_emmetteur="+user!!.ID+"&libelle="+message

        val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                //block!!.visibility = View.GONE
                progressBar!!.visibility = View.GONE
                validation_image!!.visibility = View.VISIBLE
                Toast.makeText(applicationContext,"Votre demande vient d'etre effectuer avec succes !!",Toast.LENGTH_LONG).show()
                //volley_de_sms_notification()
            },
            Response.ErrorListener {
                val snack = Snackbar.make(findViewById<CoordinatorLayout>(R.id.coordinatorLayout),applicationContext.getString(R.string.error_volley_servererror),
                    Snackbar.LENGTH_LONG)
                snack.show()
                progressBar!!.visibility = View.GONE
                validation_image!!.visibility = View.VISIBLE
                block!!.visibility = View.VISIBLE
            }){
            @Throws(AuthFailureError::class)
            override fun getParams():Map<String, String> {
                val params = HashMap<String, String>()
                return params
            }
        }

        MyApplication.instance?.addToRequestQueue(stringRequest)
    }


}