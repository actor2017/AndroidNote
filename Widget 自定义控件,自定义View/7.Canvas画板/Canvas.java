
canvas.drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint);//画一个弧,三点钟方向为0°.useCenter:是否经过圆心
canvas.drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, Paint paint);

canvas.drawBitmap(Bitmap bitmap, float left, float top, Paint paint);

canvas.drawCircle(x, y, r, paint);//绘制一个圆; 参1,2:圆心坐标; 参3:半径

canvas.drawLine(startX, startY, endX, endY, paint);//绘制一条线

canvas.drawRect(startX, startY, endX, endY, paint);//画一个矩形

canvas.drawRoundRect(RectF rect, float rx, float ry, Paint paint);//画一个圆角矩形，大小为rectF，20，20分表表示左边圆角的半径和右边圆角的半径
canvas.drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint);

canvas.drawText(char[] text, int index, int count, float x, float y, Paint paint);
canvas.drawText(String text, float x, float y, Paint paint);//text左下角坐标
canvas.drawText(String text, int start, int end, float x, float y, Paint paint);
canvas.drawText(CharSequence text, int start, int end, float x, float y, Paint paint);


canvas.drawPath(path, paint);//path

canvas.scale(1, -1);                         	// <-- 注意 翻转y坐标轴?见path图8