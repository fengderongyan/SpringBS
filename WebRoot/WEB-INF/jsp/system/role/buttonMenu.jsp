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
<link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css"/>
<script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
<body>
<form class="layui-form layui-form-pane" action="" id="saveForm">
    <ul id="ztree" class="tree" style="overflow:auto;"></ul>
  <input type="hidden" id="ROLE_ID" value="${ROLE_ID}"/>
   <div class="layui-form-item" style="display:none;">
    <button class="layui-btn" id="submitForm" lay-submit="" lay-filter="saveSubmit">提交</button>
  </div>
</form>

<!--   <div th:include="include :: footer"></div>
   <script type="text/javascript" th:src="@{/lib/ztree/js/jquery.ztree.core.js}"></script>
   <script type="text/javascript" th:src="@{/lib/ztree/js/jquery.ztree.excheck.js}"></script>
   <script type="text/javascript" th:src="@{/lib/ztree/js/jquery.ztree.exedit.js}"></script> -->
	<script>
	var zTree;
	layui.config({
		base : 'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus' ], function() {
		var $ = layui.$, 
		form = layui.form, 
		webplus=layui.webplus;
		//数据的配置
		/* var setting = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "s" }
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		}; */
		var setting = {
		    showLine: true,
		    checkable: true
		};
		var zn = '${zTreeNodes}';
		var zTreeNodes = eval(zn);
		zTree = $("#ztree").zTree(setting, zTreeNodes);
		/* var roleId=$("#roleId").val();
		webplus.initTree("system/role/listRoleMenu?roleId="+roleId,setting) */
	   
	    form.on('submit(saveSubmit)', function(data){
			 var nodes = zTree.getCheckedNodes();
				var tmpNode;
				var ids = "";
				for(var i=0; i<nodes.length; i++){
					tmpNode = nodes[i];
					if(i!=nodes.length-1){
						ids += tmpNode.id+",";
					}else{
						ids += tmpNode.id;
					}
			 }
			var msg = "${msg}";
			var ROLE_ID = "${ROLE_ID}";
			var url = "<%=basePath%>role/saveB4Button.do";
			var params = {"ROLE_ID":ROLE_ID,"menuIds":ids,"msg":msg};
			 webplus.doAjax(url,params,'','','2');
			//阻止表单默认提交
		    return false;
		  }); 
			
			function getTreeCheckNodeId(){
				var ids= "";
				var treeObj = $.fn.zTree.getZTreeObj("ztree");
		        var nodes = treeObj.getCheckedNodes(true);
		        var len = nodes.length;
		             for (var i=0; i<len; i++) {
		               if(nodes[i].id!=0){
		                   if(i==len-1){
		                	   ids += nodes[i].id
		                   }else{
		                	   ids += nodes[i].id+",";
		                   }
		                	
		              }
		          }
		       return ids;      
			}
			
		});
	</script>

<link rel="stylesheet"    th:href="@{/css/common/openshow.css}" media="all"/>

</body>
</html>