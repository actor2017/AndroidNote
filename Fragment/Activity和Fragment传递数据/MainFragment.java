public class MainFragment extends Fragment {

	private FragmentResultListener listener;

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentResultListener)activity;
    }

	//���ղ���
    @Override
    public void onStart() {
        super.onStart();
		//����1.����?���Ƽ�
		//String mArgument = getActivity().getIntent().getStringExtra("argument");
		//����2.
        Bundle bundle = getArguments();
        if (bundle != null) String mArgument = bundle.getString("argument");
		
		//����3.��Fragment��д���ղ����ķ���,Activity���÷���ֱ�Ӵ�
    }
	
	//���ز���
	@Override
    public void onClick(View v) {
		//if(listener == null) listener = (FragmentResultListener)getActivity();
		listener.process("���Ƿ�������");
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
