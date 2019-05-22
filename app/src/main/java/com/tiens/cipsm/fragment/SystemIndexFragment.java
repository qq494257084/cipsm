package com.tiens.cipsm.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmCompanyEntity;
import com.tiens.cipsm.entity.CipsmParkEntity;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/13 16:31
 */
@EFragment(R.layout.system_index_fragment)
public class SystemIndexFragment extends Fragment {
    private final String CLASSNAME = getClass().getName();
    private static List<LatLng> latLngList = new ArrayList<>();
    private static boolean isEnclosed = true;
    private List<CipsmParkEntity> listByURLWithParam;
    private LatLng centerLatLng = null;

    @ViewById
    TextureMapView system_map;
    @ViewById
    ImageButton system_index_toggle;
    @ViewById
    TextView system_index_toggle_text;
    @ViewById
    Button system_index_clear_all;
    @ViewById
    Button system_index_clear_last;
    @ViewById
    Button system_index_enclosed;
    @ViewById
    Button system_index_add_park;
    @ViewById
    Spinner system_index_spinner;
    @ViewById
    EditText system_park_name;
    @ViewById
    EditText system_park_contact;
    @ViewById
    EditText system_park_contact_phone;

    @AfterViews
    public void init() {
        latLngList.clear();
        isEnclosed = false;
        system_map.getMap().setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (isEnclosed) {
                    toast("图形已封闭，请清除所有点");
                }
                addPoint(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        new Thread(this::loadPark).start();
    }

    @Click(R.id.system_index_toggle)
    protected void switchToggle(View view) {
        String text = system_index_toggle_text.getText().toString();
        if (text.equals("已开启")) {
            system_index_toggle.setBackgroundResource(R.drawable.toggle_off);
            system_index_toggle_text.setText("已关闭");
        } else {
            system_index_toggle.setBackgroundResource(R.drawable.toggle_on);
            system_index_toggle_text.setText("已开启");
        }
    }

    @Click(R.id.system_index_clear_all)
    protected void clearAll(View view) {
        latLngList.clear();
        isEnclosed = false;
        centerLatLng = null;
        system_map.getMap().clear();
        system_park_name.setText("");
        system_park_contact.setText("");
        system_park_contact_phone.setText("");
    }

    @Click(R.id.system_index_enclosed)
    protected void enclosed(View view) {
        system_map.getMap().clear();
        centerLatLng = null;
        if (latLngList.size() < 3) {
            toast("点小于三个");
            return;
        }
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(latLngList)
                .fillColor(Color.parseColor("#88FAF603"))
                .stroke(new Stroke(10, Color.RED));
        system_map.getMap().addOverlay(mPolygonOptions);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngList) {
            builder.include(latLng);
        }
        LatLngBounds build = builder.build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngBounds(build);
        system_map.getMap().animateMapStatus(mapStatusUpdate);
        centerLatLng = system_map.getMap().getMapStatus().target;
        isEnclosed = true;
    }

    @Click(R.id.system_index_clear_last)
    protected void clearLast(View view) {
        system_map.getMap().clear();
        centerLatLng = null;
        if (latLngList.size() == 0) {
            isEnclosed = false;
            return;
        }
        if (latLngList.size() == 1) {
            latLngList.remove(latLngList.size() - 1);
            isEnclosed = false;
            return;
        }
        if (latLngList.size() == 2) {
            latLngList.remove(latLngList.size() - 1);
            MarkerOptions markerOptions = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
                    .position(latLngList.get(0));
            system_map.getMap().addOverlay(markerOptions);
        } else {
            latLngList.remove(latLngList.size() - 1);
            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(10)
                    .color(Color.RED)
                    .points(latLngList);
            system_map.getMap().addOverlay(polylineOptions);
        }
        isEnclosed = false;
    }

    @Click(R.id.system_index_add_park)
    protected void addOrUpdatePark(View view) {
        addOrUpdateThread();
    }

