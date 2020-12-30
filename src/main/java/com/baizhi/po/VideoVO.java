package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author VideoVO
 * @time 2020/12/25-11:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoVO {

    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private String uploadTime;
    private String description;
    private Integer likeCount;
    private String cateName;
    private String userPhoto;

}
