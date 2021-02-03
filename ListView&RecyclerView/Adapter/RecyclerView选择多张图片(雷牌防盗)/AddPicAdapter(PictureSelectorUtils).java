package com.ysytech.zhongjiao.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.myandroidframework.utils.picture_selector.PictureSelectorUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
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
    private List<LocalMedia> localMedias = new ArrayList<>();

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
                                Activity topActivity = ActivityUtils.getTopActivity();
                                if (topActivity == null) {
                                    return;
                                }
                                switch (actiontype) {
                                    case TYPE_TAKE_PHOTO://拍照
                                        PictureSelectorUtils.takePhoto(topActivity, new OnResultCallbackListener<LocalMedia>() {
                                            @Override
                                            public void onResult(List<LocalMedia> result) {
                                                LocalMedia localMedia = result.get(0);
                                                addData(position, new AddPicBean(localMedia.getPath()));
                                                localMedias.add(localMedia);
                                            }
                                            @Override
                                            public void onCancel() {
                                            }
                                        });
                                        break;
                                    case TYPE_SELECT_PHOTO://选择图片
                                        PictureSelectorUtils.selectImage/*s*/(topActivity, false, false, localMedias,/* maxPic,*/ new OnResultCallbackListener<LocalMedia>() {
                                            @Override
                                            public void onResult(List<LocalMedia> result) {
                                                LocalMedia localMedia = result.get(0);
                                                addData(position, new AddPicBean(localMedia.getPath()));
                                                localMedias.add(localMedia);
                                            }
                                            @Override
                                            public void onCancel() {
                                            }
                                        });
                                        break;
                                    case TYPE_TAKE_SELECT_PHOTO://拍照&选择图片
                                        PictureSelectorUtils.selectImage/*s*/(topActivity, true, false, localMedias,/* maxPic,*/ new OnResultCallbackListener<LocalMedia>() {
                                            @Override
                                            public void onResult(List<LocalMedia> result) {
                                                LocalMedia localMedia = result.get(0);
                                                addData(position, new AddPicBean(localMedia.getPath()));
                                                localMedias.add(localMedia);
                                            }
                                            @Override
                                            public void onCancel() {
                                            }
                                        });
                                        break;
                                    default:
                                        break;
                                }

                            }
                        } else {//预览
                            Activity topActivity = ActivityUtils.getTopActivity();
                            if (topActivity != null && !topActivity.isDestroyed()) {
                                PictureSelectorUtils.previewImageVideos(topActivity, position, localMedias);
                            }
                        }
                        break;
                    case R.id.iv_delete://删除
                        remove(position);
                        localMedias.remove(position);
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
