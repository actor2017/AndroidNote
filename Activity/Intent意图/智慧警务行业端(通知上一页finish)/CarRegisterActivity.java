
/**
 * 汽车租赁信息登记
 */
public class CarRegisterActivity extends BaseActivity {

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (uploadSuccess) {
                    setResult(1, new Intent());//如果上传成功,就让上一个页面finish
                }
                finish();
                break;
        }
    }
}
