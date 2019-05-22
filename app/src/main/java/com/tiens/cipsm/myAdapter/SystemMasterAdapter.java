package com.tiens.cipsm.myAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiens.cipsm.R;
import com.tiens.cipsm.entity.CipsmUserEntity;

import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/5/14 10:53
 */

public class SystemMasterAdapter extends BaseAdapter {
    private final String CLASSNAME = getClass().getName();
    private Context context;
    private List<CipsmUserEntity> list;

    public SystemMasterAdapter(Context context, List<CipsmUserEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CipsmUserEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null)
            view = View.inflate(context, R.layout.system_master_list_item, null);
        else view = convertView;
        CipsmUserEntity cipsmUserEntity = list.get(position);
        ImageView checkedIV = view.findViewById(R.id.system_master_item_checked);
        TextView nameTV = view.findViewById(R.id.system_master_item_name);
        //1是已管理当前园区
        if (cipsmUserEntity.getParkFlag() == 1)
            checkedIV.setVisibility(View.VISIBLE);
            //0是可分配
        else if (cipsmUserEntity.getParkFlag() == 0)
            checkedIV.setVisibility(View.INVISIBLE);
        nameTV.setText(cipsmUserEntity.getNick());
        nameTV.setOnClickListener(v -> {
            if (cipsmUserEntity.getParkFlag() == 1) {
                checkedIV.setVisibility(View.INVISIBLE);
                cipsmUserEntity.setParkFlag(0);
            }
            //0是可分配
            else if (cipsmUserEntity.getParkFlag() == 0) {
                checkedIV.setVisibility(View.VISIBLE);
                cipsmUserEntity.setParkFlag(1);
            }
        });
        checkedIV.setOnClickListener(v -> nameTV.performLongClick());
        view.findViewById(R.id.system_master_item_all).setOnClickListener(v -> nameTV.performLongClick());
        return view;
    }
}
