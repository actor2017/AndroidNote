1.�̳й�ϵ
RadioGroup extends LinearLayout
RadioButton extends CompoundButton extends Button implements Checkable

2.XML��
<RadioGroup
	android:id="@+id/rg_answer"
	android:orientation="vertical"	//RadioButton���з���,Ĭ��vertical
	android:checkedButton="@id/rb_b"//Ĭ��ѡ����һ��
	android:layout_width="wrap_content"
	android:layout_height="wrap_content">

	<!--û���������ֵ���ߵ�����...-->
	<RadioButton
		android:id="@+id/rb_a"
		android��button="@null"//ȥ�����ԲȦͼ�� radioButton.setButtonDrawable(new BitmapDrawable(null));
		android:button="@drawable/b_btn1"	//CompoundButton���� �Զ���logoͼƬ, selectorû�Թ�
		android:buttonTint="@color/color_red"//CompoundButton���� button��ɫ, ��ʹbutton��ͼƬҲ�ܸı�ͼƬ��ɫ...��ôʵ�ֵ�??��Դ��...
		android:buttonTintMode="src_in"		//CompoundButton���� ���ģʽ����Ӧ�ð�ť��ͼ����ɫ��(button��buttonTint��ɫ��Ϸ�ʽ)
		android:checked="true"				//CompoundButton���� �Ƿ�ѡ��
		android:text="1934��"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"/>

	<RadioButton
		android:id="@+id/rb_b"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"/>
</RadioGroup>

3.������
//��RadioGroupĬ��ѡ���0��
radioGroup.check(R.id.rb_home);
radioGroup.clearCheck();//���ѡ����

//RadioGroup����check����
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


��RecyclerView��Ӧ��: ����С����bug����
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
            helper.setText(R.id.tv_num, String.format(Locale.getDefault(), "%d. ", position + 1))//���
                    .setText(R.id.tv_question, item.getQuestion())
                    .setText(R.id.tv_answer_a, item.getAnswera())
                    .setText(R.id.tv_answer_b, item.getAnswerb())
                    .setText(R.id.tv_answer_c, item.getAnswerc())
                    .setText(R.id.tv_answer_d, item.getAnswerd())
                    .getView(R.id.ll_right_answer).setVisibility(isHaveTested ? View.VISIBLE : View.GONE);//��ȷ��

            String answer = item.getAnswer();//�Լ�ѡ��Ĵ�
            if (answer != null) {
                helper.setText(R.id.tv_right_answer, item.getCorrect());//��ȷ��
                int resId = -1;
                if (answer.equals(item.getAnswera())) resId = R.id.rb_a;
                if (answer.equals(item.getAnswerb())) resId = R.id.rb_b;
                if (answer.equals(item.getAnswerc())) resId = R.id.rb_c;
                if (answer.equals(item.getAnswerd())) resId = R.id.rb_d;
                //��������ķ�������...ԭ��δ֪����
//                rbA.setChecked(answer.equals(item.getAnswera()));
//                rbB.setChecked(answer.equals(item.getAnswerb()));
//                rbC.setChecked(answer.equals(item.getAnswerc()));
//                rbD.setChecked(answer.equals(item.getAnswerd()));
                //����Ҫ�������ò��С���
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