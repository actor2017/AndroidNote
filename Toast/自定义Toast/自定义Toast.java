������Դ:������ʾ���Զ���Toast
    //չʾ�Զ�����˾(�鿴Toast,MakeText,show��Դ��)
    /**
     * ����2��ȫ�ֱ���:
     * private WindowManager mWM;
     * private TextView mView;
     */
    private void AddressToast(String address) {
        //���ڹ�����
        //����: ��׿ϵͳ��߼���Ĳ���, ���н��涼���ڴ�����ʾ, �ڴ�������ʾactivity, dialog, ״̬��
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        //��ʼ�����ֲ���(��δ�Toast�ĵ�366�г���)
        //��ʼ�����ֵĿ��, λ�õ���Ϣ
        final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//��߰�������
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//��߰�������
        mParams.format = PixelFormat.TRANSLUCENT;//��ʾ��ʽ, Ĭ�Ͼ���
        //���ֶ���(�����ָToast�ĵ��뵭������,��Ҫ�ɲ�Ҫ)
        //mParams.windowAnimations = com.android.internal.R.style.Animation_Toast;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;//��������(Toast������)
        mParams.setTitle("Toast");//���ڱ���(��Ҫ�ɲ�Ҫ)
        //���ڱ��, �ƶ�ĳЩ����
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON//������Ļ֮��
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE //û�н���
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;//���ܴ���

        //��ʼ�����ֶ���(���ǳ���,��д��)
        mView = new TextView(this);
        mView.setText(address);
        mView.setTextColor(Color.RED);
        mView.setTextSize(30);

        //��������Ӳ��ֶ���(����,Toast��425��)
        mWM.addView(mView, mParams);
    }
}
