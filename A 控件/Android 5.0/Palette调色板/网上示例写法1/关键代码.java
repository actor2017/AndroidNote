//����Palette���󣬲���ȡͼƬ����ɫֵ

// ������ȡ��ɫ��Bitmap
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), PaletteFragment.getBackgroundBitmapPosition(position));
// Palette�Ĳ���
Palette.Builder builder = Palette.from(bitmap);
builder.generate(new Palette.PaletteAsyncListener() {@Override public void onGenerated(Palette palette) {
        //��ȡ����������������ɫ��
        Palette.Swatch vibrant = palette.getVibrantSwatch();
        //���ݵ�ɫ��Palette��ȡ��ͼƬ�е���ɫ���õ�toolbar��tab�б���������ȣ�ʹ����UI������ɫͳһ
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