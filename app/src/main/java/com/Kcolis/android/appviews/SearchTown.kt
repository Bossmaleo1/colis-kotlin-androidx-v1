package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.adapter.SearchTownAdapter
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.data.TownItem
import com.Kcolis.android.utils.CustomRecyclerViewItemTouchListener
import com.Kcolis.android.utils.MyApplication
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.connexion.toolbar
import kotlinx.android.synthetic.main.searchtown.*
import org.json.JSONArray
import org.json.JSONException

class SearchTown : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private var allUsersAdapter: SearchTownAdapter? = null
    private var data : ArrayList<TownItem>? = null
    private var searchview : TextInputEditText? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchtown)

        //we init the array
        data = ArrayList<TownItem>()

        val anim = AnimationUtils.loadAnimation(this,R.anim.slide_in)
        findViewById<CoordinatorLayout>(R.id.coordinatorLayout).startAnimation(anim)

        searchview = findViewById<TextInputEditText>(R.id.searchview)


        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  intent.getStringExtra("title")
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        allUsersAdapter = data?.let { SearchTownAdapter(it) }
        recyclerView!!.adapter = allUsersAdapter


        searchview!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                SearchTown(searchview!!.text.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        recyclerView!!.addOnItemTouchListener(CustomRecyclerViewItemTouchListener(
            recyclerView!!,
            intArrayOf(R.id.user_first_name),
            object : CustomRecyclerViewItemTouchListener.MyCustomClickListener {
                override fun onBackupClick(view: View, position: Int) {
                    val intent = Intent()
                    intent.putExtra("ville", data!![position].Libelle)
                    intent.putExtra("id",data!![position].ID)
                    setResult(Activity.RESULT_OK,intent)
                    finish()
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

    private fun SearchTown(townvalue:String) {

        val stringRequest = object : StringRequest(
            Request.Method.GET, Const.dns+"/colis/ColisApi/public/api/SearchTown?town="+townvalue,
            Response.Listener<String> { response ->
                data!!.clear()
                try {
                    val reponses : JSONArray = JSONArray(response)
                    for (i in 0 until  reponses.length()){
                        val item = reponses.getJSONObject(i)
                        data!!.add(TownItem(item.getInt("ID"),item.getString("Libelle")))
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                    Toast.makeText(applicationContext,"Hello Wordld2",Toast.LENGTH_LONG).show()
                }

                allUsersAdapter!!.notifyDataSetChanged()
                if (data!!.isEmpty()) {
                    message_error.visibility = View.VISIBLE
                } else {
                    message_error.visibility = View.GONE
                }


            },
            Response.ErrorListener {
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