package com.unique.blockchain.nft.infrastructure.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.WalletListAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.me.DeleteWallet;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * 钱包列表
 * */
public class WalletListDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnClickView onClickView;
    RecyclerView recyclerView;
    private TextView tv_cancel;
    private List<User> users_list;
    private String users_item_name;

    public WalletListAdapter allAsstesAdapter=new WalletListAdapter(R.layout.wallet_list_item,new ArrayList<>());
    private ArrayList <String>list=new ArrayList();

    public WalletListDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public WalletListDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public void setSelectData(String users_item_name){
        this.users_item_name = users_item_name;
    }
    public void setData(List<User> users_list){
        this.users_list = users_list;
        Log.e("FKFK3","setData...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.waller_list_dialog_layout, null);

        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        WindowManager windowManager=getWindow().getWindowManager();
//        Display display= windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
//        layoutParams.width=display.getWidth();//设置Dialog的宽度为屏幕宽度
//        getWindow().setAttributes(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*1);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);


        recyclerView = view.findViewById(R.id.recyclerView);
        if(!TextUtils.isEmpty(list.toString())){
            list.clear();
        }
        if(!TextUtils.isEmpty(users_list.toString())){
            for (int i = 0; i < users_list.size(); i++){
                list.add(users_list.get(i).getName());
                if(users_item_name.equals(users_list.get(i).getName())){
                    allAsstesAdapter.setColor(getContext(),"yes");
                }else {
                    allAsstesAdapter.setColor(getContext(),"no");
                }
            }
        }
        Log.e("FKFK3","list:" + list.size() + "---users_list:" + users_list.size());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allAsstesAdapter);
        allAsstesAdapter.setNewData(list);
        allAsstesAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                onClickView.setOnClickView(adapter.getData().get(position).toString());
                dismiss();
                SPUtils.putString(mContext,"witch_wallet",users_list.get(position).getName());
            }
        });
    }


    public void setOnclick(OnClickView onclick){
    this.onClickView=onclick;
    }
    public interface OnClickView{
        void setOnClickView(String name);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    dismiss();
                    break;
//                case R.id.tv_sure:
//                    onClickView.setOnClickView();
//                    break;
            }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(DeleteWallet message) { //方法名任意，这里的参数和  EventBus.getDefault().post(111);对应即可
        if(!TextUtils.isEmpty(message.getName())){
            for (int i = 0; i < list.size(); i++){
                if(message.getName().equals(list.get(i))){
                    allAsstesAdapter.remove(i);
                    allAsstesAdapter.notifyDataSetChanged();
                    Log.e("FKFK3","删除对应的钱包");
                }
            }
        }
    }
}
