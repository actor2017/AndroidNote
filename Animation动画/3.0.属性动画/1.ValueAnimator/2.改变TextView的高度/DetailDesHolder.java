package cn.itcast.googleplay12.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.itcast.googleplay12.R;
import cn.itcast.googleplay12.utils.UiUtils;

/**
 * Created by zhengping on 2017/4/9,11:00.
 */

public class DetailDesHolder extends BaseHolder<String> implements View.OnClickListener {

    private TextView des;

    @Override
    public void refreshView() {
        des.setText(data);
        ViewGroup.LayoutParams layoutParams = des.getLayoutParams();
        layoutParams.height = getMinHeight();
        des.setLayoutParams(layoutParams);

    }

    //获取des  7行的高度
    //我们可以打造一个TextView ，这个TextView和我们自己的textview完全一致，然后给这个TextView设置maxLines
    //然后返回的高度就 = 我们控件的高度
    //注意,这个自定义的textview只是用于计算高度
    private int getMinHeight() {
        //des.setMaxLines(7);
        TextView tv = new TextView(UiUtils.getContext());
        tv.setText(data);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        tv.getPaint().setFakeBoldText(true);//设置粗体
        tv.setMaxLines(7);
        int size = UiUtils.getScreenWidth()  - UiUtils.dp2px(10) * 2;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.AT_MOST);
        tv.measure(widthMeasureSpec,0);
        return tv.getMeasuredHeight();
    }

    //获取des 完整的高度
    private int getMaxHeight() {
        TextView tv = new TextView(UiUtils.getContext());
        tv.setText(data);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        tv.getPaint().setFakeBoldText(true);//设置粗体
        //tv.setMaxLines(7);
        int size = UiUtils.getScreenWidth()  - UiUtils.dp2px(10) * 2;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.AT_MOST);
        tv.measure(widthMeasureSpec,0);
        return tv.getMeasuredHeight();
    }


    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.layout_detail_des, null);
        des = (TextView) view.findViewById(R.id.des);
        View rlBottom =  view.findViewById(R.id.rlBottom);
        rlBottom.setOnClickListener(this);
        return view;
    }

    private boolean isOpen = false;
    @Override
    public void onClick(View v) {
        if(isOpen) {
            close();
            //isOpen = false;
        } else {
            open();
            //isOpen = true;
        }
        isOpen = !isOpen;
    }

    private void open() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(getMinHeight(), getMaxHeight());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int temp = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = des.getLayoutParams();
                layoutParams.height = temp;
                des.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //让ScrollView自动滚动到底部
                ScrollView sc = getScrollView(des);

                if(sc != null) {
                    sc.fullScroll(View.FOCUS_DOWN);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }


    private void close() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getMaxHeight(), getMinHeight());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int temp = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = des.getLayoutParams();
                layoutParams.height = temp;
                des.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }

    /*private ScrollView sc;
    public void setScrollView(ScrollView sc) {
        this.sc = sc;
    }*/

    //需要注意的是，通过此方法来获取ScrollView，就必须保证，view的父控件层级中必须得要有一个ScrollView
    private ScrollView getScrollView(View view) {
        //获取父控件
        ViewParent parent = view.getParent();
        if(parent instanceof  ScrollView) {
            return (ScrollView) parent;
        } else {
            return getScrollView((View) parent);
        }
    }
}
