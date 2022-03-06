package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.SureBWordAdapter;
import com.unique.blockchain.nft.adapter.SureWordAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.domain.wallet.AddWalletBean;
import com.unique.blockchain.nft.domain.wallet.RelAddBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.BackNewZhujiciDialog;
import com.unique.blockchain.nft.infrastructure.dialog.StandTwoDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.AndroidGetId;
import com.unique.blockchain.nft.infrastructure.other.GridSpacingItemDecoration;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.ToastaUtils;
import com.unique.blockchain.nft.net.JsonCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;
import wallet.core.jni.PrivateKey;


public class SureWordActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, StandTwoDialog.OnClickView, BackNewZhujiciDialog.OnBackZhujiClickView {
    private static final String TAG = "SureWordActivity";

    static {
        Log.d(TAG, "static initializer: " + 1);
        System.loadLibrary("TrustWalletCore");
    }


//    @BindView(R.id.tv_re)
//    TextView tv_re;
//    @BindView(R.id.tv_)
//    TextView tv_;
//    @BindView(R.id.iv_img)
//    ImageView iv_img;
    @BindView(R.id.recyclerView_top)
    RecyclerView recyclerView_top;
    @BindView(R.id.recyclerView_boom)
    RecyclerView recyclerView_boom;
    @BindView(R.id.tv_nest)
    TextView tv_nest;
    @BindView(R.id.iv_finish)
    LinearLayout iv_finish;
    //用户账号
    private String name;
    //用户密码
    private String psd;
    //正确的助记词
    private String content;

    private SureBWordAdapter wordbAdapter = new SureBWordAdapter(R.layout.sure_b_word_item, new ArrayList<>());
    private SureWordAdapter surewordAdapter = new SureWordAdapter(R.layout.sure_word_item, new ArrayList<>());
    //打乱的助记词的数组
    private ArrayList<String> list_boom = new ArrayList();
    //填入的数组
    private ArrayList<String> list_top = new ArrayList();
    //正确助记词的数组
    private ArrayList<String> list_yes = new ArrayList();
    private ArrayList<String> list_yuan = new ArrayList();
    //设备唯一标识码
    private String dvelop_id;
    //助记词sha256
    private String did;

    private StandTwoDialog standTwoDialog;

    private HDWallet hdWallet;
    private List<UserInfoItem> list = new ArrayList<UserInfoItem>();

    private UserDao userDao;
    //didAes加密
    private byte[] did_psd;
    //上传的地址
    private String usdgAdress_;
    private String gaussAdress_;
//    @BindView(R.id.tv_content)
//    TextView tv_content;
    BackNewZhujiciDialog backNewZhujiciDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sure_word;
    }

    @Override
    public void initUI() {

        tv_nest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
                if (MoreClick.MoreClicks()) {
                    if (surewordAdapter.getData().size() == 0) {
                        ToastaUtils toastaUtils = new ToastaUtils(SureWordActivity.this, getResources().getString(R.string.please_complete_mnemonic));
                        toastaUtils.show();
//                        dismissDialog();
                        return;
                    }
                    if (surewordAdapter.getData().size() != wordbAdapter.getData().size()) {
                        ToastaUtils toastaUtils = new ToastaUtils(SureWordActivity.this, getResources().getString(R.string.please_complete_mnemonic));
                        toastaUtils.show();
//                        dismissDialog();
                        return;
                    }

                    for (int i = 0; i < list_yes.size(); i++) {

                        if (!surewordAdapter.getData().get(i).trim().equals(list_yuan.get(i).trim())) {
                            ToastaUtils toastaUtils = new ToastaUtils(SureWordActivity.this, getResources().getString(R.string.mnemonic_phrase_is_wrong));
                            toastaUtils.show();
                            dismissDialog();
                            return;
                        }
                    }

                    insertGreenDao(2);
//                    postData(gaussAdress_, did);
                    Intent intent = new Intent(SureWordActivity.this, CreateWalletSuccessActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("gaussAdress_", gaussAdress_);
                    intent.putExtra("did", did);
                    intent.putExtra("psd", psd);
                    intent.putExtra("name", name);
                    intent.putExtra("did_psd", did_psd + "");
                    intent.putExtra("hdWallet", hdWallet + "");
                    intent.putExtra("content", content);
                    startActivity(intent);
                    dismissDialog();
                    finish();
                    //todo 创建成功
//                    startActivity(new Intent(SureWordActivity.this, CreateWalletSuccessActivity.class));
                }
            }


        });
