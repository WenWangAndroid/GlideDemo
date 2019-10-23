package cn.xhuww.glidedemo

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.Rotate
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        load(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.glide_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val url = "https://i.pinimg.com/originals/27/96/f9/2796f933293d620c2c919c232c4cc8fb.jpg"
        val context = this
        when (item.itemId) {
            R.id.normal -> Glide.with(context).load(url).placeholder(R.drawable.img_default).into(
                imageView
            )
            R.id.image_transform -> Glide.with(context).load(url).transform(
                CircleCrop(),
                Rotate(45)
            ).into(imageView)

            R.id.image_target -> {

                //指定加载目标
                val target = object : DrawableImageViewTarget(imageView) {
                    override fun getSize(cb: SizeReadyCallback) {
                        super.getSize(cb)
//                        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    }

                    override fun setResource(resource: Drawable?) {
                        super.setResource(resource)
                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        super.onResourceReady(resource, transition)
                    }
                }

            }
        }

        return true
    }



    private fun load(context: Context) {
        val urls = listOf(
            "http://img2.imgtn.bdimg.com/it/u=4291103922,399797940&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2179738447,296099848&fm=26&gp=0.jpg"
        )

        val url = urls.first()

        //基本用法
        Glide.with(context).load(url).into(imageView)
        Glide.with(context).load(url).placeholder(R.drawable.img_default)
            .error(R.drawable.img_default).into(imageView)
        //指定加载类型
        // asFile asGif asBitmap asDrawable
        Glide.with(context).asGif().load(url).into(imageView)
        //图片变换
        Glide.with(context).load(url).override(120).circleCrop().into(imageView)
        Glide.with(context).load(url).transform(Rotate(45)).into(imageView)
        Glide.with(context).load(url).transform(CircleCrop(), Rotate(45)).into(imageView)
        //回调监听
        val requestListener = object : RequestListener<Drawable> {
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
                return false
            }
        }
        Glide.with(context).load(url).listener(requestListener).into(imageView)
        //指定加载目标
        val target = object : DrawableImageViewTarget(imageView) {
            override fun getSize(cb: SizeReadyCallback) {
                super.getSize(cb)
                cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            }

            override fun setResource(resource: Drawable?) {
                super.setResource(resource)
            }

            override fun onLoadStarted(placeholder: Drawable?) {
                super.onLoadStarted(placeholder)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                super.onResourceReady(resource, transition)
            }
        }

        Glide.with(context).load(url).into(target)

        //缓存设置
        //自定义缓key
        Glide.with(context)
            .load(url)
            .signature(ObjectKey("key"))
            .into(target)
        //指定磁盘缓存类型
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(target)
        //仅从缓存中加载
        Glide.with(context).load(url).onlyRetrieveFromCache(true).into(imageView)
        //不缓存
        Glide.with(context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用磁盘缓存
            .skipMemoryCache(true) //不使用内存缓存
            .into(imageView)

        //Glide 配置
        /**
         * 使用其它集成库（OkHttp3,Volley,RecyclerView）
         * 修改 Glide 的配置(configuration)（磁盘缓存大小/位置，内存缓存大小等）
         * 扩展 Glide 的API。
         */

        //GIF
        Glide.with(context)
            .asGif()
            .load(url)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: GifDrawable,
                    model: Any,
                    target: Target<GifDrawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    resource.setLoopCount(GifDrawable.LOOP_INTRINSIC)
                    resource.stop()
                    resource.startFromFirstFrame()
                    return false
                }
            })
            .into(imageView)
    }
}
