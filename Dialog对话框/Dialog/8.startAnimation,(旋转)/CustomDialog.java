    /** 
         * �õ��Զ����progressDialog 
         * @param context 
         * @param msg 
         * @return 
         */  
        public static Dialog createLoadingDialog(Context context, String msg) {  
      
            LayoutInflater inflater = LayoutInflater.from(context);  
            View v = inflater.inflate(R.layout.loading_dialog, null);// �õ�����view  
            LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// ���ز���  
            // main.xml�е�ImageView  
            ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);  
            TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// ��ʾ����  
            // ���ض���  
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(  
                    context, R.anim.load_animation);  
            // ʹ��ImageView��ʾ����  
            spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
            tipTextView.setText(msg);// ���ü�����Ϣ  
      
            Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// �����Զ�����ʽdialog  
      
            loadingDialog.setCancelable(false);// �������á����ؼ���ȡ��  
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
                    LinearLayout.LayoutParams.FILL_PARENT,  
                    LinearLayout.LayoutParams.FILL_PARENT));// ���ò���  
            return loadingDialog;  
      
        }  