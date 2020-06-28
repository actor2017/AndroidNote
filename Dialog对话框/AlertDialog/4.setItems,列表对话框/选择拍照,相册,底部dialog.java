
    /**
     * 在屏幕底部的AlertDialog,用于让用户选择拍照/从手机选择照片.
     * 缺点:宽度不是全屏,item文字没有居中,如果要实现宽度全屏,用Dialog.
     */
    private AlertDialog choosePicSourceDialog;
    public AlertDialog getChoosePicSourceDialog(final OnItemClickListener onItemClickListener){
        if (choosePicSourceDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(new String[]{"拍照","从手机选择","取消"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(dialog,which);
                    }
                }
            });
            choosePicSourceDialog = builder.create();
            Window dialogWindow = choosePicSourceDialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
//            WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
//            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;//设置了没什么卵用,左右下都有较大边距(左右30dp,下15dp)
//            dialogWindow.setAttributes(attributes);
            dialogWindow.setBackgroundDrawableResource(android.R.color.white);//设置了也没什么卵用,左右有较小边距(10dp),下方(5dp)
        }
        return choosePicSourceDialog;
    }

    protected interface OnItemClickListener{
        void onItemClick(DialogInterface dialog, int which);
    }