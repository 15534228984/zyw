<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    //延迟加载
    $(function(){
        pageInit();
    });

    //修改状态
    function updateUserStatus(id,status){
        if(status==0){
            $.post("${path}/user/edit",{"id":id,"status":"1","oper":"edit"},function(data){
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            })
        }else{
            $.post("${path}/user/edit",{"id":id,"status":"0","oper":"edit"},function(data){
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            })
        }
    }

    //创建表格
    function pageInit(){
        $("#userTable").jqGrid({
            url : "${path}/user/queryUserPage",  //接收  page=当前页   rows=每页展示条数   返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
            editurl:"${path}/user/edit",  //增删改走的路径  oper:add ,edit,del
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
            colNames : [ 'Id', '手机', '用户名', '头像','性别','省份', '简介','状态', '创建时间',"学分" ],
            colModel : [
                {name : 'id',width : 35},
                {name : 'phone',editable:true,width : 70},
                {name : 'username',editable:true,width : 80},
                {name : 'headImg',editable:true,width : 130,align : "center",edittype:"file",
                    //cellvalue:值,options:设置的参数，rowObject:行数据
                    formatter:function(cellvalue){
                        return "<img src='${path}/upload/cover/"+cellvalue+"' width='160px' height='80px' >";
                    }
                },
                {name : 'sex',editable:true,width : 60,align : "center"},
                {name : 'city',editable:true,width : 60,align : "center"},
                {name : 'brief',editable:true,width : 60,align : "center"},
                {name : 'status',width : 60,align : "center",
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue=="1"){
                            //正常  展示冻结（绿色）
                            return "<button value='"+cellvalue+"' id='"+rowObject.phone+"' class='btn btn-success' onclick='updateUserStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")' >冻结</button>";
                        }else{
                            //冻结  展示解除冻结（红色）
                            return "<button value='"+cellvalue+"' id='"+rowObject.phone+"' class='btn btn-danger' onclick='updateUserStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")'>解除冻结</button>";
                        }
                    }
                },
                {name : 'createDate',width : 130},
                {name : 'score',width : 130} //是否展示 hidden:true,
            ]
        });

        //分页工具栏
        $("#userTable").jqGrid('navGrid', '#userPage',
            {edit : true,add : true,del : true,addtext:"添加",edittext:"编辑",deltext:"删除"},
            {
                closeAfterEdit:true,  //关闭对话框
                afterSubmit:function(data){  //提交之后执行
                    //异步文件上传
                    $.ajaxFileUpload({
                        url: "${path}/user/uploadUserCover", //后台文件上传方法的路径
                        type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                        //dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                        fileElementId: "headImg",    //需要上传的文件域的ID，即<input type="file" name="headImg" id="headImg" >的ID。
                        data:{"id":data.responseText},
                        success:function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            //刷新表单
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            },  //修改之后的额外操作
            {
                closeAfterAdd:true,  //关闭对话框
                afterSubmit:function(data){  //提交之后执行
                    //异步文件上传
                    $.ajaxFileUpload({
                        url: "${path}/user/uploadUserCover", //后台文件上传方法的路径
                        type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                        //dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                        fileElementId: "headImg",    //需要上传的文件域的ID，即<input type="file" name="headImg" id="headImg" >的ID。
                        data:{"id":data.responseText},
                        success:function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            //刷新表单
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            }, //添加之后的额外操作
            {

            }  //删除之后的额外操作
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
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户信息</a></li>
    </ul><br>

    <div>
        <button class="btn btn-info">导出用户信息</button>
        <button class="btn btn-info">测试</button>
    </div><br>

    <%--创建表格--%>
    <table id="userTable" />

    <%--分页工具栏--%>
    <div id="userPage"/>

</div>