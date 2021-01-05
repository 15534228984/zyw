<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    //延迟加载
    $(function(){
        pageInit();
    });

    //创建表格
    function pageInit(){
        $("#adminTable").jqGrid({
            url : "${path}/feedbckConrtoller/show",  //接收
            datatype : "json", //数据格式
            rowNum : 2,  //每页展示条数
            rowList : [ 2,3, 4, 10 ],  //可选每页展示的条数
            pager : '#userPage',  //分页工具栏
            sortname : 'id', //排序
            type : "post",  //请求方式
            styleUI:"Bootstrap", //使用Bootstrap
            autowidth:true,  //宽度自动
            height:"auto",   //高度自动
            viewrecords : true, //是否展示总条数
            colNames : [ 'Id', '标题', '内容', '时间', '用户id', ],
            colModel : [
                {name : 'id',width : 35},
                {name : 'title',editable:true,width : 80},
                {name : 'content',editable:true,width : 80},
                {name : 'crateDate',width : 60,align : "center",},
                {name : 'userId',width : 130},
            ]
        });
    }

</script>

<%--创建一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <span>反馈信息</span>
    </div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">反馈信息</a></li>
    </ul><br>

    <div>
    </div><br>

    <%--创建表格--%>
    <table id="adminTable" />

    <%--分页工具栏--%>
    <div id="userPage"/>

</div>