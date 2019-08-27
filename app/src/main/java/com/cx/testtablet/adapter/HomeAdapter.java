package com.cx.testtablet.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.testtablet.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context context;
    private OnItemClick onItemClick;
    private int[] iconList = new int[]{
       R.drawable.icon_lcd,R.drawable.icon_tp,R.drawable.icon_cam,R.drawable.icon_ir,R.drawable.icon_nfc,
       R.drawable.icon_wifi,R.drawable.icon_ble,R.drawable.icon_sp,R.drawable.icon_mic,R.drawable.icon_ttl,
            R.drawable.icon_4g
    };
    private int[] textList = new int[]{
      R.string.lcd,R.string.tp,R.string.cam,R.string.ir,R.string.nfc,R.string.wifi,R.string.bt,R.string.sp,
      R.string.mic,R.string.ttl,R.string.fourth_network
    };

    private List<Integer> list;
    public HomeAdapter(Context context,List<Integer> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test_classify_layout,null);
        return new HomeViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder homeViewHolder, int i) {
        homeViewHolder.icon.setImageResource(iconList[i]);
        homeViewHolder.name.setText(textList[i]);

        if (list.get(i) == 0){
            homeViewHolder.status.setSelected(false);
            homeViewHolder.status.setBackground(ContextCompat.getDrawable(context,R.drawable.item_test_btn_bg));
            homeViewHolder.status.setText("?");
        }else if (list.get(i) == 1){
            homeViewHolder.status.setSelected(true);
            homeViewHolder.status.setText("NG");
            homeViewHolder.status.setBackground(ContextCompat.getDrawable(context,R.drawable.item_test_status_ng_bg));
        }else {
            homeViewHolder.status.setSelected(true);
            homeViewHolder.status.setText("OK");
            homeViewHolder.status.setBackground(ContextCompat.getDrawable(context,R.drawable.item_test_status_ok_bg));
        }

        homeViewHolder.itemView.setOnClickListener(view -> onItemClick.itemClick(i));
    }

    @Override
    public int getItemCount() {
        return iconList.length;
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView name;
        private TextView status;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_test_classify);
            name = itemView.findViewById(R.id.tv_name);
            status = itemView.findViewById(R.id.tv_status);
        }
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void itemClick(int positon);
    }
}
