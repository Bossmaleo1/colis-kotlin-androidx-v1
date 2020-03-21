package com.Kcolis.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Kcolis.android.R
import com.Kcolis.android.model.data.TownItem
import kotlinx.android.synthetic.main.searchadapter.view.*

class SearchTownAdapter(var data: ArrayList<TownItem>):
    RecyclerView.Adapter<SearchTownAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder (
       LayoutInflater.from(parent.context)!!.inflate(R.layout.searchadapter,parent,false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(data[position])
    }

    class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        private val title = view.title

        fun bind(townitem : TownItem) {
            title.text = townitem.Libelle
        }
    }

}