package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.domain.wallet.AddWalletBean;
import com.unique.blockchain.nft.domain.wallet.RelAddBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.AndroidGetId;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.StringUtils;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtil;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;
import wallet.core.jni.Mnemonic;
import wallet.core.jni.PrivateKey;

public class LoadWalletActivity extends BaseActivity {

    static {
        System.loadLibrary("TrustWalletCore");
    }
    private static final String TAG = "LoadWalletActivity";
    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.tv_isok)
    TextView tv_isok;

    @BindView(R.id.tv_nest)
    TextView tv_nest;

//    @BindView(R.id.tv_)
//    TextView tv_;
//    @BindView(R.id.tv_content)
//    TextView tv_content;
    private String name;
    private List<UserInfoItem> list = new ArrayList<UserInfoItem>();

    private  String psd;
    //设备唯一标识码
    private String dvelop_id;
    //助记词sha256
    private String did;
    private String gaussAdress_,sha_psd,usdgAdress_;
    //didAes加密
    private byte[] did_psd;
    private HDWallet hdWallet;

    private UserDao userDao;
    //上传的gauss
    private String gauss_psd;
    //上传的usdg
    private String usdg_psd;
    @BindView(R.id.iv_finish)
    ImageView iv_finish;
    private User unique;
    private String content_trim;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_load_wallet;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        CommonUtil.setStatusBarColor(this,1);
