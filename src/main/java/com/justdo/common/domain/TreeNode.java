package com.justdo.common.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
/**
 * Created by chenlin on 2018/7/25.
 */
public class TreeNode {
    /**
     * 节点ID
     */
    private String id;
    /**
     * 显示节点文本
     */
    private String text;
    /**
     * 节点状态，open closed
     */
    private Map<String, Object> state;
    /**
     * 节点是否被选中 true false
     */
    private boolean checked = false;
    /**
     * 节点属性
     */
    private Map<String, Object> attributes;
    /**
     * 节点的子节点
     */
    private List<TreeNode> children = new ArrayList<TreeNode>();
    /**
     * 父ID
     */
    private String parentid;
    /**
     * 节点icon
     */
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public TreeNode(String id, String text, Map<String, Object> state, boolean checked, Map<String, Object> attributes,
                List<TreeNode> children, String parentid,String icon ) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
        this.checked = checked;
        this.attributes = attributes;
        this.children = children;
        this.parentid = parentid;
        this.icon = icon;
    }

    public TreeNode() {
        super();
    }

    @Override
    public String toString() {

        return JSON.toJSONString(this);
    }

}
