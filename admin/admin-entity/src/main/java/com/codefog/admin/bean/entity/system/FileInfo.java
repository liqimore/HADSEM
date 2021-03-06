package com.codefog.admin.bean.entity.system;

import com.codefog.admin.bean.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@Table(name="t_sys_file_info")
public class FileInfo extends BaseEntity {
    @Column
    private String originalFileName;
    @Column
    private String realFileName;
    @Transient
    private String ablatePath;

}
