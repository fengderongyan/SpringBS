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
  		<div class="layui-inline" style="width: 50%">
	  		<label class="layui-form-label">用户名<span>*</span></label>
		    <div class="layui-input-inline">
 		    	<input type="text" name="username" lay-verify="required"  autocomplete="off"  class="layui-input" width="100px">
		    </div>
	    </div>
	    <div class="layui-inline">
	  		<label class="layui-form-label">姓名<span>*</span></label>
		    <div class="layui-input-inline">
		      <input type="text" name="name" lay-verify="required"  autocomplete="off"  class="layui-input">
		    </div>
	    </div>
  </div>
  <div class="layui-form-item">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label">角色<span>*</span></label>
	    <div class="layui-input-inline">
	    	<select name="role_id" lay-verify="required" id="role_id" lay-filter="roleChange" disabled>
	      		<option value="9ce87784fd90448b9e4b6e03427979ac">员工</option>
	      	</select>
	    </div>
    </div>
    <div class="layui-inline">
  		<label class="layui-form-label">所属组织<span>*</span></label>
	    <div class="layui-input-inline">
	      <input type="text" name="org_name" id="org_name" readonly="readonly"
	      	lay-verify="required"  autocomplete="off"  class="layui-input">
	      <input type="hidden" id="org_id" name="org_id">
	    </div>
    </div>
  </div>
  <div class="layui-form-item">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label">性别<span>*</span></label>
	    <div class="layui-input-inline" style="height: 36px;border: 1px solid; border-color: #e6e6e6">
	    	<input type="radio" name="sex" value="1" title="男" checked>
      		<input type="radio" name="sex" value="0" title="女" >
	    </div>
    </div>
    <div class="layui-inline">
  		<label class="layui-form-label">电话<span>*</span></label>
	    <div class="layui-input-inline">
	      <input type="text" name="phone" lay-verify="required|phone"  autocomplete="off"  class="layui-input">
	    </div>
    </div>
  </div>
  <div class="layui-form-item">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label">岗位<span>*</span></label>
	    <div class="layui-input-inline" >
	    	 <input type="text" name="station" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
    </div>
    <div class="layui-inline">
  		<label class="layui-form-label">是否需证书<span>*</span></label>
	    <div class="layui-input-inline">
	     	<select name="is_need_certificate" id="is_need_certificate" lay-verify="required" id="is_need_certificate" lay-filter="needChange">
	      		<option value="">请选择</option>
	      		<option value="1">是</option>
	      		<option value="0">否</option>
	      	</select>
	    </div>
    </div>
  </div>
  <div class="layui-form-item" style="display: none" id="certificate_info_div">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label">证书编号<span>*</span></label>
	    <div class="layui-input-inline" >
	    	 <input type="text" id="certificate_num" name="certificate_num" autocomplete="off"  class="layui-input">
	    </div>
    </div>
    <div class="layui-inline">
  		<label class="layui-form-label">证书名称<span>*</span></label>
	    <div class="layui-input-inline">
	      	<!-- <input type="text" id="certificate_end_date" name="certificate_end_date" lay-verify="date"
	      		autocomplete="off"  class="layui-input"> -->
			<input type="text" id="certificate_name" name="certificate_name" autocomplete="off"  class="layui-input">
	    </div>
    </div>
  </div>
  <div class="layui-form-item" style="display: none" id="certificate_info_div2">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label">发证日期<span>*</span></label>
	    <div class="layui-input-inline" >
	    	 <input type="text" name="certificate_begain_date" id="certificate_begain_date" autocomplete="off" readonly="readonly" 
				onfocus="WdatePicker();" class="layui-input"/>
	    </div>
    </div>
    <div class="layui-inline">
  		<label class="layui-form-label">到期时间<span>*</span></label>
	    <div class="layui-input-inline">
	      	<!-- <input type="text" id="certificate_end_date" name="certificate_end_date" lay-verify="date"
	      		autocomplete="off"  class="layui-input"> -->
			<input type="text" name="certificate_end_date" id="certificate_end_date" autocomplete="off" readonly="readonly" 
				onfocus="WdatePicker();" class="layui-input"/>
	    </div>
    </div>
  </div>
  <div class="layui-form-item" style="display:none;" id="certificate_img_div">
	  <div class="layui-upload-list">
	  	<button type="button" class="layui-btn layui-btn-sm" id="certificate_upload"><i class="layui-icon">&#xe67c;</i>上传证书</button>
	    <img class="layui-upload-img" id="certificate_img"  width="70px">
	    <input type="hidden" id="certificate_img_url" name="certificate_img_url" id="certificate_img_url">
	    <input type="hidden" id="certificate_img_name" name="certificate_img_name" id="certificate_img_name">
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
		}).use([ 'form','webplus', 'layer', 'upload'], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var form = layui.form;
		    var upload = layui.upload;
		  //选完自动上传
		   upload.render({
			    elem: '#certificate_upload'
			    ,url: '<%=basePath%>fileUpload/upload.do'
			    
			    ,done: function(res){
			      if(res.code == 0){//如果上传成功
			    	$('#certificate_img').attr('src', res.url);
			      	$('#certificate_img_url').val(res.url);
			      	$('#certificate_img_name').val(res.picName);
			      }else{ //如果上传失败
			      	return layer.msg('上传失败');
			      }
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
			//选择是否需要证书
		    form.on('select(needChange)', function(data){
		    	needChange(data.value)
		    });
	
		    form.on('submit(saveSubmit)', function(data){
		    	//提交之前先校验重复问题
		    	//检查用户名是否存在
		    	var result = webplus.doReturnAjax('<%=basePath%>sysuser/checkUsername', data.field);
		    	if(result >= 1){
		    		webplus.alertError('用户名已存在，请重试！');
		    		return false;
		    	}
		    	
		    	//检查手机号码是否存在
		    	var result = webplus.doReturnAjax('<%=basePath%>sysuser/checkPhone', data.field);
		    	if(result >= 1){
		    		webplus.alertError('手机号码已存在，请重试！');
		    		return false;
		    	}
				
				webplus.doAjax('<%=basePath%>sysuser/saveUser',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		    
		    
		    function needChange(value){
		    	if(value == 1){
		    		$('#certificate_info_div').show();
		    		$('#certificate_info_div2').show();
		    		$('#certificate_img_div').show();
		    		$('#certificate_num').attr('lay-verify', 'required');
		    		$('#certificate_end_date').attr('lay-verify', 'required');
		    		
		    	}else{
		    		$('#certificate_info_div').hide();
		    		$('#certificate_info_div2').hide();
		    		$('#certificate_img_div').hide();
		    		$('#certificate_num').val('');
		    		$('#certificate_name').val('');
		    		$('#certificate_begain_date').val('');
		    		$('#certificate_end_date').val('');
		    		$('#certificate_img_url').val('');
		    		$('#certificate_img_name').val('');
		    		$('#certificate_num').removeAttr('lay-verify');
		    		$('#certificate_end_date').removeAttr('lay-verify');
		    	}
		    }
		    
		   
		});

	</script>
</body>
</html>