    /**
     * 拍照
     *
     * @param needSaveInDir 是否需要保存到指定目录
     * @param picName       如果需要保存到指定目录,那么给照片取个名字吧,例:1.jpg or 1.png.如果不需要保存到指定目录,传null吧
     * @param requestCode   请求码
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
                toast("takePhoto:拍照时保存照片名字为null");
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
     } else { //拍照
       Bundle extras = data.getExtras();
       if (extras != null) {
         Bitmap bm = extras.getParcelable("data");
       }
     }
   }
