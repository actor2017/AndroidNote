package com.kuchuanyun.enginemanager.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.kuchuanyun.enginemanager.R;
import com.kuchuanyun.enginemanager.utils.BitmapToPngFile;
import com.kuchuanyun.enginemanager.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加发动机
 */
public class AddEngineActivity extends BaseActivity {

    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_detail)
    EditText etDetail;
    File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_engine);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this, getResources().getColor(R.color.deep_green));
        tempFile = new File(BitmapToPngFile.getDefaultPath("tempPic.jpg"));
    }

    @OnClick({R.id.iv_back, R.id.iv_add, R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                //拍照,并把图片存到指定临时文件
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempFile));
                startActivityForResult(intent,1);
                break;
            case R.id.btn_upload:
                ToastUtils.showDefault(this, "上传发动机信息的逻辑");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                try {
                    crop(Uri.fromFile(tempFile));//裁剪图片
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                if (data != null) {
                    //把裁剪的图片显示出来
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        ivAdd.setImageBitmap(bitmap);
                    }
                    try {
                        //将临时文件删除
                        tempFile.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    //裁剪图片
    private void crop(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");//url需要传入
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小(如果不设置,半天都裁剪不出来)
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, 2);
    }
}