//        tv_.setText(getResources().getString(R.string.import_mnemonic_words));
//        tv_content.setText(getResources().getString(R.string.please_enter_mnemonic_words_order));

        psd = getIntent().getStringExtra("psd");
        name = getIntent().getStringExtra("name");
        String sha_psd = psd.substring(0, 16);
        DaoSession daoSession = MyApplication.getDaoSession();

        userDao = daoSession.getUserDao();

    }
    private void insertGreenDao(int pos) {
//        unique = new User();
//        unique.setName(name);
//        unique.setPsd(psd);
//        unique.setDid(did);
//        unique.setDid_psd(did_psd);
//        List<UserInfoItem> userInfoItems = setCoinKey(hdWallet, list);
//        unique.setMObjectList(userInfoItems);
//        userDao.insert(unique);
//        SPUtils.putString(LoadWalletActivity.this, Tools.name, name);
//        SPUtils.putString(LoadWalletActivity.this,"witch_wallet",name);
    }
    @Override
    public void bindView(Bundle bundle) {
        list.add(new UserInfoItem("UNIQUE", "m/44\'/995\'/0\'/0/0", 995, getResources().getResourceEntryName(R.mipmap.app_logo_new)));
//        list.add(new UserInfoItem("GPB", "m/44\'/994\'/0\'/0/0", 994, getResources().getResourceEntryName(R.mipmap.logo_gpb)));
//        list.add(new UserInfoItem("GAUSS", "m/44\'/991\'/0\'/0/0", 991, getResources().getResourceEntryName(R.drawable.logo_wallte_gauss)));
//        list.add(new UserInfoItem("USDG", "m/44\'/881\'/0\'/0/0", 881, getResources().getResourceEntryName(R.drawable.logo_wallte_usdg)));
        list.add(new UserInfoItem("ETH", "m/44\'/60\'/0\'/0/0", 60,getResources().getResourceEntryName(R.drawable.logo_wallte_eth)));
        list.add(new UserInfoItem("TRON", "m/44\'/195\'/0\'/0/0", 195, getResources().getResourceEntryName(R.drawable.logo_wallte_tron)));
//        list.add(new UserInfoItem("ATOM", "m/44\'/118\'/0\'/0/0", 118,getResources().getResourceEntryName(R.drawable.logo_wallte_atom)));
//        list.add(new UserInfoItem("BTC", "m/44\'/0\'/0\'/0/0", 0, getResources().getResourceEntryName(R.drawable.logo_wallte_btc)));
//        list.add(new UserInfoItem("DOT", "m/44\'/354\'/0\'/0/0", 354, getResources().getResourceEntryName(R.drawable.logo_wallte_dot)));
//        list.add(new UserInfoItem("FIL", "m/44\'/461\'/0\'/0/0", 461, getResources().getResourceEntryName(R.drawable.logo_wallte_fil)));

    }
    @OnClick({R.id.tv_nest,R.id.iv_finish})
    public void setOnclickView(View view){
        switch (view.getId()){
            case R.id.iv_finish:
                finish();
                break;
            case R.id.tv_nest:
                if (MoreClick.MoreClicks()) {
                    if (TextUtils.isEmpty(et_content.getText().toString())) {
                        ToastUtil.showShort(this, getResources().getString(R.string.please_enter_the_mnemonic_phrase));
                        return;
                    }
                    if (StringUtils.isContainNumber(et_content.getText().toString())) {
                        ToastUtil.showShort(this, "助记词不能为数字");
                        return;
                    }
                    String content = StringUtils.replaceBlank(et_content.getText().toString());
                    String c_trim_ = content.replaceAll("\\s+|\r", " ");
                    String c_trim = c_trim_.toLowerCase();
                    content_trim = c_trim.trim();
                    if (!Mnemonic.isValid(content_trim)) {
                        tv_isok.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        tv_isok.setVisibility(View.INVISIBLE);
                    }
                    if (content_trim.split(" ").length != 24) {
                        tv_isok.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        tv_isok.setVisibility(View.INVISIBLE);
                    }
//                showDialog();
                    DaoSession daoSession = MyApplication.getDaoSession();
                    userDao = daoSession.getUserDao();
                    sha_psd = TransactionUtil.INSTANCE.hexStringToByteArray(psd).toString();
                    hdWallet = new HDWallet(content_trim.trim(), "");
                    PrivateKey gaussAdress = hdWallet.getKey(CoinType.UNIQUE, "m/44\'/995\'/0\'/0/0");
                    //生成usdg
                    PrivateKey usdgAdress = hdWallet.getKey(CoinType.USDG, "m/44\'/881\'/0\'/0/0");

                    gaussAdress_ = CoinType.UNIQUE.deriveAddress(gaussAdress);
                    Log.e("ff7656", "导入钱包的助记词:" + content_trim.trim());
                    Log.e("ff7656", "导入钱包生成的钱包地址:" + gaussAdress_);
                    usdgAdress_ = CoinType.USDG.deriveAddress(usdgAdress);
                    //获取设备ID
                    dvelop_id = android.provider.Settings.Secure.getString(
                            getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                    did = AESEncrypt.sha(content_trim);
                    did_psd = EncryptUtils.encryptAES(TransactionUtils.hexString2Bytes(did), TransactionUtils.hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);

                    List<User> users = userDao.loadAll();
                    PrivateKey keyForCoin = hdWallet.getKeyForCoin(CoinType.UNIQUE);
                    String key = CoinType.UNIQUE.deriveAddress(keyForCoin);
                    Log.e("ff7656", "key:" + key);
                    if (!users.isEmpty()) {
                        for (int i = 0; i < users.size(); i++) {
                            if (!users.get(i).getMObjectList().isEmpty()) {
                                Log.e("ff7656", "yek:" + users.get(i).getMObjectList().get(0).getCoin_psd());
                                if (users.get(i).getMObjectList().get(0).getCoin_psd().trim().equals(key.trim())) {
                                    Log.e("ff7656", "本地已有该钱包,不能重复导入:");
                                    ToastUtil.showShort(LoadWalletActivity.this, "本地已有该钱包,不能重复导入");
                                    return;
                                }
//                            }
                            }

                        }
                    }
//                postData(gaussAdress_, did);


                    usdg_psd = AESEncrypt.encrypt(usdgAdress_, String.valueOf(sha_psd));
                    gauss_psd = AESEncrypt.encrypt(gaussAdress_, String.valueOf(sha_psd));

                    String gaussAdress_2 = CoinType.ETHEREUM.deriveAddress(gaussAdress);

                    String usdgAdress_ = CoinType.ETHEREUM.deriveAddress(usdgAdress);
                    insertGreenDao(1);
                    if (!TextUtils.isEmpty(gaussAdress.toString())) {
//                    postData(gaussAdress_, did);
                        Intent intent = new Intent(LoadWalletActivity.this, ImportWalletSuccessActivity.class);
                        intent.putExtra("type", "2");
                        intent.putExtra("gaussAdress_", gaussAdress_);
                        intent.putExtra("did", did);
                        intent.putExtra("name", name);
                        intent.putExtra("psd", psd);
                        intent.putExtra("did_psd", did_psd + "");
                        intent.putExtra("content", content_trim);
                        startActivity(intent);
                    }

                }
                break;
        }
    }

    private void postData(String gaussAdress_, String did) {
        AddWalletBean addWalletBean = new AddWalletBean();
        addWalletBean.setDeviceId(AndroidGetId.getAndroidId(LoadWalletActivity.this));
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
                                Intent intent = new Intent(LoadWalletActivity.this, ImportWalletSuccessActivity.class);
                                intent.putExtra("type", "2");
                                intent.putExtra("gaussAdress_",gaussAdress_);
                                intent.putExtra("did",did);
                                intent.putExtra("name",name);
                                intent.putExtra("psd",psd);
                                intent.putExtra("did_psd",did_psd + "");
                                intent.putExtra("content",content_trim);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
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
            PrivateKey key = wallet.getKey(CoinType.createFromValue(mlist.get(i).getNum()), mlist.get(i).getCoin_psd());
            if (CoinType.createFromValue(mlist.get(i).getNum()) != null) {
                String key_coin = CoinType.createFromValue(mlist.get(i).getNum()).deriveAddress(key);
                byte[] bytes = EncryptUtils.encryptAES(key.data(), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
                userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes));
            } else {

                switch (mlist.get(i).getCoin_name()) {
                    case "UNIQUE":
                        String key_coin = CoinType.UNIQUE.deriveAddress(key);
                        byte[] bytes = EncryptUtils.encryptAES(key.data(), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
                        userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes));
                        break;
                    case "USDG":
                        String key_coin_usdg = CoinType.USDG.deriveAddress(key);
                        byte[] bytes1 = EncryptUtils.encryptAES(key.data(), hexString2Bytes(psd), "AES/ECB/PKCS5Padding", null);
                        userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin_usdg, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes1));
                        break;
                    default:


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