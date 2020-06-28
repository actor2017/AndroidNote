public class MainFragment extends Fragment {

	private FragmentResultListener listener;

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentResultListener)activity;
    }

	//接收参数
    @Override
    public void onStart() {
        super.onStart();
		//方法1.行吗?不推荐
		//String mArgument = getActivity().getIntent().getStringExtra("argument");
		//方法2.
        Bundle bundle = getArguments();
        if (bundle != null) String mArgument = bundle.getString("argument");
		
		//方法3.在Fragment中写接收参数的方法,Activity调用方法直接传
    }
	
	//返回参数
	@Override
    public void onClick(View v) {
		//if(listener == null) listener = (FragmentResultListener)getActivity();
		listener.process("这是返回数据");
	}
	
	public interface FragmentResultListener {
		void process(String str);
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
