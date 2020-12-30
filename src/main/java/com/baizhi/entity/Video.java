package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    private String id;

    private String title;

    private String descrlption;
    @Column(name = "video_path")
    private String videoPath;
    @Column(name = "cover_path")
    private String coverPath;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "upload_time")
    private Date uploadTime;

    private String playCount;

    private String likeCount;
    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "user_id")
    private String userId;
}