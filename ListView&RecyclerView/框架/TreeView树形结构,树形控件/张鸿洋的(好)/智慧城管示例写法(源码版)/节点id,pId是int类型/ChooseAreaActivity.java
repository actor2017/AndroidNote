package com.kuchuanyun.wisdomcitymanagement.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.global.Global;
import com.kuchuanyun.wisdomcitymanagement.gson.ChooseAreaInfo;
import com.kuchuanyun.wisdomcitymanagement.utils.MyOkHttpUtils;
import com.zhy.tree.bean.Node;
import com.zhy.tree.bean.TreeListViewAdapter;
import com.zhy.tree.bean.TreeNodeId;
import com.zhy.tree.bean.TreeNodeLabel;
import com.zhy.tree.bean.TreeNodePid;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 选择区域
 */
public class ChooseAreaActivity extends BaseActivity {

    @BindView(R.id.tb_toolBar)
    Toolbar tbToolBar;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.lv_listView)
    ListView lvListView;
    private ProgressDialog progressDialog;
    private String id;
    private String address;
    private TreeListViewAdapter mAdapter;
    private List<FileBean> mDatas = new ArrayList<FileBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        ButterKnife.bind(this);

        initSupportToolBar(tbToolBar,true);
        progressDialog = createProgressDialog(this, "正在请求网络,请稍后...");
        requestAreas();
    }

    private void requestAreas() {
        progressDialog.show();
        MyOkHttpUtils.requestByGet(Global.IP + Global.TREE_VIEW_DATA, null, new
                MyOkHttpUtils.OnNetResponseListener() {

                    @Override
                    public void onOk(String response, int id) {
                        progressDialog.dismiss();
                        try {
                            initTreeView(response);
                        } catch (Exception e) {
                            toast("请检查网络");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErro(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        toast("请检查网络!");
                    }
                });
    }

    private void initTreeView(String response) {
        if (response != null) {
            List<ChooseAreaInfo> chooseAreaInfos = JSON.parseArray(response, ChooseAreaInfo.class);
            if (chooseAreaInfos != null) {
                for (int i = 0; i < chooseAreaInfos.size(); i++) {
                    mDatas.add(new FileBean(chooseAreaInfos.get(i).id,chooseAreaInfos.get(i).pId,chooseAreaInfos.get(i).name));
                }
            }
            try {
                mAdapter = new SimpleTreeAdapter<FileBean>(lvListView, this, mDatas, 4);
                mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                    @Override
                    public void onClick(Node node, int position) {
                        id = node.getId() + "";
                        address = node.getName();
                        tvAddress.setText(address);
                        if (node.isLeaf()) {//叶子,最后节点
//                            toast(address);
                        }
                    }
                });
                lvListView.setAdapter(mAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        if (id != null && address != null) {
            Intent intent = new Intent();
            intent.putExtra(Global.ID, id);
            intent.putExtra(Global.ADDRESS, address);
            setResult(RESULT_OK, intent);
            finish();
        } else toast("请选择地区");
    }

    public class FileBean {
        @TreeNodeId
        private int _id;
        @TreeNodePid
        private int parentId;
        @TreeNodeLabel
        private String name;
        private long length;
        private String desc;

        public FileBean(int _id, int parentId, String name) {
            super();
            this._id = _id;
            this.parentId = parentId;
            this.name = name;
        }

    }

    public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {

        public SimpleTreeAdapter(ListView mTree, Context context, List<FileBean> datas, int defaultExpandLevel)
                throws IllegalArgumentException, IllegalAccessException {
            super(mTree, context, (List<T>) datas, defaultExpandLevel);
        }

        @Override
        public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_tree_view, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_arrow);
                viewHolder.label = (TextView) convertView.findViewById(R.id.tv_address);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //设置箭头★★注意:TreeHelper的R文件导成自己的★★
            if (node.getIcon() == -1) {
                viewHolder.icon.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.icon.setVisibility(View.VISIBLE);
                viewHolder.icon.setImageResource(node.getIcon());
            }

            viewHolder.label.setText(node.getName());

            return convertView;
        }

        private final class ViewHolder {
            ImageView icon;
            TextView label;
        }
    }
}
