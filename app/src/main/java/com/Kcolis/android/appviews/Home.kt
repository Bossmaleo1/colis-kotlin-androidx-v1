package com.Kcolis.android.appviews
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.Kcolis.android.R
import com.Kcolis.android.connInscript.MainActivity
import com.Kcolis.android.fragments.AddAnnonce
import com.Kcolis.android.fragments.Notifications
import com.Kcolis.android.fragments.Recherche
import com.Kcolis.android.model.cache.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.connexion.*

class Home : AppCompatActivity() {

    private var bottomNavigationView : BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Kcolis"

        bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //init fragments
        val fragment = Recherche()
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .commit()

        //make badges
        //bottomNavigationView!!.removeBadge(R.id.notification) // Remove badge
        //bottomNavigationView!!.getBadge(R.id.notification)!!.setNumber(10)
        val badge = bottomNavigationView!!.showBadge(R.id.notification)
        badge!!.number = 4
        badge!!.badgeTextColor = Color.WHITE
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.recherche -> {
                val fragment = Recherche()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.annonce -> {
                val fragment = AddAnnonce()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.notification -> {
                val fragment = Notifications()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
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