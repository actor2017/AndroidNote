package com.ly.meeting.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.style.ImageSpan;

public class RichImageSpan extends ImageSpan {
    private Uri mUri;
    public RichImageSpan(Context context, Bitmap b, Uri uri) {
        super(context, b);
        mUri = uri;
    }

    /**
     * 必须重写此方法,否则img获取的src=null
     * @return
     */
    @Override
    public String getSource() {
        return mUri.toString();
    }
}
