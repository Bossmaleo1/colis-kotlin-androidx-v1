package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.adapter.PaymentAdapter
import com.Kcolis.android.adapter.SearchTownAdapter
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.Annonce
import com.Kcolis.android.model.data.Payment
import com.Kcolis.android.model.data.TownItem
import com.Kcolis.android.utils.CustomRecyclerViewItemTouchListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.connexion.*

class PaymentList : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private var allUsersAdapter: PaymentAdapter? = null
    private var snackbar: Snackbar? = null
    private var data : ArrayList<Payment>? = null
    private var session : SessionManager? = null
    private var database: DatabaseHandler? = null
    private var coordinatorLayout : CoordinatorLayout? = null
    private var annonce: Annonce? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment)

        //we init the array
        data = ArrayList<Payment>()

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "Paiements"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)
        session = SessionManager(applicationContext)
        database = DatabaseHandler(applicationContext,null)

        //we add our element
        data!!.add(Payment("CARTE BANCAIRE",R.drawable.baseline_credit_card_black_48))
        data!!.add(Payment("VISA",R.drawable.visa))
        data!!.add(Payment("MASTERCARD",R.drawable.mastercard))
        data!!.add(Payment("PAYPAL",R.drawable.paypal))

        annonce = intent!!.getParcelableExtra("annonce")

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        allUsersAdapter = data?.let { PaymentAdapter(it) }
        recyclerView!!.adapter = allUsersAdapter


        recyclerView!!.addOnItemTouchListener(
            CustomRecyclerViewItemTouchListener(
                recyclerView!!,
                intArrayOf(R.id.user_first_name),
                object : CustomRecyclerViewItemTouchListener.MyCustomClickListener {
                    override fun onBackupClick(view: View, position: Int) {
                      val intent = Intent(applicationContext,Validation_Annonce::class.java)
                      intent.putExtra("annonce",annonce)
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
}