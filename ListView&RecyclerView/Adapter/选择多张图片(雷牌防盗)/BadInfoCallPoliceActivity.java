package com.rm.lpsj.activity.me;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.actor.myandroidframework.utils.SPUtils;
import com.actor.myandroidframework.utils.okhttputils.MyOkHttpUtils;
import com.actor.myandroidframework.utils.okhttputils.PostFileCallback;
import com.actor.myandroidframework.widget.BaseItemDecoration;
import com.rm.lpsj.R;
import com.rm.lpsj.activity.BaseActivity;
import com.rm.lpsj.adapter.AddPicAdapter;
import com.rm.lpsj.model.AddKeyUploadFileModel;
import com.rm.lpsj.utils.BaseCallback3;
import com.rm.lpsj.utils.MyAppliction;
import com.runman.baselibrary.base.AddPicBean;
import com.runman.baselibrary.base.BaseInfo;
import com.runman.baselibrary.control.Global;
import com.runman.baselibrary.view.CustomTitleView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 不良信息举报
 *
 * @author : 李大发
 * date       : 2020/9/3 on 14:05
 * @version 1.0
 */
public class BadInfoCallPoliceActivity extends BaseActivity {

    @BindView(R.id.custom_title_view)
    CustomTitleView customTitleView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private AddPicAdapter mAdapter;
    private List<AddPicBean> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_info_call_police);
        ButterKnife.bind(this);

        customTitleView.setTitle("不良信息举报");
        customTitleView.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        items.clear();
        items.add(null);//添加一个+号
        int dp10 = MyAppliction.getInstance().dp1 * 10;
        recyclerView.addItemDecoration(new BaseItemDecoration(dp10, dp10));//增加间隔
        recyclerView.setAdapter(mAdapter = new AddPicAdapter(5, items));
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        if (isNoEmpty(etContent)) {
            if (mAdapter.hasPicSelected()) {
                checkPic();
            } else {
                addReport();
            }
        }
    }

    //检查图片是否有未上传的
    private void checkPic() {
        List<AddPicBean> data = mAdapter.getData();
        for (int i = 0; i < data.size() - 1; i++) {//最后一个对象=null
            AddPicBean picBean = data.get(i);
            if (picBean.uploadPath == null) {
                uploadPic(i, picBean.picPath);//上传图片
                return;
            }
        }
        addReport();
    }

    //上传图片
    private void uploadPic(int position, String picPath) {
        File file = new File(picPath);
        params.clear();
        params.put("type", "");
        params.put(Global.userId, SPUtils.getString(Global.userId));
        params.put("pid", "");
        MyOkHttpUtils.postFiles(Global.API_UPLOAD_PIC, file.getAbsolutePath(), file, null,
                params, new PostFileCallback<BaseInfo<AddKeyUploadFileModel>>(this, position) {
            @Override
            public void onOk(@NonNull BaseInfo<AddKeyUploadFileModel> info, int id) {
                dismissLoadingDialog();
                if (info.isSuccess()) {
                    AddKeyUploadFileModel datas = info.datas;
                    if (datas != null) {
                        //设置上传路径
                        mAdapter.getItem(id).uploadPath = datas.getPicDic();
                        checkPic();
                    } else {
                        toast("返回数据为空");
                    }
                } else {
                    toast(info.responseMsg);
                }
            }
        });
    }

    //不良信息
    private void addReport() {
        params.clear();
        params.put(Global.userId, SPUtils.getString(Global.userId));
        params.put(Global.content, getText(etContent));
        List<AddPicBean> data = mAdapter.getData();
        if (data.size() > 1) {
            StringBuilder sb = new StringBuilder();
            //将图片用,拼接, 最后一个对象=null
            for (int i = 0; i < data.size() - 1; i++) {
                AddPicBean picBean = data.get(i);
                sb.append(picBean.uploadPath);
                if (i != data.size() - 1 - 1) {//倒数第2个元素不加',', 最后一个对象=null
                    sb.append(",");
                }
            }
            params.put(Global.image, sb.toString());
        }
        MyOkHttpUtils.post(Global.REPORT_ADD, params, new BaseCallback3<BaseInfo>(this) {
            @Override
            public void onOk(@NonNull BaseInfo info, int id) {
                dismissLoadingDialog();
                if (info.isSuccess()) {
                    toast("举报成功!");
                    onBackPressed();
                } else {
                    toast(info.responseMsg);
                }
            }
        });
    }
}