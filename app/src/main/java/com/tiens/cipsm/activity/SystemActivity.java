package com.tiens.cipsm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.tiens.cipsm.R;
import com.tiens.cipsm.fragment.MineFragment_;
import com.tiens.cipsm.fragment.SystemIndexFragment_;
import com.tiens.cipsm.fragment.SystemParkFragment_;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/13 15:51
 */

@SuppressLint("Registered")
@EActivity(R.layout.system_activity)
public class SystemActivity extends Activity {
    @ViewById
    TextView system_index;
    @ViewById
    TextView system_park;
    @ViewById
    TextView system_mine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyUtils.setStatusBarFullTransparent(this);
        MyUtils.BaiduMapInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void indexClick() {
        system_index.callOnClick();
    }

    @Click({R.id.system_index, R.id.system_park, R.id.system_mine})
    protected void switchFragment(View view) {
        switch (view.getId()) {
            case R.id.system_index: {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.system_ft, new SystemIndexFragment_());
                fragmentTransaction.commit();
                system_index.setTextColor(Color.parseColor("#1296db"));
                system_park.setTextColor(Color.parseColor("#8a8a8a"));
                system_mine.setTextColor(Color.parseColor("#8a8a8a"));
                Drawable index_on = getResources().getDrawable(R.drawable.index_on);
                index_on.setBounds(0, 0, index_on.getMinimumWidth(), index_on.getMinimumHeight());
                Drawable park_off = getResources().getDrawable(R.drawable.park_off);
                park_off.setBounds(0, 0, park_off.getMinimumWidth(), park_off.getMinimumHeight());
                Drawable mine_off = getResources().getDrawable(R.drawable.mine_off);
                mine_off.setBounds(0, 0, mine_off.getMinimumWidth(), mine_off.getMinimumHeight());
                system_index.setCompoundDrawables(null, index_on, null, null);
                system_park.setCompoundDrawables(null, park_off, null, null);
                system_mine.setCompoundDrawables(null, mine_off, null, null);
            }
            break;
            case R.id.system_park: {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.system_ft, new SystemParkFragment_());
                fragmentTransaction.commit();
                system_index.setTextColor(Color.parseColor("#8a8a8a"));
                system_park.setTextColor(Color.parseColor("#1296db"));
                system_mine.setTextColor(Color.parseColor("#8a8a8a"));
                Drawable index_on = getResources().getDrawable(R.drawable.index_off);
                index_on.setBounds(0, 0, index_on.getMinimumWidth(), index_on.getMinimumHeight());
                Drawable park_off = getResources().getDrawable(R.drawable.park_on);
                park_off.setBounds(0, 0, park_off.getMinimumWidth(), park_off.getMinimumHeight());
                Drawable mine_off = getResources().getDrawable(R.drawable.mine_off);
                mine_off.setBounds(0, 0, mine_off.getMinimumWidth(), mine_off.getMinimumHeight());
                system_index.setCompoundDrawables(null, index_on, null, null);
                system_park.setCompoundDrawables(null, park_off, null, null);
                system_mine.setCompoundDrawables(null, mine_off, null, null);
            }
            break;
            case R.id.system_mine: {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.system_ft, new MineFragment_());
                fragmentTransaction.commit();
                system_index.setTextColor(Color.parseColor("#8a8a8a"));
                system_park.setTextColor(Color.parseColor("#8a8a8a"));
                system_mine.setTextColor(Color.parseColor("#1296db"));
                Drawable index_on = getResources().getDrawable(R.drawable.index_off);
                index_on.setBounds(0, 0, index_on.getMinimumWidth(), index_on.getMinimumHeight());
                Drawable park_off = getResources().getDrawable(R.drawable.park_off);
                park_off.setBounds(0, 0, park_off.getMinimumWidth(), park_off.getMinimumHeight());
                Drawable mine_off = getResources().getDrawable(R.drawable.mine_on);
                mine_off.setBounds(0, 0, mine_off.getMinimumWidth(), mine_off.getMinimumHeight());
                system_index.setCompoundDrawables(null, index_on, null, null);
                system_park.setCompoundDrawables(null, park_off, null, null);
                system_mine.setCompoundDrawables(null, mine_off, null, null);
            }
            break;
        }
    }
}
