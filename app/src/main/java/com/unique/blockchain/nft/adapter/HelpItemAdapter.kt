package com.unique.blockchain.nft.adapter

import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.space.exchange.biz.net.response.QuestionListResponse
import com.unique.blockchain.nft.R

class HelpItemAdapter : BaseQuickAdapter<QuestionListResponse.DataBean, BaseViewHolder> {
    constructor(layoutResId: Int, data: MutableList<QuestionListResponse.DataBean>?) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder, item: QuestionListResponse.DataBean) {
        helper.setText(R.id.tv_index, (helper.adapterPosition.plus(1)).toString())
        helper.setText(R.id.tv_question, item.question)
        val view = helper.getView<TextView>(R.id.tv_content)
        if (item.isShowAnswer) {
            helper.setImageResource(R.id.iv_jiantou, R.mipmap.icon_shouqi)
            view.visibility = View.VISIBLE
            view.text =Html.fromHtml(item.answer)
        } else {
            view.visibility = View.GONE
            helper.setImageResource(R.id.iv_jiantou, R.mipmap.icon_zhankai)
        }
        helper.getView<LinearLayout>(R.id.ll_jiantou).setOnClickListener {
            if (item.isShowAnswer) {
                item.isShowAnswer = !item.isShowAnswer
            } else {
                for (index in data.indices) {
                    data[index].isShowAnswer = index == helper.adapterPosition
                }
            }
            notifyDataSetChanged()
        }
    }
}