package com.Kcolis.android.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.model.Const
import com.Kcolis.android.model.cache.SessionManager
import com.Kcolis.android.model.dao.DatabaseHandler
import com.Kcolis.android.model.data.ListAvis
import com.Kcolis.android.model.data.User
import kotlinx.android.synthetic.main.lisavisadpter.view.*
import kotlinx.android.synthetic.main.payment_adapter.view.icon

class ListAvisAdapter (var data: ArrayList<ListAvis>,var context: Context):
    RecyclerView.Adapter<ListAvisAdapter.MyViewHolder>()  {

    private var database: DatabaseHandler? = null
    private var session: SessionManager? = null
    private var user: User? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListAvisAdapter.MyViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(R.layout.lisavisadpter, parent, false)



        )

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ListAvisAdapter.MyViewHolder, position: Int) {

        database = DatabaseHandler(context,null)
        session = SessionManager(context)
        user = database!!.getUser((session!!.getUserDetail()[Const.Key_ID] ?: error("")).toInt())
        holder.bind(data[position],context,user!!.PHOTO)
    }

    class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        private val  nomprenom = view.title1
        private val date = view.title
        private val commentary = view.date
        private val photo = view.icon
        private val shimmercommentaryblock = view.notre_shimmer

        fun bind(listavis : ListAvis,context: Context, photomaleo:String) {

            nomprenom.text = listavis.prenom+" "+listavis.nom
            photo.setImageResource(R.drawable.ic_profile_colorier)
            val uri = Uri.parse(Const.dns+"/colis/uploads/photo_de_profil/"+photomaleo)
            photo!!.setImageURI(uri)
            commentary.text = listavis.commentaire
            date.text = listavis.dateavis
            shimmercommentaryblock.visibility = View.GONE
        }
    }

}