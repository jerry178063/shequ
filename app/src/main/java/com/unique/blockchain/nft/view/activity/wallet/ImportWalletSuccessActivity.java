package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
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
import com.unique.blockchain.nft.infrastructure.other.AndroidGetId;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;
import wallet.core.jni.PrivateKey;

public class ImportWalletSuccessActivity extends BaseActivity {


    //    @BindView(R.id.tv_)
//    TextView tv_;
//    @BindView(R.id.iv_img)
//    ImageView iv_img;
//    @BindView(R.id.tv_content)
//    TextView tv_content;
    @BindView(R.id.tv_nest)
    TextView tv_nest;
    @BindView(R.id.iv_finish)
    ImageView iv_finish;
    private String name;
    private String unique, content;
    private UserDao userDao;

    // type=1 为创建 2为导入成功
    private String type, gaussAdress_, did, psd;
    private HDWallet hdWallet;
    private byte[] did_psd;
    private List<UserInfoItem> list = new ArrayList<UserInfoItem>();
    private int hasOnclick = 0;
    @BindView(R.id.tv_import_detail)
    TextView tv_import_detail;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_import_wallet_success;
    }

    @Override
    public void initUI() {
        tv_nest.setText("下一步");
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
        SpUtil.getInstance(ImportWalletSuccessActivity.this).putString("did",did);
        SpUtil.getInstance(ImportWalletSuccessActivity.this).putString("name",name);
        SpUtil.getInstance(ImportWalletSuccessActivity.this).putString("type",type);
        SpUtil.getInstance(ImportWalletSuccessActivity.this).putString("psd",psd);
        SpUtil.getInstance(ImportWalletSuccessActivity.this).putString("content",content);
        did_psd = EncryptUtils.encryptAES(TransactionUtils.hexString2Bytes(did), TransactionUtils.hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);

        CommonUtil.setStatusBarColor(this, 2);

        tv_nest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    postData(gaussAdress_, did);
//                    initMainActivity();
                }
            }
        });
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_import_detail.setText("导入钱包成功,您可进入钱包查看管理。");
        tv_title.setText("导入助记词");
        tv_title.setVisibility(View.INVISIBLE);
    }

    private void initMainActivity() {
        Intent intent = new Intent(ImportWalletSuccessActivity.this, MainActivity.class);
        startActivity(intent);
        SpUtil.getInstance(ImportWalletSuccessActivity.this).putString("isFirst", "no");
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
        dismissDialog();
        hdWallet = new HDWallet(content, "");
        MyApplication.createWallet = 1;
        if(aa == 0) {
            User unique = new User();
            unique.setName(name);
            unique.setPsd(psd);
            unique.setDid(did);
            unique.setDid_psd(did_psd);
            List<UserInfoItem> userInfoItems = setCoinKey(hdWallet, list);
            unique.setMObjectList(userInfoItems);
            userDao.insert(unique);
            SPUtils.putString(ImportWalletSuccessActivity.this, Tools.name, name);
            SPUtils.putString(ImportWalletSuccessActivity.this, "witch_wallet", name);
            aa = 1;
        }
    }
    private int aa = 0;
    private void postData(String gaussAdress_, String did) {
        showDialog();
        AddWalletBean addWalletBean = new AddWalletBean();
        addWalletBean.setDeviceId(AndroidGetId.getAndroidId(ImportWalletSuccessActivity.this));
        addWalletBean.setDid(did);
        addWalletBean.setWalletAddr(gaussAdress_);
        OkGo.post(UrlConstant.baseCreateInportWallet + "api/wallet/createOrImport")
                .upJson(addWalletBean.toString())
                .execute(new JsonCallback<RelAddBean>() {
                    @Override
                    public void onSuccess(RelAddBean relAddBean, Call call, Response response) {

                        //请求成功
                        if (relAddBean != null) {
                            if (relAddBean.getCode() == 200) {

                                initMainActivity();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aa = 0;
    }

    @Override
    public void bindView(Bundle bundle) {
        String type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
//            tv_.setText(getResources().getString(R.string.successfully_created_wallet));
//            tv_content.setText("创建钱包成功,已为您生成了专属钱包地址.");
//            iv_img.setImageResource(R.mipmap.pic_backups);
        } else {
//            tv_.setText(getResources().getString(R.string.successfully_imported_wallet));
////            tv_content.setText(getResources().getString(R.string.enter_the_wallet_to_view_management));
//            tv_content.setText("导入钱包成功,已为您生成了专属钱包地址.");
//            iv_img.setImageResource(R.mipmap.pic_backups);
        }


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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }

        return super.onKeyDown(keyCode, event);
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