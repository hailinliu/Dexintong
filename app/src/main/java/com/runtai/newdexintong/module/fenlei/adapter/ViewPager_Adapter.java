package com.runtai.newdexintong.module.fenlei.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;

import java.util.ArrayList;

/**
 * @作者：高炎鹏
 * @日期：2017/4/1时间13:54
 * @描述：
 */

public class ViewPager_Adapter extends AbstractViewPagerAdapter {

    private ArrayList<String> data;
    private Context mContext;
    private int index;

    public ViewPager_Adapter(ArrayList<String> data, Context context) {
        super(data);
        this.data = data;
        this.mContext = context;
    }

    public void setCurrent(int index) {
        this.index = index;
    }

    @Override
    public View newView(final int position) {
        View view = View.inflate(mContext, R.layout.adapter_viewpager_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
//        RelativeLayout adapter_vp_item_bg = (RelativeLayout) view.findewById(R.id.adapter_vp_item_bg);
        ImageLoader.getInstance().displayImage(getItem(position).toString(),
                imageView, ImageLoadUtil.getBannerOptions(), null);
        

//        adapter_vp_item_bg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("position", "position" + position);
//                Intent intent = new Intent(mContext, ImageBrowseActivity.class);
//                intent.putStringArrayListExtra("images",data);
//                intent.putExtra("position", position);
//                mContext.startActivity(intent);
//            }
//        });
        return view;
    }
}
