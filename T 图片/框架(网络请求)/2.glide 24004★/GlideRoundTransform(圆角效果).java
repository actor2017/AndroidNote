package com.kuchuan.education.view.impl.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 示例:
 * Glide
 *     .with(this)
 *     .load(url)
 *     .transform(new GlideRoundTransform(this,30)) // 指定自定义图片样式
 *     .into(imageView);
 */
private class GlideRoundTransform extends BitmapTransformation {

	private  float radius = 0f;//圆角弧度,单位px

	public GlideRoundTransform(Context context) {
		this(context, 4);
	}

	public GlideRoundTransform(Context context, int dp) {	//dp:圆角弧度
		super(context);
		this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
	}

	@Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
		return roundCrop(pool, toTransform);
	}

	private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
		if (source == null) return null;

		Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
		canvas.drawRoundRect(rectF, radius, radius, paint);
		return result;
	}

	@Override public String getId() {
		return getClass().getName() + Math.round(radius);
	}
}