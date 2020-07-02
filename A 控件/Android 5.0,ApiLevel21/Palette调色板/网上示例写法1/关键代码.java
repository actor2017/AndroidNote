//创建Palette对象，并获取图片的颜色值

// 用来提取颜色的Bitmap
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), PaletteFragment.getBackgroundBitmapPosition(position));
// Palette的部分
Palette.Builder builder = Palette.from(bitmap);
builder.generate(new Palette.PaletteAsyncListener() {@Override public void onGenerated(Palette palette) {
        //获取到充满活力的这种色调
        Palette.Swatch vibrant = palette.getVibrantSwatch();
        //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
        toolbar_tab.setBackgroundColor(vibrant.getRgb());
        toolbar_tab.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
        toolbar.setBackgroundColor(vibrant.getRgb());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(colorBurn(vibrant.getRgb()));
            window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
        }
    }
});