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
	<div class="layui-upload-list">
	  	<button type="button" class="layui-btn layui-btn-sm layui-btn-normal" id="choose_file">选择文件</button>
	    <button type="button" class="layui-btn layui-btn-sm" id="upload_file"><i class="layui-icon">&#xe67c;</i>开始上传</button>
	    <a style="text-decoration:underline;cursor:pointer" href="<%=basePath %>uploadFiles/file/questionModel.xls">模板下载</a>
	</div>
  </div>
  <p align="center">
	 <span id="resultInfo_sp" style="display: none"></span>		
  </p>
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
		}).use([ 'form','webplus', 'upload'], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var form = layui.form;
		    var upload = layui.upload;
		  //选完文件后不自动上传
		  upload.render({
		      elem: '#choose_file'
		      ,auto: false
		      ,url: '<%=basePath%>question/impResult.do'
		      ,accept : 'file'
		      ,exts :'xls'
		      ,bindAction: '#upload_file'
		      ,before: function(obj){
		    	  webplus.showLoading(); //上传loading
	    	  }
	    	  ,done: function(res, index, upload){
	    		  webplus.hideLoading(); //关闭loading
	    		  
	    	    if(res.code == '0' || res.code == '1'){
	    	    	$('#resultInfo_sp').html(res.errorMsg);
	    	    	$('#resultInfo_sp').show();
	    	    }else if(res.code == '2'){
	    	    	$('#resultInfo_sp').html('部分导入成功，错误部分点击<a style="color:red;cursor:pointer" href="<%=basePath %>uploadFiles/downloadFile/exammanage/questioninfo/' + res.to_file_name + '">错误信息下载</a>查看');
	    	    	$('#resultInfo_sp').show();
	    	    }
	    	  }
	    	  ,error: function(index, upload){
	    		  webplus.hideLoading(); //关闭loading
	    	  }
		      
		  });
		  
		});
		
	</script>
</body>
</html>