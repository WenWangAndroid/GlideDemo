package cn.xhuww.glidedemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.image_progress_view.view.*

class ImageViewWithProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var imageView: ImageView

    init {
        val view = View.inflate(context, R.layout.image_progress_view, null)
        imageView = view.findViewById(R.id.image)
    }

    fun setImage(image: Any) {
        when (image) {
            is Drawable -> imageView.setImageDrawable(image)
            is Int -> imageView.setImageResource(image)
            is Bitmap -> imageView.setImageBitmap(image)
        }
    }

    fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    fun dismissProgress() {
        progress.visibility = View.GONE
    }
}