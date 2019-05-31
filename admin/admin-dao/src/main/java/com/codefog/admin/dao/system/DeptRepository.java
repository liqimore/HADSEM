package com.codefog.admin.dao.system;


import com.codefog.admin.bean.entity.system.Dept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeptRepository extends PagingAndSortingRepository<Dept,Long> {
    List<Dept> findByPidsLike(String pid);
    @Query(nativeQuery = true,value = "SELECT id, pid AS pId, simplename AS NAME, ( CASE WHEN (pId = 0 OR pId IS NULL) THEN 'true' ELSE 'false' END ) AS isOpen FROM t_sys_dept")
    List tree();

    List<Dept> findBySimplenameLikeOrFullnameLike(String name,String name2);
}
