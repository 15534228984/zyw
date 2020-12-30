package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author VideoPO
 * @time 2020/12/25-10:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPO {
    private String id;
    private String title;
    private String descrlption;
    private String videoPath;
    private String coverPath;
    private String uploadTime;
    private String categoryId;
    private String groupId;
    private String userId;
    private String headImg;
    private String cateName;
}
