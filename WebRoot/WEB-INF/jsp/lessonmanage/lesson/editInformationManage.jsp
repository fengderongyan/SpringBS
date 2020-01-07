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
  		<label class="layui-form-label">课程标题<span>*</span></label>
	    <div class="layui-input-block">
	    	<input type="text" name="title" lay-verify="required"  autocomplete="off" value="${informationMap.title }"  class="layui-input">
	    	<input type="hidden" id="info_id" name="info_id" value="${informationMap.id }" >
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">课程简介<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="introduction" lay-verify="required"  autocomplete="off"  class="layui-input" value="${informationMap.introduction }" >
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">课程排序</label>
	    <div class="layui-input-block">
	      <input type="number" name="order_num" id="order_num"  autocomplete="off"  class="layui-input" value="${informationMap.order_num }" >
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">课程类型</label>
	    <div class="layui-input-block">
	      <select name="lesson_type" lay-filter="lesson_type">
	      	<c:forEach items="${lessonList}" var="type">
	      		<option value="${type.id }" ${informationMap.lesson_type == type.id ? 'selected' : '' } >${type.title} </option>
	      	</c:forEach>
	      </select>
	    </div>
  </div>
  <div class="layui-form-item" >
	  <div class="layui-upload-list" id="lay-img">
	  	<button type="button" class="layui-btn" id="cover_upload"><i class="layui-icon">&#xe67c;</i>上传封面</button>
	    <img class="layui-upload-img" id="cover_img" src="${informationMap.cover }" width="70px">
	    <input type="hidden" id="cover" name="cover" value="${informationMap.cover }" >
	  </div>
  </div>
  
  <div class="layui-form-item">
  		<label class="layui-form-label">类别<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="radio" lay-filter="type"  id="type" name="type" value="1" title="行业" ${informationMap.type == 1 ? 'checked' : '' } >
	      <input type="radio" lay-filter="type"  id="type" name="type" value="2" title="园区" ${informationMap.type == 2 ? 'checked' : '' } >
	      <input type="radio" lay-filter="type"  id="type" name="type" value="3" title="企业" ${informationMap.type == 3 ? 'checked' : '' } >
	    </div>
    </div>
    <div class="layui-form-item" id="hy" style="display: none;">
  		<label class="layui-form-label">行业</label>
	    <div class="layui-input-block">
	    	<input type="text" name="text1" id="text1" readonly="readonly" value="${informationMap.target_id_name }"
	      	  autocomplete="off"  class="layui-input">
	    	<input type="hidden" id="target_id1" name="target_id1" value="${informationMap.target_id }" >
	    </div>
  </div>
  <div class="layui-form-item" id="yq" style="display: none;">
  		<label class="layui-form-label">园区</label>
	    <div class="layui-input-block">
	      <select name="target_id2">
	      	<c:forEach items="${yqList}" var="type">
	      		<option value="${type.org_id }" ${informationMap.type == 2 && informationMap.target_id == type.org_id ? 'selected' : '' } >${type.org_name} </option>
	      	</c:forEach>
	      </select>
	    </div>
  </div>
  <div class="layui-form-item" id="qy" style="display: none;">
  		<label class="layui-form-label">企业<span>*</span></label>
	    <div class="layui-input-block">
	     <input type="text" name="org" id="org" readonly="readonly" value="${informationMap.org_name }"
	      	autocomplete="off"  class="layui-input">
	      <input type="hidden" id="target_id3" name="target_id3" value="${informationMap.target_id }" >
	    </div>
  </div>
  <div class="layui-form-item" id="dz" style="display: none;">
  		<label class="layui-form-label">是否定制<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="radio" lay-filter="is_public"   name="is_public" value="0" title="是" ${informationMap.is_public == 0 ? 'checked' : '' } >
	      <input type="radio" lay-filter="is_public"   name="is_public" value="1" title="否" ${informationMap.is_public == 1 ? 'checked' : '' } >
	    </div>
    </div>
    <div class="layui-form-item">
  		<label class="layui-form-label">课程视频<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="type_name" id="type_name" readonly="readonly"
	      	lay-verify="required"  autocomplete="off"  class="layui-input" value="${informationMap.video_name }">
	    </div>
    </div>
   <div class="layui-form-item">
  		<label class="layui-form-label">视频地址<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="video" id="video" readonly="readonly"  autocomplete="off"  class="layui-input" value="${informationMap.video }" >
	      <input type="hidden" name="expert_id" id="expert_id" value="${informationMap.expert_id }" >
	      <input type="hidden" name="area_id" id="area_id" value="${informationMap.area_id }" >
	      <input type="hidden" name="org_id" id="org_id" value="${informationMap.org_id }" >
	      <input type="hidden" name="video_id" id="video_id" value="${informationMap.video_id }" >
	      <input type="hidden" name="video_name" id="video_name" value="${informationMap.video_name }" >
	    </div>
  </div>
  <div class="layui-form-item layui-form-text">
	  <label class="layui-form-label">课程详情</label>
	  <div class="layui-input-block">
	  	<textarea id="content" name="content" style="width: 100%;height: 100%" lay-ignore>${informationMap.content }</textarea>
	  </div>
  </div>
  <!-- 隐藏域 -->
   <div class="layui-form-item" style="display:none;">
    <button class="layui-btn"  lay-submit="" lay-filter="saveSubmit">提交</button>
  </div>
