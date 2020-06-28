
    /**
     * ����Ļ�ײ���AlertDialog,�������û�ѡ������/���ֻ�ѡ����Ƭ.
     * ȱ��:��Ȳ���ȫ��,item����û�о���,���Ҫʵ�ֿ��ȫ��,��Dialog.
     */
    private AlertDialog choosePicSourceDialog;
    public AlertDialog getChoosePicSourceDialog(final OnItemClickListener onItemClickListener){
        if (choosePicSourceDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(new String[]{"����","���ֻ�ѡ��","ȡ��"}, new DialogInterface.OnClickListener() {
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
//            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;//������ûʲô����,�����¶��нϴ�߾�(����30dp,��15dp)
//            dialogWindow.setAttributes(attributes);
            dialogWindow.setBackgroundDrawableResource(android.R.color.white);//������Ҳûʲô����,�����н�С�߾�(10dp),�·�(5dp)
        }
        return choosePicSourceDialog;
    }

    protected interface OnItemClickListener{
        void onItemClick(DialogInterface dialog, int which);
    }