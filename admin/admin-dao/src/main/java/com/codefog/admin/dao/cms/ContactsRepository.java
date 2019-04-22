package com.codefog.admin.dao.cms;

import com.codefog.admin.bean.entity.cms.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactsRepository extends PagingAndSortingRepository<Contacts,Long>,
        JpaSpecificationExecutor<Contacts>,
        JpaRepository<Contacts,Long> {
}
