
/**
 * 身份证信息录入界面
 */
public class IdCardActivity extends BaseActivity {

    @OnClick({R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                startActivityForResult(new Intent(this, CarRegisterActivity.class), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0://汽车租赁信息登记页面返回
                if (data != null) {
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
