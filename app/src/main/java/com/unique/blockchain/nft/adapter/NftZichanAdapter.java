package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.common.util.java.StringUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;
import com.unique.blockchain.nft.view.activity.me.GriiActivity;
import com.unique.blockchain.nft.view.activity.me.SouMeActivity;

import java.util.List;

/**
 * 我的-交易中
 */
public class NftZichanAdapter extends BaseQuickAdapter<MarkHotBean.Rows, BaseViewHolder> {
    private CommAdapter.OutPrice mOutPrice;
    private SparseArray<CountDownTimer> timerArray;
    private String uniqueAdress;
    private Context context;

    private TradingAdapter.CancelTrade cancelTrade;

    public interface CancelTrade {
        void CancelTrade(int position, MarkHotBean.Rows item);
    }

    private Transfer mTransfer;

    public interface OutPrice {
        void outPrice(int position);
    }

    public interface Transfer {
        void Transfer(int position, MarkHotBean.Rows item);
    }

    private TiHuo mTiHuo;

    public interface TiHuo {
        void TiHuo(int position, MarkHotBean.Rows item);
    }

    private GoTransfer mGoTransfer;

    public interface GoTransfer {
        void GoTransfer(int position, MarkHotBean.Rows item);
    }

    public void setCancelTrade(TradingAdapter.CancelTrade cancelTrade) {
        this.cancelTrade = cancelTrade;
    }

    public void setTiHuo(TiHuo tiHuo) {
        this.mTiHuo = tiHuo;
    }

    public void setGoTransfer(GoTransfer goTransfer) {
        this.mGoTransfer = goTransfer;
    }

    public void setTransfer(Transfer transfer) {
        this.mTransfer = transfer;
    }

    public void setOutPriceListener(CommAdapter.OutPrice outPrice) {
        this.mOutPrice = outPrice;
    }

    public NftZichanAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<MarkHotBean.Rows> data, Context context, String uniqueAdress) {
        super(layoutResId, data);
        timerArray = new SparseArray<>();
        this.context = context;
        this.uniqueAdress = uniqueAdress;
    }

    public void cancelAllTimers() {
        if (timerArray == null) {
            return;
        }
        int size = timerArray.size();
        for (int i = 0; i < size; i++) {
            CountDownTimer cdt = timerArray.get(timerArray.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MarkHotBean.Rows item) {


//        TextView search_text2 = helper.getView(R.id.tv_name);
//        TextView search_text1 = helper.getView(R.id.tv_product_type_name);
//        TextView current_price = helper.getView(R.id.current_price);
        ImageView img_icon = helper.getView(R.id.img_icon);
        RelativeLayout rela_search = helper.getView(R.id.rela_search);
//        TextView shi_jian = helper.getView(R.id.shi_jian);
//        LinearLayout lin_daijiaoyi = helper.getView(R.id.lin_daijiaoyi);//待交易
//        RelativeLayout lin_jiaoyizhong = helper.getView(R.id.lin_jiaoyizhong);//交易中
//        LinearLayout lin_yitihuo = helper.getView(R.id.lin_yitihuo);//已提货
//        LinearLayout lin_yishouhuo = helper.getView(R.id.lin_yishouhuo);//已收货
//        TextView tv_jianjie = helper.getView(R.id.tv_jianjie);//简介
//        TextView tv_max_trading = helper.getView(R.id.tv_max_trading);//最高出价
//        TextView tv_min_jingpai = helper.getView(R.id.tv_min_jingpai);//最低竞拍
//        ImageView img_transfer = helper.getView(R.id.img_transfer);//转移
//        ImageView img_go_trande = helper.getView(R.id.img_go_trande);//去交易
//        ImageView img_get_huowu = helper.getView(R.id.img_get_huowu);//提货
//        TextView tv_isTrade = helper.getView(R.id.tv_isTrade);//转移状态
//        ImageView img_cancel_trade = helper.getView(R.id.img_cancel_trade);//撤销交易
//        TextView tv_price_type3 = helper.getView(R.id.tv_price_type3);
//        LinearLayout lin_out_price3 = helper.getView(R.id.lin_out_price3);
//        TextView tv_name_tihuo = helper.getView(R.id.tv_name_tihuo);
//        TextView tv_product = helper.getView(R.id.tv_product);//已提货简介
        TextView tv_name_lianshang = helper.getView(R.id.tv_name_lianshang);
        tv_name_lianshang.setText(StringUtil.subStringNft15(15,item.getNftName()));
        TextView tv_name_nftid = helper.getView(R.id.tv_name_nftid);
        tv_name_nftid.setText(item.getNftId());
        TextView tv_unique_monery = helper.getView(R.id.tv_unique_monery);
        tv_unique_monery.setText(String.format("%.6f", Double.valueOf(item.getCurrentPrice())) + "");

        if (helper.getLayoutPosition() == 0) {
            rela_search.setVisibility(View.VISIBLE);
        } else {
            rela_search.setVisibility(View.GONE);
        }
        rela_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SouMeActivity.class);
                context.startActivity(intent);
            }
        });
