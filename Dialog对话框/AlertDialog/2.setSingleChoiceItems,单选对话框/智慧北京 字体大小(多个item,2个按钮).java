private String[] textSizeChoices = new String[]{"超大号字体","大号字体","正常字体" ,"小号字体","超小号字体"};

    private void showTextSizeDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
            .setTitle("字体设置");
            .setSingleChoiceItems(textSizeChoices, realWhich, new DialogInterface.OnClickListener() {
                @Override
                    public void onClick(DialogInterface dialog, int which) {//which的值范围  0~4
                    tempWhich = which;
                }
            })
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //点击确定按钮会进行的回调
                    //根据选择的字体大小
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
		    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
	    alertDialog.show();
    }
}