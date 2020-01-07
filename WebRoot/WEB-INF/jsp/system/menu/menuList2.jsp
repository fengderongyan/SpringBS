<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<body>
<div class="layui-fluid">
	<div class="layui-card">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto" id="searchForm">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">菜单名称</label>
					<div class="layui-input-block">
					
					<input type="hidden" name="MENU_ID" id="MENU_ID" value="${MENU_ID }"/>
					<input type="text" name="MENU_NAME" id="MENU_NAME" placeholder="请输入"
							autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<button class="layui-btn layuiadmin-btn-sm" lay-submit
						lay-filter="searchSubmit" >查询</button>
				</div>
			</div>
		</div>
		<div class="layui-card-body">
			<table id="dataList" lay-filter="dataList" ></table>
		</div>
	</div>
</div>
<link rel="stylesheet"  href="static/layuiadmin/style/font-awesome/css/font-awesome.min.css" media="all"/>
<div type="text/html" id="trTool" style="display:none">
    <a  class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
    <a  class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
</div>
<div type="text/html" id="toolbar" style="display:none" >
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>新增菜单</button>
  </div>
</div>
<script type="text/html" id="iconTpl">
  <li><span class="{{d.menu_ICON}}" aria-hidden="true"></span></li></ul>
</script>
<script type="text/html" id="statusTpl">
  <input type="checkbox" name="menu_STATE"  value="{{d.menu_ID}}" title="启用" lay-filter="status" {{ d.menu_STATE == '1' ? 'checked' : '' }}>
</script>
	<script>
	layui.config({
		base : 'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus' ], function() {
		var $ = layui.$, 
		form = layui.form, 
		table = layui.table,
		webplus=layui.webplus;
		var cols=[ [ {
			type : 'numbers',
			title : '序号',
			width : 60
		},{
			field : 'menu_ICON',
			title : '图标',
			templet: '#iconTpl',
			align:'center',
			unresize: true,
			width : 60
		}, {
			field : 'menu_NAME',
			title : '菜单名称',
			width : 140
		},{
			field : 'menu_ID',
			title : '菜单编码',
			minWidth : 120
		}, {
			field : 'menu_URL',
			title : 'url地址',
			minWidth : 180
		},{
			field : 'menu_TYPE',
			title : '菜单类型',
			width : 100
		}, {
			field : 'menu_STATE',
			title : '当前状态',
			templet: '#statusTpl', 
			unresize: true,
			width : 110
		}, {
			title : '操作',
			toolbar : '#trTool',
			width : 250,
			fixed: 'right'
		} ] ];
		  webplus.init('menu/list2', cols, '', '', '', '', {MENU_ID : '${MENU_ID}'});
			 //监听行工具事件
			  table.on('tool(dataList)', function(obj){
			    var data = obj.data;
			    //判断操作的按钮类别  需要在标签上配置  lay-event='判断的类型值'
			    switch(obj.event){
			    	case 'edit':
			    		//打开编辑弹出框
			    		if(webplus.checkRowEditMode(obj)){
			    			webplus.openWindowBase('<%=basePath%>menu/toEdit?MENU_ID=' + data.menu_ID,'编辑菜单','700','510');
			    		}
			   			break;
			    	case 'del':
			    		if(webplus.checkRowEditMode(obj)){
			    			var params = {MENU_ID : data.menu_ID};
			    			webplus.doAjax('<%=basePath%>menu/delete',params,'确定删除此菜单吗','','0');
			    		}
			    		break;
			    }
			  });
			 
			 //监听表头按钮事件
			  table.on('toolbar(dataList)', function(obj){
			    switch(obj.event){
			      	case 'add':
				         //打开新增弹出框
				         var MENU_ID = '${pd.MENU_ID}';
				         webplus.openWindowBase('<%=basePath%>menu/toAdd?MENU_ID=' + MENU_ID,'新增菜单','700','510');
			     	     break;
			    }
			  });
			  var rowObj;
			     //行单击事件
				  table.on('row(dataList)', function(obj){
					  obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
					     rowObj=obj;
				     });
			      //监听启用操作
				  form.on('checkbox(status)', function(obj){
					  if(webplus.checkFormEditMode(obj)){
						  var status="0";
				    	   if(obj.elem.checked){
				    		   status="1";
				    	   }
				    	   var params={};
				    	   params.MENU_ID=obj.value;
				    	   params.MENU_STATE=status;
				    	   webplus.doAjax("<%=basePath%>menu/updateState",params,"","","0");
					  }
					 
				  });
		});
	</script>

</body>
</html>