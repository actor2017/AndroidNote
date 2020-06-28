package com.example.wenlaisu.myapplication;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by wenlaisu on 2018/4/12.
 */
public class CustomViewPager extends ViewPager {
  
    private int current;  
    private int height = 0;  
    /**  
     * 保存position与对于的View  
     */  
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<>();

    private boolean scrollble = true;  
  
    public CustomViewPager(Context context) {
        super(context);  
    }  
  
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }  
  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        if (mChildrenViews.size() > current) {
            View child = mChildrenViews.get(current);  
            if (child != null) {  
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));  
                height = child.getMeasuredHeight();  
            }  
        }  
  
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);  
  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
    }  
  
    public void resetHeight(int current) {  
        this.current = current;  
        if (mChildrenViews.size() > current) {  
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {  
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
            } else {  
                layoutParams.height = height;  
            }  
            setLayoutParams(layoutParams);  
        }  
    }  
  
    /**  
     * 保存position与对于的View  
     */
    public void setObjectForPosition(View view, int position) {
        mChildrenViews.put(position, view);  
    }
  
  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollble) {  
            return true;  
        }  
        return super.onTouchEvent(ev);  
    }  
  
  
    public boolean isScrollble() {  
        return scrollble;  
    }  
  
    public void setScrollble(boolean scrollble) {  
        this.scrollble = scrollble;  
    }  
  
  
}  