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
<form class="layui-form layui-form-pane" action="" id="saveForm">
  <input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}"/>
  <input name="PARENT_ID" id="parent_id" value="${pd.parent_id }" type="hidden">
  <div class="layui-form-item">
    <label class="layui-form-label">角色名称 <span>*</span></label>
    <div class="layui-input-block">
      <input type="text" name="ROLE_NAME"   lay-verify="required|max:50"  autocomplete="off"  class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">角色级别 <span>*</span></label>
    <div class="layui-input-block">
      <select name="ROLE_LEV" lay-verify="required" id="role_lev">
	      <option value="">请选择</option>
	      <option value="0">系统级别</option>
	      <option value="3">县区级别</option>
	      <option value="4">园区级别</option>
	      <option value="5">企业级别</option>
	      <option value="6">员工级别</option>
	  </select>
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
			webplus.initForm('<%=basePath%>role/add2');
		});

	</script>
</body>
</html>