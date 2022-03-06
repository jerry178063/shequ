package com.unique.blockchain.nft.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.space.exchange.biz.net.utils.DisplayUtil;
import com.unique.blockchain.nft.R;
import com.xujiaji.happybubble.BubbleDialog;
import com.xujiaji.happybubble.BubbleLayout;

public class CustomBubbleDialog extends BubbleDialog {

    ViewHolder mHolder;

    public CustomBubbleDialog(Context context, String content) {
        super(context);
        BubbleLayout bubbleLayout = new BubbleLayout(context);
        bubbleLayout.setBubbleColor(context.getResources().getColor(R.color.white_color));
        bubbleLayout.setLook(BubbleLayout.Look.TOP);
        bubbleLayout.setLookLength(DisplayUtil.dipToPix(context,7));
        bubbleLayout.setLookWidth(DisplayUtil.dipToPix(context,12));
        bubbleLayout.setShadowColor(Color.parseColor("#29000000"));
        bubbleLayout.setShadowRadius(DisplayUtil.dipToPix(context,3));
        bubbleLayout.setBubbleRadius(DisplayUtil.dipToPix(context,3));
//        设置外部遮罩的透明度
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        getWindow().setWindowAnimations(R.style.helpPopStyles);
        attributes.dimAmount = 0.2f;
        calBar(true);
        setBubbleLayout(bubbleLayout);
        setPosition(Position.BOTTOM);
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, DisplayUtil.dipToPix(context,10));
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_bubble_layout, null);
        mHolder = new ViewHolder(rootView);
        addContentView(rootView);
        mHolder.tvContent.setText(content);
    }

    static class ViewHolder{
        private TextView tvContent;

        public ViewHolder(View rootView){
            tvContent = rootView.findViewById(R.id.tv_bubble_cotent);
        }
    }
}
