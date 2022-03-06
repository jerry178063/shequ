package com.unique.blockchain.nft.view.activity.mark;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.discover.NewDetailBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 资讯详情
 * */
public class NewsContentActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tv_content)
    TextView tv_content;
    String banner_url;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    String title,time,id;
    @BindView(R.id.tv_digest)
    TextView tv_digest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_content;
    }

    @Override
    public void initUI() {
        //第一次设置缓存位置
        RichText.initCacheDir(this);
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        banner_url = getIntent().getStringExtra("banner_url");
        id = getIntent().getStringExtra("id");
//        tv_content.setText(Html.fromHtml(banner_url + ""));
//        if(!TextUtils.isEmpty(banner_url)) {
//            RichText.from(banner_url + "")
//                    .into(tv_content);
//        }
//        title = getIntent().getStringExtra("title");
//        time = getIntent().getStringExtra("time");
//        if(!TextUtils.isEmpty(title)) {
//            tv_title.setText(title + "");
//        }
//        if(!TextUtils.isEmpty(time)) {
//            tv_time.setText(time + "");
//        }
        getData();
    }

    private void getData() {
        Log.e("FFF34454", "MyApplication.mAuthorization:" + MyApplication.mAuthorization);
        Log.e("FFF34454", "id:" + id);
        OkGo.post(UrlConstant.baseCreateInportWallet + "operation/article/pushArticleInfo/" + id)
                .execute(new JsonCallback<NewDetailBean>() {
                    @Override
                    public void onSuccess(NewDetailBean versionInfo, Call call, Response response) {
                        dismissDialog();
                        if(versionInfo.getCode() == 200 && versionInfo.getData() != null){
                            if(!TextUtils.isEmpty(versionInfo.getData().getContent())) {
                                RichText.from(versionInfo.getData().getContent() + "")
                                        .into(tv_content);
                            }
                            if(!TextUtils.isEmpty(versionInfo.getData().getTitle())) {
                                tv_title.setText(versionInfo.getData().getTitle() + "");
                            }
                            if(!TextUtils.isEmpty(versionInfo.getData().getReleaseTime())) {
                                tv_time.setText(versionInfo.getData().getReleaseTime() + "");
                            }
                            if(!TextUtils.isEmpty(versionInfo.getData().getDigest())){
                                tv_digest.setText(versionInfo.getData().getDigest());
                            }
                        }
                        Log.e("FFF34454", "资讯详情:" + versionInfo);
                        //请求成功
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF34454", "onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
//
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
