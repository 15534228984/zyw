<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    $(function(){
        pageInit();
    });



    function pageInit(){
        $("#cateTable").jqGrid({
            url : "${path}/category/selectAll",
            editurl:"${path}/category/edit",  //增删改走的路径  oper:add ,edit,del
            datatype : "json",  //数据格式
            height : "auto",
            autowidth:true,
            styleUI:"Bootstrap",
            rowNum : 5,
            rowList : [ 5, 8, 10, 20 ],
            pager : '#catePage',  //分页工具栏
            viewrecords : true,
            colNames : [ 'Id', '类别名', '级别', '上级别id'],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'cateName',index : 'invdate',editable:true,width : 90},
                {name : 'levels',index : 'name',width : 100},
                {name : 'parentId',index : 'note',width : 150,sortable : false}
            ],
            subGrid : true, //开启子表格
            // subgrid_id是在创建表数据时创建的div标签的ID
            //点击二级类别按钮，动态创建一个div,此div来容纳子表格，subgrid_id就是这个div的id
            // row_id是该行的ID
            subGridRowExpanded : function(subgrid_id, row_id) {
                addSubGird(subgrid_id, row_id);
            }
        });
        //分页工具栏
        $("#cateTable").jqGrid('navGrid', '#catePage',
            {edit : true,add : true,del : true,addtext:"添加",edittext:"编辑",deltext:"删除"},
            {
                //修改之后的操作
                closeAfterEdit:true,  //关闭对话框
            },{
                //添加操作之后的操作
                closeAfterAdd:true,  //关闭对话框
            },
            { //删除之后的操作
             }
        );
    }

    //创建子表格
    function addSubGird(subgridId, rowId) {

        //声明两个变量
        // 子表格table的id
        var subgridTableId= subgridId + "Table";
        // 子表格分页工具栏的id
        var pagerId= subgridId + "Page";

        //在div中创建子表格和分页工具栏
        $("#"+subgridId).html("<table id="+subgridTableId+" /><div id="+pagerId+" />");

        //初始化子表格
        $("#" + subgridTableId).jqGrid({
            //url : "/category/queryTwoCategory?categoryId="+rowId,
            url:"${path}/category/selectAll1?rowId="+rowId,
            editurl:"${path}/category/edit?rowId="+rowId,  //增删改走的路径  oper:add ,edit,del
            datatype : "json",
            rowNum : 20,
            pager : "#"+pagerId,
            height : "auto",
            autowidth:true,
            styleUI:"Bootstrap",
            rowNum : 5,
            rowList : [ 5, 8, 10, 20 ],
            viewrecords : true,
            colNames : [ 'Id', '类别名', '级别', '上级别id'],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'cateName',index : 'invdate',width : 90,editable:true},
                {name : 'levels',index : 'name',width : 100},
                {name : 'parentId',index : 'note',width : 150,sortable : false}
            ]
        });

        //子表格的分页工具栏
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId,
            {edit : true,add : true,del : true,addtext:"添加",edittext:"编辑",deltext:"删除"},
        {
            //修改之后的操作
            closeAfterEdit:true,  //关闭对话框
        },{
            //添加操作之后的操作
                closeAfterAdd:true,  //关闭对话框
        },
            { //删除之后的操作
            }
            );
    }

</script>

<%--
1.实现类别功能
   删除数据要给一个提示
2.手机验证码发送
--%>

<%--创建一个面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <span>类别信息</span>
    </div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">类别信息</a></li>
    </ul><br>

    <%--创建表格--%>
    <table id="cateTable" />

    <%--分页工具栏--%>
    <div id="catePage"/>

</div>