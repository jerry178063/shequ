package com.unique.blockchain.nft.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 系统加粗太粗
 */
class CustomBoldTextView : AppCompatTextView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.paint.style = Paint.Style.FILL_AND_STROKE
        this.paint.strokeWidth = 0.7f
    }
}