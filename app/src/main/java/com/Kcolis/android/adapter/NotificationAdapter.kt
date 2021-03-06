package com.Kcolis.android.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.appviews.DetailsValidation
import com.Kcolis.android.appviews.Home
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.Annonce
import com.Kcolis.android.model.data.NotificationItem
import com.Kcolis.android.model.data.User
import com.Kcolis.android.utils.MyApplication
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.annonceslistadapter.view.*
import kotlinx.android.synthetic.main.annonceslistadapter.view.title1
import kotlinx.android.synthetic.main.notification_adapter.view.*
import kotlinx.android.synthetic.main.searchadapter.view.*
import org.json.JSONException
import org.json.JSONObject

class NotificationAdapter(var data: ArrayList<NotificationItem>, var context: Context):
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    private var etat : String? = null
    private var id_validation : String? = null
    private var id_emmetteur : String? = null
    private var message : String? = null
    private var session : SessionManager? = null
    private var user : User? = null
    private var database : DatabaseHandler? = null
    var card_boolean : Boolean? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotificationAdapter.MyViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(R.layout.notification_adapter, parent, false)
        )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        holder.bind(data[position],context)
    }


    class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {

        private val title  = view.title93
        private val title1 = view.title1
        private val picture = view.icon
        private val description = view.contenu93
        private val poids = view.contenu_ville_arrivee_block93
        private val telephone = view.telephone
        private val valider = view.send
        private val annuler = view.send2
        private val user_first_name = view.user_first_name93
        private val title_block = view.block93
        private val validation_block = view.heure_arrivee_block93
        private val telephone_block = view.telephone_block
        private val poids_block = view.heure_depart_block93

        private val view_widget1 = view.trace_separation93
        private val view_widget2 = view.viewbis1893
        private val view_widget3 = view.trace_separation293

        private val view_widget4 = view.trace_separation_gone
        private val view_widget5 = view.viewbis18_gone
        private val view_widget6 = view.trace_separation2_gone
        private val icon_message = view.icon_message93

        fun bind(notificationitem : NotificationItem,context: Context) {
            title.text = notificationitem.prenom_emmetteur+" "+notificationitem.nom_emmetteur
            title1.text = notificationitem.date_validation
            picture.setImageResource(R.drawable.ic_profile_colorier)
            poids.text = notificationitem.nombre_kilo+" Kg"
            description.text = notificationitem.description
            telephone.text = notificationitem.telephone
            if(notificationitem.Id_type ===1){
                validation_block.visibility = View.GONE
                telephone_block.visibility = View.GONE
                poids_block.visibility = View.GONE
                view_widget1.visibility = View.GONE
                view_widget2.visibility = View.GONE
                view_widget3.visibility = View.GONE
                view_widget4.visibility = View.VISIBLE
                view_widget5.visibility = View.VISIBLE
                view_widget6.visibility = View.VISIBLE
                //icon_message.setImageResource(R.drawable.baseline_timeline_black_24)
                description.text = "Votre demande de transport de colis vient d'être valider"
                    //poids.visibility = View.GONE
            } else if(notificationitem.Id_type ===2) {
                validation_block.visibility = View.GONE
                telephone_block.visibility = View.GONE
                poids_block.visibility = View.GONE
                view_widget1.visibility = View.GONE
                view_widget2.visibility = View.GONE
                view_widget3.visibility = View.GONE
                view_widget4.visibility = View.VISIBLE
                view_widget5.visibility = View.VISIBLE
                view_widget6.visibility = View.VISIBLE
                icon_message.setImageResource(R.drawable.baseline_timeline_black_24)
                description.text = "Vous pouvez suivre l'itineraire de votre colis ici"
            } else if(notificationitem.Id_type == 3) {
                validation_block.visibility = View.GONE
                telephone_block.visibility = View.GONE
                poids_block.visibility = View.GONE
                view_widget1.visibility = View.GONE
                view_widget2.visibility = View.GONE
                view_widget3.visibility = View.GONE
                view_widget4.visibility = View.VISIBLE
                view_widget5.visibility = View.VISIBLE
                view_widget6.visibility = View.VISIBLE
                icon_message.setImageResource(R.drawable.baseline_timeline_black_24)
                description.text = "Vous pouvez rendre compte de l'itineraire du colis"
            }


            if(notificationitem.photo_emmetteur!= null) {
                val uri = Uri.parse(Const.dns+"/colis/uploads/photo_de_profil/"+notificationitem.photo_emmetteur)
                picture.setImageURI(uri)
            } else {
                picture.setImageResource(R.drawable.ic_profile_colorier)
            }

            valider.setOnClickListener{
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.progressdialogfragment,null)
                val dialogBuilder = AlertDialog.Builder(context).setView(mDialogView)
                val alert = dialogBuilder.create()
                alert.setCancelable(false)
                alert.show()
                val message  = "Votre demande d'expedition de colis vient d'être accepter par l'utilisateur "
                val database =  DatabaseHandler(context,null)
                val session =  SessionManager(context)
                val user = database.getUser((session.getUserDetail()[Const.Key_ID] ?: error("")).toInt())
                Connexion_volley(alert,context,notificationitem.id.toString(),user!!.ID.toString(),message,"1",user!!.TELEPHONE,user_first_name,user!!.KEYPUSH,user!!.NOM,user!!.PRENOM,"")
            }

            annuler.setOnClickListener{
                Toast.makeText(context,"Vous avez cliquer sur Annuler",Toast.LENGTH_LONG).show()
            }

            title_block.setOnClickListener{
                val intent = Intent(context, DetailsValidation::class.java)
                intent.putExtra("photo_user", notificationitem.photo_emmetteur)
                intent.putExtra("nom",notificationitem.prenom_emmetteur+" "+notificationitem.nom_emmetteur)
                context.startActivity(intent)
            }

            picture.setOnClickListener{
                val intent = Intent(context, DetailsValidation::class.java)
                intent.putExtra("photo_user", notificationitem.photo_emmetteur)
                intent.putExtra("nom",notificationitem.prenom_emmetteur+" "+notificationitem.nom_emmetteur)
                context.startActivity(intent)

            }
        }

        private fun Connexion_volley(alert:AlertDialog, context: Context, id_validation:String, id_emmetteur: String, message : String, etat: String, phone: String,
                                     card : CardView, keypush : String, nom : String, prenom : String, message_fcm: String) {
            val url = Const.dns+"/colis/ColisApi/public/api/ValidationAnnulationDemandeExpedition?id_validation="+id_validation+"&id_emmeteur="+id_emmetteur+"&message="+message+"&etat="+etat
            val stringRequest = object : StringRequest(
                Request.Method.GET,url,
                Response.Listener<String> { response ->
                    alert.dismiss()
                    //card.visibility = View.GONE
                    Toast.makeText(context,"Votre demande a ete valider avec succes !!",Toast.LENGTH_LONG).show()
                    card.visibility = View.GONE
                },
                Response.ErrorListener {

                    Toast.makeText(context,"Une erreur au niveau du serveur viens de survenir ", Toast.LENGTH_LONG).show()
                    alert.dismiss()
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


}