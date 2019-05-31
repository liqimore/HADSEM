package com.codefog.admin.bean.entity.system;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_sys_relation")
@Data
public class Relation {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long menuid;
    @Column
    private Long roleid;

}
