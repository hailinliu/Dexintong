package com.runtai.newdexintong.comment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
@作者：王毅
@时间：2016-1-6下午5:20:52
@类描述：
@版本号：
@修改人：
@修改地址
 */
public class MyListView extends ListView {

	
	public MyListView(Context context) {  
        // TODO Auto-generated method stub  
        super(context);  
    }  
  
    public MyListView(Context context, AttributeSet attrs) {  
        // TODO Auto-generated method stub  
        super(context, attrs);  
    }  
  
    public MyListView(Context context, AttributeSet attrs, int defStyle) {  
        // TODO Auto-generated method stub  
        super(context, attrs, defStyle);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        // TODO Auto-generated method stub  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
}
