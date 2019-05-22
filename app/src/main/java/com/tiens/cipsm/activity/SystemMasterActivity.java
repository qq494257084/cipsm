package com.tiens.cipsm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmCompanyEntity;
import com.tiens.cipsm.entity.CipsmParkEntity;
import com.tiens.cipsm.entity.CipsmUserEntity;
import com.tiens.cipsm.entity.CipsmUserParkEntity;
import com.tiens.cipsm.myAdapter.SystemMasterAdapter;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/14 10:45
 */

@SuppressLint("Registered")
@EActivity(R.layout.system_master_activity)
public class SystemMasterActivity extends Activity {
    private final String CLASSNAME = getClass().getName();
    private List<CipsmUserEntity> list;
    @ViewById
    ListView system_master__lv;
    private CipsmParkEntity entity;

    @AfterViews
    protected void init() {
        MyUtils.setStatusBarFullTransparent(this);
        entity = (CipsmParkEntity) getIntent().getSerializableExtra("entity");
        getUser(entity);
    }

    @Click(R.id.system_master_back)
    protected void back(View view) {
        finish();
    }

    @Click(R.id.system_master_ok)
    protected void ok(View view) {
        List<CipsmUserParkEntity> addList = new ArrayList<>();
        List<String> removeList = new ArrayList<>();
        for (CipsmUserEntity cipsmUserEntity : list) {
            removeList.add(cipsmUserEntity.getUserId());
            if (cipsmUserEntity.getParkFlag() == 1) {
                CipsmUserParkEntity cipsmUserParkEntity = new CipsmUserParkEntity();
                cipsmUserParkEntity.setUserParkUserId(cipsmUserEntity.getUserId());
                cipsmUserParkEntity.setUserParkParkId(entity.getParkId());
                addList.add(cipsmUserParkEntity);
            }
        }
        String[] stringList = new String[removeList.size()];
        for (int i = 0; i < removeList.size(); i++) {
            stringList[i] = removeList.get(i);
        }
        updateMaster(addList, stringList);
    }

    @Background
    protected void getUser(CipsmParkEntity entity) {
        CipsmCompanyEntity cipsmCompanyEntity = MyStaticVariable.cipsmCompanyEntity;
        if (cipsmCompanyEntity == null) {
            toast("获取所属企业失败，请重新登录");
            Logger.d("获取所属企业失败，请重新登录");
            return;
        }
        String param1 = "enterpriseId=" + cipsmCompanyEntity.getCompanyUuid();
        List<CipsmUserEntity> cipsmUserEntities = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmuser/infoByEnterprise?" + param1, "GET", "", CipsmUserEntity.class);
        if (cipsmUserEntities.isEmpty()) {
            Logger.d(CLASSNAME + "\t根据企业获取用户空数据：");
            return;
        }
        list = new ArrayList<>();
        List<CipsmUserParkEntity> cipsmUserParkEntities = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmuserpark/list", "GET", "", CipsmUserParkEntity.class);
        for (CipsmUserEntity cipsmUserEntity : cipsmUserEntities) {
            String userId = cipsmUserEntity.getUserId();
            boolean isFind = false;
            for (CipsmUserParkEntity cipsmUserParkEntity : cipsmUserParkEntities) {
                if (cipsmUserParkEntity.getUserParkUserId().equals(userId)) {
                    //-1是已管理其他园区
                    cipsmUserEntity.setParkFlag(-1);
                    if (cipsmUserParkEntity.getUserParkParkId().equals(entity.getParkId())) {
                        //1是已管理当前园区
                        cipsmUserEntity.setParkFlag(1);
                        list.add(cipsmUserEntity);
                    }
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                //0是可分配
                cipsmUserEntity.setParkFlag(0);
                list.add(cipsmUserEntity);
            }
        }
        setListAdapter();
    }

    @Background
    protected void updateMaster(List<CipsmUserParkEntity> addList, String[] stringList) {
        try {
            if (stringList != null && stringList.length != 0) {
                String param = "userParkIds=" + URLEncoder.encode(JSON.toJSONString(stringList), "UTF-8");
                HttpURLConnection httpURLConnectionWithParam = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmuserpark/delete", "POST", param);
                httpURLConnectionWithParam.connect();
                int responseCode1 = httpURLConnectionWithParam.getResponseCode();
                if (responseCode1 == 200) {
                    Logger.i(CLASSNAME + "\t删除园区管理员成功");
                    if (addList != null && !addList.isEmpty()) {
                        toast("修改园区管理员成功");
                    }
                } else {
                    Logger.i(CLASSNAME + "\t错误代码：" + responseCode1 + "删除园区管理员失败");
                    toast("修改失败");
                    return;
                }
            }
            if (addList != null && !addList.isEmpty()) {
                String param = "jsonString=" + URLEncoder.encode(JSON.toJSONString(addList), "UTF-8");
                HttpURLConnection httpURLConnectionWithParam = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmuserpark/saveAll", "POST", param);
                httpURLConnectionWithParam.connect();
                int responseCode1 = httpURLConnectionWithParam.getResponseCode();
                if (responseCode1 == 200) {
                    Logger.i(CLASSNAME + "\t修改园区管理员成功");
                    toast("修改园区管理员成功");
                } else {
                    Logger.i(CLASSNAME + "\t错误代码：" + responseCode1 + "修改园区管理员失败");
                    toast("修改失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.i(CLASSNAME + "\t修改园区管理员失败连接异常。异常信息：" + e.getMessage());
            toast("网络异常");
        }
    }

    @UiThread
    protected void setListAdapter() {
        system_master__lv.setAdapter(new SystemMasterAdapter(this, list));
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
