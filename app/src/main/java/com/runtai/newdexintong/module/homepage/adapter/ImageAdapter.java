package com.runtai.newdexintong.module.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;

import java.util.List;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class ImageAdapter extends BaseRecyclerAdapter<ImageAdapter.MyViewHolder> {
    
    DisplayMetrics dm;

    public ImageAdapter(Context context, List<Object> listDatas) {
        super(context, listDatas);
        dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_homepage_special_icon, parent, false);
        //动态设置ImageView的宽高，根据自己每行item数量计算
        //dm.widthPixels-dip2px(20)即屏幕宽度-左右10dp+10dp=20dp再转换为px的宽度，最后/3得到每个item的宽高
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((dm.widthPixels - dip2px(10)) / 3, (dm.widthPixels - dip2px(10)) / 3);
        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        String url = (String) listDatas.get(position);//转换
        ImageLoader.getInstance().displayImage(url,holder.iv, 
                ImageLoadUtil.getDefaultHeadPicOptions(),null);

        // 如果设置了回调，则设置点击事件
        if (mOnItemViewClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemViewClickLitener.onItemViewClick(holder.itemView, pos);
                }
            });
            
        }
    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

    int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnItemViewClickLitener {
        void onItemViewClick(View view, int position);

    }

    private OnItemViewClickLitener mOnItemViewClickLitener;

    public void setOnItemViewClickLitener(OnItemViewClickLitener mOnItemViewClickLitener) {
        this.mOnItemViewClickLitener = mOnItemViewClickLitener;
    }
}
