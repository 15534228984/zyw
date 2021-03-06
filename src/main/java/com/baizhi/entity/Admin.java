package com.baizhi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "yx_admin")   //对应数据库表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    @Id   //注明主键
    private String id;

    private String username;

    private String password;

    private String status;

    private String salt;

   }