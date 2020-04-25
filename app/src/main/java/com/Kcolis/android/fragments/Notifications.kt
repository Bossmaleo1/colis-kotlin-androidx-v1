package com.Kcolis.android.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.Kcolis.android.R
import com.Kcolis.android.adapter.NotificationAdapter
import com.Kcolis.android.appviews.Itinerance
import com.Kcolis.android.appviews.ItineranceValidation
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.NotificationItem
import com.Kcolis.android.utils.CustomRecyclerViewItemTouchListener
import com.Kcolis.android.utils.MyApplication
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONException

class Notifications : Fragment() {

    private var recyclerView : RecyclerView? = null
    private var allUsersAdapter : NotificationAdapter? = null
    private var coordinatorLayout : CoordinatorLayout? = null
    private var data : ArrayList<NotificationItem>? = null
    private var swipeRefreshLayout : SwipeRefreshLayout? = null
    private var mShimmerViewContainer : ShimmerFrameLayout? = null
    private var message_error : LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView: View =  inflater.inflate(R.layout.notifications,container,false)

        message_error = inflatedView.findViewById(R.id.message_error)
        mShimmerViewContainer = inflatedView.findViewById(R.id.shimmer_view_container)
        coordinatorLayout = inflatedView.findViewById(R.id.coordinatorLayout)
        recyclerView = inflatedView.findViewById(R.id.my_recycler_view)
        swipeRefreshLayout = inflatedView.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout!!.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayout!!.setOnRefreshListener {
            swipeRefreshLayout!!.isRefreshing = false
        }

        data = ArrayList<NotificationItem>()

        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        //recyclerView!!.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        allUsersAdapter = activity?.let { data?.let { it1 -> NotificationAdapter(it1, it) } }
        recyclerView!!.adapter = allUsersAdapter
        AffichageNotification(activity!!)

        recyclerView!!.addOnItemTouchListener(
            CustomRecyclerViewItemTouchListener(
                recyclerView!!,
                intArrayOf(R.id.user_first_name),
                object : CustomRecyclerViewItemTouchListener.MyCustomClickListener {
                    override fun onBackupClick(view: View, position: Int) {
                    }

                    override fun onBlockClick(view: View, position: Int) {
                        //Toast.makeText(view.context, "Block action on position = " + position, Toast.LENGTH_LONG).show()
                       // Toast.makeText(activity,"Test du bossmaleo !!",Toast.LENGTH_LONG).show()
                    }

                    override fun onClick(view: View, position: Int) {
                        if(data!![position].Id_type===2) {
                            val intent = Intent(activity, Itinerance::class.java)
                            intent.putExtra("notification", data!![position])
                            startActivity(intent)
                        } else if (data!![position].Id_type===3){
                            val intent = Intent(activity, ItineranceValidation::class.java)
                            intent.putExtra("notification", data!![position])
                            startActivity(intent)
                        }
                    }

                    override fun onLongClick(view: View, position: Int) {
                        //Toast.makeText(view.context, "Long click action on position = " + position, Toast.LENGTH_LONG).show()
                        //Toast.makeText(activity,"Test du bossmaleo !!",Toast.LENGTH_LONG).show()
                    }
                })
        )

        return  inflatedView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mShimmerViewContainer!!.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        mShimmerViewContainer!!.stopShimmer()
    }

    private fun AffichageNotification(context : Context) {
        val database  = DatabaseHandler(context,null)
        val session = SessionManager(context)
        val user = database.getUser(session.getUserDetail().get(Const.Key_ID)!!.toInt())
        val stringRequest = object : StringRequest(
            Request.Method.GET, Const.dns+"/colis/ColisApi/public/api/AfficherNotification?ID_USER="+user.ID,
            Response.Listener<String> { response ->
                data!!.clear()
                val reponses : JSONArray = JSONArray(response)
                try {
                    for (i in 0 until  reponses.length()){

                        val objectJson = reponses.getJSONObject(i)
                        val notificationItem = NotificationItem(
                                objectJson.getInt("id"), objectJson.getString("description")
                                , objectJson.getString("date_validation")
                                , objectJson.getString("statut_validation")
                                , objectJson.getString("nombre_kilo")
                                , objectJson.getString("id_emmeteur")
                                , objectJson.getString("id_annonce")
                                , objectJson.getString("nom_emmeteur")
                                , objectJson.getString("prenom_emmeteur")
                                , objectJson.getString("photo_emmeteur")
                                , objectJson.getString("phone_emmeteur")
                                , objectJson.getString("keypush_emmeteur")
                                , objectJson.getInt("Id_type")
                            )
                        data!!.add(notificationItem)


                    }

                }catch (e: JSONException){
                    e.printStackTrace()
                }

                allUsersAdapter!!.notifyDataSetChanged()
                mShimmerViewContainer!!.stopShimmer()
                mShimmerViewContainer!!.visibility = View.GONE
                if(data!!.size == 0) {
                    message_error!!.visibility = View.VISIBLE
                } else {
                    message_error!!.visibility = View.GONE
                }
            },
            Response.ErrorListener {
                val snack = coordinatorLayout?.let { it1 -> Snackbar.make(it1,activity!!.getString(R.string.error_volley_servererror),Snackbar.LENGTH_LONG) }
                snack!!.show()
            }){
            @Throws(AuthFailureError::class)
            override fun getParams():Map<String, String> {
                val params = HashMap<String, String>()
                return params
            }
        }

        MyApplication.instance?.addToRequestQueue(stringRequest)
    }
}