
public class MainActivity extends BaseActivity {

    private void initDialog() {
        dialog = new Dialog(this, R.style.MyDialog);
        //加载对话框的布局文件
        View contentView = getLayoutInflater().inflate(R.layout.dialog_view, null);
        contentView.findViewById(R.id.tv_zm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        contentView.findViewById(R.id.tv_cm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(contentView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //http://blog.csdn.net/fancylovejava/article/details/21617553位置修改详细说明
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        dialogWindow.setGravity(Gravity.CENTER);//对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
        //lp.x = 100; // 新位置X坐标
        //lp.y = 300; // 新位置Y坐标
        dialogWindow.setAttributes(lp);
    }
}
