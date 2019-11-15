package cn.xhuww.glidedemo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs


class AppRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var downX: Float = 0f
    private var downY: Float = 0f

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = e.x
                downY = e.y
            }
            MotionEvent.ACTION_MOVE -> {
                val diffX = e.x - downX
                val diffY = e.y - downY

                val disallowIntercept = abs(diffY) < 2 * abs(diffX)
                parent.requestDisallowInterceptTouchEvent(disallowIntercept)
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}