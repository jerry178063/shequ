package com.unique.blockchain.nft.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class MarketAdapter : FragmentPagerAdapter {
    private var mTitles: MutableList<String>
    private var fragments: List<Fragment>

    constructor(titles: MutableList<String>, fm1: List<Fragment>, fm: FragmentManager?) : super(fm) {
        mTitles = titles
        fragments = fm1
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return mTitles.size
    }
}