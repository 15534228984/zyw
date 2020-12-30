package com.baizhi.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.po.VideoPO;
import com.baizhi.po.VideoVO;
import com.baizhi.util.AliyunOSSUtil;
import com.baizhi.util.VideoInterceptCoverUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    VideoMapper videoMapper;

    @Resource
    HttpSession session;
    @Resource
    HttpServletRequest request;

    @AddLog("aaa")
    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();

        //当前页   page
        map.put("page", page);

        //总条数   records
        VideoExample example = new VideoExample();
        int records = videoMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数   total
        //总页数=总条数/每页展示条数   有余数加一页
        Integer total = records / rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);

        //数据    rows   参数：略过几条，要几条
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoMapper.selectByRowBounds(new Video(), rowBounds);
        map.put("rows", videos);

        return map;
    }

    @AddLog("aaa")
    @Override
    public String add(Video video) {

        video.setId(UUID.randomUUID().toString());
        video.setUploadTime(new Date());

        System.out.println("video  service " + video);
        //执行添加
        videoMapper.insertSelective(video);

        //将id返回
        return video.getId();
    }

    @AddLog("aaa")
    @Override
    public void update(Video video) {

        if (video.getVideoPath() == "") {
            video.setVideoPath(null);
        }
        videoMapper.updateByPrimaryKeySelective(video);

    }

    @Override
    public void uploadVdieos(MultipartFile videoPath, String id, HttpServletRequest request) {

        //1.获取文件上传的路径  根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/video");

        //2.判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();  //创建文件夹
        }
        //获取文件名
        String filename = videoPath.getOriginalFilename();

        //创建一个新的名字    原名称-时间戳  10.jpg-1590390153130
        String newName = new Date().getTime() + "-" + filename;

        //3.文件上传
        try {
            videoPath.transferTo(new File(realPath, newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //4.修改图片路径
        //修改的条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        Video video = new Video();
        video.setCoverPath("aaaa"); //设置封面
        video.setVideoPath(newName); //设置视频地址

        //修改
        videoMapper.updateByExampleSelective(video, example);
    }

    @Override
    public void uploadVdieosAliyun(MultipartFile videoPath, String id, HttpServletRequest request) {

        //获取文件名   动画.mp4
        String filename = videoPath.getOriginalFilename();
        //拼接时间戳  2341423424-动画.mp4
        String newName=new Date().getTime()+"-"+filename;
        //拼接视频名   video/2341423424-动画.mp4
        String objectName="video/"+newName;


        /*1.上传至阿里云
         * 将文件上传至阿里云
         * 参数：
         *   headImg：MultipartFile类型的文件
         *   bucketName:存储空间名
         *   objectName:文件名
         * */
        AliyunOSSUtil.uploadBytesFile(videoPath,"java-zyw",objectName);


        //拼接视频网络路径
        String videoNetPath="http://java-zyw.oss-cn-beijing.aliyuncs.com/"+objectName;

        //1.获取文件上传的路径  根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/cover");

        //2.判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();  //创建文件夹
        }

        //根据视频名拆分    0:2341423424-动画    1:mp4
        String[] split = newName.split("\\.");
        //获取视频名字  0:2341423424-动画
        String splitName=split[0];
        //拼接封面的本地路径
        String coverLocalPath=realPath+"/"+splitName+".jpg";

        /**2.截取封面
         * 获取指定视频的帧并保存为图片至指定目录
         * @param videofile  源视频文件路径
         * @param framefile  截取帧的图片存放路径
         * @throws Exception
         */
        try {
            VideoInterceptCoverUtil.fetchFrame(videoNetPath,coverLocalPath);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //拼接封面网路经名
        String netCoverName="cover/"+splitName+".jpg";

        /*
         * 3.上传封面
         * 参数：
         *   bucketName:存储空间名
         *   objectName:文件名
         *   localFilePath:本地文件路径
         * */
        AliyunOSSUtil.uploadLocalFile("java-zyw",netCoverName,coverLocalPath);

        //4.删除本地文件
        File file1 = new File(coverLocalPath);
        //判断是一个文件，并且文件存在
        if(file1.isFile()&&file1.exists()){
            //删除文件
            boolean isDel = file1.delete();
        }

        //5.修改数据
        //修改的条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        Video video = new Video();
        video.setCoverPath("http://java-zyw.oss-cn-beijing.aliyuncs.com/"+netCoverName); //设置封面
        video.setVideoPath("http://java-zyw.oss-cn-beijing.aliyuncs.com/"+objectName); //设置视频地址

        //修改
        videoMapper.updateByExampleSelective(video, example);

    }

    @Override
    public void uploadVdieosAliyuns(MultipartFile videoPath, String id, HttpServletRequest request) {
        //获取文件名   动画.mp4
        String filename = videoPath.getOriginalFilename();
        //拼接时间戳  2341423424-动画.mp4
        String newName=new Date().getTime()+"-"+filename;
        //拼接视频名   video/2341423424-动画.mp4
        String objectName="video/"+newName;

        /*1.上传至阿里云
         * 将文件上传至阿里云
         * 参数：
         *   headImg：MultipartFile类型的文件
         *   bucketName:存储空间名
         *   objectName:文件名
         * */
        AliyunOSSUtil.uploadBytesFile(videoPath,"java-zyw",objectName);

        //根据视频名拆分    0:2341423424-动画    1:mp4
        String[] split = newName.split("\\.");
        //获取视频名字  0:2341423424-动画   cover/2341423424-动画.jpg
        String coverName="cover/"+split[0]+".jpg";

        /*2.截取封面并上传
         * 视频截取帧并上传至阿里云
         * 参数：
         *   bucketName:存储空间名
         *   videoObjectName:视频文件名
         *   coverObjectName:封面文件名
         * */
        AliyunOSSUtil.videoInterceptCoverUpload("java-zyw",objectName,coverName);

        //5.修改数据
        //修改的条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        Video video = new Video();
        video.setCoverPath("http://java-zyw.oss-cn-beijing.aliyuncs.com/"+coverName); //设置封面
        video.setVideoPath("http://java-zyw.oss-cn-beijing.aliyuncs.com/"+objectName); //设置视频地址

        //修改
        videoMapper.updateByExampleSelective(video, example);
    }

    @AddLog("aaa")
    @Override
    public void delete(Video video) {
        //设置条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(video.getId());
        //根据id查询视频数据
        Video videos = videoMapper.selectOneByExample(example);

        //1.删除数据
        videoMapper.deleteByExample(example);
        String videoPath = videos.getVideoPath();

        //1.获取文件上传的路径  根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/video");

        //2.删除本地文件
        File file1 = new File(realPath+"/"+videoPath);
        //判断是一个文件，并且文件存在
        if(file1.isFile()&&file1.exists()){
            //删除文件
            boolean isDel = file1.delete();
            System.out.println("删除："+isDel);
        }

    }

    @AddLog("aaa")
    @Override
    public void deletes(Video video) {
        //设置条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(video.getId());
        //根据id查询视频数据
        Video videos = videoMapper.selectOneByExample(example);

        //1.删除数据
        videoMapper.deleteByExample(example);

        //获取视频名字并拆分
        String videoName=videos.getVideoPath().replace("http://java-zyw.oss-cn-beijing.aliyuncs.com/","");
        //获取封面名字并拆分
        String coverName=videos.getCoverPath().replace("http://java-zyw.oss-cn-beijing.aliyuncs.com/","");

        //2.删除视频
        AliyunOSSUtil.deleteFile("java-zyw",videoName);
        //3.删除封面
        AliyunOSSUtil.deleteFile("java-zyw",coverName);

    }
    @Override
    public List<VideoVO> queryByReleaseTime() {
        List<VideoPO> videoPOList = videoMapper.queryByReleaseTime();

        ArrayList<VideoVO> videoVOS = new ArrayList<>();

        for (VideoPO videoPO : videoPOList) {

            //封装VO对象
            VideoVO videoVO = new VideoVO(
                    videoPO.getId(),
                    videoPO.getTitle(),
                    videoPO.getCoverPath(),
                    videoPO.getVideoPath(),
                    videoPO.getUploadTime(),
                    videoPO.getDescrlption(),
                    18,  //根据videoPO中的视频id  redis  查询视频点赞数
                    videoPO.getCateName(),
                    videoPO.getHeadImg()
            );
            //将vo对象放入集合
            videoVOS.add(videoVO);
        }

        return videoVOS;
    }

}
