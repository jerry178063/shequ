package com.unique.blockchain.nft.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar
import com.space.exchange.biz.common.util.android.DisplayUtil

class CustomSeekBar @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatSeekBar(context, attrs, defStyleAttr) {
    private var paint: Paint? = null
    private var canvas: Canvas? = null
    private fun initPaint() {
        paint = Paint()
        paint!!.color = Color.parseColor("#ffffff")
        paint!!.textSize = DisplayUtil.sp2px(context, 10f).toFloat()
        paint!!.style = Paint.Style.FILL
        paint!!.textAlign = Paint.Align.LEFT
        paint!!.isAntiAlias = true
    }

    @Synchronized
    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        setText()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event);
        return false
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
        setText()
    }

    private fun setText() {
        if (paint != null && canvas != null) {
            val progress = progress
            val text = (progress.toFloat() / 100).toString() + "%"
            val thumb = thumb
            val rect = thumb.bounds
            val bounds = Rect()
            paint!!.getTextBounds(text, 0, text.length, bounds)
            canvas!!.drawText(text, rect.left + rect.width() / 2 - bounds.width() / 2.toFloat(), measuredHeight / 2 + bounds.height() / 2.toFloat(), paint!!)
        }
    }

    init {
        initPaint()
    }
}