//        tv_content.setText("请按顺序点击助记词,以确认您正确备份");
    }
    private void postData(String gaussAdress_, String did) {
        AddWalletBean addWalletBean = new AddWalletBean();
        addWalletBean.setDeviceId(AndroidGetId.getAndroidId(SureWordActivity.this));
        addWalletBean.setDid(did);
        addWalletBean.setWalletAddr(gaussAdress_);
        OkGo.post(UrlConstant.baseCreateInportWallet + "api/wallet/createOrImport")
                .upJson(addWalletBean.toString())
                .execute(new JsonCallback<RelAddBean>() {
                    @Override
                    public void onSuccess(RelAddBean relAddBean, Call call, Response response) {
                        Log.e("FFF33222","relAddBean:" + new Gson().toJson(relAddBean));
                        //请求成功
                        if (relAddBean != null) {
                            if(relAddBean.getCode() == 200){
                                Intent intent = new Intent(SureWordActivity.this, CreateWalletSuccessActivity.class);
                                intent.putExtra("type", "1");
                                intent.putExtra("gaussAdress_",gaussAdress_);
                                intent.putExtra("did",did);
                                intent.putExtra("psd",psd);
                                intent.putExtra("name",name);
                                intent.putExtra("did_psd",did_psd + "");
                                intent.putExtra("hdWallet",hdWallet + "");
                                intent.putExtra("content",content);
                                startActivity(intent);
                                dismissDialog();
                                finish();

                                hdWallet = new HDWallet(content, "");
                                Log.e("FFF99", "导入钱包名称:" + name);
                                User unique = new User();
                                unique.setName(name);
                                unique.setPsd(psd);
                                unique.setDid(did);
                                unique.setDid_psd(did_psd);
                                List<UserInfoItem> userInfoItems = setCoinKey(hdWallet, list);
                                unique.setMObjectList(userInfoItems);
                                userDao.insert(unique);
                                SPUtils.putString(SureWordActivity.this, Tools.name, name);
                                SPUtils.putString(SureWordActivity.this, "witch_wallet", name);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF33222","code:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
    @Override
    public void initData() {

        CommonUtil.setStatusBarColor(this, 1);

        name = getIntent().getStringExtra("name");
        psd = getIntent().getStringExtra("psd");
        content = getIntent().getStringExtra("content");
//        psd = "4fec4fdc69b37bf99280ef2a679a59c6df26a8cd36df7cebe169be0526cd26bc";


        hdWallet = new HDWallet(content, "");
        Log.d(TAG, "initData:123 " + hdWallet.mnemonic());
        //生成GPB的地址
        PrivateKey gaussAdress = hdWallet.getKey(CoinType.UNIQUE, "m/44\'/995\'/0\'/0/0");
        gaussAdress_ = CoinType.UNIQUE.deriveAddress(gaussAdress);
        Log.e("FFF444786","创建钱包生成的钱包地址_1:" + gaussAdress_);



        //生成usdg
        PrivateKey usdgAdress = hdWallet.getKey(CoinType.USDG, "m/44\'/881\'/0\'/0/0");
//        String usdgAdress_ = CoinType.COSMOS.deriveAddress(usdgAdress);
        usdgAdress_ = CoinType.USDG.deriveAddress(usdgAdress);

        //btc 地址
        PrivateKey btcForCoin = hdWallet.getKeyForCoin(CoinType.BITCOIN);
        String btcAdress_ = CoinType.BITCOIN.deriveAddress(btcForCoin);

        //eth 地址
        PrivateKey ethForCoin = hdWallet.getKeyForCoin(CoinType.ETHEREUM);
        String ethAdress_ = CoinType.ETHEREUM.deriveAddress(ethForCoin);

        // tron 地址
        PrivateKey tronForCoin = hdWallet.getKeyForCoin(CoinType.TRON);
        String tronAdress_ = CoinType.TRON.deriveAddress(tronForCoin);

        // ATOM 地址
        PrivateKey atomForCoin = hdWallet.getKeyForCoin(CoinType.COSMOS);
        String atomAdress_ = CoinType.COSMOS.deriveAddress(atomForCoin);

        // DOT 地址
        PrivateKey dotForCoin = hdWallet.getKeyForCoin(CoinType.DOGECOIN);
        String dotAdress_ = CoinType.DOGECOIN.deriveAddress(dotForCoin);

        // Fil 地址
        PrivateKey filForCoin = hdWallet.getKey(CoinType.FILECOIN, "m/44'/461'/0'/0/0");
        String filAdress_ = CoinType.DOGECOIN.deriveAddress(filForCoin);
        //将助记词加密（sha-256）
        did = AESEncrypt.sha(content);
        did_psd = EncryptUtils.encryptAES(hexString2Bytes(did), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);


        //获取设备ID
        dvelop_id = android.provider.Settings.Secure.getString(
                getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        Log.d(TAG, "initData: " + dvelop_id);
        list_boom = getIntent().getStringArrayListExtra("list");

        list_yes = getIntent().getStringArrayListExtra("list");
        if (!TextUtils.isEmpty(list_boom.toString()) && list_boom.toString().length() > 0) {
            for (int i = 0; i < list_boom.size(); i++) {
                list_yuan.add(list_boom.get(i));
            }
        }
        //打乱集合方法
        Collections.shuffle(list_boom);
//        tv_.setText(getResources().getString(R.string.determine_booster_words));

//        tv_re.setVisibility(View.VISIBLE);
//        iv_img.setImageResource(R.mipmap.pic_backups);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView_boom.setLayoutManager(gridLayoutManager);
        recyclerView_boom.setAdapter(wordbAdapter);
        recyclerView_boom.addItemDecoration(new GridSpacingItemDecoration(3, GridSpacingItemDecoration.px2dp(15), true));
        wordbAdapter.setNewData(list_boom);
        wordbAdapter.setOnItemChildClickListener(this);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 3);
        recyclerView_top.setLayoutManager(gridLayoutManager1);
        recyclerView_top.setAdapter(surewordAdapter);
//         gridSpacingItemDecoration= new GridSpacingItemDecoration(4, GridSpacingItemDecoration.px2dp(15), true);
//        recyclerView_top.addItemDecoration(gridSpacingItemDecoration);
        surewordAdapter.setNewData(list_top);
        surewordAdapter.setOnItemChildClickListener(this);
        recyclerView_boom.setNestedScrollingEnabled(false);
        recyclerView_boom.setHasFixedSize(true);
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();


    }


    @Override
    public void bindView(Bundle bundle) {
        list.add(new UserInfoItem("UNIQUE", "m/44\'/995\'/0\'/0/0", 995, getResources().getResourceEntryName(R.mipmap.app_logo_new)));
//        list.add(new UserInfoItem("GPB", "m/44\'/994\'/0\'/0/0", 994, getResources().getResourceEntryName(R.mipmap.logo_gpb)));
//        list.add(new UserInfoItem("GAUSS", "m/44\'/991\'/0\'/0/0", 991, getResources().getResourceEntryName(R.drawable.logo_wallte_gauss)));
//        list.add(new UserInfoItem("USDG", "m/44\'/881\'/0\'/0/0", 881, getResources().getResourceEntryName(R.drawable.logo_wallte_usdg)));
        list.add(new UserInfoItem("ETH", "m/44\'/60\'/0\'/0/0", 60, getResources().getResourceEntryName(R.drawable.logo_wallte_eth)));
        list.add(new UserInfoItem("TRON", "m/44\'/195\'/0\'/0/0", 195, getResources().getResourceEntryName(R.drawable.logo_wallte_tron)));
//        list.add(new UserInfoItem("ATOM", "m/44\'/118\'/0\'/0/0", 118, getResources().getResourceEntryName(R.drawable.logo_wallte_atom)));
//        list.add(new UserInfoItem("BTC", "m/44\'/0\'/0\'/0/0", 0, getResources().getResourceEntryName(R.drawable.logo_wallte_btc)));
//        list.add(new UserInfoItem("DOT", "m/44\'/3\'/0\'/0/0", 3, getResources().getResourceEntryName(R.drawable.logo_wallte_dot)));
//        list.add(new UserInfoItem("FIL", "m/44\'/3\'/0\'/0/0", 3, getResources().getResourceEntryName(R.drawable.logo_wallte_fil)));
//        list.add(new UserInfoItem("igpc", "m/44\'/3\'/0\'/0/0", 3, getResources().getResourceEntryName(R.drawable.logo_wallte_igpc)));
//        list.add(new UserInfoItem("FEC", "m/44\'/3\'/0\'/0/0", 3, getResources().getResourceEntryName(R.drawable.logo_wallte_fec)));

    }


    @OnClick({R.id.tv_skip, R.id.iv_finish})
    public void setOnclickView(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                    if (standTwoDialog == null) {
                        standTwoDialog = new StandTwoDialog(this);
                        standTwoDialog.setOnclick(this);
                        standTwoDialog.show();
                    } else {
                        standTwoDialog.show();
                    }
                break;
            case R.id.iv_finish:
                if (backNewZhujiciDialog == null) {
                    backNewZhujiciDialog = new BackNewZhujiciDialog(this);
                    backNewZhujiciDialog.setOnclick(this);
                    backNewZhujiciDialog.show();
                } else {
                    backNewZhujiciDialog.show();
                }
                break;
        }
    }

    @Override
    public void setBackZhujiClickView(String et_pass) {
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (backNewZhujiciDialog == null) {
                backNewZhujiciDialog = new BackNewZhujiciDialog(this);
                backNewZhujiciDialog.setOnclick(this);
                backNewZhujiciDialog.show();
            } else {
                backNewZhujiciDialog.show();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        if (wordbAdapter == adapter) {
            TextView textView = (TextView) adapter.getViewByPosition(recyclerView_boom, position, R.id.tv_word);
            String name = (String) adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_word:
                    textView.setSelected(true);
                    list_top.add(name);
                    surewordAdapter.setNewData(list_top);
                    if (textView.isSelected()) {
                        textView.setEnabled(false);
                    }

//                        for(int i=0;i<surewordAdapter.getData().size();i++){
//                                if(!surewordAdapter.getData().get(i).equals(wordbAdapter.getData().get(i))){
//
//                                }
//                        }

                    break;
            }
        }
        if (surewordAdapter == adapter) {

            switch (view.getId()) {
                case R.id.iv_clear:
                    String name = surewordAdapter.getData().get(position);
                    for (int i = 0; i < wordbAdapter.getData().size(); i++) {
                        if (wordbAdapter.getData().get(i).equals(name)) {
                            TextView textView = (TextView) wordbAdapter.getViewByPosition(recyclerView_boom, i, R.id.tv_word);
                            textView.setEnabled(true);
                            textView.setSelected(false);
                        }
                    }


                    surewordAdapter.remove(position);

//                    if (recyclerView_top.getItemDecorationCount() == 0) {
//                        recyclerView_top.addItemDecoration(gridSpacingItemDecoration);
//                    }

                    break;
            }
        }
    }


    @Override
    public void setOnClickView() {

        insertGreenDao(1);

        standTwoDialog.dismiss();
//        postData(gaussAdress_, did);
        Intent intent = new Intent(SureWordActivity.this, CreateWalletSuccessActivity.class);
        intent.putExtra("type", "1");
        intent.putExtra("gaussAdress_",gaussAdress_);
        intent.putExtra("did",did);
        intent.putExtra("psd",psd);
        intent.putExtra("name",name);
        intent.putExtra("did_psd",did_psd + "");
        intent.putExtra("hdWallet",hdWallet + "");
        intent.putExtra("content",content);
        startActivity(intent);
        dismissDialog();
        finish();
    }

    private void insertGreenDao(int pos) {
        Log.e("FF99","NAME:" + name);
//        User unique = new User();
//        unique.setName(name);
//        unique.setPsd(psd);
//        unique.setDid(did);
//        unique.setDid_psd(did_psd);
//        List<UserInfoItem> userInfoItems = setCoinKey(hdWallet, list);
//        unique.setMObjectList(userInfoItems);
//        userDao.insert(unique);
//        SPUtils.putString(SureWordActivity.this, Tools.name, name);
//        Log.e("ffff444","插入数据库:" + pos);
//        SPUtils.putString(SureWordActivity.this,"witch_wallet",name);
    }

    /***
     * 拿到钱包的地址
     * @param wallet
     * @param mlist
     * @return
     */
    public List<UserInfoItem> setCoinKey(HDWallet wallet, List<UserInfoItem> mlist) {
        List<UserInfoItem> userInfoItems = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            PrivateKey key = wallet.getKey(CoinType.createFromValue(list.get(i).getNum()), mlist.get(i).getCoin_psd());
            if (CoinType.createFromValue(mlist.get(i).getNum()) != null) {
                String key_coin = CoinType.createFromValue(mlist.get(i).getNum()).deriveAddress(key);
                byte[] bytes = EncryptUtils.encryptAES(key.data(), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
                userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin, mlist.get(i).getNum(), mlist.get(i).getImg(),bytes));
                Log.e("ffff444","pad:" + psd + "---" + "bytes:" + bytes);
            } else {

                switch (mlist.get(i).getCoin_name()) {
                    case "UNIQUE":
                        String key_coin = CoinType.UNIQUE.deriveAddress(key);
                        byte[] bytes = EncryptUtils.encryptAES(key.data(), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
                        userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin, mlist.get(i).getNum(), mlist.get(i).getImg(),bytes));
                        Log.e("ffff444","pad_GAUSS:" + psd + "---" + "bytes_GAUSS:" + bytes);
                        break;
                    case "USDG":
                        String key_coin_usdg = CoinType.COSMOS.deriveAddress(key);
                        byte[] bytes1 = EncryptUtils.encryptAES(key.data(), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
                        userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin_usdg, mlist.get(i).getNum(), mlist.get(i).getImg(),bytes1));
                        break;
                    default:

                        Log.d("teddddd", mlist.get(i).getCoin_name());
                }
            }

        }
        return userInfoItems;
    }
    public static byte[] hexString2Bytes(String hexString) {
        if (StringUtils.isSpace(hexString)) return new byte[0];
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }
    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

}