package com.unique.blockchain.nft.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;

/**
 * 结合TextSwitcher空间实现文字广告，轮播
 */
public class TextSwitchBanner implements ViewSwitcher.ViewFactory, View.OnClickListener {

    private Context context;
    private TextSwitcher textSwitcher;
    private List<String> texts;
    private OnItemClickLitener listener;

    private int marker;
    private AnimationSet InAnimationSet;
    private AnimationSet OutAnimationSet;

    private int delayTime = 2000;
    private static final int DURATION = 1000;

    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            nextView();
            handler.postDelayed(task, delayTime * 2);
        }
    };

    public TextSwitchBanner(Context context, TextSwitcher textSwitcher) {
        this.context = context;
        this.textSwitcher = textSwitcher;
        init();
    }

    private void init() {
        textSwitcher.setFactory(this);
        textSwitcher.setText("");
        createAnimation();
        textSwitcher.setInAnimation(InAnimationSet);
        textSwitcher.setOutAnimation(OutAnimationSet);
        textSwitcher.setOnClickListener(this);
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.START);
        textView.setSingleLine(true);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    @Override
    public void onClick(View v) {
        if(listener != null && texts != null && texts.size() != 0){
            listener.onClick(texts, marker);
        }
    }

    public void update(List<String> list){
        if(list == null || list.size() == 0){
            return;
        }
        stop();
        texts = list;
        marker = 0;
        textSwitcher.setText(texts.get(0));
        start();
    }

    public void start() {
        stop();
        handler.postDelayed(task, delayTime);
    }

    public void stop(){
        handler.removeCallbacks(task);
    }

    public int getMarker() {
        return marker;
    }

    public TextSwitchBanner setTexts(List<String> texts) {
        this.texts = texts;
        return this;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    private void createAnimation() {
        AlphaAnimation alphaAnimation;
        TranslateAnimation translateAnimation;

        int h = textSwitcher.getHeight();
        if (h <= 0) {
            textSwitcher.measure(0,0);
            h = textSwitcher.getMeasuredHeight();
        }

        InAnimationSet = new AnimationSet(true);
        OutAnimationSet = new AnimationSet(true);

        alphaAnimation = new AlphaAnimation(0,1);
        translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, h, Animation.ABSOLUTE, 0);
        InAnimationSet.addAnimation(alphaAnimation);
        InAnimationSet.addAnimation(translateAnimation);
        InAnimationSet.setDuration(DURATION);

        alphaAnimation = new AlphaAnimation(1,0);
        translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -h);
        OutAnimationSet.addAnimation(alphaAnimation);
        OutAnimationSet.addAnimation(translateAnimation);
        OutAnimationSet.setDuration(DURATION);
    }

    private void nextView() {
        marker = ++marker % texts.size();
        textSwitcher.setText(texts.get(marker));
    }

    public interface OnItemClickLitener{
        void onClick(List<String> list, int position);
    }

    public void setOnItemClickListener(OnItemClickLitener listener){
        this.listener = listener;
    }

}