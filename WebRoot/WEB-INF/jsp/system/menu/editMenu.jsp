<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<link rel="stylesheet" href="static/layuiadmin/othercss/css/common/openshow.css" media="all"/>
<body>
<form class="layui-form layui-form-pane" action="" id="saveForm"  autocomplete="off">
  <input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${MENU_ID}"/>
  <input type="hidden" name="MENU_ID" id="MENU_ID" value="${pd.MENU_ID}"/>
  <div class="layui-form-item">
    <label class="layui-form-label">父级菜单 </label>
    <div class="layui-input-block">
      <input type="text" name="MENU_NAME" readonly="readonly" class="layui-input layui-disabled" value="${pds.MENU_NAME }"> 
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">菜单名称<span>*</span> </label>
    <div class="layui-input-block">
      	<input type="text" name="MENU_NAME" lay-verify="required|max:50" class="layui-input" value="${pd.MENU_NAME }">
    </div>
  </div>
 <div class="layui-form-item">
    <label class="layui-form-label">菜单链接<span>*</span> </label>
    <div class="layui-input-block">
      	<input type="text" name="MENU_URL"  class="layui-input" 
      		<c:if test="${MENU_ID == 0}">placeholder="顶级菜单无需填写链接" readonly="readonly"</c:if> value="${pd.MENU_URL }">
    </div>
 </div>
 <div class="layui-form-item">
 	<div class="layui-inline">
	    <label class="layui-form-label">图标<span>*</span> </label>
	    <div class="layui-input-inline">
	      	<input type="text" id="MENU_ICON" name="MENU_ICON" value="${pd.MENU_ICON }" lay-verify="required" class="layui-input">
	    </div>
    </div>
 	<div class="layui-inline">
	    <label class="layui-form-label">序号<span>*</span> </label>
	    <div class="layui-input-inline">
	      	<input type="text" name="MENU_ORDER" value="${pd.MENU_ORDER }" lay-verify="number" class="layui-input">
	    </div>
    </div>
 </div>
  <!-- 隐藏域 -->
   <div class="layui-form-item" style="display:none;">
    <button class="layui-btn"  lay-submit="" lay-filter="saveSubmit">提交</button>
  </div>
</form>
	<script>
		layui.config({
			base :  'static/layuiadmin/' //静态资源所在路径
		}).extend({
			webplus : 'lib/webplus', //主入口模块
		}).use([ 'form','webplus' ], function() {
		    var webplus=layui.webplus;
		    $("#MENU_ICON").focus(function(){
		    	webplus.openWindow('menu/plugsIcon','选择图标','500','400','1');
		    });
			webplus.initForm('<%=basePath%>menu/edit');
		});

	</script>
</body>
</html>