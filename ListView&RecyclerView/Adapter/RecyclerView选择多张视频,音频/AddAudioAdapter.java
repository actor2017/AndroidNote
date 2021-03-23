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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 添加音频, 目前只有选择音频功能, 使用示例: {@link AddLocalMediaAble}
 *
 * @author : 李大发
 * date       : 2020/9/18 on 20:28
 * @version 1.0
 */
public class AddAudioAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> implements AddLocalMediaAble<String> {

    public static final int TYPE_RECORD_AUDIO = 0;//录制音频
    public static final int TYPE_SELECT_AUDIO = 1;//选择音频
    public static final int TYPE_RECORD_SELECT_AUDIO = 2;//录制音频&选择音频

    private int maxPic;//最多选择多少个
    private int actiontype = 1;//动作类型

    private List<LocalMedia>    localMedias    = new ArrayList<>();
    private Map<String, String> uploads        = new LinkedHashMap<>();
    private Map<String, String> alreadyUploads = new LinkedHashMap<>();

    /**
     * @param maxPic 最多选择多少个音频
     */
    public AddAudioAdapter(int maxPic, @IntRange(from = TYPE_RECORD_AUDIO, to = TYPE_RECORD_SELECT_AUDIO) int type) {
        super(R.layout.item_pic_add);
        this.maxPic = maxPic;
        this.actiontype = type;
        initAddLocalMediaAble();
        addData(EXTRA_LAST_MEDIA);//添加一个+号

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
                                ToastUtils.showShort(TextUtils2.getStringFormat("最多选择%d个", maxPic));
                            } else {
                                Activity topActivity = ActivityUtils.getTopActivity();
                                if (topActivity == null) {
                                    return;
                                }
                                switch (actiontype) {
                                    case TYPE_RECORD_AUDIO://录制音频
                                        //调用系统录音
//                                        PictureSelectorUtils.recoreAudio(mContext, null, null);
//                                        break;
                                    case TYPE_SELECT_AUDIO://选择音频
                                        PictureSelectorUtils.selectAudio(topActivity, maxPic, localMedias, new OnResultCallbackListener<LocalMedia>() {
                                            @Override
                                            public void onResult(List<LocalMedia> result) {
                                                localMedias.clear();
                                                localMedias.addAll(result);
                                                result.add(EXTRA_LAST_MEDIA);
                                                setNewData(result);
                                            }
                                            @Override
                                            public void onCancel() {
                                            }
                                        });
                                        break;
                                    case TYPE_RECORD_SELECT_AUDIO://录制音频&选择音频
                                        PictureSelectorUtils.selectAudio(topActivity, maxPic, localMedias, new OnResultCallbackListener<LocalMedia>() {
                                            @Override
                                            public void onResult(List<LocalMedia> result) {
                                                localMedias.clear();
                                                localMedias.addAll(result);
                                                result.add(EXTRA_LAST_MEDIA);
                                                setNewData(result);
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
                            if (topActivity != null) {
                                LocalMedia item = getItem(position);
                                if (item != null) {
                                    PictureSelectorUtils.previewAudio(topActivity, item.getRealPath());
                                }
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
    protected void convert(@NonNull BaseViewHolder helper, LocalMedia item) {
        //是否是最后一个pos
        boolean isLastPos = helper.getAdapterPosition() == getItemCount() - 1;
        ImageView iv = helper.setGone(R.id.iv_delete, !isLastPos)
                .addOnClickListener(R.id.iv, R.id.iv_delete)
                .getView(R.id.iv);
        if (isLastPos) {
            Glide.with(iv).load(R.drawable.audio_gray).into(iv);
        } else {
            Glide.with(iv).load(R.drawable.headset_gray).into(iv);
        }
    }

    @Override
    public List<LocalMedia> getLocalMedias() {
        return localMedias;
    }

    @Override
    public Map<String, String> getUploads() {
        return uploads;
    }

    @Override
    public Map<String, String> getAlreadyUploads() {
        return alreadyUploads;
    }
}
