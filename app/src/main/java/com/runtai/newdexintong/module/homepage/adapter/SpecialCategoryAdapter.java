package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.module.homepage.bean.HomePageAdsPicBean;
import com.runtai.newdexintong.module.homepage.bean.HomepageSpecialBean;
import com.runtai.newdexintong.module.homepage.utils.SkipToPointActivityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class SpecialCategoryAdapter extends BaseRecyclerAdapter<SpecialCategoryAdapter.MyViewHolder> {


    public SpecialCategoryAdapter(Context context, List<Object> listDatas) {
        super(context, listDatas);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_homepage_special_category, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final HomepageSpecialBean infoBean = (HomepageSpecialBean) listDatas.get(position);//转换
        holder.tv_special_name.setText(infoBean.getText());//填充数据
        final List<HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean.ViewAdLocationsBean> viewAdLocations = infoBean.getViewAdLocations();
        List<Object> imageList = new ArrayList<>();
        imageList.clear();
        for (int j = 0; j < viewAdLocations.size(); j++) {
            imageList.add(viewAdLocations.get(j).getImgUrl());
        }
        infoBean.setImgList(imageList);
        if (imageList.size() > 0) {
            ImageAdapter imageAdapter = new ImageAdapter(context, imageList);
            final LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            holder.item_myRecycler.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.HORIZONTAL));
            holder.item_myRecycler.setLayoutManager(manager);
            holder.item_myRecycler.setAdapter(imageAdapter);
            holder.item_myRecycler.setVisibility(View.VISIBLE);

            //水平滚动的recyclerview添加滚动监听
//            holder.item_myRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//
//                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                    if (layoutManager instanceof LinearLayoutManager) {
//                        LinearLayoutManager manager1 = (LinearLayoutManager) layoutManager;
//                        int firstVisibleItemPosition = manager1.findFirstVisibleItemPosition();
//                        int lastVisibleItemPosition = manager1.findLastVisibleItemPosition();
//                        if (firstVisibleItemPosition == 0) {
//                            holder.iv_to_bottom.setVisibility(View.VISIBLE);
//                            holder.iv_to_top.setVisibility(View.GONE);
//                        } else if (lastVisibleItemPosition == infoBean.getImgList().size() - 1) {
//                            holder.iv_to_bottom.setVisibility(View.GONE);
//                            holder.iv_to_top.setVisibility(View.VISIBLE);
//                        } else {
//                            holder.iv_to_bottom.setVisibility(View.VISIBLE);
//                            holder.iv_to_top.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//
//            });


            //设置图片条目点击
            imageAdapter.setOnItemViewClickLitener(new ImageAdapter.OnItemViewClickLitener() {
                @Override
                public void onItemViewClick(View view, int position) {
                   
                        HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean.ViewAdLocationsBean viewAdLocationsBean = viewAdLocations.get(position);
                        String url = viewAdLocationsBean.getUrl();
                        List<HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean.ViewAdLocationsBean.Ads7ParamsBean> params = viewAdLocationsBean.getParams();
                        LogUtil.e("tag", String.valueOf(params.size()));
                        int size = params.size();
                        if (size == 0) {
                            SkipToPointActivityUtil.jumpToNoParams(context,url);
                        } else if (size == 1) {
                            String value0 = params.get(0).getValue();
                            String key0 = params.get(0).getKey();
                            SkipToPointActivityUtil.jumpToOneParams(context,url, key0, value0);
                        } else if (size == 2) {
                            String key0 = params.get(0).getKey();
                            String value0 = params.get(0).getValue();
                            String key1 = params.get(1).getKey();
                            String value1 = params.get(1).getValue();
                            SkipToPointActivityUtil.jumpToTwoParams(context,url, key0, value0, key1, value1);
                        }
                }
            });


            //点击置顶
            holder.iv_to_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.item_myRecycler.smoothScrollToPosition(0);
                }
            });

            //点击到达底部
            holder.iv_to_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.item_myRecycler.smoothScrollToPosition(infoBean.getImgList().size() - 1);
                }
            });
        } else {
            holder.item_myRecycler.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_special_name;//内容
        RecyclerView item_myRecycler;
        ImageView iv_to_top, iv_to_bottom;

        public MyViewHolder(View view) {
            super(view);
            tv_special_name = (TextView) view.findViewById(R.id.tv_special_name);
            item_myRecycler = (RecyclerView) view.findViewById(R.id.item_myRecycler);
            iv_to_top = (ImageView) view.findViewById(R.id.iv_to_top);
            iv_to_bottom = (ImageView) view.findViewById(R.id.iv_to_bottom);

        }
    }
}
