private String[] textSizeChoices = new String[]{"���������","�������","��������" ,"С������","��С������"};

    private void showTextSizeDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
            .setTitle("��������");
            .setSingleChoiceItems(textSizeChoices, realWhich, new DialogInterface.OnClickListener() {
                @Override
                    public void onClick(DialogInterface dialog, int which) {//which��ֵ��Χ  0~4
                    tempWhich = which;
                }
            })
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //���ȷ����ť����еĻص�
                    //����ѡ��������С
                    WebSettings settings = webView.getSettings();
                    switch (tempWhich) {
                        case 0:
                            settings.setTextSize(WebSettings.TextSize.LARGEST);
                            break;
                        case 1:
                            settings.setTextSize(WebSettings.TextSize.LARGER);
                            break;
                        case 2:
                            settings.setTextSize(WebSettings.TextSize.NORMAL);
                            break;
                        case 3:
                            settings.setTextSize(WebSettings.TextSize.SMALLER);
                            break;
                        case 4:
                            settings.setTextSize(WebSettings.TextSize.SMALLEST);
                            break;
                    }
                    realWhich = tempWhich;
                }
            })
		    .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
	    alertDialog.show();
    }
}