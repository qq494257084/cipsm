package com.tiens.cipsm.myAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.SysDictEntity;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.UiThread;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/17 9:44
 */

public class SensorManageItemAdapter extends BaseAdapter {

    private final String CLASSNAME = getClass().getName();
    private static Toast toast = null;

    private Context context;
    private List<SysDictEntity> list;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            toast(msg.obj.toString());
            notifyDataSetChanged();
        }
    };

    public SensorManageItemAdapter(Context context, List<SysDictEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SysDictEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null)
            view = View.inflate(context, R.layout.sensor_manage_item, null);
        else view = convertView;
        SysDictEntity sysDictEntity = list.get(position);
        TextView sensorName = view.findViewById(R.id.sensor_manage_item_name);
        TextView sensorDelete = view.findViewById(R.id.sensor_manage_item_delete);
        sensorName.setText(sysDictEntity.getDictName());
        sensorDelete.setOnClickListener(v -> deleteThread(position));
        return view;
    }

    private void deleteThread(int position) {
        new Thread(() -> {
            try {
                String param = "dictId=" + list.get(position).getDictId();
                HttpURLConnection httpURLConnection = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "sysdict/delete", "POST", param);
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                Message message = new Message();
                if (responseCode == 200) {
                    message.obj = "删除监测点类型成功";
                    Logger.i(CLASSNAME + "\t删除监测点类型成功");
                    handler.sendMessage(message);
                } else {
                    message.obj = "删除监测点类型失败";
                    Logger.i(CLASSNAME + "\t错误代码：" + responseCode + "删除监测点类型失败");
                    handler.sendMessage(message);
                    return;
                }
                list.remove(position);
            } catch (Exception e) {
                Message message = new Message();
                message.obj = "删除监测点类型失败";
                e.printStackTrace();
                Logger.i(CLASSNAME + "\t错误信息：" + e.getMessage() + "删除监测点类型失败");
                handler.sendMessage(message);
            }
        }).start();
    }

    private void toast(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
            return;
        }
        toast.setText(msg);
        toast.setDuration(duration);
        toast.show();
    }

    private void toast(String msg) {
        toast(msg, 0);
    }
}
