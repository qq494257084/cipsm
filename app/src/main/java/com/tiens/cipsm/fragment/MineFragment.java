package com.tiens.cipsm.fragment;

import android.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmCompanyEntity;
import com.tiens.cipsm.entity.CipsmUserEntity;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/14 17:36
 */

@EFragment(R.layout.mine_fragment)
public class MineFragment extends Fragment {
    private final String CLASSNAME = getClass().getName();

    @ViewById
    TextView mine_phone_et;
    @ViewById
    EditText mine_nick_et;
    @ViewById
    RadioGroup mine_rg;
    @ViewById
    RadioButton mine_male;
    @ViewById
    RadioButton mine_female;
    @ViewById
    EditText mine_age_et;
    @ViewById
    EditText mine_card_et;
    @ViewById
    TextView mine_enterprise_et;
    @ViewById
    EditText mine_address_et;
    @ViewById
    TextView mine_confirm;

    @AfterViews
    protected void loadInfo() {
        CipsmUserEntity userManagerEntity = MyStaticVariable.userManagerEntity;
        CipsmCompanyEntity cipsmCompanyEntity = MyStaticVariable.cipsmCompanyEntity;
        if (userManagerEntity == null) {
            toast("获取用户数据失败，请重新登录");
            Logger.d(CLASSNAME + "\t获取用户数据失败，请重新登录");
            return;
        }
        if (cipsmCompanyEntity == null) {
            toast("获取企业数据失败，请重新登录");
            Logger.d(CLASSNAME + "\t获取企业数据失败，请重新登录");
            return;
        }
        mine_phone_et.setText(userManagerEntity.getUsername());
        mine_nick_et.setText(userManagerEntity.getNick());
        Integer age = userManagerEntity.getAge();
        mine_age_et.setText(age == null ? String.valueOf(0) : String.valueOf(age));
        mine_card_et.setText(userManagerEntity.getIdCard());
        mine_enterprise_et.setText(cipsmCompanyEntity.getCompanyName());
        mine_address_et.setText(userManagerEntity.getUserAddress());
        Integer gender = userManagerEntity.getGender();
        if (gender == 0)
            mine_male.setChecked(true);
        else if (gender == 1)
            mine_female.setChecked(true);
    }

    @Click(R.id.mine_confirm)
    protected void ok(View view) {
        CipsmUserEntity userManagerEntity = MyStaticVariable.userManagerEntity;
        if (userManagerEntity == null) {
            toast("获取用户数据失败，请重新登录");
            Logger.d(CLASSNAME + "\t获取用户数据失败，请重新登录");
            return;
        }
        String nick = mine_nick_et.getText().toString();
        String age = mine_age_et.getText().toString();
        String idCard = mine_card_et.getText().toString();
        String address = mine_address_et.getText().toString();
        if (MyUtils.isEmpty(nick)) {
            Logger.d(CLASSNAME + "\t昵称不能为空");
            toast("昵称不能为空");
            return;
        }
        if (MyUtils.isEmpty(age)) {
            age = "0";
        } else if (!age.matches("[\\d]{1,3}")) {
            Logger.d(CLASSNAME + "\t年龄输入不合法");
            toast("年龄输入不合法");
            return;
        }
        if (MyUtils.isEmpty(idCard)) {
            idCard = "";
        } else if (!idCard.matches("[\\d]{18}")) {
            Logger.d(CLASSNAME + "\t身份证号输入不合法");
            toast("身份证号输入不合法");
            return;
        }
        if (MyUtils.isEmpty(address)) {
            address = "";
        }
        userManagerEntity.setNick(nick);
        userManagerEntity.setAge(Integer.parseInt(age));
        userManagerEntity.setIdCard(idCard);
        userManagerEntity.setUserAddress(address);
        update(userManagerEntity);
    }

    @Background
    protected void update(CipsmUserEntity userManagerEntity) {
        try {
            String param = "cipsmUser=" + URLEncoder.encode(JSON.toJSONString(userManagerEntity), "UTF-8");
            HttpURLConnection httpURLConnection = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmuser/update", "POST", param);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                Logger.i(CLASSNAME + "\t更新个人信息成功");
                toast("更新个人信息成功");
            } else {
                Logger.e(CLASSNAME + "更新个人信息失败");
                toast("更新个人信息失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(CLASSNAME + "更新个人信息失败。异常信息：" + e.getMessage());
            toast("更新个人信息失败");
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
