package com.tiens.cipsm.myAdapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.activity.SystemMasterActivity_;
import com.tiens.cipsm.entity.CipsmParkEntity;
import com.tiens.cipsm.fragment.SystemIndexFragment_;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/14 9:55
 */

public class SystemParkAdapter extends BaseAdapter {
    private final String CLASSNAME = getClass().getName();
    private Context context;
    private List<CipsmParkEntity> cipsmParkEntityList;

    public SystemParkAdapter(Context context, List<CipsmParkEntity> cipsmParkEntityList) {
        this.context = context;
        this.cipsmParkEntityList = cipsmParkEntityList;
    }

    @Override
    public int getCount() {
        return cipsmParkEntityList.size();
    }

    @Override
    public CipsmParkEntity getItem(int position) {
        return cipsmParkEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.system_park_list_item, null);
        } else view = convertView;
        TextView titleTV = view.findViewById(R.id.system_park_list_title);
        LinearLayout buttonAll = view.findViewById(R.id.system_park_list_bt_all);
        TextView masterBT = view.findViewById(R.id.system_park_list_master);
        TextView editBT = view.findViewById(R.id.system_park_list_edit);
        TextView deleteBT = view.findViewById(R.id.system_park_list_delete);
        CipsmParkEntity cipsmParkEntity = cipsmParkEntityList.get(position);
        titleTV.setText(cipsmParkEntity.getParkName());
        buttonAll.setVisibility(View.GONE);
        titleTV.setOnClickListener(v -> {
            if (buttonAll.getVisibility() == View.GONE)
                buttonAll.setVisibility(View.VISIBLE);
            else buttonAll.setVisibility(View.GONE);
        });
        masterBT.setOnClickListener(v -> {
            Intent intent = new Intent(context, SystemMasterActivity_.class);
            intent.putExtra("entity", cipsmParkEntity);
            context.startActivity(intent);
        });
        editBT.setOnClickListener(v -> ((Activity) context).findViewById(R.id.system_index).performClick());
        deleteBT.setOnClickListener(v -> deletePark(cipsmParkEntity.getParkId()));
        return view;
    }

    private void deletePark(String id) {
        new Thread(() -> {
            try {
                String param = "parkId=" + id;
                HttpURLConnection httpURLConnection = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmpark/delete", "POST", param);
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    Logger.i(CLASSNAME + "\t删除园区成功");
                    MyUtils.toast("删除园区成功");
                    notifyDataSetChanged();
                } else {
                    Logger.e(CLASSNAME + "\t删除园区失败");
                    MyUtils.toast("失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Logger.e(CLASSNAME + "\t网络异常。异常信息：" + e.getMessage());
                MyUtils.toast("网络异常");
            }
        }).start();
    }

}
