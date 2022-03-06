package com.unique.blockchain.nft.view.activity.mark;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MyAdapter;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private ArrayList<View> views;
    private View page1;
    private View page2;
    private View page3;
    private View page4;
    private MyAdapter myAdapter;
    private ViewPager mActivityVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        initVp();
    }

    private void initVp() {
        page1 = View.inflate(this, R.layout.page1, null);
        page2 = View.inflate(this, R.layout.page2, null);
        page3 = View.inflate(this, R.layout.page3, null);
        page4 = View.inflate(this, R.layout.page4, null);

        views = new ArrayList<>();
        views.add(page1);
        views.add(page2);
        views.add(page3);
        views.add(page4);
//        myAdapter = new MyAdapter(views);
//        viewpager.setAdapter(myAdapter);
    }

    private void initView() {
        mActivityVp = findViewById(R.id.activity_vp);

    }
}