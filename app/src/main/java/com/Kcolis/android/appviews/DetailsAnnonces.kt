package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.data.Annonce
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.connexion.*


class DetailsAnnonces : AppCompatActivity() {

    private var annonce: Annonce? = null
    private var pictureuser: SimpleDraweeView? = null
    private var user_name: TextView? = null
    private var user_label_time: TextView? = null
    private var ville_depart: TextView? = null
    private var ville_arrivee: TextView? = null
    private var dateannonce: TextView? = null
    private var prix: TextView? = null
    private var heure_depart: TextView? = null
    private var heure_arrivee: TextView? = null
    private var dateannonce2: TextView? = null
    private var poids: TextView? = null
    private var rdv1: TextView? = null
    private var rdv2: TextView? = null
    private var block_title: RelativeLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_annonces)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "Details Annonce"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        annonce = intent.getParcelableExtra("annonce")

        pictureuser = findViewById<SimpleDraweeView>(R.id.icon)
        user_name  = findViewById<TextView>(R.id.title)
        user_label_time = findViewById<TextView>(R.id.title1)
        ville_depart = findViewById<TextView>(R.id.contenu)
        ville_arrivee = findViewById<TextView>(R.id.contenu_ville_arrivee_block)
        dateannonce = findViewById<TextView>(R.id.contenu_heure_depart)
        dateannonce2 = findViewById<TextView>(R.id.contenu_heure_depart2)
        prix = findViewById<TextView>(R.id.contenu_heure_arrivee)
        heure_depart = findViewById<TextView>(R.id.contenu_heure_depart_vrai)
        heure_arrivee = findViewById<TextView>(R.id.contenu_heure_arrivee_vrai)
        poids = findViewById<TextView>(R.id.poids_vrai)
        rdv1 = findViewById<TextView>(R.id.rdv1)
        rdv2 = findViewById<TextView>(R.id.rdv2)
        block_title = findViewById<RelativeLayout>(R.id.iconetmachin);

        user_name!!.text = annonce!!.NOM_USER
        user_label_time!!.text = annonce!!.DATE_ANNONCE
        ville_depart!!.text = annonce!!.VILLE_DEPART
        ville_arrivee!!.text = annonce!!.VILLE_ARRIVEE

        dateannonce!!.text = "Date de départ : "+annonce!!.DATE_ANNONCE_VOYAGE
        dateannonce2!!.text = "Date d'arrivée : "+annonce!!.DATE_ANNONCE_VOYAGE2
        prix!!.text = annonce!!.PRIX+" euros/Kg"
        heure_depart!!.text = "Heure de départ : "+annonce!!.HEURE_DEPART
        heure_arrivee!!.text = "Heure d'arrivée : "+annonce!!.HEURE_ARRIVEE
        rdv1!!.text = "Rdv de départ : "+annonce!!.LIEUX_RDV1
        rdv2!!.text = "Rdv d'arrivée : "+annonce!!.LIEUX_RDV2
        poids!!.text = annonce!!.NOMBRE_KILO+" Kg (max)"

        if(annonce!!.PHOTO_USER != null) {
            val uri = Uri.parse(Const.dns+"/colis/uploads/photo_de_profil/"+annonce!!.PHOTO_USER)
            pictureuser!!.setImageURI(uri)
        } else {
            pictureuser!!.setImageResource(R.drawable.ic_profile_colorier)
        }

        block_title!!.setOnClickListener {
            /*val intent = Intent(applicationContext,DetailsAnnonceur::class.java)
            intent.putExtra("annonce", annonce)
            startActivity()*/
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
        setResult(Activity.RESULT_OK,intent)
        finish()
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.validationannonce, menu)
        val favoriteItem: MenuItem = menu.findItem(R.id.check_annonce)
        val newIcon = favoriteItem.icon as Drawable
        newIcon.mutate().setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_IN)
        favoriteItem.icon = newIcon
        return true
    }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // User chose the "Settings" item, show the app settings UI...
                val i = Intent()
                setResult(Activity.RESULT_OK, i)
                finish()
                true
            }
            R.id.check_annonce -> {
                val intent = Intent(applicationContext, PaymentList::class.java)
                intent.putExtra("annonce", annonce)
                startActivity(intent)
                true
            }
            else ->  // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
        }
    }

}