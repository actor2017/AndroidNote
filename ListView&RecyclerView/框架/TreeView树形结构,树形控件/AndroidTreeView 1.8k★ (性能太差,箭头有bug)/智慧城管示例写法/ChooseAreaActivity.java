
    <RelativeLayout
        android:id="@+id/rl_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </RelativeLayout>

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

/**
 * 选择区域
 */
public class ChooseAreaActivity extends BaseActivity {

    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private JSONObject jsonObject;

    private void initTreeView(String response) {
        TreeNode root = TreeNode.root();
        if (response != null) {
            jsonObject = JSONObject.parseObject(response);
            List<JSONObject> longLiXianList = (List<JSONObject>) jsonObject.get("4");//龙里县
            TreeNode longLiXianNode = new TreeNode(new TreeItemHolder.IconTreeItem("龙里县", "4",
                    longLiXianList));
            TreeNode parent = new TreeNode(new TreeItemHolder.IconTreeItem("贵州省", "",
                    longLiXianList));
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
    private void initAddress(JSONObject jsonObject, TreeNode longLiXianNode, List<JSONObject> longLiXianList) {
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

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener () {
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
}
