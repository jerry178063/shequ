package com.unique.blockchain.nft.view.fragment;

import android.widget.ImageView;

import com.space.exchange.biz.common.base.BaseFragment;
import com.unique.blockchain.nft.R;

import butterknife.BindView;

public class GuideThreeFragment extends BaseFragment {

    @BindView(R.id.img_guide)
    ImageView img_guide;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guide_one;
    }

    @Override
    public void initView() {

        img_guide.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.guide_three));

    }

}
