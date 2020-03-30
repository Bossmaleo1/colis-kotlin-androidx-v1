package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
        collapsingToolbarLayout!!.title = annonce!!.NOM_USER
        collapsingToolbarLayout!!.setContentScrimColor(applicationContext.getColor(R.color.colorPrimary))
        pictureuser = findViewById(R.id.my_image_view)

        if(annonce!!.PHOTO_USER!=null){
            val uri = Uri.parse(Const.dns+"/colis/uploads/photo_de_profil/"+annonce!!.PHOTO_USER)
            pictureuser!!.setImageURI(uri)
        } else {
            pictureuser!!.setImageResource(R.drawable.ic_profile_colorier)
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