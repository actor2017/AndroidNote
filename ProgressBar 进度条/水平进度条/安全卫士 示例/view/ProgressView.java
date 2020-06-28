package com.heima.mobilesafe_work.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heima.mobilesafe_work.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin.
 * 本class写在view文件夹里面,本LinearLayout用layout_progreee.xml填充,且写逻辑
 */

public class ProgressView extends LinearLayout {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_progress, this);
        ButterKnife.bind(view);
    }

    //修改标题
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    //修改左侧文字
    public void setLeftText(String text) {
        tvLeft.setText(text);
    }

    //修改右侧文字
    public void setRightText(String text) {
        tvRight.setText(text);
    }

    //修改进度
    public void setProgress(int progress) {
        pbProgress.setProgress(progress);
    }

    //设置最大进度,如果不设定,默认是最大值100
    public void setMaxProgress(int maxProgress){
        pbProgress.setMax(maxProgress);
    }
}
