package com.kuchuanyun.wisdomcitymanagement.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.global.Global;
import com.kuchuanyun.wisdomcitymanagement.holder.TreeItemHolder;
import com.kuchuanyun.wisdomcitymanagement.utils.MyOkHttpUtils;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.kuchuanyun.wisdomcitymanagement.application.MyApplication.aCache;

/**
 * 选择区域
 */
public class ChooseAreaActivity extends BaseActivity {

    @BindView(R.id.tb_toolBar)
    Toolbar tbToolBar;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    private ProgressDialog progressDialog;
    private JSONObject jsonObject;
    private String id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        ButterKnife.bind(this);

        setSupportActionBar(tbToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tbToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = createProgressDialog(this, "正在请求网络,请稍后...");
        requestAreas();
    }

    private void requestAreas() {
        progressDialog.show();//http://192.168.1.3:9999/CM/api/case/treeData//Global.IP + Global
        // .TREE_VIEW_DATA
        MyOkHttpUtils.requestByGet("http://192.168.1.3:9999/CM/api/case/treeData", null, new
                MyOkHttpUtils
                .OnNetResponseListener() {

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
        aCache.put(Global.IS_FIRST_INIT, "1");//第一个箭头有bug,所以做了标记
        TreeNode root = TreeNode.root();
        if (response != null) {
            jsonObject = JSONObject.parseObject(response);
            List<JSONObject> longLiXianList = (List<JSONObject>) jsonObject.get("4");//龙里县
            TreeNode longLiXianNode = new TreeNode(new TreeItemHolder.IconTreeItem("龙里县", "4", longLiXianList));
            TreeNode parent = new TreeNode(new TreeItemHolder.IconTreeItem("贵州省", "", longLiXianList));
//            initAddress(jsonObject,longLiXianNode,longLiXianList);//递归获取全部地址
            parent.addChild(longLiXianNode);
            root.addChild(parent);
            AndroidTreeView treeView = new AndroidTreeView(this, root);
            treeView.setDefaultAnimation(true);
            treeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
            treeView.setDefaultViewHolder(TreeItemHolder.class);
            treeView.setDefaultNodeClickListener(nodeClickListener);
            rlContainer.addView(treeView.getView());
            treeView.collapseAll();
        }
    }

    //递归获取全部地址
    private void initAddress(JSONObject jsonObject, TreeNode longLiXianNode, List<JSONObject>
            longLiXianList) {
        for (JSONObject jsonobject : longLiXianList) {
            TreeNode treeNode = new TreeNode(new TreeItemHolder.IconTreeItem(jsonobject.getString
                    ("name"), jsonobject.getString("id"), longLiXianList));
            List<JSONObject> list = (List<JSONObject>) jsonObject.get(jsonobject.getString("id"));
            if (list != null) {
                initAddress(jsonObject, treeNode, list);
            } else {
                longLiXianNode.addChild(treeNode);
            }
        }
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener
            () {
        @Override
        public void onClick(TreeNode node, Object value) {
            TreeItemHolder.IconTreeItem item = (TreeItemHolder.IconTreeItem) value;
            tvAddress.setText(item.text);
            if (node.size() == 0 && item.itemData != null && item.itemData.size() > 0) {
                for (int i = 0; i < item.itemData.size(); i++) {
                    String name = item.itemData.get(i).getString("name");
                    String id = item.itemData.get(i).getString("id");
                    node.addChild(new TreeNode(new TreeItemHolder.IconTreeItem(name, id,
                            (List<JSONObject>) jsonObject.get(id))));
                }
            }
            id = item.id;
            name = item.text;
        }
    };

    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        if (id != null && name != null) {
            Intent intent = new Intent();
            intent.putExtra(Global.ID,id);
            intent.putExtra(Global.NAME,name);
            setResult(RESULT_OK,intent);
            finish();
        } else toast("请选择地区");
    }
}
