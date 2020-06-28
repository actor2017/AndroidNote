package com.itheima.mobileplayer.ui.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itheima.mobileplayer.R;
import com.itheima.mobileplayer.adapter.AudioListAdapter;
import com.itheima.mobileplayer.bean.AudioBean;
import com.itheima.mobileplayer.ui.activity.AudioPlayerActivity;

import java.util.ArrayList;

/**
 * Description:音乐播放列表
 * Copyright  : Copyright (c) 2016
 * Company    : 传智播客
 * Author     : 独孤求败
 * Date       : 2016/10/14 10:04
 */
public class AudioFragment extends BaseFragment {
    private ListView lv;
    private ArrayList<AudioBean> audioList;

    @Override
    public int getLayout() {
        return R.layout.fragment_audio_list;
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
         * import android.provider.MediaStore.Audio.Media;
         */
        ContentResolver resolver = getActivity().getContentResolver();
        final Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI,
                //                 id         日期         标题       音频时长(ms)   大小(byte)
                new String[]{Media._ID, Media.DATA, Media.DISPLAY_NAME, Media.DURATION, Media.SIZE},
                null,
                null,
                null);

        audioList = AudioBean.getBeanFormCursor(cursor);
        lv.setAdapter(new AudioListAdapter(getActivity(), audioList));
        for (int i = 0; i < audioList.size(); i++) {
            System.out.println(audioList.get(i));
        }

        //点击音乐的条
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), AudioPlayerActivity.class);
                intent.putExtra("list", audioList);
                intent.putExtra("Position",position);
                startActivity(intent);
            }
        });

        //        while(cursor.moveToNext()){
        //            AudioBean audioBean = AudioBean.newInstanceFromCursor(cursor);
        //            LogUtils.e(TAG, audioBean.toString());
        //        }
    }

    @Override
    public void initListener() {

    }
}
