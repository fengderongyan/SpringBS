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
  <input type="hidden" name="user_id" value="${userMap.user_id }">
  <div class="layui-form-item">
  		<label class="layui-form-label">用户名<span>*</span></label>
	    <div class="layui-input-block">
	    	<input type="text" name="username" value="${userMap.username }" lay-verify="required"  autocomplete="off"  class="layui-input" disabled>
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">姓名<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="name" value="${userMap.name }" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">角色<span>*</span></label>
	    <div class="layui-input-block">
	    	<select name="role_id" lay-verify="required" id="role_id" lay-filter="roleChange">
	      		<option value="">请选择</option>
	      		<c:forEach items="${childRole }" var="child">
	      			<option value="${child.role_id }" <c:if test="${child.role_id == userMap.role_id }">selected</c:if>>${child.role_name }</option>
	      		</c:forEach>
	      	</select>
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">所属组织<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="org_name" id="org_name" readonly="readonly" value="${userMap.org_name}"
	      	lay-verify="required"  autocomplete="off"  class="layui-input">
	      <input type="hidden" id="org_id" name="org_id" value="${userMap.org_id}">
	    </div>
  </div>
  <div class="layui-form-item" pane>
  		<label class="layui-form-label">性别<span>*</span></label>
	    <div class="layui-input-block">
	    	<input type="radio" name="sex" value="1" title="男" <c:if test="${userMap.sex == 1 }">checked</c:if>>
      		<input type="radio" name="sex" value="0" title="女" <c:if test="${userMap.sex == 0 }">checked</c:if>>
	    </div>
  </div>
  <div class="layui-form-item">
  		<label class="layui-form-label">电话<span>*</span></label>
	    <div class="layui-input-block">
	      <input type="text" name="phone" value="${userMap.phone}" lay-verify="required|phone"  autocomplete="off"  class="layui-input">
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
		    $("#org_name").focus(function(){
		    	var role_id = $('#role_id').val();
		    	if(webplus.isEmpty(role_id)){
		    		webplus.alertError('请先选择角色！');
		    		return false;
		    	}else{
		    		webplus.openWindow('sysuser/orgTree?role_id=' + role_id,'选择组织','400','400','1');
		    	}
		    });
			//重新选择角色时，先清空组织
		    form.on('select(roleChange)', function(data){
		    	$("#org_name").val('');
	    		$("#org_id").val('');
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
				
				webplus.doAjax('<%=basePath%>sysuser/saveEdit',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		    
		   
		});

	</script>
</body>
</html>