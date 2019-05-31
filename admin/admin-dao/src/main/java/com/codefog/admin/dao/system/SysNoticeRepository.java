package com.codefog.admin.dao.system;


import com.codefog.admin.bean.entity.system.Notice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SysNoticeRepository extends PagingAndSortingRepository<Notice,Long> {
    List<Notice> findByTitleLike(String name);
}
