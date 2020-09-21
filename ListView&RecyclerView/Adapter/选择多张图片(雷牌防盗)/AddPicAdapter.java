package com.rm.lpsj.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.myandroidframework.utils.album.AlbumUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rm.lpsj.R;
import com.runman.baselibrary.base.AddPicBean;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

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

    private int maxPic;

    public AddPicAdapter(int maxPic, List<AddPicBean> data) {
        super(R.layout.item_pic_add, data);
        this.maxPic = maxPic;

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //是否是最后一个pos
                boolean isLastPos = position == getItemCount() - 1;
                switch (view.getId()) {
                    case R.id.iv://添加
                        if (isLastPos) {
                            //判断是否能选择更多
                            if (AddPicAdapter.super.getItemCount() > maxPic) {
                                ToastUtils.showShort(TextUtils2.getStringFormat("最多选择%d张", maxPic));
                            } else {
                                AlbumUtils.selectImage(mContext, true, new Action<ArrayList<AlbumFile>>() {
                                    @Override
                                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                                        addData(position, new AddPicBean(result.get(0).getPath()));
                                    }
                                });
                            }
                        } else {//预览
                            List<AddPicBean> data = getData();
                            ArrayList<String> items = new ArrayList<>();
                            for (AddPicBean datum : data) {
                                items.add(datum.picPath);
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
            Glide.with(iv).load(R.drawable.add_gray_8a8a8a).into(iv);
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
}
