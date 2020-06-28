package com.kuchuanyun.wisdomcitymanagement.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.GridLayoutManager;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaeger.library.StatusBarUtil;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.utils.BitmapToPngFile;
import com.kuchuanyun.wisdomcitymanagement.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;

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
    @BindView(R.id.tv_prcCounter)
    TextView tvPrcCounter;
    @BindView(R.id.rv_pics)
    RecyclerView rvPics;
    private RecyclerView rv_categroy;
    private PopupWindow popupWindow;
    private ArrayList<String> picPaths = new ArrayList<>();//图片路径
    private MyPicsAdapter myPicsAdapter;

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

        rvPics.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @OnClick({R.id.btn_select, R.id.ll_takePhoto, R.id.btn_commit, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                //在editText下面显示弹窗
                popupWindow.showAsDropDown(tilCategroy, 0, 0);
                break;
            case R.id.ll_takePhoto:
                if (picPaths.size() >= 6) {
                    ToastUtils.show(this, "最多拍6张照片!");
                    return;
                }
                //把图片放到自定义文件夹(必须保证这个文件夹已经存在,否则拍照之后点击"确定"不能返回,甚至崩溃.)
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(BitmapToPngFile.getDefaultPath(getClass().getSimpleName
                        () + picPaths.size() + ".jpg"));
                Uri imageUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
        //这里data == null,注意
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //这里的picPaths里是String path.
            picPaths.add(BitmapToPngFile.getDefaultPath(getClass().getSimpleName() + picPaths
                    .size() + ".jpg"));
            if (myPicsAdapter == null) {//初始化
                myPicsAdapter = new MyPicsAdapter();
                rvPics.setAdapter(myPicsAdapter);
            } else {
                myPicsAdapter.notifyDataSetChanged();
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

    //选择案件类型
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

    //照片的adapter
    private class MyPicsAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CaseUploadActivity.this).inflate(R.layout
                    .item_caseuploadactivity_pic, parent, false);
            return new MyPicsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyPicsViewHolder viewHolder = (MyPicsViewHolder) holder;
            //注意:如果这样直接加载图片,会造成滑动巨卡顿,不能这样!!!
//            viewHolder.ivPic.setImageBitmap(picPaths.get(position));//这里的picPaths里是Bitmap
            tvPrcCounter.setText(picPaths.size() + "/6");
            //用Glide不会造成巨卡顿现象.
            Glide.with(CaseUploadActivity.this)
                    .load(picPaths.get(position))//这里的picPaths里是String path
                    .skipMemoryCache(true)// 跳过内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//跳过磁盘缓存(ALL:不跳过,NONE:跳过)
                    .into(viewHolder.ivPic);
        }

        @Override
        public int getItemCount() {
            return picPaths.size();
        }

    }

    static class MyPicsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;

        MyPicsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}