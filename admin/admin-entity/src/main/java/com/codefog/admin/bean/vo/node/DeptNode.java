package com.codefog.admin.bean.vo.node;

import com.codefog.admin.bean.entity.system.Dept;

import java.util.ArrayList;
import java.util.List;

/**
 * DeptNode
 *
 */
public class DeptNode extends Dept {

    private List<DeptNode> children = new ArrayList<>(10);

    public List<DeptNode> getChildren() {
        return children;
    }

    public void setChildren(List<DeptNode> children) {
        this.children = children;
    }
}
