<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<style>
html{background:#fff;}
body{padding:0 0 5px 0!important;}
body>form>ul{background:#fff;}
</style>
<link rel="stylesheet" href="static/layuiadmin/otherjs/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" media="all">
<body>
<div class="layui-fluid" style="height: 98%">
	<ul id="ztree" class="ztree"></ul>
</div>

<script type="text/javascript" src="static/layuiadmin/otherjs/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="static/layuiadmin/otherjs/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="static/layuiadmin/otherjs/lib/ztree/js/jquery.ztree.exedit.js"></script>
<script>

	layui.config({
		base : 'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus' ], function() {
		var $ = layui.$, 
		form = layui.form, 
		webplus=layui.webplus;
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: zTreeOnClick//节点点击事件
			}
		};
		function zTreeOnClick(event, treeId, treeNode) {
		    $("#org_name", parent.document).val(treeNode.name);
		    $("#org_id", parent.document).val(treeNode.id);
            parent.layer.closeAll();
		};
		var role_id = '${param.role_id}';
		webplus.initTree("sysuser/loadOrgTree",setting, 'ztree', {role_id : role_id});
		
	});
	</script>
<link rel="stylesheet" href="static/layuiadmin/othercss/css/common/openshow.css" media="all"/>
</body>
</html>