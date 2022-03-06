package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.MainActivity;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.domain.wallet.AddWalletBean;
import com.unique.blockchain.nft.domain.wallet.RelAddBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.BackNewZhujiciDialog;
import com.unique.blockchain.nft.infrastructure.other.AndroidGetId;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.PhoneUtils;
import com.unique.blockchain.nft.infrastructure.utils.ToastaUtils;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;
import wallet.core.jni.PrivateKey;

public class CreateWalletSuccessActivity extends BaseActivity {


//    @BindView(R.id.tv_)
//    TextView tv_;
//    @BindView(R.id.tv_content)
//    TextView tv_content;
    @BindView(R.id.tv_nest)
    TextView tv_nest;
    private String name;
    private String unique, content;

    // type=1 为创建 2为导入成功
    private String type, gaussAdress_, did, psd;
    private byte[] did_psd;

    private HDWallet hdWallet;
    private List<UserInfoItem> list = new ArrayList<UserInfoItem>();
    private UserDao userDao;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_wallet_success;
    }

    @Override
    public void initUI() {
        CommonUtil.setStatusBarColor(this, 2);
    }

    @Override
    public void initData() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        gaussAdress_ = getIntent().getStringExtra("gaussAdress_");
        did = getIntent().getStringExtra("did");
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        psd = getIntent().getStringExtra("psd");
        content = getIntent().getStringExtra("content");
        SpUtil.getInstance(CreateWalletSuccessActivity.this).putString("did",did);
        SpUtil.getInstance(CreateWalletSuccessActivity.this).putString("name",name);
        SpUtil.getInstance(CreateWalletSuccessActivity.this).putString("type",type);
        SpUtil.getInstance(CreateWalletSuccessActivity.this).putString("psd",psd);
        SpUtil.getInstance(CreateWalletSuccessActivity.this).putString("content",content);
        tv_title.setVisibility(View.INVISIBLE);
