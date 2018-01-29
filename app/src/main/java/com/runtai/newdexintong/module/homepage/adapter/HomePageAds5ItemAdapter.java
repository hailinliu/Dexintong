package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.module.homepage.bean.HomePageAdsPicBean;
import com.runtai.newdexintong.module.homepage.utils.SkipToPointActivityUtil;

import java.util.List;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class HomePageAds5ItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<HomePageAdsPicBean.DataBean.Ads5Bean.AdLocationsBeanXXXX> mData;
    private final LayoutInflater mInflater;

    public HomePageAds5ItemAdapter(Context mContext, List<HomePageAdsPicBean.DataBean.Ads5Bean.AdLocationsBeanXXXX> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
       
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
            convertView = mInflater.inflate(R.layout.item_homepage_ads5, null);
            holder.iv_ads5_pic = (ImageView) convertView.findViewById(R.id.iv_ads5_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HomePageAdsPicBean.DataBean.Ads5Bean.AdLocationsBeanXXXX ads5Bean = mData.get(position);

        ImageLoader.getInstance().displayImage(ads5Bean.getImgUrl(),holder.iv_ads5_pic, 
                ImageLoadUtil.getDefaultHeadPicOptions(),null);

        holder.iv_ads5_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String ads5Url = ads5Bean.getUrl();
                List<HomePageAdsPicBean.DataBean.Ads5Bean.AdLocationsBeanXXXX.ParamsBeanXXXX> ads5Params = ads5Bean.getParams();
                int ads5Size = ads5Params.size();
                if (ads5Size == 0) {
                    SkipToPointActivityUtil.jumpToNoParams(mContext, ads5Url);
                } else if (ads5Size == 1) {
                    String value0 = ads5Params.get(0).getValue();
                    String key0 = ads5Params.get(0).getKey();
                    SkipToPointActivityUtil.jumpToOneParams(mContext, ads5Url, key0, value0);
                } else if (ads5Size == 2) {
                    String key0 = ads5Params.get(0).getKey();
                    String value0 = ads5Params.get(0).getValue();
                    String key1 = ads5Params.get(1).getKey();
                    String value1 = ads5Params.get(1).getValue();
                    SkipToPointActivityUtil.jumpToTwoParams(mContext, ads5Url, key0, value0, key1, value1);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {
        ImageView iv_ads5_pic;
    }
}