</form>
	<script>
		var $;
		var layer;
		var webplus;
		layui.config({
			base :  'static/layuiadmin/' //静态资源所在路径
		}).extend({
			webplus : 'lib/webplus', //主入口模块
		}).use([ 'form','webplus', 'layer', 'upload'], function() {
			$ = layui.$;
			layer = layui.layer;
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
			
			//选择视频
			$("#type_name").focus(function(){
	    		webplus.openWindow('lessonmanage/lesson/lessonTypeList','选择课程视频','600','400','1');
		    });
		    
		    //选择企业
			$("#org").focus(function(){
	    		webplus.openWindow('lessonmanage/lesson/orgList','选择企业','600','400','1');
		    });
		    //监听下拉框 
		    form.on('select(lesson_type)', function(data){
				var selectValue = data.value;
				var url = '<%=basePath%>lessonmanage/lesson/getUpHy.do';
		    	var param = {lesson_type: selectValue};
		    	var result = webplus.doReturnAjax(url, param,'','','text');
		    	var values = result.split('&');
		    	var id = values[0];
		    	var name = values[1];
		    	$('#text1').val(name);
		    	$('#target_id1').val(id);
			});
		    //监听单选框
			form.on('radio(type)', function(data){
				var radioValue = data.value;
				if(radioValue == 1){
					$('#hy').show();
					$('#yq').hide();
					$('#qy').hide();
					$('#dz').hide();
					$('#org').removeAttr('lay-verify');
				}else if(radioValue == 2){
					$('#yq').show();
					$('#hy').hide();
					$('#qy').hide();
					$('#dz').hide();
					$('#org').removeAttr('lay-verify');
				}else if(radioValue == 3){//企业
					$('#qy').show();
					$('#dz').show();
					$('#hy').hide();
					$('#yq').hide();
					$('#org').attr('lay-verify', 'required');
				}
			});
		    
		    layer.photos({
			  photos: '#lay-img'
			  ,anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
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
		    		layer.msg('请上传课程封面！');
		    		return false;
		    	}
		    	var is_public = $("input[name='is_public']:checked").val();
		    	var type = $("input[name='type']:checked").val();
		    	if(webplus.isEmpty(type) ){
		    		layer.msg('请选择类别！');
		    		return false;
		    	}
		    	if(webplus.isEmpty(is_public) && type == 3){
		    		layer.msg('请选择是否定制！');
		    		return false;
		    	}
				webplus.doAjax('<%=basePath%>lessonmanage/lesson/updateInformation',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		});
		
		init();
		function init(){
			var radioValue = '${informationMap.type}';
			if(radioValue == 1){
				$('#hy').show();
				$('#yq').hide();
				$('#qy').hide();
				$('#dz').hide();
				$('#org').removeAttr('lay-verify');
			}else if(radioValue == 2){
				$('#yq').show();
				$('#hy').hide();
				$('#qy').hide();
				$('#dz').hide();
				$('#org').removeAttr('lay-verify');
			}else if(radioValue == 3){//企业
				$('#qy').show();
				$('#dz').show();
				$('#hy').hide();
				$('#yq').hide();
				$('#org').attr('lay-verify', 'required');
			}
		}
		
		
		
	</script>
</body>
</html>