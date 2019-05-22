package com.tiens.cipsm.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.tiens.cipsm.R;
import com.tiens.cipsm.fragment.MineFragment_;
import com.tiens.cipsm.fragment.ParkIndexFragment_;
import com.tiens.cipsm.fragment.ParkInfoFragment_;
import com.tiens.cipsm.fragment.SensorFragment_;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/16 9:26
 */

@EActivity(R.layout.park_activity)
public class ParkActivity extends Activity {

    private final String CLASSNAME = getClass().getName();


    @ViewById
    TextView system_index;
    @ViewById
    TextView park_point;
    @ViewById
    TextView park_info;
    @ViewById
    TextView system_mine;


    @AfterViews
    protected void init() {
        MyUtils.setStatusBarFullTransparent(this);
        MyUtils.BaiduMapInitialize(getApplicationContext());
    }

    @Click({R.id.system_index, R.id.park_point, R.id.park_info, R.id.system_mine})
    protected void switchFragment(View view) {
        switch (view.getId()) {
            case R.id.system_index: {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.park_ft, new ParkIndexFragment_());
                fragmentTransaction.commit();
                system_index.setTextColor(Color.parseColor("#1296db"));
                park_point.setTextColor(Color.parseColor("#8a8a8a"));
                park_info.setTextColor(Color.parseColor("#8a8a8a"));
                system_mine.setTextColor(Color.parseColor("#8a8a8a"));
                Drawable index_on = getResources().getDrawable(R.drawable.index_on);
                index_on.setBounds(0, 0, index_on.getMinimumWidth(), index_on.getMinimumHeight());
                Drawable park_off = getResources().getDrawable(R.drawable.park_point_off);
                park_off.setBounds(0, 0, park_off.getMinimumWidth(), park_off.getMinimumHeight());
                Drawable info_off = getResources().getDrawable(R.drawable.park_info_off);
                info_off.setBounds(0, 0, info_off.getMinimumWidth(), info_off.getMinimumHeight());
                Drawable mine_off = getResources().getDrawable(R.drawable.mine_off);
                mine_off.setBounds(0, 0, mine_off.getMinimumWidth(), mine_off.getMinimumHeight());
                system_index.setCompoundDrawables(null, index_on, null, null);
                park_point.setCompoundDrawables(null, park_off, null, null);
                park_info.setCompoundDrawables(null, info_off, null, null);
                system_mine.setCompoundDrawables(null, mine_off, null, null);
            }
            break;
            case R.id.park_point: {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.park_ft, new SensorFragment_());
                fragmentTransaction.commit();
                system_index.setTextColor(Color.parseColor("#8a8a8a"));
                park_point.setTextColor(Color.parseColor("#1296db"));
                park_info.setTextColor(Color.parseColor("#8a8a8a"));
                system_mine.setTextColor(Color.parseColor("#8a8a8a"));
                Drawable index_on = getResources().getDrawable(R.drawable.index_off);
                index_on.setBounds(0, 0, index_on.getMinimumWidth(), index_on.getMinimumHeight());
                Drawable park_off = getResources().getDrawable(R.drawable.park_point_on);
                park_off.setBounds(0, 0, park_off.getMinimumWidth(), park_off.getMinimumHeight());
                Drawable info_off = getResources().getDrawable(R.drawable.park_info_off);
                info_off.setBounds(0, 0, info_off.getMinimumWidth(), info_off.getMinimumHeight());
                Drawable mine_off = getResources().getDrawable(R.drawable.mine_off);
                mine_off.setBounds(0, 0, mine_off.getMinimumWidth(), mine_off.getMinimumHeight());
                system_index.setCompoundDrawables(null, index_on, null, null);
                park_point.setCompoundDrawables(null, park_off, null, null);
                park_info.setCompoundDrawables(null, info_off, null, null);
                system_mine.setCompoundDrawables(null, mine_off, null, null);
            }
            break;
            case R.id.park_info: {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.park_ft, new ParkInfoFragment_());
                fragmentTransaction.commit();
                system_index.setTextColor(Color.parseColor("#8a8a8a"));
                park_point.setTextColor(Color.parseColor("#8a8a8a"));
                park_info.setTextColor(Color.parseColor("#1296db"));
                system_mine.setTextColor(Color.parseColor("#8a8a8a"));
                Drawable index_on = getResources().getDrawable(R.drawable.index_off);
                index_on.setBounds(0, 0, index_on.getMinimumWidth(), index_on.getMinimumHeight());
                Drawable park_off = getResources().getDrawable(R.drawable.park_point_off);
                park_off.setBounds(0, 0, park_off.getMinimumWidth(), park_off.getMinimumHeight());
                Drawable info_off = getResources().getDrawable(R.drawable.park_info_on);
                info_off.setBounds(0, 0, info_off.getMinimumWidth(), info_off.getMinimumHeight());
                Drawable mine_off = getResources().getDrawable(R.drawable.mine_off);
                mine_off.setBounds(0, 0, mine_off.getMinimumWidth(), mine_off.getMinimumHeight());
                system_index.setCompoundDrawables(null, index_on, null, null);
                park_point.setCompoundDrawables(null, park_off, null, null);
                park_info.setCompoundDrawables(null, info_off, null, null);
                system_mine.setCompoundDrawables(null, mine_off, null, null);
            }
            break;
            case R.id.system_mine: {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.park_ft, new MineFragment_());
                fragmentTransaction.commit();
                system_index.setTextColor(Color.parseColor("#8a8a8a"));
                park_point.setTextColor(Color.parseColor("#8a8a8a"));
                park_info.setTextColor(Color.parseColor("#8a8a8a"));
                system_mine.setTextColor(Color.parseColor("#1296db"));
                Drawable index_on = getResources().getDrawable(R.drawable.index_off);
                index_on.setBounds(0, 0, index_on.getMinimumWidth(), index_on.getMinimumHeight());
                Drawable park_off = getResources().getDrawable(R.drawable.park_point_off);
                park_off.setBounds(0, 0, park_off.getMinimumWidth(), park_off.getMinimumHeight());
                Drawable info_off = getResources().getDrawable(R.drawable.park_info_off);
                info_off.setBounds(0, 0, info_off.getMinimumWidth(), info_off.getMinimumHeight());
                Drawable mine_off = getResources().getDrawable(R.drawable.mine_on);
                mine_off.setBounds(0, 0, mine_off.getMinimumWidth(), mine_off.getMinimumHeight());
                system_index.setCompoundDrawables(null, index_on, null, null);
                park_point.setCompoundDrawables(null, park_off, null, null);
                park_info.setCompoundDrawables(null, info_off, null, null);
                system_mine.setCompoundDrawables(null, mine_off, null, null);
            }
            break;
        }
    }

}
