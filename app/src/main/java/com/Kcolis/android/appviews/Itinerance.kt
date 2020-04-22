package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.data.NotificationItem
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.connexion.*


class Itinerance : AppCompatActivity() {

    private var notificationitem: NotificationItem? = null

    private var nom_user1: TextView? = null
    private var date_user1: TextView? = null
    private var message1 : TextView? = null
    private var picture: SimpleDraweeView? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itinerance)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "Suivi du colis"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        nom_user1 = findViewById(R.id.title93)
        date_user1 = findViewById(R.id.title1)
        picture = findViewById(R.id.icon)
        message1 = findViewById(R.id.contenu93)

        notificationitem = intent.getParcelableExtra("notification")
        nom_user1!!.text = notificationitem!!.prenom_emmetteur+""+notificationitem!!.nom_emmetteur
        date_user1!!.text = notificationitem!!.date_validation
        if(notificationitem!!.photo_emmetteur!= null) {
            val uri = Uri.parse(Const.dns+"/colis/uploads/photo_de_profil/"+notificationitem!!.photo_emmetteur)
            picture!!.setImageURI(uri)
        } else {
            picture!!.setImageResource(R.drawable.ic_profile_colorier)
        }
        message1!!.text = "Votre demande de transport de colis vient d'être valider"
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