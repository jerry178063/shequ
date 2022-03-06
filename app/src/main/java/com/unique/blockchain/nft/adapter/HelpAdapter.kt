package com.unique.blockchain.nft.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.space.exchange.biz.net.response.QuestionResponse

class HelpAdapter : FragmentPagerAdapter {
    private var mTitles: MutableList<QuestionResponse.DataBean>
    private var fragments: List<Fragment>

    constructor(data: MutableList<QuestionResponse.DataBean>, mutableListOf: MutableList<Fragment>, fm: FragmentManager) : super(fm) {
        mTitles = data
        fragments = mutableListOf
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position].name

    }

    override fun getCount(): Int {
        return mTitles.size
    }
}