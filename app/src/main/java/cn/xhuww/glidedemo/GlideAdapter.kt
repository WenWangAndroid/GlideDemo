package cn.xhuww.glidedemo

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.Rotate
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.recycle_glide_item.view.*


class GlideAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_glide_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 15

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val url =
            "https://www.sciencemag.org/sites/default/files/styles/article_main_large/public/polar%20bear.jpg"
        val gifUrl =
            "https://n.sinaimg.cn/tech/transform/557/w319h238/20190820/0e94-icmpfxa4912263.gif"

        val progressBar = holder.itemView.progress
        val imageView = holder.itemView.imageView
        val textView = holder.itemView.textView
        val context = imageView.context

        val target = object : DrawableImageViewTarget(imageView) {
            override fun onLoadStarted(placeholder: Drawable?) {
                progressBar.visibility = View.VISIBLE
                super.onLoadStarted(placeholder)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                progressBar.visibility = View.GONE
                super.onLoadFailed(errorDrawable)
            }

            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                progressBar.visibility = View.GONE
                super.onResourceReady(resource, transition)
            }
        }

        when (position) {
            0 -> {
                Glide.with(context).load(url).into(imageView)
                textView.text = "图片：常规加载"
            }
            1 -> {
                Glide.with(context).load(url).placeholder(R.drawable.img_default).into(imageView)
                textView.text = "图片：使用占位图"
            }
            2 -> {
                Glide.with(context)
                    .load(url)
                    .override(240, 240)
                    .centerCrop()
                    .into(imageView)
                textView.text = "图片变换：指定图片宽高为240px"
            }
            3 -> {
                Glide.with(context)
                    .load(url)
                    .transform(Rotate(90))
                    .into(imageView)
                textView.text = "图片变换：旋转90度"
            }
            4 -> {
                Glide.with(context)
                    .load(R.drawable.nezha_gif_01)
                    .transform(CircleCrop(), Rotate(90))
                    .into(imageView)
                textView.text = "gif_01：图片变换 转换圆图，旋转90度 "
            }
            5 -> {
                Glide.with(context).load(R.drawable.nezha_gif_02).into(imageView)
                textView.text = "gif_02: 默认无限循环播放"
            }
            6 -> {
                Glide.with(context).load(R.drawable.nezha_gif_03).listener(gifListener(1))
                    .into(imageView)
                textView.text = "gif_03: 自定义播放次数 1次"
            }
            7 -> {
                Glide.with(context).load(R.drawable.nezha_gif_04)
                    .listener(gifListener(GifDrawable.LOOP_INTRINSIC)).into(imageView)
                textView.text = "gif_04: 播放默认播放次数(3次)"
            }
            8 -> {
                Glide.with(context)
                    .load(R.drawable.nezha_gif_05)
                    .listener(gifListener(1))
                    .into(imageView)
                textView.text = "gif_05: 点我重新播放 "
                textView.setOnClickListener {
                    val drawable = imageView.drawable
                    if (drawable is GifDrawable) {
                        drawable.stop()
                        drawable.startFromFirstFrame()
                    }
                }
            }
            9->{
                Glide.with(context)
                    .load(gifUrl)
                    .into(target)
                textView.text = "图片加载: 使用Target 进度监听"
            }
            10 -> {
                Glide.with(context)
                    .load(gifUrl)
                    .signature(ObjectKey(System.currentTimeMillis()))
                    .into(target)
                textView.text = "图片加载: 自定义缓存Key 为当前时间，区分相同URL"
            }
            11 -> {
                Glide.with(context)
                    .load("https://n.sinaimg.cn/tech/transform/438/w280h158/20190820/3ebc-icmpfxa4910530.gif")
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用磁盘缓存
                    .skipMemoryCache(true) //不使用内存缓存
                    .into(target)
                textView.text = "图片加载: 使用Target进度监听,且不使用缓存"
            }
        }
    }

    private fun gifListener(playCount: Int): RequestListener<Drawable> =
        object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                if (resource is GifDrawable) {
                    resource.setLoopCount(playCount)
                }
                return false
            }
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}