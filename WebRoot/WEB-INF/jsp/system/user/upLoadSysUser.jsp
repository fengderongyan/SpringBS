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
  		<label class="layui-form-label">所属组织<span>*</span></label>
	    <div class="layui-input-block">
	      <c:choose>
	      	<c:when test="${orgInfoMap.role_lev == 5}">
	      		<input type="text" name="org_name" id="org_name" value="${orgInfoMap.org_name }" disabled="disabled" class="layui-input layui-disabled" >
			    <input type="hidden" id="org_id" name="org_id" value="${orgInfoMap.org_id }">
	      	</c:when>
	      	<c:otherwise>
	      		<input type="text" name="org_name" id="org_name" readonly="readonly"
			      	lay-verify="required"  autocomplete="off"  class="layui-input">
			    <input type="hidden" id="org_id" name="org_id">
	      	</c:otherwise>
	      </c:choose>
	      
	    </div>
  </div>
  <div class="layui-form-item">
	<div class="layui-upload-list">
	  	<button type="button" class="layui-btn layui-btn-sm layui-btn-normal" id="choose_file">选择文件</button>
	    <button type="button" class="layui-btn layui-btn-sm" id="upload_file"><i class="layui-icon">&#xe67c;</i>开始上传</button>
	    <a style="text-decoration:underline;cursor:pointer" href="<%=basePath %>uploadFiles/file/sysUserModel.xls">模板下载</a>
	    <br/>
	  	<span id="p_file_name"></span>
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
		}).use([ 'form','webplus', 'layer', 'upload'], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var layer = layui.layer;
		    var form = layui.form;
		    var upload = layui.upload;
		  //选完文件后不自动上传
		  upload.render({
		      elem: '#choose_file'
		      ,auto: false
		      ,url: '<%=basePath%>sysuser/saveUpload.do'
		      ,data: {
		    	  org_id: function(){
		    		  return $('#org_id').val();
		    	  }
		      }
		      ,accept : 'file'
		      ,exts :'xls|xlsx'
		      ,bindAction: '#upload_file'
		      ,choose:function(obj){
		    	  var org_id = $('#org_id').val();
		    	  if(webplus.isEmpty(org_id)){
		    		  webplus.alertError('请先选择所属组织！');
		    		  return false;
		    	  }else{
		    		  obj.preview(function(index, file, result){
		    			  console.log(file.name);
		    			  $('#p_file_name').html(file.name);
		    		  });
		    		  return true;
		    	  }
		      }
		      ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
		    	    layer.load(); //上传loading
	    	  }
	    	  ,done: function(res, index, upload){
	    		
	    	    layer.closeAll('loading'); //关闭loading
	    	    //resultInfo_sp
	    	    if(res.code == '0' || res.code == '1'){
	    	    	$('#resultInfo_sp').html(res.errorMsg);
	    	    	$('#resultInfo_sp').show();
	    	    }else if(res.code == '2'){
	    	    	$('#resultInfo_sp').html('部分导入成功，错误部分点击<a style="color:red;cursor:pointer" href="<%=basePath %>uploadFiles/downloadFile/system/user/' + res.to_file_name + '">错误信息下载</a>查看');
	    	    	$('#resultInfo_sp').show();
	    	    }
	    	  }
	    	  ,error: function(index, upload){
	    	    layer.closeAll('loading'); //关闭loading
	    	  }
		      
		  });
		  
		  $("#org_name").focus(function(){
			  var role_id = 'de9de2f006e145a29d52dfadda295353';//写死
		    	if(webplus.isEmpty(role_id)){
		    		webplus.alertError('请先选择角色！');
		    		return false;
		    	}else{
		    		webplus.openWindow('sysuser/orgTree?role_id=' + role_id,'选择组织','400','400','1');
		    	}
		    });
		  
		});
		
	</script>
</body>
</html>