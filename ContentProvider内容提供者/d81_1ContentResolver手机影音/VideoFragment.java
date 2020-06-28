package com.itheima.mobileplayer.ui.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore.Video.Media;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itheima.mobileplayer.R;
import com.itheima.mobileplayer.adapter.VideoListAdapter;
import com.itheima.mobileplayer.bean.VideoBean;
import com.itheima.mobileplayer.ui.activity.VideoPlayerActivity;
import com.itheima.mobileplayer.ui.activity.VitamioVideoPlayerActivity;

import java.util.ArrayList;

/**
 * Description:视频播放列表
 * Copyright  : Copyright (c) 2016
 * Company    : 传智播客
 * Author     : 独孤求败
 * Date       : 2016/10/14 10:06
 */
public class VideoFragment extends BaseFragment {

    private ListView lv;

    @Override
    public int getLayout() {
        return R.layout.fragment_video_list;
    }

    @Override
    public void initView() {
        lv = findViewById(R.id.lv);
    }

    @Override
    public void initData() {
        /**
         * 利用内容提供者查询多媒体数据库里的数据(音频/视频/图片/文件)
         * SD卡数据库在:data/data/com.android.providers.media/databases/external.db/files(表)
         * 内部存储数据库在:data/data/com.android.providers.media/databases/internal.db
         */
        ContentResolver resolver = activity.getContentResolver();

        //CursorAdapter使用Cursor的时候,查询的时候必须写_id列，不写报无效的参数异常
        /**
         * @NonNull Uri uri,                //uri
         * @Nullable String[] projection,   //你要查哪些数据
         * @Nullable String selection,      //
         * @Nullable String[] selectionArgs,//
         * @Nullable String sortOrder       //排序
         * import android.provider.MediaStore.Video.Media;
         */
        Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI,
                //                 id         日期         标题       视频时长(ms)   大小(byte)
                new String[]{Media._ID, Media.DATA, Media.TITLE, Media.DURATION, Media.SIZE},
                null,
                null,
                null);

        //设置数据适配
        VideoListAdapter videoListAdapter = new VideoListAdapter(getActivity(), cursor);
        final ArrayList<VideoBean> list = VideoBean.getListFromCursor(cursor);
        lv.setAdapter(videoListAdapter);

        //设置条目点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (false) {
                    intent = new Intent(activity, VideoPlayerActivity.class);
                } else intent = new Intent(activity, VitamioVideoPlayerActivity.class);
                intent.putExtra("list", list);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initListener() {

    }
}
