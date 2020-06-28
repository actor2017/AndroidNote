iv.setWillNotDraw(true);//清除画面


<ImageView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:scaleType="centerInside"	//缩放类型, 默认?
    android：adjustViewBounds="true"	//是否保持原图的长宽比
    android:adjustViewBounds="true"	//保持宽高比
    android:tint="@android:color/white"	//将图片染成白色
    android:tindMode=""			//见: 图片和颜色.html
/>


XML定义里的android:adjustViewBounds="true"会将这个ImageView的scaleType设为fitCenter。不过这个fitCenter会被后面定义的scaleType属性覆盖（如果定义了的话），除非在Java代码里再次显示调用setAdjustViewBounds(true)。

如果设置的layout_width与layout_height都是定值，那么设置adjustViewBounds是没有效果的，ImageView将始终是设定的定值的宽高。

如果设置的layout_width与layout_height都是wrap_content,那么设置adjustViewBounds是没有意义的，因为ImageView将始终与图片拥有相同的宽高比（但是并不是相同的宽高值，通常都会放大一些）。

如果两者中一个是定值，一个是wrap_content，比如layout_width="100px",layout_height="wrap_content"时，ImageView的宽将始终是100px，而高则分两种情况：
（1）当图片的宽小于100px时，layout_height将与图片的高相同，即图片不会缩放，完整显示在ImageView中，ImageView高度与图片实际高度相同。图片没有占满ImageView，ImageView中有空白。
（2）当图片的宽大于等于100px时，此时ImageView将与图片拥有相同的宽高比，因此ImageView的layout_height值为：100除以图片的宽高比。比如图片是500X500的，那么layout_height是100。图片将保持宽高比缩放，完整显示在ImageView中，并且完全占满ImageView。
