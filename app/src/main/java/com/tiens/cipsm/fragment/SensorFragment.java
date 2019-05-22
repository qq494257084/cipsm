package com.tiens.cipsm.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;

import com.tiens.cipsm.R;
import com.tiens.cipsm.activity.SensorManageActivity_;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/16 17:40
 */


@EFragment(R.layout.sensor_fragment)
public class SensorFragment extends Fragment {

    private final String CLASSNAME = getClass().getName();

    @Click(R.id.sensor_type)
    protected void sensorTypeClick(View view) {
        Intent intent = new Intent(getActivity(), SensorManageActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.sensor_confirm)
    protected void okClick(View view) {
        //Todo
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
