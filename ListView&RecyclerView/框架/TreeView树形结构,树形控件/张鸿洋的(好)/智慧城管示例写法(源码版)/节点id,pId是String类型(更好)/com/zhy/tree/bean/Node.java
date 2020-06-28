package com.zhy.tree.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/lmj623565791/article/details/40212367
 *
 * @author zhy
 */
public class Node {

    private String id;//子节点id
    private String pId = "0";//根节点pId为0
    private String name;

    private int level;//当前的级别,可用于设置Item的PaddingLeft

    private boolean isExpand = false;//是否展开

    private int icon;

    private List<Node> children = new ArrayList<>();//下一级的子Node
    private Node parent;//父Node

    public Node() {
    }

    public Node(String id, String pId, String name) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    //是否为根节点
    public boolean isRoot() {
        return parent == null;
    }

    //判断父节点是否展开
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    //是否是叶子节点
    public boolean isLeaf() {
        return children.size() == 0;
    }

    //获取level
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    //设置是否展开
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {

            for (Node node : children) {
                node.setExpand(isExpand);
            }
        }
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