//        tv_.setText(getResources().getString(R.string.successfully_created_wallet));
//        tv_content.setText(getResources().getString(R.string.the_wallet_is_successfully_created));
//        tv_nest.setText("下一步");
        did_psd = EncryptUtils.encryptAES(TransactionUtils.hexString2Bytes(did), TransactionUtils.hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
        Log.e("gdde22","创建——did:" + did);
        Log.e("gdde22","创建——name:" + name);
        Log.e("gdde22","创建——psd:" + psd);
        Log.e("gdde22","创建——content:" + content);
    }

    @OnClick({R.id.tv_nest,R.id.iv_finish})
    public void setOnclickView(View view) {
        switch (view.getId()) {
            case R.id.tv_nest:
                //判断是否有网络
                if (!PhoneUtils.isNetworkConnected(CreateWalletSuccessActivity.this)) {
                    ToastaUtils toastaUtils = new ToastaUtils(CreateWalletSuccessActivity.this, getResources().getString(R.string.the_network_is_not_open));
                    toastaUtils.show();
                } else {
                    if (MoreClick.MoreClicks()) {
                        showDialog();
                        postData(gaussAdress_, did);
//                    Intent intent = new Intent(CreateWalletSuccessActivity.this, MainActivity.class);
//                    startActivity(intent);
                        SpUtil.getInstance(CreateWalletSuccessActivity.this).putString("isFirst", "no");
                        EventBus.getDefault().post("choose_wallet");
                    }
                }
                break;
            case R.id.iv_finish:
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }
    private int aa = 0;
    private void postData(String gaussAdress_, String did) {
        AddWalletBean addWalletBean = new AddWalletBean();
        addWalletBean.setDeviceId(AndroidGetId.getAndroidId(CreateWalletSuccessActivity.this));
        addWalletBean.setDid(did);
        addWalletBean.setWalletAddr(gaussAdress_);
        Log.e("relAddBean","请求参数:" + new Gson().toJson(addWalletBean));
        OkGo.post(UrlConstant.baseCreateInportWallet + "api/wallet/createOrImport")
                .upJson(addWalletBean.toString())
                .execute(new JsonCallback<RelAddBean>() {
                    @Override
                    public void onSuccess(RelAddBean relAddBean, Call call, Response response) {
                        Log.e("relAddBean","relAddBean:" + new Gson().toJson(relAddBean));

                        //请求成功
                        if (relAddBean != null) {
                            if (relAddBean.getCode() == 200) {
                                handler.sendEmptyMessage(112);

                            }else {
                                dismissDialog();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                    }
                });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(CreateWalletSuccessActivity.this, MainActivity.class);
            startActivity(intent);
            SpUtil.getInstance(CreateWalletSuccessActivity.this).putString("isFirst", "no");
            list.add(new UserInfoItem("UNIQUE", "m/44\'/995\'/0\'/0/0", CoinType.UNIQUE.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
//            list.add(new UserInfoItem("GPB", "m/44\'/994\'/0\'/0/0", 994, getResources().getResourceEntryName(R.mipmap.logo_gpb)));
//            list.add(new UserInfoItem("GAUSS", "m/44\'/991\'/0\'/0/0", 991, getResources().getResourceEntryName(R.drawable.logo_wallte_gauss)));
//            list.add(new UserInfoItem("USDG", "m/44\'/881\'/0\'/0/0", 881, getResources().getResourceEntryName(R.drawable.logo_wallte_usdg)));
            list.add(new UserInfoItem("ETH", "m/44\'/60\'/0\'/0/0", 60, getResources().getResourceEntryName(R.drawable.logo_wallte_eth)));
            list.add(new UserInfoItem("TRON", "m/44\'/195\'/0\'/0/0", 195, getResources().getResourceEntryName(R.drawable.logo_wallte_tron)));
//            list.add(new UserInfoItem("ATOM", "m/44\'/118\'/0\'/0/0", 118, getResources().getResourceEntryName(R.drawable.logo_wallte_atom)));
//            list.add(new UserInfoItem("BTC", "m/44\'/0\'/0\'/0/0", 0, getResources().getResourceEntryName(R.drawable.logo_wallte_btc)));
//            list.add(new UserInfoItem("DOT", "m/44\'/3\'/0\'/0/0", 3, getResources().getResourceEntryName(R.drawable.logo_wallte_dot)));
//            list.add(new UserInfoItem("FIL", "m/44\'/3\'/0\'/0/0", 3, getResources().getResourceEntryName(R.drawable.logo_wallte_fil)));
//            list.add(new UserInfoItem("igpc", "m/44\'/3\'/0\'/0/0", 3, getResources().getResourceEntryName(R.drawable.logo_wallte_igpc)));
//            list.add(new UserInfoItem("FEC", "m/44\'/3\'/0\'/0/0", 3, getResources().getResourceEntryName(R.drawable.logo_wallte_fec)));
            dismissDialog();
            hdWallet = new HDWallet(content, "");
            MyApplication.createWallet = 0;
            Log.e("FFF99", "导入钱包名称:" + name);
            if(aa == 0) {
                User unique = new User();
                unique.setName(name);
                unique.setPsd(psd);
                unique.setDid(did);
                unique.setDid_psd(did_psd);
                List<UserInfoItem> userInfoItems = setCoinKey(hdWallet, list);
                unique.setMObjectList(userInfoItems);
                userDao.insert(unique);
                SPUtils.putString(CreateWalletSuccessActivity.this, Tools.name, name);
                SPUtils.putString(CreateWalletSuccessActivity.this, "witch_wallet", name);
                aa = 1;
            }
        }
    };
    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aa = 0;
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
                userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes));
                Log.e("ffff444", "pad:" + psd + "---" + "bytes:" + bytes);
            } else {

                switch (mlist.get(i).getCoin_name()) {
                    case "UNIQUE":
                        String key_coin = CoinType.UNIQUE.deriveAddress(key);
                        byte[] bytes = EncryptUtils.encryptAES(key.data(), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
                        userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes));
                        Log.e("ffff444", "pad_GAUSS:" + psd + "---" + "bytes_GAUSS:" + bytes);
                        break;
                    case "USDG":
                        String key_coin_usdg = CoinType.COSMOS.deriveAddress(key);
                        byte[] bytes1 = EncryptUtils.encryptAES(key.data(), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
                        userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin_usdg, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes1));
                        break;
                    default:

                        Log.d("teddddd", mlist.get(i).getCoin_name());
                }
            }

        }
        return userInfoItems;
    }

    public static byte[] hexString2Bytes(String hexString) {
        if (com.blankj.utilcode.util.StringUtils.isSpace(hexString)) return new byte[0];
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
