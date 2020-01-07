<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
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
						<label class="layui-form-label">角色名称</label>
						<div class="layui-input-block">
							<input type="text" name="role_name" placeholder="请输入"
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
<div type="text/html" id="trTool" style="display:none">
	<c:if test="${QX.edit == 1}">
    	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
    	<a class="layui-btn layui-btn-success layui-btn-xs" lay-event="roleMenu"><i class="layui-icon layui-icon-auz"></i>菜单授权</a>
    	<a class="layui-btn layui-btn-success layui-btn-xs" lay-event="roleButtonAdd"><i class="layui-icon layui-icon-auz"></i>增</a>
    	<a class="layui-btn layui-btn-success layui-btn-xs" lay-event="roleButtonDel"><i class="layui-icon layui-icon-auz"></i>删</a>
    	<a class="layui-btn layui-btn-success layui-btn-xs" lay-event="roleButtonEdit"><i class="layui-icon layui-icon-auz"></i>改</a>
	</c:if>
	<c:if test="${QX.del == 1 }">
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
	</c:if>
</div>
<div type="text/html" id="toolbar" style="display:none" >
  <c:if test="${QX.add == 1}">
	  <div class="layui-btn-container">
	    <button class="layui-btn layui-btn-sm" lay-event="add"> <i class="layui-icon">&#xe654;</i>添加</button>
	    <button class="layui-btn layui-btn-sm" lay-event="adminRoleMenu">admin菜单授权</button>
	  </div>
  </c:if>
</div>
<script>
	layui.config({
		base :  'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus  : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus' ], function() {
		var $ = layui.$, 
		form = layui.form, 
		table = layui.table,
		webplus=layui.webplus;
		var cols=[ [ {
			type : 'numbers',
			title : '序号',
			width : 50
		},{
			field : 'role_ID',
			title : '角色编号',
			hide:true
		}, {
			field : 'role_NAME',
			title : '角色名称',
			minWidth : 150
		}, {
			title : '操作',
			toolbar : '#trTool',
			width : 400,
			fixed: 'right'
		} ] ];
		
		webplus.init('<%=basePath%>role/list2',cols);
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
		    	   
		    	   var roleId=obj.value;
		    	   var params={};
		    	   params.roleId=roleId;
		    	   params.status=status;
		    	   webplus.doAjax("system/role/update",params,"","0");
			  }
			  
		  });
		 
		  //监听启用操作
		  form.on('switch(editMode)', function(obj){
			  if(webplus.checkFormEditMode(obj)){
				  var editMode="0";
		    	   if(obj.elem.checked){
		    		   editMode="1";
		    	   }
		    	   
		    	   var params={};
		    	   params.roleId=obj.value;
		    	   params.editMode=editMode;
		    	   webplus.doAjax("system/role/update",params,"","","3",function(data){
		    		   rowObj.update({editMode:editMode});
		    	   });
			  }
			 
	    	   
		  });
		 //监听行工具事件
		  table.on('tool(dataList)', function(obj){
		    var data = obj.data;
		    //判断操作的按钮类别  需要在标签上配置  lay-event='判断的类型值'
		    switch(obj.event){
		    	case 'edit':
		    		//打开编辑弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    	     webplus.openWindowBase('<%=basePath%>role/toEdit2?ROLE_ID=' + data.role_ID,'编辑角色','500','450');
		    	   }
		   			break;
		    	case 'roleMenu':
		    		//打开编辑弹出框
		    		if(webplus.checkRowEditMode(obj)){
		    		  webplus.openWindowBase('<%=basePath%>role/roleMenu?ROLE_ID=' + data.role_ID + '&flag=2','授权菜单','400','');
		    		}
		   			break;
		    	case 'roleUser':
		    		if(webplus.checkRowEditMode(obj)){
		    		  webplus.openDetailWindow('system/role/roleUser','授权用户',obj,'roleId');
		    		}
		       		
		   			break;
		    	case 'del':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {ROLE_ID:obj.data.role_ID};
		    			webplus.doAjax('<%=basePath%>role/delete2',params,'删除角色相应的权限也会删除，请慎重','','0');
		    		}
		    		break;
		    	case 'roleButtonAdd':
		    		if(webplus.checkRowEditMode(obj)){
		    			webplus.openWindowBase('<%=basePath%>role/b4Button.do?ROLE_ID=' + data.role_ID + '&msg=add_qx','授权新增权限','400','');
		    		}
		    		break;
		    	case 'roleButtonDel':
		    		if(webplus.checkRowEditMode(obj)){
		    			webplus.openWindowBase('<%=basePath%>role/b4Button.do?ROLE_ID=' + data.role_ID + '&msg=del_qx','授权删除权限','400','');
		    		}
		    		break;
		    	case 'roleButtonEdit':
		    		if(webplus.checkRowEditMode(obj)){
		    			webplus.openWindowBase('<%=basePath%>role/b4Button.do?ROLE_ID=' + data.role_ID + '&msg=edit_qx','授权修改权限','400','');
		    		}
		    		break;
		    }
		  });
		 
		 
		 //监听表头按钮事件
		  table.on('toolbar(dataList)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		   
		    switch(obj.event){
		      	case 'add':
		      		 var parent_id = '${pd.ROLE_ID }';
			         //打开添加弹出框
			         webplus.openWindow('<%=basePath%>role/toAdd2?parent_id=' + parent_id,'新增角色','500','500');
		     	     break;
		      	case 'adminRoleMenu':
		      		//admin菜单授权
		      		webplus.openWindowBase('<%=basePath%>role/roleMenu?flag=1&ROLE_ID=e2cdd69a87f411e9b2ec00163e1024a6','admin菜单授权','400','');
			        break;
		    }
		  });	
			

		});
	</script>
  
  
  </script>
</body>
</html>
