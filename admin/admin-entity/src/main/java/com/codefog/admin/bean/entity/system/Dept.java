package com.codefog.admin.bean.entity.system;

import com.codefog.admin.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_dept")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Dept extends BaseEntity {
    @Column
    private Integer num;
    @Column
    private Long pid;
    @Column
    private String pids;
    @Column
    private String simplename;
    @Column
    private String fullname;
    @Column
    private String tips;
    @Column
    private Integer version;
}
