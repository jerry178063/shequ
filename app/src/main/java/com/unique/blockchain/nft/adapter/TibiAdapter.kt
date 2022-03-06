package com.unique.blockchain.nft.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.space.exchange.biz.net.response.UserListCurrentResponse
import com.unique.blockchain.nft.R


class TibiAdapter : BaseQuickAdapter<UserListCurrentResponse.DataBean.DataBeans.ChainsBean, BaseViewHolder> {


    constructor(layoutResId: Int, data: MutableList<UserListCurrentResponse.DataBean.DataBeans.ChainsBean>) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder?, item: UserListCurrentResponse.DataBean.DataBeans.ChainsBean?) {


        helper?.setText(R.id.tv_name,item?.chain_name)
        helper?.addOnClickListener(R.id.tv_name)
        val name=helper?.getView<TextView>(R.id.tv_name)
        name?.isSelected= item?.isIsselected!!

    }
}