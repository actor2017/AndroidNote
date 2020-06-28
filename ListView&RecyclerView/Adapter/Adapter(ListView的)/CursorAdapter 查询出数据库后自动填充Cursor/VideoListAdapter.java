package com.itheima.mobileplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.itheima.mobileplayer.R;
import com.itheima.mobileplayer.bean.VideoBean;
import com.itheima.mobileplayer.utils.StringUtils;

/**
 * Description:视频播放列表的数据适配器
 * Copyright  : Copyright (c) 2016
 * Company    : 传智播客
 * Author     : 独孤求败
 * Date       : 2016/10/14 14:48
 */
public class VideoListAdapter extends CursorAdapter {

    public VideoListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    /**
     * 加载listview条目的布局
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.adapter_list_item, null);
        view.setTag(new ViewHolder(view));
        return view;
    }

    /**
     * 给view的控件设置数据
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        VideoBean bean = VideoBean.newInstanceFromCursor(cursor);

        ViewHolder holder = (ViewHolder) view.getTag();

        String size = Formatter.formatFileSize(context, bean.getSize());
        holder.tvSize.setText(size);//大小
        holder.tvTitle.setText(bean.getTitle());//标题
        holder.tvTime.setText(StringUtils.formatDuration(bean.getTime()));//时长转换成时分秒
    }

    class ViewHolder {
        private TextView tvTitle, tvTime, tvSize;

        public ViewHolder(View view) {
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvSize = (TextView) view.findViewById(R.id.tv_size);
        }
    }
}
