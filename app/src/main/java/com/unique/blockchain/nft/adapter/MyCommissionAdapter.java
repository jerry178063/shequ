package com.unique.blockchain.nft.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.node.DelegetaJBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 投票区adapter
 * */
public class MyCommissionAdapter extends BaseQuickAdapter<DelegetaJBean.ResultBean, BaseViewHolder> {
    private GetIncomelickView getIncomelickView;
    private GetCommissionlickView getCommissionlickView;
    private RedemptionlickView redemptionlickView;
    List<DelegetaJBean.ResultBean> lists;
    String adress,rootAdress;
    public void setData(List<DelegetaJBean.ResultBean> lists){
        this.lists = lists;
        this.notifyDataSetChanged();
    }
    public void setValiAdress(String adress){
        this.adress = adress;
        this.notifyDataSetChanged();
    }
    public void setRootValiAdress(String rootAdress){
        this.rootAdress = rootAdress;
        this.notifyDataSetChanged();
    }
    public MyCommissionAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<DelegetaJBean.ResultBean> data) {
        super(layoutResId, data);
    }

    public void setIncomelickView(GetIncomelickView getIncomelickView){
        this.getIncomelickView=getIncomelickView;
    }
    public void setCommissionlickView(GetCommissionlickView getCommissionlickView){
        this.getCommissionlickView=getCommissionlickView;
    }
    public void setRedemptionlickView(RedemptionlickView redemptionlickView){
        this.redemptionlickView=redemptionlickView;
    }
    public interface GetIncomelickView{
        void setIncomeView(DelegetaJBean.ResultBean item, int pos,String monery);
    }
    public interface GetCommissionlickView{
        void setCommissionView(DelegetaJBean.ResultBean item, int pos,String yongjin);
    }
    public interface RedemptionlickView{
        void setRedemptionView(DelegetaJBean.ResultBean item, int pos,String shares);
    }
    double reward;
    @Override
    protected void convert(BaseViewHolder helper, DelegetaJBean.ResultBean item) {
        TextView tv_word=helper.getView(R.id.has_compler);
        TextView tv_get_income = helper.getView(R.id.tv_get_income);//提取收益
        TextView tv_get_commission = helper.getView(R.id.tv_get_commission);//提取佣金
        TextView tv_redemption_principal = helper.getView(R.id.tv_redemption_principal);//赎回本金
        TextView tv_kgf = helper.getView(R.id.tv_kgf);//矿工费
//
        helper.addOnClickListener(R.id.has_compler);

        TextView tv_gauss_name = helper.getView(R.id.tv_gauss_name);
        tv_gauss_name.setText(item.getValidatorsinfo().getMoniker());
        LinearLayout lin_yzryj = helper.getView(R.id.lin_yzryj);

        TextView tv_delegator_shares = helper.getView(R.id.tv_delegator_shares);
        if(item.getReward() != null && item.getReward().size() > 0){
            tv_delegator_shares.setText(String.format("%.6f",Double.valueOf(item.getReward().get(0).getAmount())/1000000) + "");
        }else {
            tv_delegator_shares.setText("0.000000");
        }


        if(!TextUtils.isEmpty(item.getValidator_address())) {
            String replace =item.getValidator_address().substring(7,42);
            tv_word.setText(item.getValidator_address().replace(replace , "****" ));
        }

        TextView tv_tokens = helper.getView(R.id.tv_tokens);

        BigDecimal bigDecimal1 = new BigDecimal(item.getShares() + "");
        BigDecimal multiply1 = bigDecimal1.divide(new BigDecimal(10).pow(6));
        if(item.getShares() != null && !item.getShares().equals("")) {
            tv_tokens.setText(String.format("%.6f",Double.valueOf(item.getShares())/1000000)+ "");
        }else {
            tv_tokens.setText("0.000000");
        }
        TextView tv_yzryj = helper.getView(R.id.tv_yzryj);
        TextView tv_shuhui = helper.getView(R.id.tv_shuhui);
        BigDecimal bigDecimal=new BigDecimal("0.000000");
        for(int i=0;i<item.getUnbond().size();i++){
            bigDecimal=bigDecimal.add(new BigDecimal(item.getUnbond().get(i).getBalance()));
        }
        if(item.getUnbond() != null && item.getUnbond().size() > 0) {
//            tv_shuhui.setText(bigDecimal.divide(new BigDecimal("10").pow(6)).stripTrailingZeros().toPlainString());

            tv_shuhui.setText(String.format("%.6f",Double.valueOf(bigDecimal.toString())/1000000));
        }else {
            tv_shuhui.setText("0.000000");
        }
        BigDecimal bigDecimal_yzryj=new BigDecimal("0");
        if(item.getCommissions()!=null){
            if(item.getCommissions().getCommission()==null){
                tv_yzryj.setText("0.000000");
            }else {
                if(item.getCommissions().getCommission().isEmpty()){
                    tv_yzryj.setText("0.000000");
                }else {
                    for (int i = 0; i < item.getCommissions().getCommission().size();i++){
                        bigDecimal_yzryj = bigDecimal_yzryj.add(new BigDecimal(item.getCommissions().getCommission().get(i).getAmount()));
                    }

                    tv_yzryj.setText(String.format("%.6f",Double.valueOf(bigDecimal_yzryj.toString())/1000000));
                }

            }
        }else {
            tv_yzryj.setText("0.000000");
        }
        Log.e("ggg33","rootAdress:" + rootAdress);
        Log.e("ggg33","adress:" + adress);
        Log.e("ggg33","item.getValidator_address():" + item.getValidator_address());
//        if(!TextUtils.isEmpty(rootAdress) && rootAdress.length() > 0) {//根验证人
//            if (!rootAdress.equals(item.getValidator_address())) {
//                tv_get_commission.setVisibility(View.GONE);
//                lin_yzryj.setVisibility(View.GONE);
//            }
//        }
        if(!TextUtils.isEmpty(adress)) {//非根验证人
            if (item.getValidator_address().equals(adress)) {
                tv_get_commission.setVisibility(View.VISIBLE);
                lin_yzryj.setVisibility(View.VISIBLE);
            } else {
                tv_get_commission.setVisibility(View.INVISIBLE);
                lin_yzryj.setVisibility(View.INVISIBLE);
            }
        }
        else{
            tv_get_commission.setVisibility(View.GONE);
            lin_yzryj.setVisibility(View.GONE);
        }
        tv_get_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIncomelickView!=null){

                    if(item.getReward() != null && item.getReward().size() > 0 && !TextUtils.isEmpty(Double.valueOf(item.getReward().get(0).getAmount()) + "")) {
                        Log.e("FF8777","getAmount:" + item.getReward().get(0).getAmount());
                        getIncomelickView.setIncomeView(item, helper.getLayoutPosition(), String.format("%.6f", Double.valueOf(item.getReward().get(0).getAmount()) / 1000000) + "");
                    }else {
                        getIncomelickView.setIncomeView(item, helper.getLayoutPosition(), "0.000000");
                        Log.e("FF8777","getAmount:" + "0.000000");
                    }
                }
            }
        });
        tv_get_commission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getCommissionlickView!=null){
                    getCommissionlickView.setCommissionView(item,helper.getLayoutPosition(),tv_yzryj.getText().toString() + "");
                }
            }
        });
        tv_redemption_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(redemptionlickView!=null){
                    redemptionlickView.setRedemptionView(item,helper.getLayoutPosition(),String.format("%.6f",Double.valueOf(item.getShares())/1000000)+ "");
                    Log.e("FFF4442","getShares:" + String.format("%.6f",Double.valueOf(item.getShares())/1000000)+ "");
                }
            }
        });
    }
}
