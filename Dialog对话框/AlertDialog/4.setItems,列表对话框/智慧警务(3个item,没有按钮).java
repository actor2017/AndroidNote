    private AlertDialog.Builder alertDialog;
    private Intent mSourceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_result);
        ButterKnife.bind(this);

        alertDialog = new AlertDialog.Builder(this);
        initAlertDialog(alertDialog);
    }

    private void initAlertDialog(AlertDialog.Builder alertDialog) {
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueCallback != null) {
//                    mValueCallback.onReceiveValue(null);
//                    mValueCallback = null;
                }

                if(mValueCallbackAndroid5!=null) {
//                    mValueCallbackAndroid5.onReceiveValue(null);
//                    mValueCallbackAndroid5 = null;
                }
            }
        });
        alertDialog.setTitle("ѡ��ͼƬ");
        alertDialog.setItems(new String[]{"�������ѡ��","����","ȡ��"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {//�������ѡ��
                            mSourceIntent = ImageUtil.choosePicture();
                            startActivityForResult(mSourceIntent, REQUEST_CODE_PICK_IMAGE);
                        } else if(which==1) {//����
                            mSourceIntent = ImageUtil.takeBigPicture();
                            startActivityForResult(mSourceIntent, REQUEST_CODE_IMAGE_CAPTURE);
                        } else {//ȡ��
                            dialog.dismiss();
                        }
                    }
                }
        );
    }
