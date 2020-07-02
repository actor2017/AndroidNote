package com.hc.studyCoordinatorLayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * Package com.hc.studyCoordinatorLayout
 * Created by HuaChao on 2016/6/1.
 */
public class MyBehavior extends CoordinatorLayout.Behavior<Button> {
    private int width;

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        width = display.widthPixels;
    }

/**
* �ж�child�Ĳ����Ƿ�����dependency
*/
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        //�����߼��ж�rs��ȡֵ
    //����false��ʾchild������dependency��ture��ʾ����
		//���dependency��TempView��ʵ����˵����������������Ҫ��Dependency
        return dependency instanceof TempView;
    }

/**
* ��dependency�����ı�ʱ��λ�á���ߵȣ���ִ���������
* ����true��ʾchild��λ�û����ǿ��Ҫ�����ı䣬����ͷ���false
*/
    //ÿ��dependencyλ�÷����仯������ִ��onDependentViewChanged����
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button btn, View dependency) {
//childҪִ�еľ��嶯��
        //����dependency��λ�ã�����Button��λ��

        int top = dependency.getTop();
        int left = dependency.getLeft();

        int x = width - left - btn.getWidth();
        int y = top;

        setPosition(btn, x, y);
        return true;
    }

    private void setPosition(View v, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        v.setLayoutParams(layoutParams);
    }


}