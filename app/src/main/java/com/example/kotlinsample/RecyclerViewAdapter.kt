package com.example.kotlinsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*

class RecyclerViewAdapter(
    private val context: Context,
    private var imageList: ArrayList<String>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(imageList[position]).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun setItem(imageList: ArrayList<String>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        itemView: View,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val imageView: ImageView = itemView.iv_image

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            onItemClickListener.onItemClick(v, adapterPosition)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}
