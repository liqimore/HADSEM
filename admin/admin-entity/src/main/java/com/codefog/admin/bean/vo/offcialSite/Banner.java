package com.codefog.admin.bean.vo.offcialSite;

import lombok.Data;

import java.util.List;

@Data
public class Banner {
    private Integer index = 0;
    private List<com.codefog.admin.bean.entity.cms.Banner> list;

}
