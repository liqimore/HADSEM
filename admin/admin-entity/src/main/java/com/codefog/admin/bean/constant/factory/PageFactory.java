package com.codefog.admin.bean.constant.factory;

import com.codefog.admin.bean.constant.state.Order;
import com.codefog.admin.utils.HttpKit;
import com.codefog.admin.utils.StringUtils;
import com.codefog.admin.utils.factory.Page;
import com.codefog.admin.utils.ToolUtil;

import javax.servlet.http.HttpServletRequest;
/**
 * BootStrap Table默认的分页参数创建
 *
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        //每页多少条数据
        int limit = Integer.valueOf(request.getParameter("limit"));
        String pageNum = request.getParameter("page");
        //每页的偏移量(本页当前有多少条)
        int offset = 0;
        if(StringUtils.isNotEmpty(pageNum)){
            offset = (Integer.valueOf(pageNum)-1)*limit;
        }else {

            offset = Integer.valueOf(request.getParameter("offset"));
        }
        //排序字段名称
        String sort = request.getParameter("sort");
        //asc或desc(升序或降序)
        String order = request.getParameter("order");
        if (ToolUtil.isEmpty(sort)) {
            Page<T> page = new Page<>((offset / limit + 1), limit);
            page.setOpenSort(false);
            return page;
        } else {
            Page<T> page = new Page<>((offset / limit + 1), limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }
}
