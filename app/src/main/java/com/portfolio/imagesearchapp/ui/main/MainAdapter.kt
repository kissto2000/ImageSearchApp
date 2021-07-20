package com.portfolio.imagesearchapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.portfolio.imagesearchapp.GlobalApp
import com.portfolio.imagesearchapp.R
import com.portfolio.imagesearchapp.base.BaseViewHolder
import com.portfolio.imagesearchapp.databinding.AdapterMainBinding
import com.portfolio.imagesearchapp.interfaces.OnPositionListener
import com.portfolio.imagesearchapp.network.model.SearchImageObj
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(
    var items: ArrayList<SearchImageObj.Document>,
    var listener: OnPositionListener
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]

        holder.binding.image.layoutParams.height = GlobalApp.width / 3

        holder.binding.image.setOnClickListener {
            listener.onPosition(position)
        }

        Glide.with(holder.itemView.context)
            .load(data.thumbnail_url)
            .into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : BaseViewHolder<AdapterMainBinding>(view)
}