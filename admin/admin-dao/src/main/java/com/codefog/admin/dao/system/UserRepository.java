package com.codefog.admin.dao.system;


import com.codefog.admin.bean.entity.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,Long>,JpaSpecificationExecutor<User> {
    User findByAccount(String account);
}
