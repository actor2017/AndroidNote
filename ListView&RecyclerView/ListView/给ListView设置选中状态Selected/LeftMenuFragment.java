
/**
 * Description: 类的功能描述//Created by : ＄{USER} on ＄{DATE}.[原Date位置]
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/2/21 on 19:28.
 */

public class LeftMenuFragment extends BaseFragment{

    private ListView lv_leftmenu;
    private LeftMenuFragment.mAdapter mAdapter;
    private ArrayList<NewsData.NewsMenuData> data;
    private int mClickPosition;     //在条目点击监听中记录点击的条目ID,

    @Override
    View initView() {

        //给LeftMenuFragment填充布局
        View view = View.inflate(activity, R.layout.fragment_leftmenu, null);
        lv_leftmenu = (ListView) view.findViewById(R.id.lv_leftmenu);
        //给listView设置条目点击监听
        lv_leftmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录点击的条目ID
                mClickPosition = position;
                //刷新
                mAdapter.notifyDataSetChanged();
                //切换到对应的ViewPager?
            }
        });
        return view;
    }

    //listView 的布局文件

    //由NewsCenterPager那边Gson解析出数据后再调用本方法,再给listView设置数据
    public void setNewsData(NewsData newsData){
        data = newsData.data;
        mAdapter = new mAdapter();
        lv_leftmenu.setAdapter(mAdapter);
    }

    private class mAdapter extends BaseAdapter{
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public NewsData.NewsMenuData getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null) {
                    convertView = View.inflate(activity, R.layout.item_left_menu, null);
                    viewHolder = new ViewHolder();
                    viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                //刷新holder中tcContent的数据
                viewHolder.tv_content.setText(getItem(position).title);
                //设置是否是选中状态,因为布局文件中tv_content设有图片和文字选择器,状态是Selected
                if (mClickPosition == position) {
                    viewHolder.tv_content.setSelected(true);
                } else {
                    viewHolder.tv_content.setSelected(false);
                }

                return convertView;
            }
    }

    static class ViewHolder{
        public TextView tv_content;
    }

    //LeftMenu不用加载什么
    @Override
    public void initData() {
    }
}
