package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.space.exchange.biz.common.ActivityManager;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.WordAdapter;
import com.unique.blockchain.nft.infrastructure.other.GridSpacingItemDecoration;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import wallet.core.jni.HDWallet;

public class WordActivity extends BaseActivity {
    static {
        System.loadLibrary("TrustWalletCore");
    }
    private static final String TAG = "WordActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_nest)
    TextView tv_nest;
//    @BindView(R.id.tv_)
//    TextView tv_;
    private String name;
    private String psd;
    private WordAdapter wordAdapter=new WordAdapter(R.layout.word_item,new ArrayList<>());
    private StringBuffer stringBuffer;
    private ArrayList <String>list=new ArrayList();
//    @BindView(R.id.iv_img)
//    ImageView iv_img;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_word;
    }

    @Override
    public void initUI() {

//        tv_.setText("备份助记词");
//        iv_img.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        CommonUtil.setStatusBarColor(this, 1);


    }

    @Override
    public void bindView(Bundle bundle) {
        if(bundle!=null){
            ActivityManager.getActivityManager().finishAllActivity();
        }
        name=getIntent().getStringExtra("name");
        psd=getIntent().getStringExtra("psd");


        InputStream is = null;
//        try {
//            is = getAssets().open("English.txt");
//            int lenght = is.available();
//            byte[]  buffer = new byte[lenght];
//            is.read(buffer);
//            String result =  new String(buffer, "utf8");
//            String[] split = result.split("\n");
//            Log.e("FFF344","split:" + split);
//            int[] random = ArrayRandom.random(24, split.length);
//             stringBuffer=new StringBuffer();
//            for(int i=0;i<random.length;i++){
//                list.add(split[random[i]]);
//                stringBuffer.append(split[random[i]] + " ");
//            }

            HDWallet hdWallet2 = new HDWallet(256, "");

            String[] s = hdWallet2.mnemonic().split(" ");
            stringBuffer = new StringBuffer();
            for (int i = 0; i < s.length; i++) {
                list.add(s[i]);
                stringBuffer.append(s[i]+ " ");
            }
        Log.e("ff7656","stringBuffer_创建之前:" + stringBuffer.toString().toLowerCase().trim());
//            StringUtils.CopyText(this,stringBuffer.toString());
//            ToastUtil.showShort(this,getResources().getString(R.string.all_words_have_been_copied));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,GridSpacingItemDecoration.px2dp(15),true));
        wordAdapter.setNewData(list);
        Log.e("FFF344","list:" + list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HDWallet hdWallet2 = new HDWallet(256, "");

        String[] s = hdWallet2.mnemonic().split(" ");
        if(!TextUtils.isEmpty(stringBuffer.toString())){
            stringBuffer= null;
        }
        stringBuffer = new StringBuffer();
        if(!TextUtils.isEmpty(list.toString())){
            list.clear();
        }
        for (int i = 0; i < s.length; i++) {
            list.add(s[i]);
            stringBuffer.append(s[i]+ " ");
        }
        wordAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_nest,R.id.iv_finish})
    public void setOnClickView(View view){
        switch (view.getId()){
            case R.id.tv_nest:
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(this, SureWordActivity.class);
                    intent.putStringArrayListExtra("list", list);
                    intent.putExtra("content", stringBuffer.toString().toLowerCase().trim());
                    intent.putExtra("name", name);
                    intent.putExtra("psd", psd);
                    intent.putExtra("add", getIntent().getStringExtra("add"));
                    startActivity(intent);
                }
                break;
            case R.id.iv_finish:
                finish();
                break;
        }
    }
}