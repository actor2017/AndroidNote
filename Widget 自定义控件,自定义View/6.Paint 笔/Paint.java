
Paint paint = new Paint();
Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//光滑的线条, 抗锯齿

paint.setAntiAlias(true);//光滑的线条, 抗锯齿
paint.setColor(Color.RED);
paint.setTextSize(1L);//设置"文字"大小
paint.measureText("Hello World");//63.0测量字符串的长度

paint.setStyle(Paint.Style.FILL);//实心
paint.setStyle(Paint.Style.STROKE);//空心,填充模式 - 描边
paint.setStyle(Paint.Style.FILL_AND_STROKE);//??

paint.setStrokeWidth(20);//设置画笔宽度

paint.setAlpha(255);//[0..255], 255代表完全不透明; 0代表完全透明
paint.getAlpha();//当前透明度

paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//PorterDuffXfermode:图形混合模式
