package com.unique.blockchain.nft.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.net.bean.AssetsBillBean;

import java.util.List;

public class RecordAdapter extends BaseQuickAdapter<AssetsBillBean.DataBean.DataBean1, BaseViewHolder> {
    //
    public RecordAdapter(int layoutResId, @Nullable List<AssetsBillBean.DataBean.DataBean1> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssetsBillBean.DataBean.DataBean1 item) {
       /* ImageView more = helper.getView(R.id.iv_more);
        helper.addOnClickListener(R.id.ll_root);
        if (item.getScene().equals("recharge")) {
            helper.setText(R.id.tv_type, "充币");
            helper.setText(R.id.tv_all_money, "+" + item.getNum().stripTrailingZeros().toPlainString());
            helper.setText(R.id.tv_use_money, "已完成");
            more.setVisibility(View.VISIBLE);
        }
        if (item.getScene().equals("draw")) {
            helper.setText(R.id.tv_type, "提币");
            if (item.getRemarks().getStatus() == -2) {
                helper.setText(R.id.tv_use_money, "已拒绝");
            } else if (item.getRemarks().getStatus() == 0) {
                helper.setText(R.id.tv_use_money, "待审核");
            } else if (item.getRemarks().getStatus() == 1) {
                helper.setText(R.id.tv_use_money, "处理中");
            } else if (item.getRemarks().getStatus() == 2) {
                helper.setText(R.id.tv_use_money, "已完成");
            } else if (item.getRemarks().getStatus() == 3) {
                helper.setText(R.id.tv_use_money, "上链失败");
            }

            if (item.getNum().stripTrailingZeros().toPlainString().startsWith("-")) {
                helper.setText(R.id.tv_all_money, item.getNum().stripTrailingZeros().toPlainString());
            } else {
                helper.setText(R.id.tv_all_money, "+" + item.getNum().stripTrailingZeros().toPlainString());
            }
            more.setVisibility(View.VISIBLE);
        }
        if (item.getScene().equals("draw_reject")) {
            helper.setText(R.id.tv_type, "提币失败");
            if (item.getRemarks().getStatus() == -2) {
                helper.setText(R.id.tv_use_money, "已拒绝");
            } else if (item.getRemarks().getStatus() == 0) {
                helper.setText(R.id.tv_use_money, "待审核");
            } else if (item.getRemarks().getStatus() == 1) {
                helper.setText(R.id.tv_use_money, "处理中");
            } else if (item.getRemarks().getStatus() == 2) {
                helper.setText(R.id.tv_use_money, "已完成");
            } else if (item.getRemarks().getStatus() == 3) {
                helper.setText(R.id.tv_use_money, "上链失败");
            }

            if (item.getNum().stripTrailingZeros().toPlainString().startsWith("-")) {
                helper.setText(R.id.tv_all_money, item.getNum().stripTrailingZeros().toPlainString());
            } else {
                helper.setText(R.id.tv_all_money, "+" + item.getNum().stripTrailingZeros().toPlainString());
            }
            more.setVisibility(View.VISIBLE);
        }

        if (item.getScene().equals("admin_issue")) {
            helper.setText(R.id.tv_type, "管理员增发");
            helper.setText(R.id.tv_use_money, "已完成");
            if (item.getNum().stripTrailingZeros().toPlainString().startsWith("-")) {
                helper.setText(R.id.tv_all_money, item.getNum().stripTrailingZeros().toPlainString());
            } else {
                helper.setText(R.id.tv_all_money, "+" + item.getNum().stripTrailingZeros().toPlainString());
            }
            more.setVisibility(View.GONE);
        }
        if (item.getScene().equals("income_issue")) {
            helper.setText(R.id.tv_type, "收益发放");
            helper.setText(R.id.tv_use_money, "已完成");
            if (item.getNum().stripTrailingZeros().toPlainString().startsWith("-")) {
                helper.setText(R.id.tv_all_money, item.getNum().stripTrailingZeros().toPlainString());
            } else {
                helper.setText(R.id.tv_all_money, "+" + item.getNum().stripTrailingZeros().toPlainString());
            }
            more.setVisibility(View.GONE);
        }
        if (item.getScene().equals("third_party")) {
            helper.setText(R.id.tv_type, "第三方发放");
            helper.setText(R.id.tv_use_money, "已完成");
            if (item.getNum().stripTrailingZeros().toPlainString().startsWith("-")) {
                helper.setText(R.id.tv_all_money, item.getNum().stripTrailingZeros().toPlainString());
            } else {
                helper.setText(R.id.tv_all_money, "+" + item.getNum().stripTrailingZeros().toPlainString());
            }
            more.setVisibility(View.GONE);
        }
        if (item.getScene().equals("invest")) {
            helper.setText(R.id.tv_type, "理财订单");
            helper.setText(R.id.tv_use_money, "已完成");
            if (item.getNum().stripTrailingZeros().toPlainString().startsWith("-")) {
                helper.setText(R.id.tv_all_money, item.getNum().stripTrailingZeros().toPlainString());
            } else {
                helper.setText(R.id.tv_all_money, "+" + item.getNum().stripTrailingZeros().toPlainString());
            }
            more.setVisibility(View.GONE);
        }
        if (DateUtil.getUserDate(item.getCreated_at()) != null) {
            helper.setText(R.id.tv_time, DateUtil.getUserDate(item.getCreated_at()));

        }
*/
    }


}
