package com.Kcolis.android.appviews
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.Kcolis.android.R
import com.Kcolis.android.connInscript.MainActivity
import com.Kcolis.android.fragments.AddAnnonce
import com.Kcolis.android.fragments.Notifications
import com.Kcolis.android.fragments.Recherche
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.connexion.*

class Home : AppCompatActivity() {

    private var bottomNavigationView : BottomNavigationView? = null
    private var Icon_notification : Drawable? = null
    private var Icon_recherche: Drawable? = null
    private var Icon_annonce: Drawable? = null
    private var menu: Menu? = null
    private var annonce_title_text: SpannableString? = null
    private var recherche_title_text: SpannableString? = null
    private var notification_title_text: SpannableString? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Kcolis"

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //init fragments
        val fragment = Recherche()
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment, fragment.javaClass.simpleName)
            .commit()

        //make badges
        //bottomNavigationView!!.removeBadge(R.id.notification) // Remove badge
        //bottomNavigationView!!.getBadge(R.id.notification)!!.setNumber(10)
        val badge = bottomNavigationView!!.showBadge(R.id.notification)
        badge!!.number = 4
        badge!!.badgeTextColor = Color.WHITE
        /*val session = SessionManager(applicationContext)
        val databasemaleo = DatabaseHandler(this,null)
        val maleo = databasemaleo.getUser(session.getUserDetail().get(Const.Key_ID)!!.toInt())*/
        menu = bottomNavigationView!!.menu
        Icon_notification = applicationContext.getDrawable(R.drawable.ic_notifications_black_24dp)
        Icon_recherche = applicationContext.getDrawable(R.drawable.baseline_search_black_24)
        Icon_annonce = applicationContext.getDrawable(R.drawable.baseline_control_point_black_24)
        annonce_title_text = SpannableString("Ajouter une annonce")
        recherche_title_text = SpannableString("Recherche")
        notification_title_text = SpannableString("Notifications")
        Icon_recherche!!.setColorFilter(applicationContext.getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN)
        recherche_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.colorPrimary)),0,recherche_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menu!!.findItem(R.id.recherche).icon = Icon_recherche
        menu!!.findItem(R.id.recherche).title = recherche_title_text

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Icon_recherche!!.mutate().setColorFilter(applicationContext.getColor(R.color.colorPrimary),
                PorterDuff.Mode.SRC_IN)
        }*/
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.recherche -> {
                Icon_notification = applicationContext.getDrawable(R.drawable.ic_notifications_black_24dp)
                Icon_recherche = applicationContext.getDrawable(R.drawable.baseline_search_black_24)
                Icon_annonce = applicationContext.getDrawable(R.drawable.baseline_control_point_black_24)
                Icon_recherche!!.setColorFilter(applicationContext.getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN)
                /*Icon_notification!!.setColorFilter(applicationContext.getColor(R.color.item_name),PorterDuff.Mode.SRC_IN)
                Icon_annonce!!.setColorFilter(applicationContext.getColor(R.color.item_name),PorterDuff.Mode.SRC_IN)*/
                Icon_annonce!!.clearColorFilter()
                Icon_notification!!.clearColorFilter()
                recherche_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.colorPrimary)),0,recherche_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                notification_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.item_name)),0,notification_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                annonce_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.item_name)),0,annonce_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                menu!!.findItem(R.id.recherche).icon = Icon_recherche
                menu!!.findItem(R.id.notification).icon = Icon_notification
                menu!!.findItem(R.id.annonce).icon = Icon_annonce

                menu!!.findItem(R.id.recherche).title = recherche_title_text
                menu!!.findItem(R.id.notification).title = notification_title_text
                menu!!.findItem(R.id.annonce).title = annonce_title_text

                val fragment = Recherche()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment, fragment.javaClass.simpleName)
                    .commit()

                //icon_notification = applicationContext.getDrawable(R.drawable.ic_notifications_black_24dp)

                return@OnNavigationItemSelectedListener true
            }
            R.id.annonce -> {
                Icon_notification = applicationContext.getDrawable(R.drawable.ic_notifications_black_24dp)
                Icon_recherche = applicationContext.getDrawable(R.drawable.baseline_search_black_24)
                Icon_annonce = applicationContext.getDrawable(R.drawable.baseline_control_point_black_24)
                Icon_annonce!!.setColorFilter(applicationContext.getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN)
                Icon_recherche!!.clearColorFilter()
                Icon_notification!!.clearColorFilter()

                annonce_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.colorPrimary)),0,annonce_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                menu!!.findItem(R.id.annonce).icon = Icon_annonce
                menu!!.findItem(R.id.notification).icon = Icon_notification
                menu!!.findItem(R.id.recherche).icon = Icon_recherche

                notification_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.item_name)),0,notification_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                recherche_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.item_name)),0,recherche_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                menu!!.findItem(R.id.notification).title = notification_title_text
                menu!!.findItem(R.id.recherche).title = recherche_title_text
                menu!!.findItem(R.id.annonce).title = annonce_title_text
                val fragment = AddAnnonce()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.notification -> {
                Icon_notification = applicationContext.getDrawable(R.drawable.ic_notifications_black_24dp)
                Icon_recherche = applicationContext.getDrawable(R.drawable.baseline_search_black_24)
                Icon_annonce = applicationContext.getDrawable(R.drawable.baseline_control_point_black_24)

                Icon_notification!!.setColorFilter(applicationContext.getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN)
                Icon_annonce!!.clearColorFilter()
                Icon_recherche!!.clearColorFilter()

                notification_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.colorPrimary)),0,notification_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                menu!!.findItem(R.id.notification).icon = Icon_notification
                menu!!.findItem(R.id.recherche).icon = Icon_recherche
                menu!!.findItem(R.id.annonce).icon = Icon_annonce

                annonce_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.item_name)),0,notification_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                recherche_title_text!!.setSpan(ForegroundColorSpan(applicationContext.getColor(R.color.item_name)),0,recherche_title_text!!.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                menu!!.findItem(R.id.notification).title = notification_title_text
                menu!!.findItem(R.id.annonce).title = annonce_title_text
                menu!!.findItem(R.id.recherche).title = recherche_title_text


                val fragment = Notifications()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when(item.itemId) {
            R.id.deco -> {
                val session = SessionManager(applicationContext)
                session.logoutUser()
                val intent = Intent(applicationContext,MainActivity::class.java)
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}