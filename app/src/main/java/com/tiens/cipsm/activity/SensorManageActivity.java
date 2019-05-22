package com.tiens.cipsm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.SysDictEntity;
import com.tiens.cipsm.myAdapter.SensorManageItemAdapter;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/16 18:04
 */


@SuppressLint("Registered")
@EActivity(R.layout.sensor_manage_activity)
public class SensorManageActivity extends Activity {

    private final String CLASSNAME = getClass().getName();
    public static List<SysDictEntity> typeList = null;

    @ViewById
    ListView sensor_manage_lv;
    @ViewById
    EditText sensor_manage_type_name;

    @Click(R.id.sensor_manage_back)
    protected void back(View view) {
        finish();
    }

    @Click(R.id.sensor_manage_type_add)
    protected void add() {
        String sensorName = sensor_manage_type_name.getText().toString();
        if (MyUtils.isEmpty(sensorName)) {
            Logger.i(CLASSNAME + "\t监测点类型不能为空");
            toast("监测点类型不能为空");
            return;
        }
        SysDictEntity sysDictEntity = new SysDictEntity();
        sysDictEntity.setDictName(sensorName);
        if (typeList == null)
            typeList = new ArrayList<>();
        sysDictEntity.setDictCode(typeList.size());
        sysDictEntity.setDictType("sensor");
        sysDictEntity.setDictStatus(1);
        addThread(sysDictEntity);
    }

    @AfterViews
    protected void init() {
        getType();
    }

    @Background
    protected void addThread(SysDictEntity sysDictEntity) {
        try {
            String param = "sysDict=" + URLEncoder.encode(JSON.toJSONString(sysDictEntity), "UTF-8");
            HttpURLConnection httpURLConnection = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "sysdict/save", "POST", param);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                Logger.i(CLASSNAME + "\t添加监测点类型成功");
                toast("添加监测点类型成功");
                typeList.add(sysDictEntity);
            } else {
                Logger.i(CLASSNAME + "\t错误代码：" + responseCode + "添加监测点类型失败");
                toast("添加监测点类型失败");
                return;
            }
            if (typeList == null) {
                toast("获取监测点类型失败");
                return;
            }
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.i(CLASSNAME + "\t错误信息：" + e.getMessage() + "添加监测点类型失败");
            toast("添加监测点类型失败");
        }
        setAdapter();
    }

    @Background
    protected void getType() {
        try {
            String param = "type=sensor";
            typeList = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "sysdict/infoByType?" + param, "GET", "", SysDictEntity.class);
            if (typeList == null) {
                toast("获取监测点类型失败");
                return;
            }
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    protected void setAdapter() {
        sensor_manage_lv.setAdapter(new SensorManageItemAdapter(getApplicationContext(), typeList));
        sensor_manage_lv.deferNotifyDataSetChanged();
    }

    @UiThread
    protected void toast(String msg, int duration) {
        if (msg.contains("成功")) {
            sensor_manage_type_name.setText("");
        }
        MyUtils.toast(msg, duration);
    }

    @UiThread
    protected void toast(String msg) {
        toast(msg, 0);
    }
}
