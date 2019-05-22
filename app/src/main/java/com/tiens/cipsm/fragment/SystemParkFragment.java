package com.tiens.cipsm.fragment;

import android.app.Fragment;
import android.widget.ListView;

import com.orhanobut.logger.Logger;
import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmCompanyEntity;
import com.tiens.cipsm.entity.CipsmParkEntity;
import com.tiens.cipsm.myAdapter.SystemParkAdapter;
import com.tiens.cipsm.utils.MyStaticVariable;
import com.tiens.cipsm.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/14 9:47
 */


@EFragment(R.layout.system_park_fragment)
public class SystemParkFragment extends Fragment {
    private final String CLASSNAME = getClass().getName();
    @ViewById
    ListView system_park_lv;
    private List<CipsmParkEntity> cipsmParkEntityList;

    @AfterViews
    protected void initListView() {
        loadPark();
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
        cipsmParkEntityList = MyUtils.getListByURLWithParam(MyStaticVariable.URL + "cipsmpark/infoByEnterpriseId?" + param, "GET", "", CipsmParkEntity.class);
        if (cipsmParkEntityList == null) {
            Logger.i(CLASSNAME + "\t获取企业信息失败，参数信息：" + param);
        }
        setAdapter();
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
    protected void setAdapter() {
        system_park_lv.setAdapter(new SystemParkAdapter(getActivity(), cipsmParkEntityList));

    }
}
