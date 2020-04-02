package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.adapter.AnnoncesListAdapter
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.Annonce
import com.Kcolis.android.utils.CustomRecyclerViewItemTouchListener
import com.Kcolis.android.utils.MyApplication
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.annonceslist.*
import kotlinx.android.synthetic.main.connexion.toolbar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AnnoncesList : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private var allUsersAdapter : AnnoncesListAdapter? = null
    private var database : DatabaseHandler? = null
    private var session : SessionManager? = null
    private var data : ArrayList<Annonce>? = null
    private var messageError : TextView? = null
    private var mShimmerViewContainer : ShimmerFrameLayout? = null
    private var coordinatorLayout : CoordinatorLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.annonceslist)

        //we init the array
        data = ArrayList<Annonce>()

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "Annonces"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        ConnexionListeAnnonce()

        session = SessionManager(applicationContext)
        database = DatabaseHandler(applicationContext,null)
        mShimmerViewContainer = findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        messageError = findViewById<TextView>(R.id.message_error)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        allUsersAdapter = data?.let { AnnoncesListAdapter(it) }
        recyclerView!!.adapter = allUsersAdapter

        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        swipe_refresh_layout.setOnRefreshListener {
            swipe_refresh_layout.isRefreshing = false
        }

        recyclerView!!.addOnItemTouchListener(
            CustomRecyclerViewItemTouchListener(
                recyclerView!!,
                intArrayOf(R.id.user_first_name),
                object : CustomRecyclerViewItemTouchListener.MyCustomClickListener {
                    override fun onBackupClick(view: View, position: Int) {


                         /*Intent intent = new Intent(getApplicationContext(),DetailsAnnonces.class);
                intent.putExtra("annonce",data.get(position));
                startActivity(intent);*/

                        val intent = Intent(applicationContext, DetailsAnnonces::class.java)
                        intent.putExtra("annonce", data!![position])
                        startActivity(intent)

                    }

                    override fun onBlockClick(view: View, position: Int) {
                        //Toast.makeText(view.context, "Block action on position = " + position, Toast.LENGTH_LONG).show()
                    }

                    override fun onClick(view: View, position: Int) {

                    }

                    override fun onLongClick(view: View, position: Int) {
                        //Toast.makeText(view.context, "Long click action on position = " + position, Toast.LENGTH_LONG).show()
                    }
                })
        )
    }



    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                val intent = Intent()
                setResult(Activity.RESULT_OK,intent)
                finish()
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


    override fun onResume() {
        super.onResume()
        mShimmerViewContainer!!.startShimmer()
    }

    override fun onPause() {
        mShimmerViewContainer!!.stopShimmer()
        super.onPause()
    }

    private fun ConnexionListeAnnonce() {

        val datevoyageannonce : String = intent.getStringExtra("date").toString().split("-")[2]+"-"+intent.getStringExtra("date").split("-")[1]+"-"+intent.getStringExtra("date").split("-")[0]

        val url_liste_annonce = Const.dns+"/colis/ColisApi/public/api/Rechercher?lieux_arrivee="+intent.getStringExtra("arrivee")+"&lieux_depart="+intent.getStringExtra("depart")+"&date_voyage="+datevoyageannonce

        val stringRequest = object : StringRequest(
            Request.Method.GET, url_liste_annonce,
            Response.Listener<String> { response ->
                data!!.clear()
                try {
                    //Toast.makeText(applicationContext,response,Toast.LENGTH_LONG).show()
                    val reponses : JSONArray = JSONArray(response)
                    for (i in 0 until  reponses.length()){
                        val item = reponses.getJSONObject(i)
                        val annonce = Annonce(item.getInt("ID"),item.getInt("ID_USER"),
                            item.getString("PHOTO_USER"),item.getString("NOM_USER"),item.getString("PHONE_USER"),item.getString("DATE_ANNONCE"),
                            item.getString("DATE_ANNONCE_VOYAGE"),item.getString("Prix"),item.getString("lieux_rdv1"),item.getString("lieux_rdv2"),
                            item.getString("ville_depart"),item.getString("ville_arrivee"),item.getString("heure_depart"),
                            item.getString("heure_darrivee"),item.getString("nombre_kilo"),item.getString("DATE_ANNONCE_VOYAGE2"),item.getString("KEYPUSH"))
                        data!!.add(annonce)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                    Toast.makeText(applicationContext,"Hello Wordld2", Toast.LENGTH_LONG).show()
                }

                allUsersAdapter!!.notifyDataSetChanged()
                mShimmerViewContainer!!.stopShimmer()
                mShimmerViewContainer!!.visibility = View.GONE

                if (data!!.isEmpty()) {
                    messageError!!.visibility = View.VISIBLE
                } else {
                    messageError!!.visibility = View.GONE
                }


            },
            Response.ErrorListener {
               // Toast.makeText(applicationContext,"Une erreur réseaux, veuillez revoir votre connexion internet",
                 //   Toast.LENGTH_LONG).show()
                mShimmerViewContainer!!.stopShimmer()
                val snack = Snackbar.make(findViewById<CoordinatorLayout>(R.id.coordinatorLayout),applicationContext.getString(R.string.error_volley_servererror),Snackbar.LENGTH_LONG)
                snack.show()
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