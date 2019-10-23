package cn.xhuww.glidedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycle_image_item.view.*

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    var items: List<String> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_image_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(url: String) {
            Glide.with(itemView).load(url).into(itemView.imageView)
//            Glide.with(itemView).load(R.mipmap.ic_launcher).into(itemView.imageView)
//            itemView.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }
}