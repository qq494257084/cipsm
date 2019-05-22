package com.tiens.cipsm.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmParkEntity;
import com.tiens.cipsm.entity.CipsmSensorEntity;
import com.tiens.cipsm.entity.CipsmUserEntity;
import com.tiens.cipsm.entity.CipsmUserParkEntity;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/17 11:40
 */

@EFragment(R.layout.park_index_fragment)
public class ParkIndexFragment extends Fragment {

    private final String CLASSNAME = getClass().getName();

    @ViewById
    TextView park_index_title;
    @ViewById
    TextureMapView park_index_map;
    private List<LatLng> latLngList = new ArrayList<>();
    private List<CipsmSensorEntity> sensorEntities = null;

    @AfterViews
    protected void init() {
        park_index_title.setText("");
        getParkInfo();
    }

    @Background
    protected void getParkInfo() {
        CipsmUserEntity userManagerEntity = MyStaticVariable.userManagerEntity;
        if (userManagerEntity == null) {
            toast("获取用户数据失败，请重新登录！");
            return;
        }
        String userId = "userParkId=" + userManagerEntity.getUserId();
        List<CipsmUserParkEntity> listByURLWithParam3 = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmuserpark/infoByUserId?" + userId, "GET", "", CipsmUserParkEntity.class);
        if (!listByURLWithParam3.isEmpty()) {
            CipsmUserParkEntity cipsmUserParkEntity = listByURLWithParam3.get(0);
            String parkParkId = cipsmUserParkEntity.getUserParkParkId();
            List<CipsmParkEntity> listByURLWithParam4 = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmpark/info/" + parkParkId, "GET", "", CipsmParkEntity.class);
            if (!listByURLWithParam4.isEmpty()) {
                MyStaticVariable.cipsmParkEntity = listByURLWithParam4.get(0);
                loadPark();
                getSensorInfo(MyStaticVariable.cipsmParkEntity);
            } else {
                toast("该用户没有管理园区的权限");
            }
        } else {
            toast("该用户没有管理园区的权限");
        }
    }

    @Background
    protected void getSensorInfo(CipsmParkEntity cipsmParkEntity) {
        CipsmUserEntity userManagerEntity = MyStaticVariable.userManagerEntity;
        if (userManagerEntity == null) {
            toast("获取用户数据失败，请重新登录！");
            return;
        }
        if (cipsmParkEntity == null) {
            toast("获取园区数据失败，请重新登录！");
            return;
        }
        String userId = "infoByParkId=" + cipsmParkEntity.getParkId();
        sensorEntities = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmsensor/infoByParkId?" + userId, "GET", "", CipsmSensorEntity.class);
        loadSensor();
    }

    @UiThread
    protected void loadPark() {
        CipsmParkEntity cipsmParkEntity = MyStaticVariable.cipsmParkEntity;
        park_index_title.setText(cipsmParkEntity.getParkName());
        String jsonString = cipsmParkEntity.getParkShape();
        ArrayList arrayList = JSON.parseObject(jsonString, ArrayList.class);
        for (Object o : arrayList) {
            JSONObject jsonObject = (JSONObject) o;
            LatLng latLng = new LatLng(Double.parseDouble(jsonObject.getString("latitude")), Double.parseDouble(jsonObject.getString("longitude")));
            latLngList.add(latLng);
        }
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(latLngList)
                .fillColor(Color.parseColor("#88FAF603"))
                .stroke(new Stroke(10, Color.RED));
        park_index_map.getMap().addOverlay(mPolygonOptions);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngList) {
            builder.include(latLng);
        }
        LatLngBounds build = builder.build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngBounds(build);
        park_index_map.getMap().animateMapStatus(mapStatusUpdate);
    }

    @UiThread
    protected void loadSensor() {
        if (sensorEntities == null)
            return;
        for (CipsmSensorEntity sensorEntity : sensorEntities) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("entity", sensorEntity);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Double.parseDouble(sensorEntity.getSensorLatitude()), Double.parseDouble(sensorEntity.getSensorLongitude())));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.sensor_position));
            markerOptions.extraInfo(bundle);
            park_index_map.getMap().addOverlay(markerOptions);
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
