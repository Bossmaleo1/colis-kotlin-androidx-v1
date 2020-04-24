package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cl.jesualex.stooltip.Position
import cl.jesualex.stooltip.Tooltip
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.connexion.*


class DetailsValidation : AppCompatActivity() {

    private var collapsingToolbarLayout : CollapsingToolbarLayout? = null
    private var pictureuser : SimpleDraweeView? = null
    private var notationblock : RelativeLayout? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailsannonceur)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar)
        notationblock =  findViewById(R.id.notation_block)
        collapsingToolbarLayout!!.title = intent.getStringExtra("nom")
        collapsingToolbarLayout!!.setContentScrimColor(applicationContext.getColor(R.color.colorPrimary))
        pictureuser = findViewById(R.id.my_image_view)
        if (!intent.getStringExtra("photo_user").equals("null")) {
            val uri: Uri = Uri.parse(
                Const.dns.toString() + "/colis/uploads/photo_de_profil/" + intent.getStringExtra("photo_user")
            )
            pictureuser!!.setImageURI(uri)
        } else {
            pictureuser!!.setImageResource(R.drawable.ic_profile_colorier)
        }

        Tooltip.on(notationblock!!)
            .text("Voir les détails des avis sur cet utilisateur")
            .iconSize(30, 30)
            .color(resources.getColor(R.color.colorPrimary))
            .clickToHide(true)
            .corner(5)
            .padding(50,50,50,50)
            .position(Position.BOTTOM)
            .show(6000)

        notationblock!!.setOnClickListener{
            val intent = Intent(applicationContext,ListAvis::class.java)
            startActivity(intent)
        }

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