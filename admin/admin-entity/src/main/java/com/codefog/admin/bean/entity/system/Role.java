package com.codefog.admin.bean.entity.system;

import com.codefog.admin.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_role")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntity {
    @Column
    private Integer num;
    @Column
    private Long pid;
    @Column
    private String name;
    @Column
    private Long deptid;
    @Column
    private String tips;
    @Column
    private Integer version;

}
