package cn.xhuww.glidedemo

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.load.model.GlideUrl
import java.io.InputStream
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.GlideBuilder


//@GlideModule
//class OkHttpGlideModule : AppGlideModule() {
//    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//        //替换网络请求库
//        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
//    }
//
//    override fun isManifestParsingEnabled(): Boolean {
//        return true
//    }
//
//    override fun applyOptions(context: Context, builder: GlideBuilder) {
//        //设置内存缓存大小
//        val memoryCacheSizeBytes = 1024 * 1024 * 20 // 20mb
//        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
//    }
//}

