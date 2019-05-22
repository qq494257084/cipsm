package com.tiens.cipsm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmCompanyEntity;
import com.tiens.cipsm.entity.CipsmUserEntity;
import com.tiens.cipsm.entity.CipsmUserParkEntity;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/12 23:23
 */

@SuppressLint("Registered")
@EActivity(R.layout.register_activity)
@WindowFeature({Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS})
public class RegisterActivity extends Activity {
    private final String CLASSNAME = getClass().getName();
    @ViewById
    EditText register_phone_et;
    @ViewById
    EditText register_nick_et;
    @ViewById
    EditText register_password_et;
    @ViewById
    EditText register_confirm_password_et;
    @ViewById
    EditText register_confirm_code_et;
    @ViewById
    EditText register_company_code_et;
    @ViewById
    EditText register_company_hash_et;
    @ViewById
    RadioButton register_male;
    @ViewById
    RadioButton register_female;
    @ViewById
    RadioButton register_park;
    @ViewById
    RadioButton register_manager;
    @ViewById
    LinearLayout register_company_hash_all;
    @ViewById
    RadioGroup register_type_rg;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        MyUtils.setStatusBarFullTransparent(this);
        super.onPostCreate(savedInstanceState);
        register_company_hash_all.setVisibility(View.GONE);
        register_company_code_et.setText("07f9d186f6dc4fb1885d2111a877cfee");
        register_company_hash_et.setText("c92e9334d27641b0afc3f3edc59446ff");
        register_type_rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.register_park:
                    register_company_hash_all.setVisibility(View.GONE);
                    break;
                case R.id.register_manager:
                    register_company_hash_all.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    @Click(R.id.register_code_tv)
    protected void sendCode(View view) {
        toast("发送验证码成功");
    }

    @Click(R.id.register_back)
    protected void back(View view) {
        finish();
    }

    @Click(R.id.register_bt)
    protected void register(View view) {
        if (MyUtils.isEmpty(register_phone_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t手机号码为空");
            toast("手机号不能为空");
            return;
        }
        if (MyUtils.isEmpty(register_nick_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t昵称为空");
            toast("昵称不能为空");
            return;
        }
        if (MyUtils.isEmpty(register_password_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t密码为空");
            toast("密码不能为空");
            return;
        }
        if (MyUtils.isEmpty(register_confirm_password_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t确认密码为空");
            toast("确认密码不能为空");
            return;
        }
        if (MyUtils.isEmpty(register_company_code_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t公司编号为空");
            toast("公司编号不能为空");
            return;
        }
        if (register_manager.isChecked()) {
            if (MyUtils.isEmpty(register_company_hash_et.getText().toString())) {
                Logger.d(CLASSNAME + "\t公司哈希值为空");
                toast("公司哈希值不能为空");
                return;
            }
        }
        if (MyUtils.isEmpty(register_confirm_code_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t手机验证码为空");
            toast("手机验证码不能为空");
            return;
        }
        if (!register_password_et.getText().toString().equals(register_confirm_password_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t两次密码不一致。第一次密码=" + register_password_et.getText().toString() + "\t第二次密码：" + register_confirm_password_et.getText().toString());
            toast("两次密码输入不一致");
            return;
        }
        registerThread();
    }

    @Background
    protected void registerThread() {
        try {
            CipsmUserEntity userEntity = new CipsmUserEntity();
            if (register_male.isChecked())
                userEntity.setGender(0);
            else if (register_female.isChecked())
                userEntity.setGender(1);
            if (register_park.isChecked())
                userEntity.setUserType(1);
            else if (register_manager.isChecked())
                userEntity.setUserType(0);
            userEntity.setSalt(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
            userEntity.setUsername(register_phone_et.getText().toString());
            userEntity.setPassword(MyUtils.md5((register_password_et.getText().toString() + userEntity.getSalt()).getBytes()));
            userEntity.setNick(register_nick_et.getText().toString());
            userEntity.setUserCreateTime(new Date());
            userEntity.setUserUpdateTime(new Date());
            List<CipsmCompanyEntity> listByURLWithParam = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmcompany/list", "GET", "", CipsmCompanyEntity.class);
            boolean isCode = false;
            boolean isHash = false;
            String companyId = null;
            if (!listByURLWithParam.isEmpty()) {
                for (CipsmCompanyEntity cipsmCompanyEntity : listByURLWithParam) {
                    if (cipsmCompanyEntity.getCompanyCode().equals(register_company_code_et.getText().toString())) {
                        isCode = true;
                        userEntity.setUserEnterprise(cipsmCompanyEntity.getCompanyName());
                        companyId = cipsmCompanyEntity.getCompanyUuid();
                        break;
                    }
                    if (register_manager.isChecked()) {
                        if (cipsmCompanyEntity.getCompanyHash().equals(register_company_hash_et.getText().toString())) {
                            isHash = true;
                            break;
                        }
                    }
                }
            }
            if (!isCode) {
                return;
            }
            if (register_manager.isChecked()) {
                if (!isHash) {
                    return;
                }
            }
            userEntity.setUserEnterprise(companyId);
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
            String param = "cipsmUser=" + URLEncoder.encode(JSON.toJSONString(userEntity), "UTF-8");
            HttpURLConnection httpURLConnection = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmuser/save", "POST", param);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
               /* InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                Map<String, Object> map = JSON.parseObject(stringBuilder.toString());
                String newId = map.get("newId").toString();
                CipsmUserParkEntity cipsmUserParkEntity = new CipsmUserParkEntity();
                cipsmUserParkEntity.setUserParkParkId(companyId);
                cipsmUserParkEntity.setUserParkUserId(newId);
                JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
                String param1 = "cipsmUserPark=" + URLEncoder.encode(JSON.toJSONString(cipsmUserParkEntity), "UTF-8");
                HttpURLConnection httpURLConnection1 = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmuserpark/save", "POST", param1);
                httpURLConnection1.connect();
                int responseCode1 = httpURLConnection1.getResponseCode();
                if (responseCode1 == 200) {
                    toast("注册成功");
                    Logger.e(CLASSNAME + "\t关联表注册成功");
                } else {
                    toast("注册失败", 1);
                    Logger.e(CLASSNAME + "\t关联表注册失败");
                }*/
                toast("注册成功");
                Logger.e(CLASSNAME + "\t关联表注册成功");

            } else {
                toast("注册失败", 1);
                Logger.e(CLASSNAME + "\t注册失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            toast("注册失败", 1);
            Logger.e(CLASSNAME + "\t注册失败");
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
