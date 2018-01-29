package com.runtai.newdexintong.module.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author caizhiming
 * @created on 2015-4-13
 */
public class XCFlowLayout extends ViewGroup {

	public XCFlowLayout(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public XCFlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public XCFlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
		int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
		int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

		int lineWidth = 0;
		int lineHeight = 0;
		int height = 0;
		int width = 0;
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);

			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			int childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			int childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;

			if (lineWidth + childWidth > measureWidth) {
				// 需要换行
				width = Math.max(lineWidth, width);
				height += lineHeight;
				// 因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
				lineHeight = childHeight;
				lineWidth = childWidth;
			} else {
				// 否则累加值lineWidth,lineHeight取最大高度
				lineHeight = Math.max(lineHeight, childHeight);
				lineWidth += childWidth;
			}

			// 最后一行是不会超出width范围的，所以要单独处理
			if (i == count - 1) {
				height += lineHeight;
				width = Math.max(width, lineWidth);
			}

		}
		// 当属性是MeasureSpec.EXACTLY时，那么它的高度就是确定的，
		// 只有当是wrap_content时，根据内部控件的大小来确定它的大小时，大小是不确定的，属性是AT_MOST,此时，就需要我们自己计算它的应当的大小，并设置进去
		setMeasuredDimension(
				(measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth
						: width,
				(measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight
						: height);
	}

	// 存储所有子View
	private List<List<View>> mAllChildViews = new ArrayList<List<View>>();
	// 每一行的高度
	private List<Integer> mLineHeight = new ArrayList<Integer>();

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		mAllChildViews.clear();
		mLineHeight.clear();
		// 获取当前ViewGroup的宽度
		int width = getWidth();

		int lineWidth = 0;
		int lineHeight = 0;
		// 记录当前行的view
		List<View> lineViews = new ArrayList<View>();
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

			// 如果需要换行
			if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {
				// 记录LineHeight
				mLineHeight.add(lineHeight);
				// 记录当前行的Views
				mAllChildViews.add(lineViews);
				// 重置行的宽高
				lineWidth = 0;
				lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
				// 重置view的集合
				lineViews = new ArrayList();
			}
			lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
			lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
					+ lp.bottomMargin);
			lineViews.add(child);
		}
		// 处理最后一行
		mLineHeight.add(lineHeight);
		mAllChildViews.add(lineViews);

		// 设置子View的位置
		int left = 0;
		int top = 0;
		// 获取行数
		int lineCount = mAllChildViews.size();
		for (int i = 0; i < lineCount; i++) {
			// 当前行的views和高度
			lineViews = mAllChildViews.get(i);
			lineHeight = mLineHeight.get(i);
			for (int j = 0; j < lineViews.size(); j++) {
				View child = lineViews.get(j);
				// 判断是否显示
				if (child.getVisibility() == View.GONE) {
					continue;
				}
				MarginLayoutParams lp = (MarginLayoutParams) child
						.getLayoutParams();
				int cLeft = left + lp.leftMargin;
				int cTop = top + lp.topMargin;
				int cRight = cLeft + child.getMeasuredWidth();
				int cBottom = cTop + child.getMeasuredHeight();
				// 进行子View进行布局
				child.layout(cLeft, cTop, cRight, cBottom);
				left += child.getMeasuredWidth() + lp.leftMargin
						+ lp.rightMargin;
			}
			left = 0;
			top += lineHeight;
		}

	}

	/**
	 * 与当前ViewGroup对应的LayoutParams
	 */
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		// TODO Auto-generated method stub

		return new MarginLayoutParams(getContext(), attrs);
	}
}
