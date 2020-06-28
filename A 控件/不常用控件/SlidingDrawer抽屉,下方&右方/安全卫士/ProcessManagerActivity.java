    @BindView(drawer)
    SlidingDrawer mDrawer;                   //����

public class ProcessManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_manager);
        ButterKnife.bind(this);

        //��ʼ����ͷ����
        initArrowAnim();

        //��������򿪵��¼�
        mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                //���뱻��
                ivArrow1.setImageResource(R.drawable.drawer_arrow_down);
                ivArrow2.setImageResource(R.drawable.drawer_arrow_down);

                //ֹͣ����
                ivArrow1.clearAnimation();
                ivArrow2.clearAnimation();
            }
        });
		//��������رյ��¼�
        mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                //����ر�
                ivArrow1.setImageResource(R.drawable.drawer_arrow_up);
                ivArrow2.setImageResource(R.drawable.drawer_arrow_up);

                //��ʼ����
                initArrowAnim();
            }
        });
    }

    //��ʼ����ͷ����
    private void initArrowAnim() {
        //���䶯��
        AlphaAnimation arrow1 = new AlphaAnimation(0.2f, 1);
        //����ʱ��
        arrow1.setDuration(500);
        //�ظ�����,infinite:����
        arrow1.setRepeatCount(Animation.INFINITE);
        //�ظ�ģʽ:����
        arrow1.setRepeatMode(Animation.REVERSE);
        //������ͷ1�Ķ���
        ivArrow1.startAnimation(arrow1);
        
        //���䶯��
        AlphaAnimation arrow2 = new AlphaAnimation(1, 0.2f);
        //����ʱ��
        arrow2.setDuration(500);
        //��������
        arrow2.setRepeatCount(Animation.INFINITE);
        //����ģʽ
        arrow2.setRepeatMode(Animation.REVERSE);
        //������ͷ2�Ķ���
        ivArrow2.startAnimation(arrow2);
    }
