package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "yx_feedback")   //对应数据库表
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable {
    @Id
    private String id;

    private String title;

    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date crateDate;
    @Column(name = "user_id")   //和数据库格式不符合
    private String userId;



}