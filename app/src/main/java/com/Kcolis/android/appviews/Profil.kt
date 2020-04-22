package com.Kcolis.android.appviews

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.User
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.connexion.*


class Profil : AppCompatActivity() {

    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var pictureuser: ImageView? = null
    private var database: DatabaseHandler? = null
    private var session: SessionManager? = null
    private var user: User? = null
    private var draweeView: SimpleDraweeView? = null
    private var mention_icon: ImageView? = null
    private var notation_block: RelativeLayout? = null
    private var piece_identite_block: RelativeLayout? = null
    private var verification_piece_icon: ImageView? = null
    private var verification_piece_text: TextView? = null
    private var piece_identite_icon_failed: Drawable? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = DatabaseHandler(applicationContext,null)
        session = SessionManager(applicationContext)
        val dark_mode_item = database!!.getDARKMODE()
        if(dark_mode_item=="1") {
            setTheme(R.style.AppDarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        setContentView(R.layout.profil)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title =  "Paiements"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)


        user = database!!.getUser((session!!.getUserDetail()[Const.Key_ID] ?: error("")).toInt())

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar)
        mention_icon = findViewById(R.id.mention_icon)
        draweeView = findViewById(R.id.my_image_view)
        collapsingToolbarLayout!!.title = user!!.PRENOM+" "+user!!.NOM
        collapsingToolbarLayout!!.setContentScrimColor(applicationContext.getColor(R.color.colorPrimary))

        notation_block = findViewById(R.id.notation_block)
        verification_piece_icon = findViewById(R.id.verification_piece_icon)
        verification_piece_text = findViewById(R.id.verification_piece_text)
        piece_identite_block = findViewById(R.id.piece_identite_block)

        if (user!!.PHOTO != null) {
            val uri = Uri.parse(Const.dns+"/colis/uploads/photo_de_profil/"+user!!.PHOTO)
            draweeView!!.setImageURI(uri)
        } else {
            pictureuser!!.setImageResource(R.drawable.ic_profile_colorier)
        }

        //verification piece d'identite
        piece_identite_icon_failed = resources.getDrawable(R.drawable.baseline_block_black_24)
        piece_identite_icon_failed!!.mutate().setColorFilter(resources.getColor(R.color.colorError), PorterDuff.Mode.SRC_IN)
        verification_piece_icon!!.setImageDrawable(piece_identite_icon_failed)
        verification_piece_text!!.setTextColor(resources.getColor(R.color.colorError))
        verification_piece_text!!.text = "Faites verifier votre piece d'identite"

        notation_block!!.setOnClickListener {
            val intent = Intent(applicationContext,Avis::class.java)
            startActivity(intent)
        }

        piece_identite_block!!.setOnClickListener {
            val intent = Intent(applicationContext,VerificationPieceIdentite::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.menu_profil, menu)
        val edit_profil_menu: MenuItem = menu.findItem(R.id.profil_user)
        val edit_profil_icon = edit_profil_menu.icon as Drawable
        edit_profil_icon.mutate().setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_IN)
        edit_profil_menu.icon = edit_profil_icon
        return true
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

    fun DarkMode(maleoIcon: Drawable) {

    }


}