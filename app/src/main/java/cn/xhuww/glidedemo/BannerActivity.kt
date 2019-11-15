package cn.xhuww.glidedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_banner.*

class BannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        recycleView.layoutManager = LoopLayoutManager()
        recycleView.adapter = ImageAdapter().apply {
            items = listOf(
//                "http://pic1.win4000.com/wallpaper/6/57a2ea842cd95.jpg",
//                "https://n.sinaimg.cn/tech/transform/597/w272h325/20191015/d741-ifzxhxk8054452.gif",
//                "https://f.sinaimg.cn/tech/transform/504/w264h240/20191015/bc44-ifzxhxk8053781.gif",
//                "https://n.sinaimg.cn/tech/transform/32/w512h320/20191015/d62e-ifzxhxk8050557.gif",
//                "https://f.sinaimg.cn/tech/transform/185/w400h585/20181022/Sd3T-hmuuiyv6801634.gif",

                "http://pic1.win4000.com/wallpaper/6/57a2ea842cd95.jpg",
                "http://pic1.win4000.com/wallpaper/8/583f832f1825f.jpg",
                "http://pic1.win4000.com/wallpaper/2/53c4c2146f6ac.jpg",
                "http://pic1.win4000.com/wallpaper/2017-12-15/5a33962b679e0.jpg",
                "http://pic1.win4000.com/wallpaper/6/57a2ea842cd95.jpg",
                "http://pic1.win4000.com/wallpaper/8/583f832f1825f.jpg",
                "http://pic1.win4000.com/wallpaper/2/53c4c2146f6ac.jpg",
                "http://pic1.win4000.com/wallpaper/2017-12-15/5a33962b679e0.jpg"
            )
        }
//        val snapHelper = ViewPagerSnapHelper().apply {
//            attachToRecyclerView(recycleView)
//        }
    }
}