package com.codefog.admin.bean.vo.node;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Node
 */
@Data
public class Node {
    private Long id;
    private Long pid;
    private String name;
    private Boolean checked;
    private List<Node> children = new ArrayList<>(10);

}
