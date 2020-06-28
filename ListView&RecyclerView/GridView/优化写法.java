
import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.bean.SearchInfo;
import com.kuchuan.wisdompolice.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    int[] ids = {R.drawable.oa, R.drawable.cj, R.drawable.mjcf, R.drawable.xl, R.drawable.xszc,
            R.drawable.xgmm, R.drawable.doorplate, R.drawable.xl, R.drawable.update};
    String[] items = {"OA系统", "采集", "民警查访", "巡逻", "刑事侦查",
            "修改密码", "智慧门牌", "门禁管理", "系统更新"};
    ArrayList<SearchInfo> itemList = new ArrayList<>();
    @BindView(R.id.gv_gridview)
    GridView gv_Gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        for (int i = 0; i < ids.length; i++) {
            SearchInfo info = new SearchInfo();
            info.picId = ids[i];
            info.itemname = items[i];
            itemList.add(info);
        }
        gv_Gridview.setAdapter(new MyGridViewAdapter());
        gv_Gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                }
            }
        });
    }

    private class MyGridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public SearchInfo getItem(int position) {//注意:如果不手动调用方法,如果自己不用,可以直接返回null
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout
                        .item_mainactivity_gridview, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.iv_pic.setImageResource(ids[position]);
            viewHolder.tv_item.setText(items[position]);
            return convertView;
        }
    }
    static class ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView iv_pic;
        @BindView(R.id.tv_item)
        TextView tv_item;

        ViewHolder(View view) {
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_item = (TextView) view.findViewById(R.id.tv_item);

        }
    }
}
