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
  <input type="hidden" name="id" id="id" value="${pd.id}"/>
  <div class="layui-form-item">
    <label class="layui-form-label">添加类型<span>*</span></label>
    <div class="layui-input-inline">
      	<select name="type" id="type" lay-verify="required" lay-filter="type">
      		<option value="">请选择</option>
      		<option value="1" >大类</option>
      		<option value="2" selected >小类</option>
      	</select>
    </div>
  </div>
  <div class="layui-form-item" id="test1">
    <label class="layui-form-label">大类<span>*</span></label>
    <div class="layui-input-inline">
      	<select name="data_type_code" id="data_type_code" >
      		<option value="">请选择</option>
      		<c:forEach items="${pd.typeList }" var="item">
      			<option value="${item.data_type_code }">${item.data_type_name }</option>
      		</c:forEach>
      	</select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">小类编码<span>*</span></label>
    <div class="layui-input-inline">
      	<input type="number" name="dd_item_code" class="layui-input" autocomplete="off">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">小类名称<span>*</span></label>
    <div class="layui-input-inline">
      	<input name="dd_item_name" class="layui-input" autocomplete="off">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">大类编码<span>*</span></label>
    <div class="layui-input-inline">
      	<input type="text" name="username" id="username" lay-verify="username" placeholder="请输入用户名">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">大类名称<span>*</span></label>
    <div class="layui-input-inline">
      	<input type="number" name="data_type_name" class="layui-input" autocomplete="off">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">序号<span>*</span></label>
    <div class="layui-input-inline">
      	<input type="number" lay-filter="order_id" name="order_id" class="layui-input" autocomplete="off">
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
			var $ = layui.$;
		    var webplus=layui.webplus;
		    var form = layui.form;
		    form.on('submit(saveSubmit)', function(data){
				webplus.doAjax('<%=basePath%>ddw/saveAdd',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	}); 
		    
		});
		
	</script>
</body>
</html>