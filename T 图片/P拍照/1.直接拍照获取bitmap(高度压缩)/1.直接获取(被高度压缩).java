    /**
     * ����
     *
     * @param needSaveInDir �Ƿ���Ҫ���浽ָ��Ŀ¼
     * @param picName       �����Ҫ���浽ָ��Ŀ¼,��ô����Ƭȡ�����ְ�,��:1.jpg or 1.png.�������Ҫ���浽ָ��Ŀ¼,��null��
     * @param requestCode   ������
     */
    public void takePhoto(boolean needSaveInDir, @Nullable String picName, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (needSaveInDir) {
            if (picName != null) {
                if (!picName.toLowerCase().endsWith(".jpg") && !picName.toLowerCase().endsWith(""
                        + ".png")) {
                    picName += ".jpg";
                }
            } else {
                toast("takePhoto:����ʱ������Ƭ����Ϊnull");
                return;
            }
            File file = new File(BitmapToPngFile.getDefaultPath(getClass().getSimpleName() + picName));
            Uri imageUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }
        startActivityForResult(intent, requestCode);
    }


protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
     if (data == null) {
       return;
     } else { //����
       Bundle extras = data.getExtras();
       if (extras != null) {
         Bitmap bm = extras.getParcelable("data");
       }
     }
   }
