1.继承关系
RadioGroup extends LinearLayout
RadioButton extends CompoundButton extends Button implements Checkable

2.XML中
<RadioGroup
	android:id="@+id/rg_answer"
	android:orientation="vertical"	//RadioButton排列方向,默认vertical
	android:checkedButton="@id/rb_b"//默认选中哪一项
	android:layout_width="wrap_content"
	android:layout_height="wrap_content">

	<!--没有设置文字到左边的属性...-->
	<RadioButton
		android:id="@+id/rb_a"
		android：button="@null"//去掉左侧圆圈图标 radioButton.setButtonDrawable(new BitmapDrawable(null));
		android:button="@drawable/b_btn1"	//CompoundButton属性 自定义logo图片, selector没试过
		android:buttonTint="@color/color_red"//CompoundButton属性 button颜色, 即使button是图片也能改变图片颜色...怎么实现的??见源码...
		android:buttonTintMode="src_in"		//CompoundButton属性 混合模式用于应用按钮的图形着色。(button和buttonTint颜色混合方式)
		android:checked="true"				//CompoundButton属性 是否选中
		android:text="1934年"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"/>

	<RadioButton
		android:id="@+id/rb_b"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"/>
</RadioGroup>

3.代码中
//让RadioGroup默认选择第0项
radioGroup.check(R.id.rb_home);
radioGroup.clearCheck();//清空选中项

//RadioGroup设置check监听
radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_all:
                break;
            case R.id.radio_button_mine:
                break;
        }
    }
});


在RecyclerView中应用: ★★★小心有bug★★★
    private void setAdapter() {
        myAdapter = new MyAdapter(R.layout.item_examine_layout, items);
        myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String answer = null;
                switch (view.getId()) {
                    case R.id.rb_a:
                        answer = items.get(position).getAnswera();
                        break;
                    case R.id.rb_b:
                        answer = items.get(position).getAnswerb();
                        break;
                    case R.id.rb_c:
                        answer = items.get(position).getAnswerc();
                        break;
                    case R.id.rb_d:
                        answer = items.get(position).getAnswerd();
                        break;
                }
                items.get(position).setAnswer(answer);
            }
        });
        recyclerView.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseQuickAdapter<ExamineBean.DataBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<ExamineBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ExamineBean.DataBean item) {
            int position = helper.getAdapterPosition();
            RadioGroup rgAnswer = helper.getView(R.id.rg_answer);
            helper.setText(R.id.tv_num, String.format(Locale.getDefault(), "%d. ", position + 1))//序号
                    .setText(R.id.tv_question, item.getQuestion())
                    .setText(R.id.tv_answer_a, item.getAnswera())
                    .setText(R.id.tv_answer_b, item.getAnswerb())
                    .setText(R.id.tv_answer_c, item.getAnswerc())
                    .setText(R.id.tv_answer_d, item.getAnswerd())
                    .getView(R.id.ll_right_answer).setVisibility(isHaveTested ? View.VISIBLE : View.GONE);//正确答案

            String answer = item.getAnswer();//自己选择的答案
            if (answer != null) {
                helper.setText(R.id.tv_right_answer, item.getCorrect());//正确答案
                int resId = -1;
                if (answer.equals(item.getAnswera())) resId = R.id.rb_a;
                if (answer.equals(item.getAnswerb())) resId = R.id.rb_b;
                if (answer.equals(item.getAnswerc())) resId = R.id.rb_c;
                if (answer.equals(item.getAnswerd())) resId = R.id.rb_d;
                //★★★下面的方法不行...原因未知★★★
//                rbA.setChecked(answer.equals(item.getAnswera()));
//                rbB.setChecked(answer.equals(item.getAnswerb()));
//                rbC.setChecked(answer.equals(item.getAnswerc()));
//                rbD.setChecked(answer.equals(item.getAnswerd()));
                //★★★要这样设置才行★★★
                if (resId != -1) {
                    rgAnswer.check(resId);
                } else rgAnswer.clearCheck();
            } else rgAnswer.clearCheck();
            if (isHaveTested) {
                helper.getView(R.id.rb_a).setEnabled(false);
                helper.getView(R.id.rb_b).setEnabled(false);
                helper.getView(R.id.rb_c).setEnabled(false);
                helper.getView(R.id.rb_d).setEnabled(false);
//                rgAnswer.setEnabled(false);
            } else {
                helper.addOnClickListener(R.id.rb_a)
                        .addOnClickListener(R.id.rb_b)
                        .addOnClickListener(R.id.rb_c)
                        .addOnClickListener(R.id.rb_d);
            }
        }
    }