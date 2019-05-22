package com.tiens.cipsm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmUserEntity;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/13 13:13
 */

@EActivity(R.layout.forget_password_activity)
public class ForgetPasswordActivity extends Activity {
    private final String CLASSNAME = getClass().getName();
    @ViewById
    EditText forget_password_phone_et;
    @ViewById
    EditText forget_password_password_et;
    @ViewById
    EditText forget_password_confirm_password_et;
    @ViewById
    EditText forget__confirm_code_et;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        MyUtils.setStatusBarFullTransparent(this);
    }

    @Click(R.id.forget_password_back)
    protected void back(View view) {
        finish();
    }

    @Click(R.id.register_code_tv)
    protected void sendCode(View view) {
        toast("发送验证码成功");
    }

    @Click(R.id.register_bt)
    protected void ok(View view) {
        if (MyUtils.isEmpty(forget_password_phone_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t手机号码为空");
            toast("手机号不能为空");
            return;
        }
        if (MyUtils.isEmpty(forget_password_password_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t新密码称为空");
            toast("新密码不能为空");
            return;
        }
        if (MyUtils.isEmpty(forget_password_confirm_password_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t确认密码为空");
            toast("确认密码不能为空");
            return;
        }
        if (MyUtils.isEmpty(forget__confirm_code_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t验证码为空");
            toast("手机验证码不能为空");
            return;
        }
        if (!forget_password_password_et.getText().toString().equals(forget_password_confirm_password_et.getText().toString())) {
            Logger.d(CLASSNAME + "\t两次密码不一致。第一次密码=" + forget_password_password_et.getText().toString() + "\t第二次密码：" + forget_password_confirm_password_et.getText().toString());
            toast("两次密码输入不一致");
            return;
        }
        update();
    }

    @Background
    protected void update() {
        try {
            String param = "userId=" + forget_password_phone_et.getText().toString();
            List<CipsmUserEntity> listByURLWithParam = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmuser/infoByUsername?" + param, "GET", "", CipsmUserEntity.class);
            if (listByURLWithParam.isEmpty()) {
                toast("手机号不存在");
                return;
            }
            CipsmUserEntity cipsmUserEntity = listByURLWithParam.get(0);
            String salt = cipsmUserEntity.getSalt();
            cipsmUserEntity.setPassword(MyUtils.md5((forget_password_password_et.getText().toString() + salt).getBytes()));
            cipsmUserEntity.setUserUpdateTime(new Date());
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
            String param1 = "cipsmUser=" + URLEncoder.encode(JSON.toJSONString(cipsmUserEntity), "UTF-8");
            HttpURLConnection httpURLConnectionWithParam = MyUtils.getHttpURLConnectionWithParam(MyStaticVariable.URL + "cipsmuser/update", "POST", param1);
            httpURLConnectionWithParam.connect();
            int responseCode1 = httpURLConnectionWithParam.getResponseCode();
            if (responseCode1 == 200) {
                Logger.i(CLASSNAME + "\t忘记密码修改成功");
                toast("修改成功");
            } else {
                Logger.i(CLASSNAME + "\t错误代码：" + responseCode1 + "忘记密码修改失败，用户：" + forget_password_phone_et.getText().toString() + "\t新密码：" + forget_password_password_et.getText().toString());
                toast("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(CLASSNAME + "\t" + e.getMessage());
            toast("修改失败");
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
