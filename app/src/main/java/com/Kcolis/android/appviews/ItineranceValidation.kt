package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cl.jesualex.stooltip.Position
import cl.jesualex.stooltip.Tooltip
import com.Kcolis.android.R
import kotlinx.android.synthetic.main.connexion.*

class ItineranceValidation : AppCompatActivity() {

    private var valider1 : TextView? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itinerance_validation)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "Tracking du Colis"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)
        valider1 = findViewById<TextView>(R.id.connexion)

        Tooltip.on(valider1!!)
            .text("Boss Fuck le tooltip")
            .iconSize(30, 30)
            .color(resources.getColor(R.color.colorPrimary))
            .clickToHide(true)
            .corner(5)
            .position(Position.BOTTOM)
            .show(6000)
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