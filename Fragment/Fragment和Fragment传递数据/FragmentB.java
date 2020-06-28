
//自己写个方法，开始返回
    private void sendResult(int resultOK) {
        if (getTargetFragment() == null) {
            return;
        } else {
            //把需要返回的数据存放到Intent中
            Intent i = new Intent();
            i.putExtra("photoPath", tempPhotoPath);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultOK, i);
        }
		getFragmentManager().popBackStack();//从任务栈中弹出
    }
	