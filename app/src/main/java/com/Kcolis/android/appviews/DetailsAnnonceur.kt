package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cl.jesualex.stooltip.Position
import cl.jesualex.stooltip.Tooltip
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.data.Annonce
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.connexion.*

class DetailsAnnonceur : AppCompatActivity() {

    private var annonce: Annonce? = null
    private var collapsingToolbarLayout : CollapsingToolbarLayout? = null
    private var pictureuser : SimpleDraweeView? = null
    private var notationblock : LinearLayout? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailsannonceur)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  ""
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)




        annonce = intent!!.getParcelableExtra("annonce")
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar)
        notationblock = findViewById(R.id.notation_sous_block)
        collapsingToolbarLayout!!.title = annonce!!.NOM_USER
        collapsingToolbarLayout!!.setContentScrimColor(applicationContext.getColor(R.color.colorPrimary))
        pictureuser = findViewById(R.id.my_image_view)

        if(annonce!!.PHOTO_USER!=null){
            val uri = Uri.parse(Const.dns+"/colis/uploads/photo_de_profil/"+annonce!!.PHOTO_USER)
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
            .position(Position.BOTTOM)
            .show(6000)

        notationblock!!.setOnClickListener{
            Toast.makeText(applicationContext,"Mon clique vient de marcher !!",Toast.LENGTH_LONG).show()
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