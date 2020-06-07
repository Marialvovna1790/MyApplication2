package com.example.myapplication

import android.graphics.*
import android.graphics.drawable.Drawable
import dpToPx
import kotlin.math.roundToInt

class CircleDrawable @JvmOverloads constructor(color: Int = Color.BLACK, private val text: Long) : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { setColor(color) }
    private val bounds = RectF()

    val textpaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setColor(Color.WHITE);
        setTextSize(dpToPx(18).toFloat());
        setAntiAlias(true);
        setFakeBoldText(true);
        setShadowLayer(6f, 0f, 0f, Color.BLACK);
        setStyle(Paint.Style.FILL);
        setTextAlign(Paint.Align.CENTER);
    }

    private var color = color
    private var alphaFactor = 1f
    private var invalidateColor = false

    fun setColor(color: Int) {
        this.color = color
        this.invalidateColor = true
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
        this.alphaFactor = (alpha / 255f)
        this.invalidateColor = true
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        this.bounds.set(bounds)
    }

    override fun draw(canvas: Canvas) {
        if (invalidateColor) {
            paint.color = color.multiplyAlpha(alphaFactor)
            invalidateColor = false
        }
        canvas.drawOval(bounds, paint)
        canvas.drawText(text.toString(), getBounds().centerX().toFloat(),
                getBounds().centerY().toFloat() - ((textpaint.descent() + textpaint.ascent()) / 2), textpaint)
    }

    private fun Int.multiplyAlpha(factor: Float): Int {
        val colorAlpha = Color.alpha(this)
        val resultAlpha = (colorAlpha * factor).roundToInt()
        return Color.argb(
                resultAlpha,
                Color.red(color),
                Color.green(color),
                Color.blue(color))

    }
}
