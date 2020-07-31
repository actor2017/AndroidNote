maxHeight��minHeight���Ը�������ͼƬ���ػ��������ο�ֵ���趨��

<android.support.v7.widget.AppCompatRatingBar
android:id="@+id/rb_score"
android:layout_width="wrap_content"
android:layout_height="wrap_content"

style="@style/Base.Widget.AppCompat.RatingBar"//���ֶ�ѡ������(3�����������)
style="?android:attr/ratingBarStyleIndicator"//������
style="?android:attr/ratingBarStyleSmall"//С����,��С
����@android:style/...û��,�ɵ��style�鿴Դ��,�Լ��޸�,�����Զ���SeekBar

android:isIndicator="true"		//�Ƿ�����ָʾ���û��޷����ģ�Ĭ��false
android:maxHeight="24dp"		//�����������߶�
android:minHeight="24dp"		//��С�߶�(һ��=maxHeight,���д̫С,���ǿ�����)
android:progressDrawable="@drawable/ratingbar_star"//ratingbar_starҪд��<layer-list,���Զ���ProgressBar
android:numStars="5"			//��ʾ������������������һ������ֵ����100��
android:rating="2.5"			//Ĭ�ϵ����֣������Ǹ������ͣ���1.2��
android:stepSize="0.5"			//���ֵĲ����������Ǹ������ͣ���1.2��
android:theme="@style/RatingBar_CustomColor"//�Զ�����ɫ
/>
//styles_rating_bar_color.xml, �Զ�����ɫ
<resources>
    <!--�Զ���RatingBar Color, ��RatingBar���������: android:theme="@style/RatingBar_CustomColor"-->
    <style name="RatingBar_CustomColor" parent="@android:style/Widget.Holo.RatingBar.Indicator">

        <!--Background Color, �ɿ��󱳾�ɫ-->
        <item name="colorControlNormal">@color/red_dc143c</item>

        <!--Progress Color, ����ʱ��ɫ-->
        <item name="colorControlActivated">@color/red_dc143c</item>
    </style>
</resources>



��ȡ����: public float getRating();
��������: ratingBar.setRating(float rating);


//�������Ǹı�
ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                
            }
        });

//��RatingBar���ý��ȶ���(View�ķ���)
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