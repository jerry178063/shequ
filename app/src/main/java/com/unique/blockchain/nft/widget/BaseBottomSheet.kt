package com.unique.blockchain.nft.widget

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.unique.blockchain.nft.R


abstract class BaseBottomSheet : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutResourceId(), null)
        initView(view)
        return view

    }

    abstract fun initView(view: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CustomDialogStyle)
    }

    override fun onResume() {
        super.onResume()

        val window = this.dialog.window
//        window.setLayout(-1, -2)
        window!!.decorView.setPadding(0,0,0,0)
        val lp = window.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM
        //设置dialog的动画
        lp.windowAnimations = R.style.BaseBottomSheet
        window.attributes = lp
    }

    abstract fun getLayoutResourceId(): Int
}