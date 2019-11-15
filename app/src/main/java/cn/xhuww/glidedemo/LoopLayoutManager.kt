package cn.xhuww.glidedemo

import android.graphics.PointF
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class LoopLayoutManager : RecyclerView.LayoutManager(),
	RecyclerView.SmoothScroller.ScrollVectorProvider {

	private val orientationHelper = OrientationHelper.createHorizontalHelper(this)

	override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
		if (childCount == 0) {
			return null
		}
		val firstChildPos = getPosition(getChildAt(0)!!)
		val direction = if (targetPosition < firstChildPos) -1 else 1
		return PointF(direction.toFloat(), 0f)
	}

	override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
		RecyclerView.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
		)

	override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
		//分离并且回收当前附加的所有View
		detachAndScrapAttachedViews(recycler)

		if (itemCount == 0) return

		var start = orientationHelper.startAfterPadding

		//初始化时则需要左右都预先加载，避免滑动初次右滑时出现空白
		val lastView = recycler.getViewForPosition(itemCount - 1)
		layoutChild(lastView, start, forward = false)

		for (i in 0 until itemCount) {
			val child = recycler.getViewForPosition(i)
			layoutChild(child, start, forward = true)
			start = orientationHelper.getDecoratedEnd(child)
			if (start > orientationHelper.endAfterPadding) break
		}
		orientationHelper.onLayoutComplete()
	}

	/**
	 * 横向滑动时的回调方法，对应还有竖向滑动的回调放
	 * @see scrollVerticallyBy
	 */
	override fun scrollHorizontallyBy(
		dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State
	): Int {
		if (childCount == 0 || dx == 0) return 0
		recycleViews(dx, recycler)
		fill(dx, recycler)
		offsetChildrenHorizontal(dx * -1)
		return dx
	}

	/**
	 * 如果不重此方法，或者返回 false，则需要重写onMeasure方法
	 * 否则 RecyclerView 高度不确定时，无法展示出子View
	 */
	override fun isAutoMeasureEnabled(): Boolean = true

	/**
	 * 是否允许横向滑动，默认禁止，返回true 代表允许，对应还有是否允许竖向滑动
	 * @see canScrollVertically
	 */
	override fun canScrollHorizontally(): Boolean = true

	private fun fill(dx: Int, recycler: RecyclerView.Recycler) {
		while (true) {
			val start: Int
			val nextView: View
			val forward = dx > 0

			if (forward) {
				val lastVisibleView = getChildAt(childCount - 1) ?: break
				start = orientationHelper.getDecoratedEnd(lastVisibleView)
				if (lastVisibleView.right - dx > orientationHelper.endAfterPadding) break

				val lastViewPosition = getPosition(lastVisibleView)
				nextView = if (lastViewPosition == itemCount - 1) {
					recycler.getViewForPosition(0)
				} else {
					recycler.getViewForPosition(lastViewPosition + 1)
				}
			} else {
				val firstVisibleView = getChildAt(0) ?: break
				start = orientationHelper.getDecoratedStart(firstVisibleView)
				if (start - dx < orientationHelper.startAfterPadding) break

				val firstViewPosition = getPosition(firstVisibleView)
				nextView = if (firstViewPosition == 0) {
					recycler.getViewForPosition(itemCount - 1)
				} else {
					recycler.getViewForPosition(firstViewPosition - 1)
				}
			}
			layoutChild(nextView, start, forward)
		}
	}

	/**
	 * @param view
	 * @param start view的绘制起始点
	 * @param forward 绘制方向是否向前(从左至右)
	 */
	private fun layoutChild(view: View, start: Int, forward: Boolean) {
		measureChildWithMargins(view, 0, 0)
		val childWidth = orientationHelper.getDecoratedMeasurement(view)
		val childHeight = orientationHelper.getDecoratedMeasurementInOther(view)

		val left: Int
		val right: Int
		val top = paddingTop
		val bottom = top + childHeight

		if (forward) {
			addView(view)
			left = start
			right = start + childWidth
		} else {
			addView(view, 0)
			left = start - childWidth
			right = start
		}
		layoutDecoratedWithMargins(view, left, top, right, bottom)
	}

	private fun recycleViews(dx: Int, recycler: RecyclerView.Recycler) {
		for (i in 0 until itemCount) {
			val childView = getChildAt(i) ?: return
			if (dx > 0) {
				if (orientationHelper.getDecoratedEnd(childView) - dx <
					orientationHelper.startAfterPadding
				) {
					removeAndRecycleViewAt(i, recycler)
				}
			} else {
				if (orientationHelper.getDecoratedStart(childView) - dx >
					orientationHelper.endAfterPadding
				) {
					removeAndRecycleViewAt(i, recycler)
				}
			}
		}
	}
}
