package com.runtai.newdexintong.module.fenlei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @作者：高炎鹏
 * @日期：2017/4/1时间16:00
 * @描述：
 */

public class ImageBrowseActivity extends BaseActivity {

    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_browse);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        images = intent.getStringArrayListExtra("images");
        MyAdapter myAdapter = new MyAdapter();
        vp.setAdapter(myAdapter);
        vp.setCurrentItem(position);//设置当前
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(ImageBrowseActivity.this, R.layout.item_image_browse, null);
            PhotoView pv = (PhotoView) view.findViewById(R.id.pv);
            String url = images.get(position);
//            Picasso.with(ImageBrowseActivity.this)
//                    .load(url)
//                    .transform(new HalfTransformation())
//                    .placeholder(R.drawable.loading_default)
//                    .error(R.mipmap.no_net_show_icon)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                    .into(pv);
            pv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    ImageBrowseActivity.this.finish();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
