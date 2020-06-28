
    //��ʾ���ضԻ���
    private void downLoad() {
        /**
         * ��ʾ�Ի���,������,������ɺ�װ
         */
        //1.��ȡһ�����ȶԻ���
        final ProgressDialog dialog = new ProgressDialog(this);
        //2.���öԻ���ı���
        dialog.setTitle("ƴ��������...");
        //3.���öԻ������ʽ:ˮƽ
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //4.���ý���
        OkHttpUtils.get().url(downloadUrl).build().execute(new FileCallBack(Environment
                .getExternalStorageDirectory().getAbsolutePath(), "MobileSafe.apk") {

            //���ý�������ʾ
            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                dialog.setProgress((int) (progress * 100));
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                e.printStackTrace();
                Toast.makeText(HomeActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
            }

            //���سɹ���ص�,���밲װ����
            @Override
            public void onResponse(File file, int i) {
                //1.������ʧ
                dialog.dismiss();
                //2.���밲װ����
                //��װapk
                //����ϵͳ��װҳ����а�װ
                //Intent, action: ��ʽ��ͼ
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }
        });

        //���ع�����,������Ի��������/���ؼ���ʱ��,���ò���ȡ���Ի���������������
        dialog.setCancelable(false);
        //�������ַ�������˼:�����ؼ����Թر�dialog
        dialog.setCanceledOnTouchOutside(false);
        //ע�������д���,�����޷���ʾ�Ի���
        dialog.show();
    }