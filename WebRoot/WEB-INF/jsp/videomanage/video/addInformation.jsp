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
  		<label class="layui-form-label">视频标题<span>*</span></label>
	    <div class="layui-input-block">
	    	<input type="text" name="title" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">视频类型<span>*</span></label>
	    <div class="layui-input-block">
	      <select name="type">
	      	<c:forEach items="${videoTypeList}" var="type">
	      		<option value="${type.id }">${type.title} </option>
	      	</c:forEach>
	      </select>
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">视频简介<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="introduction" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">专家信息<span>*</span></label>
	    <div class="layui-input-inline">
	      <input type="text" name="expert_name" id="expert_name" readonly="readonly"
	      	lay-verify="required"  autocomplete="off"  class="layui-input">
	      <input type="hidden" id="expert_id" name="expert_id">
	    </div>
    </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">视频排序</label>
	    <div class="layui-input-block">
	      <input type="number" name="order_num" id="order_num"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item" >
	  <div class="layui-upload-list" id="lay-img">
	  	<button type="button" class="layui-btn" id="cover_upload"><i class="layui-icon">&#xe67c;</i>上传封面</button>
	    <img class="layui-upload-img" id="cover_img"  width="70px">
	    <input type="hidden" id="cover" name="cover" >
	  </div>
  </div>
  <div class="layui-form-item" >
	  <div class="layui-upload-list" style="display: flex; justify-content: flex-start;align-items: flex-end; ">
		 	<button type="button" class="layui-btn" id="video_upload" onclick="videoUp();"><i class="layui-icon">&#xe67c;</i>视频上传</button>
		 	&nbsp;&nbsp;
		 	<iframe id="videoSrc" height="200" width="510"  frameborder="1" allowfullscreen></iframe>
		 	<!-- <video id="videoSrc" controls="controls" style="height: 100px;"  src="" /> -->
	  </div>
  </div>
   <div class="layui-form-item">
  		<label class="layui-form-label">视频地址<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="video" id="video"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item layui-form-text">
	  <label class="layui-form-label">视频详情</label>
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
			
			//选择专家
			$("#expert_name").focus(function(){
	    		webplus.openWindow('videomanage/video/expertList','选择专家','600','400','1');
		    });
			
			//封面上传
		   upload.render({
			    elem: '#cover_upload'
			    ,url: '<%=basePath%>fileUpload/upload.do'
			    ,before: function(){webplus.showLoading();}
			    ,done: function(res){
			      if(res.code == 0){//如果上传成功
			        webplus.hideLoading();
			    	$('#cover_img').attr('src', res.url);
			      	$('#cover').val(res.url);
			      }else{ //如果上传失败
			      	webplus.hideLoading();
			      	return layer.msg('上传失败');
			      }
			    }
			});
			
			layer.photos({
			  photos: '#lay-img'
			  ,anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
			}); 
		    
		    form.on('submit(saveSubmit)', function(data){
		    	var cover = $('#cover').val();
		    	if(webplus.isEmpty(cover)){
		    		layer.msg('请上传视频封面！');
		    		return false;
		    	}
				webplus.doAjax('<%=basePath%>videomanage/video/saveInformation',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		});
		
		function videoUp(){
    		 var title = '视频上传';
    		 var url = '<%=basePath%>informationAccidentManage/videoUpload';
    		 var w = '500';
    		 var h = '350';
    		 webplus.openWindow(url,title,w,h,'1');
		}
		
	</script>
</body>
</html>