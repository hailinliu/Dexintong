package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.view.MyGridView;
import com.runtai.newdexintong.module.homepage.activity.SpecialSaleActivity;
import com.runtai.newdexintong.module.homepage.bean.SpecialListBean;
import com.runtai.newdexintong.module.homepage.bean.SpecialListDataBean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/9/1time11:58
 * @detail：
 */

public class SpecialListAdapter extends BaseAdapter {


    private Context context;
    private List<SpecialListBean> mData;
    private final LayoutInflater mInflater;
    private SpecialListItemAdapter adapter;

    public SpecialListAdapter(Context context, List<SpecialListBean> mData) {
        this.context = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_special_list, null);
            holder.mGridView = (MyGridView) convertView.findViewById(R.id.mGridView);
            holder.tv_special_name = (TextView) convertView.findViewById(R.id.tv_special_name);
            holder.rl_spcial_item = (RelativeLayout) convertView.findViewById(R.id.rl_spcial_item);
            holder.ll_more = (LinearLayout) convertView.findViewById(R.id.ll_more);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int i1 = position % 3;
        if (i1 == 0) {
            holder.rl_spcial_item.setBackgroundColor(context.getResources().getColor(R.color.first_item_color));
        } else if (i1 == 1) {
            holder.rl_spcial_item.setBackgroundColor(context.getResources().getColor(R.color.second_item_color));
        } else {
            holder.rl_spcial_item.setBackgroundColor(context.getResources().getColor(R.color.third_item_color));
        }
        final SpecialListBean specialListBean = mData.get(position);
//        List<String> imgList = specialListBean.getImgList();
        List<SpecialListDataBean.DataBean.ListBean.ActivitysBean> activitys = specialListBean.getActivitys();
//        SpecialListDataBean.DataBean.ListBean.ActivitysBean activitysBean = activitys.get(position);
//        List<String> new_imgList = new ArrayList<>();
//        new_imgList.clear();
//        for (int i = 0; i < activitys.size(); i++) {
//            if (i < 3) {
//                new_imgList.add(activitysBean.getPhoto());
//            }
//        }
        
        holder.tv_special_name.setText(specialListBean.name);

        adapter = new SpecialListItemAdapter(context, activitys,specialListBean.getSpecialId());
        holder.mGridView.setAdapter(adapter);
        holder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SpecialSaleActivity.class);
                intent.putExtra("mUrl", "api/promotion/activity");
                intent.putExtra("paramName0", "SpecialId");
                intent.putExtra("paramValue0", String.valueOf(specialListBean.getSpecialId()));
                intent.putExtra("paramName1", "ActivityId");
                intent.putExtra("paramValue1", String.valueOf(specialListBean.getActivityId()));
                ((BaseCommonActivity) context).startActivityByIntent(intent);
            }
        });

        //点击更多
        holder.ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SpecialSaleActivity.class);
                intent.putExtra("mUrl", "api/promotion/activity");
                intent.putExtra("paramName0", "SpecialId");
                intent.putExtra("paramValue0", String.valueOf(specialListBean.getSpecialId()));
                intent.putExtra("paramName1", "ActivityId");
                intent.putExtra("paramValue1", String.valueOf(specialListBean.getActivityId()));
                ((BaseCommonActivity) context).startActivityByIntent(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tv_special_name;
        MyGridView mGridView;
        RelativeLayout rl_spcial_item;
        LinearLayout ll_more;
    }
}
