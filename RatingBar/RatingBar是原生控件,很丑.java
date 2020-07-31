maxHeight和minHeight可以根据我们图片像素或者其他参考值来设定。

<android.support.v7.widget.AppCompatRatingBar
android:id="@+id/rb_score"
android:layout_width="wrap_content"
android:layout_height="wrap_content"

style="@style/Base.Widget.AppCompat.RatingBar"//可手动选择星星(3个中最大星星)
style="?android:attr/ratingBarStyleIndicator"//大星星
style="?android:attr/ratingBarStyleSmall"//小星星,很小
还有@android:style/...没试,可点击style查看源码,自己修改,仿照自定义SeekBar

android:isIndicator="true"		//是否用作指示，用户无法更改，默认false
android:maxHeight="24dp"		//评分条的最大高度
android:minHeight="24dp"		//最小高度(一般=maxHeight,如果写太小,星星看不见)
android:progressDrawable="@drawable/ratingbar_star"//ratingbar_star要写成<layer-list,见自定义ProgressBar
android:numStars="5"			//显示的星型数量，必须是一个整形值，像“100”
android:rating="2.5"			//默认的评分，必须是浮点类型，像“1.2”
android:stepSize="0.5"			//评分的步长，必须是浮点类型，像“1.2”
android:theme="@style/RatingBar_CustomColor"//自定义颜色
/>
//styles_rating_bar_color.xml, 自定义颜色
<resources>
    <!--自定义RatingBar Color, 在RatingBar中添加属性: android:theme="@style/RatingBar_CustomColor"-->
    <style name="RatingBar_CustomColor" parent="@android:style/Widget.Holo.RatingBar.Indicator">

        <!--Background Color, 松开后背景色-->
        <item name="colorControlNormal">@color/red_dc143c</item>

        <!--Progress Color, 按下时颜色-->
        <item name="colorControlActivated">@color/red_dc143c</item>
    </style>
</resources>



获取星星: public float getRating();
设置星星: ratingBar.setRating(float rating);


//监听星星改变
ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                
            }
        });

//给RatingBar设置进度动画(View的方法)
ratingBar.startAnimation(new Animation(){

    public Animation() {
        setDuration(ratingBar.getNumStars() * 4 * getResources().getInteger(android.R.integer.config_longAnimTime/**500*/));
        setInterpolator(new LinearInterpolator());
        setRepeatCount(Animation.INFINITE);
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int progress = Math.round(interpolatedTime * ratingBar.getMax());
        ratingBar.setProgress(progress);
    }
    @Override
    public boolean willChangeTransformationMatrix() {
        return false;
    }
    @Override
    public boolean willChangeBounds() {
        return false;
    }
});