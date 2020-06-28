<declare-styleable name="pickerview"><attr name="wheelview_gravity">
            <enum name="center" value="17"/>
            <enum name="left" value="3"/>
            <enum name="right" value="5"/>
        </attr>
<attr format="dimension" name="wheelview_textSize"/>
<attr format="color" name="wheelview_textColorOut"/>
<attr format="color" name="wheelview_textColorCenter"/>
<attr format="color" name="wheelview_dividerColor"/>
<attr format="float" name="wheelview_lineSpacingMultiplier"/>


<com.contrarywind.view.WheelView
	android:id="@+id/options1"
	android:layout_width="match_parent"
	android:layout_height="200dp" />

private WheelView    wheelView;
private List<String> sdf = new ArrayList<>();

sdf.add("sdfsdfsdf");
sdf.add("sdfsdf123sdf");
sdf.add("sdfsdfs234234df");

wheelView.postDelayed(new Runnable() {
	@Override
	public void run() {
		/**
		 * date的类型: IPickerViewData, Integer, toString()
		 * @see WheelView#getContentText(Object)
		 */
		wheelView.setAdapter(new ArrayWheelAdapter<>(sdf));
		wheelView.setCurrentItem(0);//初始化时显示的数据, 默认第0条
		wheelView.setIsOptions(true);//不设置就不显示, 默认false, 意义?
		wheelView.setCyclic(false);//不循环滚动, 默认true
        wheelView.requestLayout();//网络请求/延时 设置Adapter后调用requestLayout才显示数据
	}
}, 1000);


