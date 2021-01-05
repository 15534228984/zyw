package com.baizhi;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CreateBucketRequest;
import com.baizhi.dao.CategoryMapper;
import com.baizhi.dao.VideoMapper;
import com.baizhi.service.CategoryService;
import com.baizhi.service.LogService;
import com.baizhi.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class YingxZywApplicationTests {

    @Resource
    CategoryMapper categoryMapper;

    @Autowired
    CategoryService service;

        //测试阿里云oos
        @Test
        public void createBucket(){
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = "http://oss-cn-beijing.aliyuncs.com";
            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
            String accessKeyId = "LTAI4G41rmKCF2DPA8JktbmS";
            String accessKeySecret = "5ii8Eh9b0KU5pezaJTxL061lKZIUb3";
            String bucketName = "yingx-dfghjkl";  //存储空间名

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 创建CreateBucketRequest对象。
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);

            // 如果创建存储空间的同时需要指定存储类型以及数据容灾类型, 可以参考以下代码。
            // 此处以设置存储空间的存储类型为标准存储为例。
            // createBucketRequest.setStorageClass(StorageClass.Standard);
            // 默认情况下，数据容灾类型为本地冗余存储，即DataRedundancyType.LRS。如果需要设置数据容灾类型为同城冗余存储，请替换为DataRedundancyType.ZRS。
            // createBucketRequest.setDataRedundancyType(DataRedundancyType.ZRS)

            // 创建存储空间。
            ossClient.createBucket(createBucketRequest);

            // 关闭OSSClient。
            ossClient.shutdown();
        }

        @Resource
        VideoMapper videoService;

        @Autowired
    LogService logService;

    @Test
    public void tss(){
            System.out.println(logService.show(1,1));
        }

    @Test
    public void  tas(){
        System.out.println(videoService.queryByReleaseTime());
    }
}
