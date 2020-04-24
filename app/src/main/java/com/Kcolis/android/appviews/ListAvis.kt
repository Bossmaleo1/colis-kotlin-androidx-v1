package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.adapter.ListAvisAdapter
import com.Kcolis.android.adapter.PaymentAdapter
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.ListAvis
import com.Kcolis.android.model.data.Payment
import com.Kcolis.android.model.data.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.connexion.*

class ListAvis : AppCompatActivity() {

    private var allUsersAdapter: ListAvisAdapter? = null
    private var data : ArrayList<ListAvis>? = null
    private var snackbar: Snackbar? = null
    private var recyclerView : RecyclerView? = null
    private var database: DatabaseHandler? = null
    private var session: SessionManager? = null
    private var user: User? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listavis)

        database = DatabaseHandler(applicationContext,null)
        session = SessionManager(applicationContext)
        user = database!!.getUser((session!!.getUserDetail()[Const.Key_ID] ?: error("")).toInt())

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "Les Avis"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        data = ArrayList<ListAvis>()
        data!!.add(com.Kcolis.android.model.data.ListAvis("test.png",3.2,"MALEO","Sidney","C'est un bon expediteur toujours ponctuel pour le rendez-vous","il y a 1 jour"))
        data!!.add(com.Kcolis.android.model.data.ListAvis("test.png",3.2,"MALEO","Sidney","C'est un bon expediteur toujours ponctuel pour le rendez-vous","il y a 1 jour"))
        data!!.add(com.Kcolis.android.model.data.ListAvis("test.png",3.2,"MALEO","Sidney","C'est un bon expediteur toujours ponctuel pour le rendez-vous","il y a 1 jour"))
        data!!.add(com.Kcolis.android.model.data.ListAvis("test.png",3.2,"MALEO","Sidney","C'est un bon expediteur toujours ponctuel pour le rendez-vous","il y a 1 jour"))

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext)
        allUsersAdapter = data?.let { ListAvisAdapter(it,applicationContext) }
        recyclerView!!.adapter = allUsersAdapter

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
}