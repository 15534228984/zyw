package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    private String id;

    private String phone;

    @Column(name = "head_img")
    private String headImg;

    private String username;

    private String brief;

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @JsonFormat(pattern = "yyyy-MM-dd")  //网页显示的格式
    @Column(name = "create_date")   //和数据库格式不符合
    private Date createDate;

    private String sex;
    private  String city;

    @Transient   //数据库没有的
    private String score;


}