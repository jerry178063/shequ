package com.unique.blockchain.nft;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;

import com.carlt.networklibs.NetworkManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.space.exchange.biz.common.base.BaseApplication;
import com.space.exchange.biz.common.event.BaseEvent;
import com.space.exchange.biz.common.event.EventWhat;
import com.unique.blockchain.nft.dp.DaoMaster;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.messagedb.DBHelper;
import com.unique.blockchain.nft.widget.GlideImageLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.database.Database;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends BaseApplication {

   private static DaoSession daoSession;
    private int maxImgCount = 5;               //允许选择图片最大数
    public static String mAuthorization;
    public static int mWaleetClick;
    public static int mWaleetDele;//点击了钱包删除
    public static int commission;
    public static int createWallet;//0-创建钱包，1-导入钱包
    private List<Activity> activityList = new LinkedList();
    @Override
    public void onCreate() {
        super.onCreate();
//        if (!com.gpb.blockchain.nodemgr.BuildConfig.DEBUG) {
//            //       初始化bugly
//            Beta.canShowUpgradeActs.add(MainActivity.class);
//            Bugly.init(getApplicationContext(), "d753c12ffe", false);
//        }

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "DateBaseName");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        //最好放到 Application oncreate执行
        initImagePicker();
        NetworkManager.getInstance().init(this);

        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //----个人通知数据库
        DBHelper helperMess=new DBHelper(this,"lqwvje.db");
        SQLiteDatabase dbMess=  helperMess.getReadableDatabase();
        String sql = "Create table person(id integer primary key autoincrement,ids int ,noticeId int,type int,isRead varchar," +
                "title varchar,content varchar,releaseTime varchar)";
        if(sqlTableIsExist("person")){

        }else {
            dbMess.execSQL(sql);
        }
    }
    /**
     * 判断数据库中某张表是否存在
     */
    private boolean sqlTableIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            //search.db数据库的名字
            db = openOrCreateDatabase("lqwvje.db", Context.MODE_PRIVATE, null);
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }
//    //添加Activity到容器中
//    public void addActivity(Activity activity)
//    {
//        activityList.add(activity);
//    }
//    //遍历所有Activity并finish
//
//    public void exit() {
//
//        for(int i =0 ; i < activityList.size();i++) {
//            activityList.get(i).finish();
//        }
//    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMain(BaseEvent msg) {
        if (msg.what.equals(EventWhat.NEED_LOGIN)) {
            if (((boolean) msg.obj)) {
            }
        }
        //回收消息
        msg.recycle();
    }

    public static   DaoSession getDaoSession() {
        return daoSession;
    }
}
