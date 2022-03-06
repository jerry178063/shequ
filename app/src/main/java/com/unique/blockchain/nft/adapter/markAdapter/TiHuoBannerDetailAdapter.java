package com.unique.blockchain.nft.adapter.markAdapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TiHuoBannerDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnBannerListener {
    private Context context;
    private List<BannerDatabase.DataBean> bannerl = new ArrayList<>();
    MediaController mMediaController;
    private boolean isPlay = true;
    int start1 = 0;
    int total = 0;
    int width = 0;
    int height = 0;
    int ClickNum = 0;//横竖屏切换
    float scale;

    public TiHuoBannerDetailAdapter(List<BannerDatabase.DataBean> list, Context context) {
        this.context = context;
        this.bannerl = list;
    }

    public void setBannerl(List<BannerDatabase.DataBean> bannerl) {
        this.bannerl = bannerl;
        notifyDataSetChanged();
    }

    /*@Override
        public int getItemViewType(int position) {
            if (position==0){
                return 0;

            }else {
                return 1;
            }
        }*/
    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View butt_rec = LayoutInflater.from(context).inflate(R.layout.mark_tihuo_detail_img, null, false);
        return new ViewHolder(butt_rec);

    }

    //开启一个递归定时器
    Handler handler1;

    public void close() {
        if (handler1 != null) {
            handler1.removeMessages(1);
        }
    }

    //更新视频播放进度
    private void progress(SeekBar seekBar, TextView s1, TextView s2) {

        Log.e("这里是进度:", "" + start1);
        start1 = videoView.getCurrentPosition();//现在的播放位置

        if (total <= 0) {
            total = videoView.getDuration();//总的播放长度
            seekBar.setMax(total);
        }
        Log.e("FDD3232", "total:" + total);

        s1.setText(transformationDate(start1));
        s2.setText(transformationDate(total));
        seekBar.setProgress(start1);

    }

    //转换字符
    private String changeString(int num) {
        return num >= 0 && num < 10 ? "0" + num : "" + num;
    }

    //转换时分秒函数，
    private String transformationDate(int times) {
        //参数时间戳传入进来是以毫秒为单位的时间戳
        //剩余秒数
        int second = times / 1000;
        //剩余小时，每一个小时有60分钟，每分钟有60秒
        int Time = second / 3600;
        //剩余分钟，每分钟有60秒
        second %= 3600;//剩余的秒
        int branch = second / 60;
        //最后剩余秒数
        second %= 60;
        return changeString(Time) + ":" + changeString(branch) + ":" + changeString(second);
    }

    VideoView videoView;
    int dd = 0;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Log.e("FSS24555", "SIZE: " + bannerl.size());
        videoView = viewHolder.webView_detail;
        viewHolder.butt_Banner.setImages(bannerl)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        BannerDatabase.DataBean path1 = (BannerDatabase.DataBean) path;
                        if (path1.getImageAddress().contains("png") || path1.getImageAddress().contains("jpg") || path1.getImageAddress().contains("jpeg")
                                || path1.getImageAddress().contains("gif")) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            Glide.with(context).load(path1.getImageAddress()).into(imageView);
                            viewHolder.butt_Banner.setVisibility(View.VISIBLE);
                            viewHolder.webView_detail.setVisibility(View.GONE);

                        }
                        //Log.e("哈哈", "displayImage: "+path1.getImageAddress());
                    }
                }).setIndicatorGravity(BannerConfig.CENTER)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR).setOnBannerListener(this).start();


        viewHolder.butt_Banner.isAutoPlay(false);
        viewHolder.tv_page.setText(1 + "/" + bannerl.size());
        viewHolder.butt_Banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.e("FSS2455566", "I:" + bannerl.get(i).getImageAddress());

                viewHolder.tv_page.setText((i + 1) + "/" + bannerl.size());
                if (bannerl.get(i).getImageAddress().contains(".mp4")) {
                    for (int j = 0; j < bannerl.size(); j++) {

                        String uri2 = bannerl.get(i).getImageAddress();
                        if (uri2.contains(".mp4")) {
                            viewHolder.isShow.setVisibility(View.INVISIBLE);
                            start1 = 0;

                            webView_detail = viewHolder.webView_detail;
                            play1 = viewHolder.play1;
                            viewHolder.rela_video.setVisibility(View.VISIBLE);

                            viewHolder.webView_detail.setVisibility(View.VISIBLE);
                            viewHolder.play1.setVisibility(View.VISIBLE);
                            Log.e("FSS24555", "uri2: " + uri2);
                            videoView.setVideoURI(Uri.parse(uri2));
                            videoView.requestFocus();
//                            videoView.start();
                            isPlay = true;
                            viewHolder.play1.setImageResource(R.mipmap.video_stop);
                            if (isPlay) {
                                //可以通过
                                play1(viewHolder.play1);
                            } else {
                                stop1(viewHolder.play1);
                            }
                            //监听videoview是否准备就绪
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    Log.e("这里是", "准备就绪");

                                    if (start1 == 0) {
                                        total = 0;
                                        progress(viewHolder.seekBar, viewHolder.s1, viewHolder.s1);
                                    }
                                    //监听videoViewseekTo是否设置完成
                                    mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                                        @Override
                                        public void onSeekComplete(MediaPlayer mp) {
                                            Log.d("这里是", "已经设置完成了");

                                        }
                                    });


                                }

                            });
                            viewHolder.play1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (isPlay) {
                                        //可以通过
                                        play1(viewHolder.play1);
                                    } else {
                                        stop1(viewHolder.play1);
                                    }
                                }
                            });
                            //监听videoView是否播放完成
                            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    stop1(viewHolder.play1);
                                }

                            });
                            //点击videoView，操作控件显示关闭
                            videoView.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if (viewHolder.isShow.getVisibility() == View.VISIBLE) {
                                        viewHolder.isShow.setVisibility(View.INVISIBLE);
                                    } else {
                                        viewHolder.isShow.setVisibility(View.VISIBLE);
                                    }
                                    return false;
                                }
                            });


                            //监听seekBar
                            viewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                                //进度已更改
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                   int progress1= (int) Math.floor(progress / 100f * total);
                                    if (fromUser) {
                                        total = 0;
                                        progress(viewHolder.seekBar, viewHolder.s1, viewHolder.s1);
                                        videoView.seekTo(progress);
                                    }
                                }

                                //刚开始拖动
                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }
                                //拖动完成


                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {
                                    //获取第一帧图像的bitmap对象 单位是微秒
                                }

                            });
