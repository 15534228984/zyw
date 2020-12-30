<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    //延迟加载
    $(function(){
        pageInit();
    });

    function updateStatus(id) {
        alert(id)
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/freeze",
            type: "post",
            data: 'id='+id,
            //刷新
            success : function () {
                $("#adminTable").trigger("reloadGrid");
            }
        })
    }

    //创建表格
    function pageInit(){
        $("#adminTable").jqGrid({
            url : "${path}/admin/queryAdminPage",  //接收
            editurl:"${path}/admin/edit",  //增删改走的路径
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
            colNames : [ 'Id', '管理员名', '密码', '状态', '盐', ],
            colModel : [
                {name : 'id',width : 35},
                {name : 'username',editable:true,width : 80},
                {name : 'password',editable:true,width : 80},
                {name : 'status',width : 60,align : "center",
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue==1){
                            //正常  展示冻结（绿色）
                            return "<button class='btn btn-success' id='relieve' onclick='updateStatus(\""+rowObject.id+"\")'>冻结</button>";
                        }else{
                            //冻结  展示解除冻结（红色）
                            return "<button class='btn btn-danger' id='relieve' onclick='updateStatus(\""+rowObject.id+"\")'>解除冻结</button>";
                        }
                    }
                },
                {name : 'salt',width : 130},
            ]
        });

        //分页工具栏
        $("#adminTable").jqGrid('navGrid', '#userPage',
            {edit : true,add : true,del : true,addtext:"添加",edittext:"编辑",deltext:"删除"},
            {
                closeAfterEdit:true,  //关闭对话框
            },  //修改之后的额外操作
            {
                closeAfterAdd:true,  //关闭对话框
                afterSubmit:function(data){  //提交之后执行
                return "hello";
                }
            }, //添加之后的额外操作
            {}  //删除之后的额外操作
        );
    }

</script>

<%--创建一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <span>用户信息</span>
    </div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">管理员信息</a></li>
    </ul><br>

    <div>
    </div><br>

    <%--创建表格--%>
    <table id="adminTable" />

    <%--分页工具栏--%>
    <div id="userPage"/>

</div>