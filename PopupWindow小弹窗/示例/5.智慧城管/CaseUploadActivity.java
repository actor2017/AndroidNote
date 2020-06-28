package com.kuchuanyun.wisdomcitymanagement.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 案件上报(点击之后进来的页面)
 */
public class CaseUploadActivity extends BaseActivity {

    @BindView(R.id.tb_toolBar)
    Toolbar tbToolBar;
    @BindView(R.id.et_caseName)
    EditText etCaseName;
    @BindView(R.id.til_caseName)
    TextInputLayout tilCaseName;
    @BindView(R.id.et_categroy)
    EditText etCategroy;
    @BindView(R.id.til_categroy)
    TextInputLayout tilCategroy;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.et_fddbr)
    EditText etFddbr;
    @BindView(R.id.til_fddbr)
    TextInputLayout tilFddbr;
    @BindView(R.id.et_job)
    EditText etJob;
    @BindView(R.id.til_job)
    TextInputLayout tilJob;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.til_address)
    TextInputLayout tilAddress;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.til_detail)
    TextInputLayout tilDetail;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    private RecyclerView rv_categroy;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_upload);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue_titlebar));
        setSupportActionBar(tbToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //                              内容填充      宽           高             是否有焦点
        //4.创建popWindle        (View contentView, int width, int height, boolean focusable)
        tilCategroy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                popupWindow = new PopupWindow(rv_categroy, tilCategroy.getMeasuredWidth(), 800,
                        true);
                //必须设置背景,否则点击外面和返回键都不管用
                popupWindow.setBackgroundDrawable(new ColorDrawable());//ColorDrawable()里面什么都没有
            }
        });
        rv_categroy = new RecyclerView(this);
        rv_categroy.setLayoutManager(new LinearLayoutManager(this));
        rv_categroy.setBackgroundResource(R.drawable.listview_background);
        rv_categroy.setAdapter(new MyRVCategroyAdapter());
    }

    @OnClick({R.id.btn_select, R.id.iv_pic, R.id.btn_commit, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                //在editText下面显示弹窗
                popupWindow.showAsDropDown(tilCategroy, 0, 0);
                break;
            case R.id.iv_pic:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_commit:
                ToastUtils.show(this, "提交逻辑是什么?");
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap bm = extras.getParcelable("data");
                ivPic.setImageBitmap(bm);
            }
        }
    }

    private class MyRVCategroyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CaseUploadActivity.this).inflate(R.layout
                    .item_caseuploadactivity_etcategroy, parent, false);
            return new MyCategroyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final MyCategroyViewHolder viewHolder = (MyCategroyViewHolder) holder;
            viewHolder.tv.setText("违法修建" + position);
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etCategroy.setText(viewHolder.tv.getText().toString());
                    popupWindow.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }

    static class MyCategroyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        View view;

        MyCategroyViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}