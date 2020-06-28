
import com.mobilesafe_work.R;
import com.mobilesafe_work.db.dao.CommonNumberDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 常用号码查询
 
 elvList.setSelectedGroup(position);//跳到某个group(选联系人)
 elvList.expandGroup(position);//展开
 */
public class CommonNumberActivity extends AppCompatActivity {

    @BindView(R.id.elv_list)
    ExpandableListView elvList;
    private ArrayList<CommonNumberDao.GroupInfo> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_number);
        ButterKnife.bind(this);

        //查询所有号码
        mList = CommonNumberDao.getCommonNumber(this);

        elvList.setAdapter(new CommonNumberAdapter());
        //子条目被点击
        elvList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int
                    childPosition, long id) {

//                ToastUtils.show(getApplicationContext(), "第" + groupPosition + "组" + ";第" +
//                        childPosition + "项");

                CommonNumberDao.ChildInfo info = mList.get(groupPosition).childList.get
                        (childPosition);

                //跳到打电话页面
                Intent intent = new Intent(Intent.ACTION_DIAL);//拨号页面
                intent.setData(Uri.parse("tel:" + info.number));
                startActivity(intent);

                return false;
            }
        });
    }

    class CommonNumberAdapter extends BaseExpandableListAdapter {

        //返回组布局数量
        @Override
        public int getGroupCount() {
            //return 8;
            return mList.size();
        }

        //返回某一组子布局数量
        @Override
        public int getChildrenCount(int groupPosition) {
            //return groupPosition + 1;
            return mList.get(groupPosition).childList.size();
        }

        //返回组对象
        @Override
        public CommonNumberDao.GroupInfo getGroup(int groupPosition) {
            return mList.get(groupPosition);
        }

        //返回子对象
        @Override
        public CommonNumberDao.ChildInfo getChild(int groupPosition, int childPosition) {
            return mList.get(groupPosition).childList.get(childPosition);
        }

        //返回组id
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //返回子id
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //是否有固定id, 默认就行, 不用动
        @Override
        public boolean hasStableIds() {
            return false;
        }

        //返回组布局
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {

            TextView view = new TextView(CommonNumberActivity.this);
            //view.setText("        第" + groupPosition + "组");
            view.setText("      "+getGroup(groupPosition).name);
            view.setTextSize(20);
            view.setTextColor(Color.BLACK);
            view.setBackgroundColor(Color.GREEN);
            view.setPadding(10, 10, 10, 10);

            return view;
        }

        //返回子布局
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
                convertView, ViewGroup parent) {
            TextView view = new TextView(CommonNumberActivity.this);
            //view.setText("第" + groupPosition + "组" + ";第" + childPosition + "项");
            CommonNumberDao.ChildInfo info = getChild(groupPosition, childPosition);

            view.setText(info.name + "\n" + info.number);
            view.setTextSize(18);
            view.setTextColor(Color.RED);
            view.setBackgroundColor(Color.YELLOW);
            view.setPadding(10, 10, 10, 10);

            return view;
        }

        //子布局是否可以选中
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
		
		//下面方法可不重写
		@Override
        public void registerDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
        }
		
		@Override
        public boolean isEmpty() {
            return false;
        }
		
		@Override
    	public boolean areAllItemsEnabled() {
        	return false;
    	}
		
		@Override
    	public void onGroupExpanded(int groupPosition) {
    	}
		
    	@Override
    	public void onGroupCollapsed(int groupPosition) {
    	}
		
		@Override
    	public long getCombinedChildId(long groupId, long childId) {
    	    return 0;
    	}
    	@Override
    	public long getCombinedGroupId(long groupId) {
    	    return 0;
    	}
    }
}
