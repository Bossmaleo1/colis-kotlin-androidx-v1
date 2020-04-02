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
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.connexion.*


class DetailsValidation : AppCompatActivity() {

    private var collapsingToolbarLayout : CollapsingToolbarLayout? = null
    private var pictureuser : SimpleDraweeView? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailsannonceur)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar)
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