<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<body>
<div class="layui-fluid" style="height: 98%">
	<div class="layui-row tree-item" >
		<div class="childLeft-item">
			<div class="layui-card" style="overflow:auto">
	          <div class="layui-card-header">菜单树</div>
	          <div class="layui-card-body">
	          	<ul id="ztree" class="tree"></ul>
	          </div>
	        </div>
		</div>
		<div class="childRight-item">
			<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>menu.do?MENU_ID=${MENU_ID}" style="margin:0 auto;width:100%;height:100%;"></iframe>
		</div>
	</div>
</div>
<link rel="stylesheet"  href="static/layuiadmin/style/font-awesome/css/font-awesome.min.css" media="all"/>
<link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css"/>
<script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
<script>
	var zTree;
	layui.config({
		base : 'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus' ], function() {
		var $ = layui.$, 
		form = layui.form, 
		table = layui.table,
		webplus=layui.webplus;
		var setting = {
			    showLine: true,
			    checkable: false
			};
		var zTreeNodes = eval(${zTreeNodes});
		zTree = $("#ztree").zTree(setting, zTreeNodes);
	});
	</script>
</body>
</html>