    @AfterViews
    protected void spinnerClick() {
        system_index_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearAll(null);
                if (position == 0) {
                    system_index_add_park.setText("添加园区");
                } else {
                    system_index_add_park.setText("修改园区");
                    CipsmParkEntity cipsmParkEntity = listByURLWithParam.get(position - 1);
                    showPark(cipsmParkEntity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                system_index_spinner.setSelection(0);
            }
        });

    }

    private void addPoint(LatLng latLng) {
        latLngList.add(latLng);
        if (latLngList.size() < 2) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
                    .position(latLng);
            system_map.getMap().addOverlay(markerOptions);
        } else {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(10)
                    .color(Color.RED)
                    .points(latLngList);
            system_map.getMap().addOverlay(polylineOptions);
        }
    }

    @Background
    protected void loadPark() {
        CipsmCompanyEntity cipsmCompanyEntity = MyStaticVariable.cipsmCompanyEntity;
        if (cipsmCompanyEntity == null) {
            toast("获取所属企业失败，请重新登录");
            Logger.d("获取所属企业失败，请重新登录");
            return;
        }
        String param = "infoByEnterpriseId=" + cipsmCompanyEntity.getCompanyUuid();
        listByURLWithParam = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmpark/infoByEnterpriseId?" + param, "GET", "", CipsmParkEntity.class);
        if (listByURLWithParam == null) {
            Logger.i(CLASSNAME + "\t获取企业信息失败，参数信息：" + param);
        }
        String[] items = new String[listByURLWithParam.size() + 1];
        items[0] = "新建园区";
        for (int i = 0; i < listByURLWithParam.size(); i++) {
            items[i + 1] = listByURLWithParam.get(i).getParkName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        setSpinnerAdapter(arrayAdapter);
    }

    @Background
    protected void addOrUpdateThread() {
        String addOrUpdate = system_index_add_park.getText().toString();
        try {
            String parkName = system_park_name.getText().toString();
            String contactName = system_park_contact.getText().toString();
            String contactPhoneName = system_park_contact_phone.getText().toString();
            String type = system_index_toggle_text.getText().toString();
            if (!isEnclosed || latLngList.isEmpty()) {
                toast("没有封闭图形");
                return;
            }
            if (MyUtils.isEmpty(parkName)) {
                toast("园区名字不能为空");
                return;
            }
            if (MyUtils.isEmpty(contactName)) {
                toast("园区联系人不能为空");
                return;
            }
            if (MyUtils.isEmpty(contactPhoneName)) {
                toast("园区联系人电话不能为空");
                return;
            }
            int position = system_index_spinner.getSelectedItemPosition();
            String jsonString = JSON.toJSONString(latLngList);
            CipsmParkEntity cipsmParkEntity;
            if (position == 0) {
                cipsmParkEntity = new CipsmParkEntity();
                cipsmParkEntity.setParkCreateTime(new Date());
            } else cipsmParkEntity = listByURLWithParam.get(position - 1);
            cipsmParkEntity.setParkName(parkName);
            cipsmParkEntity.setParkContact(contactName);
            cipsmParkEntity.setParkContactPhone(contactPhoneName);
            cipsmParkEntity.setParkShape(jsonString);
            cipsmParkEntity.setParkEnterprise(MyStaticVariable.cipsmCompanyEntity.getCompanyUuid());
            cipsmParkEntity.setParkLatitude(String.valueOf(centerLatLng.latitude));
            cipsmParkEntity.setParkLongitude(String.valueOf(centerLatLng.longitude));
            cipsmParkEntity.setParkUpdateTime(new Date());
            if (type.equals("已开启")) {
                cipsmParkEntity.setParkStatus(1);
            } else if (type.equals("已关闭")) {
                cipsmParkEntity.setParkStatus(0);
            }
            BigDecimal bigDecimal = MyUtils.caculateArea(latLngList);
            cipsmParkEntity.setParkArea(bigDecimal.floatValue());
            String param = "cipsmPark=" + URLEncoder.encode(JSON.toJSONString(cipsmParkEntity), "UTF-8");
            HttpURLConnection httpURLConnection = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmpark/update", "POST", param);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                Logger.i(CLASSNAME + "\t" + addOrUpdate + "成功");
                toast(addOrUpdate + "成功");
                loadPark();
            } else {
                Logger.e(CLASSNAME + "\t" + addOrUpdate + "失败");
                toast(addOrUpdate + "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(CLASSNAME + "\t" + addOrUpdate + "异常，异常信息：" + e.getMessage());
            toast(addOrUpdate + "异常");
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

    @UiThread
    protected void setSpinnerAdapter(ArrayAdapter<String> arrayAdapter) {
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        system_index_spinner.setAdapter(arrayAdapter);
    }

    private void showPark(CipsmParkEntity cipsmParkEntity) {
        system_park_name.setText(cipsmParkEntity.getParkName());
        system_park_contact.setText(cipsmParkEntity.getParkContact());
        system_park_contact_phone.setText(cipsmParkEntity.getParkContactPhone());
        Integer parkStatus = cipsmParkEntity.getParkStatus();
        if (parkStatus == 0) {
            system_index_toggle.setBackgroundResource(R.drawable.toggle_off);
            system_index_toggle_text.setText("已关闭");
        } else {
            system_index_toggle.setBackgroundResource(R.drawable.toggle_on);
            system_index_toggle_text.setText("已开启");
        }
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
        system_map.getMap().addOverlay(mPolygonOptions);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngList) {
            builder.include(latLng);
        }
        LatLngBounds build = builder.build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngBounds(build);
        system_map.getMap().animateMapStatus(mapStatusUpdate);
    }
}
