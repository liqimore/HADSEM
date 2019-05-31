package com.codefog.admin.bean.entity.system;

import com.codefog.admin.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_menu")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Menu  extends BaseEntity {
    @Column
    private String code;
    @Column
    private String pcode;
    @Column
    private String pcodes;
    @Column
    private String name;
    @Column
    private String icon;
    @Column
    private String url;
    @Column
    private Integer num;
    @Column
    private Integer levels;
    @Column
    private Integer ismenu;
    @Column
    private String tips;
    @Column
    private Integer status;
    @Column
    private Integer isopen;

}
