package com.tiens.cipsm.fragment;

import android.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmParkEntity;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/16 14:29
 */

@EFragment(R.layout.park_info_fragment)
public class ParkInfoFragment extends Fragment {

    private final String CLASSNAME = getClass().getName();

    @ViewById
    EditText park_info_name_et;
    @ViewById
    EditText park_info_contact_et;
    @ViewById
    EditText park_info_contact_phone_et;
    @ViewById
    TextView park_info_area_et;
    @ViewById
    TextView park_info_create_et;

    @AfterViews
    protected void init() {
        CipsmParkEntity cipsmParkEntity = MyStaticVariable.cipsmParkEntity;
        if (cipsmParkEntity == null) {
            Logger.d(CLASSNAME + "\t没有获取到用户园区信息");
            toast("该用户没有管理的园区");
            return;
        }
        park_info_name_et.setText(cipsmParkEntity.getParkName());
        park_info_contact_et.setText(cipsmParkEntity.getParkContact());
        park_info_contact_phone_et.setText(cipsmParkEntity.getParkContactPhone());
        Float parkArea = cipsmParkEntity.getParkArea();
        int floor = parkArea.intValue();
        if (floor / 1000000 > 0)
            park_info_area_et.setText(String.format("%s km²", String.valueOf(new BigDecimal(parkArea).divide(new BigDecimal(1000000), 6, BigDecimal.ROUND_HALF_UP).abs().doubleValue())));
        else park_info_area_et.setText(String.format("%s m²", String.valueOf(Math.abs(parkArea))));
        park_info_create_et.setText(MyUtils.dateTimeToString(cipsmParkEntity.getParkCreateTime()));
    }

    @Click(R.id.park_info_confirm)
    protected void ok(View view) {
        CipsmParkEntity cipsmParkEntity = MyStaticVariable.cipsmParkEntity;
        if (cipsmParkEntity == null) {
            Logger.d(CLASSNAME + "\t没有获取到用户园区信息");
            toast("该用户没有管理的园区");
            return;
        }
        String name = park_info_name_et.getText().toString();
        String contact = park_info_contact_et.getText().toString();
        String phone = park_info_contact_phone_et.getText().toString();
        if (MyUtils.isEmpty(name)) {
            toast("园区名称不能为空");
            return;
        }
        if (MyUtils.isEmpty(contact)) {
            toast("园区名称不能为空");
            return;
        }
        if (MyUtils.isEmpty(phone)) {
            toast("园区名称不能为空");
            return;
        }
        cipsmParkEntity.setParkName(name);
        cipsmParkEntity.setParkContact(contact);
        cipsmParkEntity.setParkContactPhone(phone);
        update(cipsmParkEntity);
    }

    @Background
    protected void update(CipsmParkEntity cipsmParkEntity) {
        try {
            String param = "cipsmPark=" + URLEncoder.encode(JSON.toJSONString(cipsmParkEntity), "UTF-8");
            HttpURLConnection httpURLConnection = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmpark/update", "POST", param);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                Logger.i(CLASSNAME + "\t修改园区成功");
                toast("修改园区成功成功");
            } else {
                Logger.e(CLASSNAME + "\t修改园区失败");
                toast("修改园区失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(CLASSNAME + "\t修改异常，异常信息：" + e.getMessage());
            toast("修改异常");
        }
    }

    @UiThread
    protected void toast(String msg, int duration) {
        MyUtils.toast(msg, duration);
    }

    @UiThread
    protected void toast(String msg) {
        toast(msg, 0);
    }

}
