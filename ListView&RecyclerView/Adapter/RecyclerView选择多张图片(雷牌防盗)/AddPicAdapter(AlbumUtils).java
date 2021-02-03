package com.ysytech.zhongjiao.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.myandroidframework.utils.album.AlbumUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;
import com.ysytech.zhongjiao.R;
import com.ysytech.zhongjiao.bean.AddPicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 添加图片
 *
 * @author : 李大发
 * date       : 2020/9/18 on 20:28
 * @version 1.0
 */
public class AddPicAdapter extends BaseQuickAdapter<AddPicBean, BaseViewHolder> {

    private int maxPic;//最多选择多少个
    private int actiontype = 1;//动作类型
    public static final int TYPE_TAKE_PHOTO = 0;//拍照
    public static final int TYPE_SELECT_PHOTO = 1;//选择图片
    public static final int TYPE_TAKE_SELECT_PHOTO = 2;//拍照&选择图片

    /**
     * @param maxPic 最多选择多少张图片
     */
    public AddPicAdapter(int maxPic, @IntRange(from = TYPE_TAKE_PHOTO, to = TYPE_TAKE_SELECT_PHOTO) int type) {
        super(R.layout.item_pic_add);
        this.maxPic = maxPic;
        this.actiontype = type;
        addData((AddPicBean) null);//添加一个+号

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //是否是最后一个pos
                boolean isLastPos = position == getItemCount() - 1;
                switch (view.getId()) {
                    case R.id.iv://添加
                        if (isLastPos) {
                            //判断是否能选择更多
                            if (getItemCount() > maxPic) {
                                ToastUtils.showShort(TextUtils2.getStringFormat("最多选择%d张", maxPic));
                            } else {
                                switch (actiontype) {
                                    case TYPE_TAKE_PHOTO://拍照
                                        AlbumUtils.takePhoto(mContext, new Action<String>() {
                                            @Override
                                            public void onAction(@NonNull String result) {
                                                addData(position, new AddPicBean(result));
                                            }
                                        });
                                        break;
                                    case TYPE_SELECT_PHOTO://选择图片
                                        AlbumUtils.selectImage(mContext, false, new Action<ArrayList<AlbumFile>>() {
                                            @Override
                                            public void onAction(@NonNull ArrayList<AlbumFile> result) {
//                                              if (result.get(0).getSize() > 1 * 1024 * 1024) {
//                                                  ToastUtils.showLong("请选择小于1M的图片");
//                                              } else {
//                                              }
                                                addData(position, new AddPicBean(result.get(0).getPath()));
                                            }
                                        });
                                        break;
                                    case TYPE_TAKE_SELECT_PHOTO://拍照&选择图片
                                        AlbumUtils.selectImage(mContext, true, new Action<ArrayList<AlbumFile>>() {
                                            @Override
                                            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                                                addData(position, new AddPicBean(result.get(0).getPath()));
                                            }
                                        });
                                        break;
                                    default:
                                        break;
                                }

                            }
                        } else {//预览
                            List<AddPicBean> data = getData();
                            ArrayList<String> items = new ArrayList<>();
                            for (AddPicBean datum : data) {
                                if (datum != null) {
                                    items.add(datum.picPath);
                                }
                            }
                            AlbumUtils.gallery(mContext, items, position, false, null);
                        }
                        break;
                    case R.id.iv_delete://删除
                        remove(position);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddPicBean item) {
        //是否是最后一个pos
        boolean isLastPos = helper.getAdapterPosition() == getItemCount() - 1;
        ImageView iv = helper.setGone(R.id.iv_delete, !isLastPos)
                .addOnClickListener(R.id.iv, R.id.iv_delete)
                .getView(R.id.iv);
        if (isLastPos) {
            Glide.with(iv).load(R.drawable.camera_gray).into(iv);
        } else {
            Glide.with(iv).load(item.picPath).into(iv);
        }
    }

    /**
     * 是否有图片选择, 最后一个对象=null
     */
    public boolean hasPicSelected() {
        return getData().size() > 1;
    }

    /**
     * 注意: item有可能 = null(最后一张)
     */
    @NonNull
    @Override
    public List<AddPicBean> getData() {
        return super.getData();
    }
}
