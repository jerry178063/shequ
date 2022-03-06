package com.unique.blockchain.nft.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.SplashActivity;

import butterknife.BindView;

public class GuideFourFragment extends BaseFragment {

    @BindView(R.id.img_guide)
    ImageView img_guide;
    @BindView(R.id.img_start)
    ImageView img_start;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guide_one;
    }

    @Override
    public void initView() {

        img_guide.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.guide_four));
        img_start.setVisibility(View.VISIBLE);

        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SplashActivity.class);
                startActivity(intent);
                getActivity().finish();
                SpUtil.putBoolean(getContext(),"firstInstall",true);
            }
        });
    }

}
