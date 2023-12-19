package com.cosmetics.core.commonviews

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable

import androidx.core.content.ContextCompat
import com.cosmetics.core.R
import com.cosmetics.core.utils.getFont


class BadgeDrawable(private val context: Context) : Drawable() {

    private val mavenTypeFace by lazy {
        context.getFont(R.font.helvetica_neue)
    }

    private var mBadgePaint: Paint? = null
    private var mTextPaint: Paint? = null
    private val mTxtRect = Rect()

    private var mCount = ""
    private var mWillDraw: Boolean = false

    init {
        val mTextSize = context.resources.getDimension(R.dimen.badge_count_textsize)

        mBadgePaint = Paint().apply {
            color = ContextCompat.getColor(context.applicationContext, R.color.black)
            isAntiAlias = true
            style = Paint.Style.FILL
        }

        mTextPaint = Paint().apply {
            color = Color.WHITE
            typeface = mavenTypeFace
            textSize = mTextSize
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun draw(canvas: Canvas) {
        if (!mWillDraw) {
            return
        }
        val bounds = bounds
        val width = bounds.right - bounds.left
        val height = bounds.bottom - bounds.top

        // Position the badge in the top-right quadrant of the icon.

        /*Using Math.max rather than Math.min */

        val radius = Math.max(width, height) / 2 / 2
        val centerX = width - radius - 1f + 5
        val centerY = radius - 5
        mBadgePaint?.let {
            if (mCount.length <= 2) {
                // Draw badge circle.
                canvas.drawCircle(
                    centerX,
                    centerY.toFloat(),
                    (radius.toFloat() + 5.5).toFloat(),
                    it
                )
            } else {
                canvas.drawCircle(
                    centerX,
                    centerY.toFloat(),
                    (radius.toFloat() + 6.5).toFloat(),
                    it
                )
            }
        }

        // Draw badge count text inside the circle.
        mTextPaint?.getTextBounds(mCount, 0, mCount.length, mTxtRect)
        val textHeight = mTxtRect.bottom - mTxtRect.top
        val textY = centerY + textHeight / 2f

        mTextPaint?.let {
            if (mCount.length > 2)
                canvas.drawText("99+", centerX, textY, it)
            else
                canvas.drawText(mCount, centerX, textY, it)
        }
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    fun setCount(count: String) {
        mCount = count

        // Only draw a badge if there are notifications.
        mWillDraw = !count.equals("0", ignoreCase = true)
        invalidateSelf()
    }
}