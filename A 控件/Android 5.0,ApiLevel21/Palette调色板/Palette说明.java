compile 'com.android.support:palette-v7:25.1.0'

int color = bitmap.getPixel(x,y);//只能单点获取颜色

Palette p = Palette.generate(Bitmap bitmap);//如果操作本来就属于后台线程

Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {//主线程:异步
		@Override
        public void onGenerated(Palette palette) {
			// 有活力的颜色
            v.setBackgroundColor(palette.getVibrantColor(Color.BLACK));//如果没有这个颜色,默认返回BLACK
            // 有活力的暗色
            vd.setBackgroundColor(palette.getDarkVibrantColor(Color.BLACK));
            // 有活力的亮色
            vl.setBackgroundColor(palette.getLightVibrantColor(Color.BLACK));
            // 柔和的颜色
            m.setBackgroundColor(palette.getMutedColor(Color.BLACK));
            // 柔和的暗色
            md.setBackgroundColor(palette.getDarkMutedColor(Color.BLACK));
            // 柔和的亮色
            ml.setBackgroundColor(palette.getLightMutedColor(Color.BLACK));
	}
});