//
                            //在视频没有初始化之前禁止拖动
                            viewHolder.seekBar.setOnTouchListener(new View.OnTouchListener() {

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if (total == 0) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            });
//                            play1(viewHolder.play1);
                        }
                    }

                    viewHolder.play1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //播放按钮被点击
                            if (isPlay) {
                                //可以通过
                                play1(viewHolder.play1);
                            } else {
                                stop1(viewHolder.play1);
                            }
                        }
                    });

                } else {
                    viewHolder.rela_video.setVisibility(View.GONE);
                    viewHolder.webView_detail.setVisibility(View.GONE);
                    viewHolder.isShow.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    VideoView webView_detail;
    ImageView play1;

    //播放
    private void play1(ImageView play1) {
        videoView.start();
        play1.setImageResource(R.mipmap.video_stop);
        isPlay = false;
        if (handler1 == null) {
            handler1 = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {

                    if (msg.what == 1) {
                        handler1.removeMessages(1);
                        total = 0;
                        progress(ViewHolder.seekBar, ViewHolder.s1, ViewHolder.s2);//执行更新
                        if (!isPlay) {
                            handler1.sendEmptyMessageDelayed(1, 1000);
                        }
                    }

                    return false;
                }
            });
            handler1.sendEmptyMessage(1);
        } else {
            handler1.sendEmptyMessage(1);
        }
    }

    //暂停
    private void stop1(ImageView play1) {
        videoView.pause();
        play1.setImageResource(R.mipmap.video_start);
        isPlay = true;
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
//                showWait(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (bannerl == null) {
            return 0;
        }
        return 1;
    }

    @Override
    public void OnBannerClick(int i) {
        Log.e("FF3211", "I:" + i);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Banner butt_Banner;
        private View itemView;
        private TextView tv_page;
        private VideoView webView_detail;
        public static SeekBar seekBar;
        public static TextView s1, s2;
        public static ImageView play1, setMax;
        LinearLayout isShow;
        private RelativeLayout rela_video;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            butt_Banner = itemView.findViewById(R.id.butt_Banner_detail_tihuo);
            tv_page = itemView.findViewById(R.id.tv_page);
            webView_detail = itemView.findViewById(R.id.webView_detail);
            //初始数据
            play1 = (ImageView) itemView.findViewById(R.id.play1);
            seekBar = (SeekBar) itemView.findViewById(R.id.SeekBar);
            s1 = (TextView) itemView.findViewById(R.id.s1);
            s2 = (TextView) itemView.findViewById(R.id.s2);
            setMax = (ImageView) itemView.findViewById(R.id.setMax);
            isShow = (LinearLayout) itemView.findViewById(R.id.isShow);
            rela_video = itemView.findViewById(R.id.rela_video);
        }
    }
}
