如果没有用Fragment,ViewPager用PagerAdapter.

如果用Fragment.
FragmentPagerAdapter 每一个生成的 Fragment 都将保存在内存之中，因此适用于那些相对静态的页，数量也比较少的那种；
如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapter。
具体见ViewPager文件夹!!


深入理解LayoutInflater.inflate()
http://www.jianshu.com/p/41796f541e67

FragmentManager fragmentManager = getSupportFragmentManager();//in activity
                                                                     //https://www.jianshu.com/p/d06b216dade4
fragmentManager.add(viewid, fragment).hide(fragment).show().commit();//add&hide&show 不走生命周期
Fragmeent.isAdded();//判断是否被添加过
fragment.setUserVisibleHint(false);

★★★★★ getChildFragmentManager()说明★★★★★
Activity-->ViewPager 嵌套 Fragment 嵌套 ViewPager 嵌套 FragMent 里面 RecyclerView啊什么的
第2个FragMent要写getChildFragmentManager(),里面的内容才显示, 否则会造成不显示白板


public class MoreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;//Fragment要考虑复用, 要通过回调返回Activity,不要强转

    public MoreFragment() {//空参构造
        // Required empty public constructor
    }

    //为何要写这个方法: https://blog.csdn.net/chniccs/article/details/51258972
	//不要使用FragmentFactory的方式保存Fragment
    public static MoreFragment newInstance(String param1, String param2) {//官方获取实例
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

    //跳转Activity后返回, 会回调
    @Override
    public void onStart() {
        super.onStart();
    }

    //跳转Activity后返回, 会回调
    @Override
    public void onResume() {
        super.onResume();
        logError(getClass().getName());
    }

    //使用add hide show时会回调这个方法
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
		logFormat(getClass().getName().concat(": hidden=%b"), hidden);
    }

    /**
     * 当Fragment可见/不可见的时候
     * 使用ViewPager + Fragment, 当ViewPager切换Fragment时会回调这个方法.
     * 在onCreate之前调用的, 如果isVisibleToUser=false, "不要使用控件 & 操作UI界面"
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        logFormat(getClass().getName().concat(": isVisibleToUser=%b"), isVisibleToUser);
        if(isVisibleToUser/*getUserVisibleHint()*/) {//当可见的时候
            //do something...
        }
    }


    //初始化 & 系统恢复页面数据 & 旋转屏幕 时, 会回调这个方法
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
         * @param container 实际上添加fragment时，包裹fragment的view
         * @param attachToRoot 将 xml 实例化为 View后，是否将 View 添加到 container 中, 默认值:root != null
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

    public void onButtonPressed(Uri uri) {//官方点击回调
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {//官方onAttach, activity需 implements OnFragmentInteractionListener
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {//官方onDetach
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

    public interface OnFragmentInteractionListener {//官方接口回调
        void onFragmentInteraction(Uri uri);
    }
}

