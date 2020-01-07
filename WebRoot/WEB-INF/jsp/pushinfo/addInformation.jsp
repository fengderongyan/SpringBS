<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<link rel="stylesheet" href="static/layuiadmin/othercss/css/common/openshow.css" media="all"/>
<script type="text/javascript" src="static/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="static/kindeditor/lang/zh-CN.js"></script>

<body>
<form class="layui-form layui-form-pane" action="" id="saveForm">
  <div class="layui-form-item">
  		<label class="layui-form-label">消息标题<span>*</span></label>
	    <div class="layui-input-block">
	    	<input type="text" name="title" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">消息内容<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="content" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  
  <!-- 隐藏域 -->
   <div class="layui-form-item" style="display:none;">
    <button class="layui-btn"  lay-submit="" lay-filter="saveSubmit">推送</button>
  </div>
</form>
	<script>
		var $;
		var webplus;
		layui.config({
			base :  'static/layuiadmin/' //静态资源所在路径
		}).extend({
			webplus : 'lib/webplus', //主入口模块
		}).use([ 'form','webplus', 'layer', 'upload'], function() {
			$ = layui.$;
		    webplus=layui.webplus;
		    var form = layui.form;
		    var upload = layui.upload;
		    
		    form.on('submit(saveSubmit)', function(data){
				webplus.doAjax('<%=basePath%>push/pushinfo/saveAndPush',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		});
	</script>
</body>
</html>