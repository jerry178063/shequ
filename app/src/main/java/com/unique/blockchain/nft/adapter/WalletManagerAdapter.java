package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.view.activity.me.WalletManagerActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 钱包管理adapter
 */
public class WalletManagerAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    private LinSelectWalletListener linSelectWalletListener;
    private boolean selectWallet;
    private Context mContext;
    private List<User> data;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    private Context context;
    private UserDao userDao;
    private int selectPision;
    private boolean isYincang;
    private List<User> users_list;


    public WalletManagerAdapter(int layoutResId, Context context, @Nullable @org.jetbrains.annotations.Nullable List<User> data) {
        super(layoutResId, data);
        this.mContext = context;

    }

    public void setData(Context context, List<User> data, UserDao userDao) {
        this.context = context;
        this.data = data;
        this.userDao = userDao;
        isClicks = new ArrayList<>();
//        for (int i = 0; i < data.size(); i++) {
//            if(i == 0){
//                isClicks.add(true);
//            }else {
        isClicks.add(false);
//            }
//        }
        Log.e("FF5444", "data:" + data.size());
        this.notifyDataSetChanged();
    }

    public void setOnItemSelectedWallet(LinSelectWalletListener linSelectWalletListener) {
        this.linSelectWalletListener = linSelectWalletListener;
    }


    public interface LinSelectWalletListener {
        void selectedWallet(int position);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        TextView tv_word = helper.getView(R.id.has_compler);
        tv_word.setText(item.getName());
        helper.addOnClickListener(R.id.has_compler);

        LinearLayout lin_item_wallet_manager = helper.getView(R.id.lin_item_wallet_manager);
        LinearLayout lin_selected = helper.getView(R.id.lin_selected);
        LinearLayout lin_unselected = helper.getView(R.id.lin_unselected);
        LinearLayout lin_delete = helper.getView(R.id.lin_delete);
        ImageView img_yc_xs = helper.getView(R.id.img_yc_xs);
        TextView tv_all_monery = helper.getView(R.id.tv_all_monery);


        lin_item_wallet_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linSelectWalletListener != null) {
                    linSelectWalletListener.selectedWallet(helper.getLayoutPosition());
                }

                notifyDataSetChanged();
                SPUtils.putString(context, "witch_wallet", item.getName());
                Log.e("FKFK3", "钱包管理选中:" + item.getName());
                EventBus.getDefault().post("choose_wallet");
            }
        });
//        if (isClicks.get(helper.getLayoutPosition())) {
//            lin_item_wallet_manager.setBackground(mContext.getResources().getDrawable(R.drawable.selected_wallet_bg));
//            lin_selected.setVisibility(View.VISIBLE);
//        } else {
//            lin_item_wallet_manager.setBackground(mContext.getResources().getDrawable(R.drawable.dialog_wallet_manager));
//            lin_selected.setVisibility(View.INVISIBLE);
//        }

        if (SPUtils.getString(context, "witch_wallet", null) != null) {
            Log.e("FF5444", "witch_wallet——1:" + SPUtils.getString(context, "witch_wallet", null));
            if (SPUtils.getString(context, "witch_wallet", null).equals(item.getName())) {
                lin_item_wallet_manager.setBackground(context.getResources().getDrawable(R.drawable.selected_wallet_bg));
                lin_selected.setVisibility(View.VISIBLE);
                lin_unselected.setVisibility(View.GONE);
            } else {
                lin_item_wallet_manager.setBackground(context.getResources().getDrawable(R.drawable.dialog_wallet_manager));
                lin_selected.setVisibility(View.GONE);
                lin_unselected.setVisibility(View.VISIBLE);
            }
        } else {
            Log.e("FF3433", "_删除后33333:");
            for (int i = 0; i < data.size(); i++) {
                if (helper.getLayoutPosition() == 0) {
                    Log.e("FF3433", "_删除后44444:");
                    lin_item_wallet_manager.setBackground(context.getResources().getDrawable(R.drawable.selected_wallet_bg));
                    lin_selected.setVisibility(View.VISIBLE);
                    lin_unselected.setVisibility(View.GONE);
                    SPUtils.putString(context, "witch_wallet", item.getName());
                    Log.e("FKFK3", "钱包管理选中:" + item.getName());
                    EventBus.getDefault().post("choose_wallet");

                    DaoSession daoSession = MyApplication.getDaoSession();
                    userDao = daoSession.getUserDao();
                    users_list = userDao.loadAll();
                    SPUtils.putString(context, Tools.name, users_list.get(helper.getLayoutPosition()).getName());
                    SPUtils.putString(context, "wallet_address",  null);
                } else {
                    Log.e("FF3433", "_删除后55555:");
                    lin_item_wallet_manager.setBackground(context.getResources().getDrawable(R.drawable.dialog_wallet_manager));
                    lin_selected.setVisibility(View.GONE);
                    lin_unselected.setVisibility(View.VISIBLE);
                }
            }
        }
        helper.addOnClickListener(R.id.lin_delete);
        img_yc_xs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isYincang) {
                    isYincang = true;
                    tv_all_monery.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏
                    img_yc_xs.setImageDrawable(context.getResources().getDrawable(R.mipmap.yan_xianshi));
                } else {
                    isYincang = false;
                    tv_all_monery.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示
                    img_yc_xs.setImageDrawable(context.getResources().getDrawable(R.mipmap.yincang));
                }
            }
        });
    }

}
