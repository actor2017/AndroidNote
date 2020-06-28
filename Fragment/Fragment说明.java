���û����Fragment,ViewPager��PagerAdapter.

�����Fragment.
FragmentPagerAdapter ÿһ�����ɵ� Fragment �����������ڴ�֮�У������������Щ��Ծ�̬��ҳ������Ҳ�Ƚ��ٵ����֣�
�����Ҫ�����кܶ�ҳ���������ݶ�̬�Խϴ�ռ���ڴ�϶�������Ӧ��ʹ��FragmentStatePagerAdapter��
�����ViewPager�ļ���!!


�������LayoutInflater.inflate()
http://www.jianshu.com/p/41796f541e67

FragmentManager fragmentManager = getSupportFragmentManager();//in activity
                                                                     //https://www.jianshu.com/p/d06b216dade4
fragmentManager.add(viewid, fragment).hide(fragment).show().commit();//add&hide&show ������������
Fragmeent.isAdded();//�ж��Ƿ���ӹ�
fragment.setUserVisibleHint(false);

������ getChildFragmentManager()˵��������
Activity-->ViewPager Ƕ�� Fragment Ƕ�� ViewPager Ƕ�� FragMent ���� RecyclerView��ʲô��
��2��FragMentҪдgetChildFragmentManager(),��������ݲ���ʾ, �������ɲ���ʾ�װ�


public class MoreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;//FragmentҪ���Ǹ���, Ҫͨ���ص�����Activity,��Ҫǿת

    public MoreFragment() {//�ղι���
        // Required empty public constructor
    }

    //Ϊ��Ҫд�������: https://blog.csdn.net/chniccs/article/details/51258972
	//��Ҫʹ��FragmentFactory�ķ�ʽ����Fragment
    public static MoreFragment newInstance(String param1, String param2) {//�ٷ���ȡʵ��
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.next_enter, R.anim.pre_exit);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.next_enter, R.anim.pre_exit);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        logFormat("onActivityResult: requestCode=%d, resultCode=%d, data=%s", requestCode, resultCode, data);
    }

    //��תActivity�󷵻�, ��ص�
    @Override
    public void onStart() {
        super.onStart();
    }

    //��תActivity�󷵻�, ��ص�
    @Override
    public void onResume() {
        super.onResume();
        logError(getClass().getName());
    }

    //ʹ��add hide showʱ��ص��������
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
		logFormat(getClass().getName().concat(": hidden=%b"), hidden);
    }

    /**
     * ��Fragment�ɼ�/���ɼ���ʱ��
     * ʹ��ViewPager + Fragment, ��ViewPager�л�Fragmentʱ��ص��������.
     * ��onCreate֮ǰ���õ�, ���isVisibleToUser=false, "��Ҫʹ�ÿؼ� & ����UI����"
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        logFormat(getClass().getName().concat(": isVisibleToUser=%b"), isVisibleToUser);
        if(isVisibleToUser/*getUserVisibleHint()*/) {//���ɼ���ʱ��
            //do something...
        }
    }


    //��ʼ�� & ϵͳ�ָ�ҳ������ & ��ת��Ļ ʱ, ��ص��������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Bundle arguments = getArguments();
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1);
            mParam2 = arguments.getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /**
         * @param inflater
         * @param container ʵ�������fragmentʱ������fragment��view
         * @param attachToRoot �� xml ʵ����Ϊ View���Ƿ� View ��ӵ� container ��, Ĭ��ֵ:root != null
         */
        View view = inflater.inflate(R.layout.frag_newslist, container, false);
        TextView tv = view.findViewById(R.id.tv);
        return view;
//        container.addView(view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }

    public void onButtonPressed(Uri uri) {//�ٷ�����ص�
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {//�ٷ�onAttach, activity�� implements OnFragmentInteractionListener
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {//�ٷ�onDetach
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d("onDestroyView, title=%s", title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy, title=%s", title);
    }

    public interface OnFragmentInteractionListener {//�ٷ��ӿڻص�
        void onFragmentInteraction(Uri uri);
    }
}