//        img_cancel_trade.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (cancelTrade != null) {
//                    cancelTrade.CancelTrade(helper.getLayoutPosition(), item);
//                }
//            }
//        });
//        tv_product.setText(StringUtil.subStringWithDot(20,item.getProductIntro()) + "");
//        if (item.getIsTransfer() != null) {
//            if (item.getIsTransfer().intValue() == 0) {
//                tv_isTrade.setText("转移中");
//            } else if (item.getIsTransfer().intValue() == 1) {
//                tv_isTrade.setText("转移失败");
//            } else if (item.getIsTransfer().intValue() == 2) {
//                tv_isTrade.setText("转移成功");
//            }
//        } else {
//            tv_isTrade.setText("");
//        }
//        img_transfer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTransfer != null) {
//                    mTransfer.Transfer(helper.getLayoutPosition(), item);
//                }
//            }
//        });
//        img_go_trande.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mGoTransfer != null) {
//                    mGoTransfer.GoTransfer(helper.getLayoutPosition(), item);
//                }
//            }
//        });
//        img_get_huowu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTiHuo != null) {
//                    mTiHuo.TiHuo(helper.getLayoutPosition(), item);
//                }
//            }
//        });
//        Log.e("FFF4447", item.getWalletAddr() + "--" + uniqueAdress);
//        Log.e("FFF4447", "-getNftState-" + item.getNftState());
//
//        if (item.getNftState() != null) {
//            if (item.getNftState().equals("0")) {
//                lin_daijiaoyi.setVisibility(View.VISIBLE);
//                lin_jiaoyizhong.setVisibility(View.GONE);
//                lin_yitihuo.setVisibility(View.GONE);
//                lin_yishouhuo.setVisibility(View.GONE);
//                tv_jianjie.setText(StringUtil.subStringWithDot(20,item.getProductIntro()) + "");
//            } else if (item.getNftState().equals("1")) {
//                lin_daijiaoyi.setVisibility(View.GONE);
//                lin_jiaoyizhong.setVisibility(View.VISIBLE);
//                lin_yitihuo.setVisibility(View.GONE);
//                lin_yishouhuo.setVisibility(View.GONE);
//
//                if(item.getSellMode() == 1){//固定价
//                    lin_out_price3.setVisibility(View.GONE);
//                    tv_price_type3.setText("固定价:");
//                }else if(item.getSellMode() == 2){//拍卖
//                    lin_out_price3.setVisibility(View.VISIBLE);
//                    tv_price_type3.setText("最高出价:");
//                }
//                tv_max_trading.setText(item.getCurrentPrice());
//                tv_min_jingpai.setText(item.getMiniDidPrice());
//            } else if (item.getNftState().equals("2")) {
//                lin_daijiaoyi.setVisibility(View.GONE);
//                lin_jiaoyizhong.setVisibility(View.GONE);
//                lin_yitihuo.setVisibility(View.VISIBLE);
//                lin_yishouhuo.setVisibility(View.GONE);
//            } else if (item.getNftState().equals("3")) {
//                lin_daijiaoyi.setVisibility(View.GONE);
//                lin_jiaoyizhong.setVisibility(View.GONE);
//                lin_yitihuo.setVisibility(View.GONE);
//                lin_yishouhuo.setVisibility(View.VISIBLE);
//            }
//        }

        Glide.with(mContext).load(item.getIconUrl()).into(img_icon);
//        search_text2.setText(item.getProductName());
//        tv_name_tihuo.setText(item.getProductName());
//        Log.e("FF3422", "item.getSurplus():" + item.getSurplus());
//        if (!TextUtils.isEmpty(item.getCurrentPrice())) {
//            current_price.setText(item.getCurrentPrice());
//        }
//
//        getType = item.getProductType();
//
//        if (getType == 1) {
//            search_text1.setBackground(mContext.getResources().getDrawable(R.drawable.shop1));
//            search_text1.setText("票务");
//        } else if (getType == 2) {
//
//            search_text1.setBackground(mContext.getResources().getDrawable(R.drawable.shop2));
//            search_text1.setText("收藏品");
//        } else if (getType == 3) {
//            search_text1.setBackground(mContext.getResources().getDrawable(R.drawable.shop));
//            search_text1.setText("艺术品");
//        } else if (getType == 4) {
//            search_text1.setBackground(mContext.getResources().getDrawable(R.drawable.shop4));
//            search_text1.setText("轻奢品");
//        }
//        type = item.getProductType() + "";
        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, PaiActivity.class);
//                intent.putExtra("nftId", type);
//                mContext.startActivity(intent);
            }
        });
    }

//    public  void formatLongToTimeStr(Long date) {
//        long day = date / (60 * 60 * 24);
//        long hour = (date / (60 * 60) - day * 24);
//        long min = ((date / 60) - day * 24 * 60 - hour * 60);
//        long s = (date - day*24*60*60 - hour*60*60 - min*60);
////        String strtime = "剩余："+day+"天"+hour+"小时"+min+"分"+s+"秒";
//        tv_detail_day.setText(day + "");//天
//        tv_detail_hour.setText(hour + "");//时
//        tv_detail_minute.setText(min + "");//分
//        tv_detail_sends.setText(s + "");//秒
//    }
}
