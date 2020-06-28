/**
 * Created by ZY on 2017/2/23.
 */

public class NeutralDialogFragment extends DialogFragment {

    private String title;
    private String message;
    private String hint;
    private DialogInterface.OnClickListener neutralCallback;

    public void show(String title, String message, String hint, DialogInterface.OnClickListener neutralCallback, FragmentManager fragmentManager) {
        this.title = title;
        this.message = message;
        this.hint = hint;
        this.neutralCallback = neutralCallback;
        show(fragmentManager, "NeutralDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(hint, neutralCallback);
        return builder.create();
    }

}

//提供调用方法
public void showNeutralDialogFragment() {
        NeutralDialogFragment neutralDialogFragment = new NeutralDialogFragment();
        neutralDialogFragment.show("Hi,你好", "叶应是叶", "确定~", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "点击了按钮 " + which, Toast.LENGTH_SHORT).show();
            }
        }, getFragmentManager());
    }