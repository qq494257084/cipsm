package com.tiens.cipsm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmCompanyEntity;
import com.tiens.cipsm.entity.CipsmParkEntity;
import com.tiens.cipsm.entity.CipsmUserEntity;
import com.tiens.cipsm.entity.CipsmUserParkEntity;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/12 15:04
 */

@SuppressLint("Registered")
@EActivity(R.layout.login_activity)
@Fullscreen
@WindowFeature({Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS})
public class LoginActivity extends Activity {
    private final String CLASSNAME = getClass().getName();
    @ViewById(R.id.login_username_et)
    protected EditText usernameET;
    @ViewById
    protected EditText login_password_et;
    @ViewById
    protected CheckBox login_remember;
    @ViewById
    protected RadioButton login_park_rb;
    @ViewById
    protected RadioButton login_system_rb;
    private SharedPreferences userInfo;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        MyUtils.setStatusBarFullTransparent(this);
        MyStaticVariable.currentContext = this;
        MyStaticVariable.applicationContext = getApplicationContext();
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        boolean isRemember = userInfo.getBoolean("isRemember", false);
        login_remember.setChecked(isRemember);
        if (isRemember) {
            usernameET.setText(userInfo.getString("username", ""));
            login_password_et.setText(userInfo.getString("password", ""));
        }
        super.onPostCreate(savedInstanceState);

    }

    @Click(R.id.login_register_tv)
    protected void register(View view) {
        Logger.i(getClass().getName() + "\t点击注册按钮");
        Intent intent = new Intent(this, RegisterActivity_.class);
        startActivity(intent);
        Logger.i(getClass().getName() + "\t启动注册界面");
    }

    @Click(R.id.login_forget_tv)
    protected void forgetPassword(View view) {
        Logger.i(getClass().getName() + "\t点击忘记密码按钮");
        Intent intent = new Intent(this, ForgetPasswordActivity_.class);
        startActivity(intent);
        Logger.i(getClass().getName() + "\t启动忘记密码界面");
    }

    @Click(R.id.login_bt)
    protected void login(View view) {
        if (MyUtils.isEmpty(usernameET.getText().toString())) {
            Logger.d(CLASSNAME + "\t手机号码为空");
            toast("手机号不能为空");
            return;
        }
        if (MyUtils.isEmpty(login_password_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t密码称为空");
            toast("密码不能为空");
            return;
        }
        loginThread();
    }

    @Background
    protected void loginThread() {
        String type = "";
        Integer typeInteger = -1;
        if (login_park_rb.isChecked()) {
            type = "园区管理员";
            typeInteger = 1;
        } else if (login_system_rb.isChecked()) {
            type = "系统管理员";
            typeInteger = 0;
        }
        String param = "userId=" + usernameET.getText().toString() + "&type=" + typeInteger;
        List<CipsmUserEntity> listByURLWithParam = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmuser/infoByUsername?" + param, "GET", "", CipsmUserEntity.class);
        if (listByURLWithParam.isEmpty()) {
            Logger.d(CLASSNAME + "\t手机号" + usernameET.getText().toString() + "不存在");
            toast("手机号或密码错误或用户不属于" + type);
            return;
        }
        CipsmUserEntity cipsmUserEntity = listByURLWithParam.get(0);
        String salt = cipsmUserEntity.getSalt();
        String password = cipsmUserEntity.getPassword();
        if (!password.equals(MyUtils.md5((login_password_et.getText().toString() + salt).getBytes()))) {
            Logger.d(CLASSNAME + "\t手机号或密码错误，手机号：" + usernameET.getText().toString());
            toast("手机号或密码错误");
            return;
        }
        String param1 = "userParkId=" + cipsmUserEntity.getUserId();
        Integer userType = cipsmUserEntity.getUserType();
        if (userType.intValue() != typeInteger.intValue()) {
            Logger.d(CLASSNAME + "\t该用户不属于" + type);
            toast("该用户不属于" + type);
            return;
        }
        List<CipsmUserParkEntity> listByURLWithParam1 = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmuserpark/infoByUserId?" + param1, "GET", "", CipsmUserParkEntity.class);
        if (listByURLWithParam1.isEmpty()) {
            Logger.d(CLASSNAME + "\t获取用户关联信息空数据：" + type);
        }
        String userParkParkId = cipsmUserEntity.getUserEnterprise();
        List<CipsmCompanyEntity> listByURLWithParam2 = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmcompany/info/" + userParkParkId, "GET", "", CipsmCompanyEntity.class);
        if (listByURLWithParam2.isEmpty()) {
            Logger.e(CLASSNAME + "\t获取关联企业数据失败。用户类别：" + type);
            return;
        }
        if (userType == 1) {
            String userId = "userParkId=" + cipsmUserEntity.getUserId();
            List<CipsmUserParkEntity> listByURLWithParam3 = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmuserpark/infoByUserId?" + userId, "GET", "", CipsmUserParkEntity.class);
            if (!listByURLWithParam3.isEmpty()) {
                CipsmUserParkEntity cipsmUserParkEntity = listByURLWithParam3.get(0);
                String parkParkId = cipsmUserParkEntity.getUserParkParkId();
                List<CipsmParkEntity> listByURLWithParam4 = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmpark/info/" + parkParkId, "GET", "", CipsmParkEntity.class);
                if (!listByURLWithParam4.isEmpty())
                    MyStaticVariable.cipsmParkEntity = listByURLWithParam4.get(0);
            }
        }
        MyStaticVariable.userManagerEntity = cipsmUserEntity;
        MyStaticVariable.listByURLWithParam1 = listByURLWithParam1;
        MyStaticVariable.cipsmCompanyEntity = listByURLWithParam2.get(0);
        SharedPreferences.Editor edit = userInfo.edit();
        edit.putBoolean("isRemember", login_remember.isChecked());
        if (login_remember.isChecked()) {
            edit.putString("username", usernameET.getText().toString());
            edit.putString("password", login_password_et.getText().toString());
        }
        boolean commit = edit.commit();
        if (commit)
            Logger.i(CLASSNAME + "\t信息提交成功");
        else Logger.i(CLASSNAME + "\t信息提交失败");
        toast("登录成功");
        Intent intent = new Intent();
        if (typeInteger == 0) {
            Logger.i(CLASSNAME + "\t跳转系统管理员");
            MyStaticVariable.loginType = 0;
            intent.setClass(LoginActivity.this, SystemActivity_.class);
            startActivity(intent);
            Logger.i(CLASSNAME + "\t跳转成功");
        } else if (typeInteger == 1) {
            //Todo
            MyStaticVariable.loginType = 1;
            Logger.i(CLASSNAME + "\t跳转系统管理员");
            intent.setClass(LoginActivity.this, ParkActivity_.class);
            startActivity(intent);
            Logger.i(CLASSNAME + "\t跳转成功");
        }
        finish();
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