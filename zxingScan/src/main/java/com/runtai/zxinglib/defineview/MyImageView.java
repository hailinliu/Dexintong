package com.runtai.zxinglib.defineview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * 类说明：画出扫描框的四个脚的脚边框，也可以直接用一张图片代替
 */
public class MyImageView extends ImageView {
    private Context context;
    private float edgeValue = 18;

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyImageView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        Paint paint = new Paint();
        paint.setColor(Color.rgb(9, 187, 7));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(t(5));

        canvas.drawLine(0, 0, 0, t(edgeValue), paint);
        canvas.drawLine(0, 0, t(edgeValue), 0, paint);

        canvas.drawLine(0, height - t(edgeValue), 0, height, paint);
        canvas.drawLine(0, height, t(edgeValue), height, paint);

        canvas.drawLine(width - t(edgeValue), 0, width, 0, paint);
        canvas.drawLine(width, 0, width, t(edgeValue), paint);

        canvas.drawLine(width, height - t(edgeValue), width, height, paint);
        canvas.drawLine(width - t(edgeValue), height, width, height, paint);
    }

    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public int t(float dpVal) {
        return dp2px(dpVal);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //   setMeasuredDimension(t(248),t(248));
    }
}
