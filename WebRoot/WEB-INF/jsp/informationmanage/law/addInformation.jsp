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
  		<label class="layui-form-label">法规标题<span>*</span></label>
	    <div class="layui-input-block">
	    	<input type="text" name="title" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">法规简介<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="introduction" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">法规排序</label>
	    <div class="layui-input-block">
	      <input type="number" name="order_num" id="order_num"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item layui-form-text">
	  <label class="layui-form-label">法规内容</label>
	  <div class="layui-input-block">
	  	<textarea id="content" name="content" style="width: 100%;height: 100%" lay-ignore></textarea>
	  </div>
  </div>
  <!-- 隐藏域 -->
   <div class="layui-form-item" style="display:none;">
    <button class="layui-btn"  lay-submit="" lay-filter="saveSubmit">提交</button>
  </div>
</form>
	<script>
		var $;
		layui.config({
			base :  'static/layuiadmin/' //静态资源所在路径
		}).extend({
			webplus : 'lib/webplus', //主入口模块
		}).use([ 'form','webplus', 'layer'], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var form = layui.form;
		    //富文本
		    var ke = KindEditor.create('#content',{
				urlType:"domain", //带域名的绝对路径
				filePostName  : "file",
				//指定上传文件请求的url。
				uploadJson : '<%=basePath%>fileUpload/upload.do',
				height: '360px',
				//上传类型，分别为image、flash、media、file
				dir : "image",
				afterChange: function() {
			        this.sync();
			    },
			    afterBlur: function() {
			        this.sync();
			    }
			});
		    
		    form.on('submit(saveSubmit)', function(data){
		    	
				webplus.doAjax('<%=basePath%>informationLawManage/saveInformation',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		    
		   
		});

	</script>
</body>
</html>