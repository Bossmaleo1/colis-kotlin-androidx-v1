package com.Kcolis.android.connInscript

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.Kcolis.android.utils.VolleySingleton
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.connexion.*
import org.json.JSONException
import org.json.JSONObject

class Connexion : AppCompatActivity() {

    private var coordinatorLayout : CoordinatorLayout? = null
    private var about : TextView? = null
    private var aboutblock : LinearLayout? = null
    private var passwordforget : TextView? = null
    private var bouton_connexion : Button? = null
    private var email : TextInputEditText? = null
    private var password : TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connexion)

        coordinatorLayout = findViewById(R.id.coordinatorLayout) as CoordinatorLayout
        about = findViewById(R.id.about) as TextView
        aboutblock = findViewById(R.id.aboutblock) as LinearLayout
        passwordforget = findViewById(R.id.passforget)  as TextView
        bouton_connexion = findViewById(R.id.connexion) as Button
        email = findViewById<TextInputEditText>(R.id.email)
        password = findViewById(R.id.password) as TextInputEditText

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Kcolis"

        bouton_connexion!!.setOnClickListener {
            connexion()
        }
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_inscript,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when(item.itemId) {
            R.id.inscript -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun connexion() {

        val email_string = email?.text.toString()
        val password_string = password?.text.toString()

        val stringRequest = object : StringRequest(Request.Method.GET,Const.dns+"/colis/ColisApi/public/api/connexion?email="+email_string+"&password="+password_string,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        Toast.makeText(applicationContext,"Hello Wordld1"+response,Toast.LENGTH_LONG).show()
                    }catch (e: JSONException){
                        e.printStackTrace()
                        Toast.makeText(applicationContext,"Hello Wordld2",Toast.LENGTH_LONG).show()
                    }
                },
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(applicationContext,"Votre mot de passe ou votre email est incorrect",Toast.LENGTH_LONG).show()
                }
            }){
            @Throws(AuthFailureError::class)
             override fun getParams():Map<String, String> {
                 val params = HashMap<String, String>()
                 return params
             }
        }

        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }


}