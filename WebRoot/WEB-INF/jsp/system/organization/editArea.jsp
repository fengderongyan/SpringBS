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
  <!-- 默认江苏省 -->
  <input type="hidden" name="province_id" value="32" class="layui-input">
  <!-- 默认市区连云港市 -->
  <input type="hidden" name="city_id" value="3207" class="layui-input">
  <div class="layui-form-item">
  	<input type="hidden" name="org_lev" value="${orgMap.org_lev }">
  	 <input type="hidden" name="org_id" value="${orgMap.org_id }">
    <label class="layui-form-label">县区名称 <span>*</span></label>
    <div class="layui-input-block">
      <select name="county_id" lay-verify="required">
      	<option value="">请选择</option>
      	<c:forEach items="${countyList }" var="countyMap">
      		<option value="${countyMap.org_id }" <c:if test="${countyMap.org_id == orgMap.county_id }">selected</c:if>>${countyMap.org_name }</option>
      	</c:forEach>
      </select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">园区名称 <span>*</span></label>
    <div class="layui-input-block">
      <input type="text" name="org_name" value="${orgMap.org_name}" lay-verify="required|max:50"  autocomplete="off"  class="layui-input">
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
			webplus.initForm('<%=basePath%>org/saveEditOrg');
		});

	</script>
</body>
</html>