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
  <!-- 默认江苏省 -->
  <input type="hidden" name="province_id" value="32" class="layui-input">
  <!-- 默认市区连云港市 -->
  <input type="hidden" name="city_id" value="3207" class="layui-input">
  <div class="layui-form-item">
  	<!-- flag -->
  	<input type="hidden" name="flag" value="addOrg" class="layui-input">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label" style="width: 130px">归属县区<span>*</span></label>
	    <div class="layui-input-inline">
	      <select name="county_id" lay-verify="required" lay-filter="countyChange">
	      	<option value="">请选择</option>
	      	<c:forEach items="${countyList }" var="countyMap">
	      		<option value="${countyMap.org_id }">${countyMap.org_name }</option>
	      	</c:forEach>
	      </select>
	    </div>
  	</div>
  	<div class="layui-inline">
  		<label class="layui-form-label">归属园区<span>*</span></label>
	    <div class="layui-input-inline">
	      <select name="area_id" id="area_id" lay-verify="required">
	      	<option value="">请选择</option>
	      	
	      </select>
	    </div>
  	</div>
  </div>
  <div class="layui-form-item">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label" style="width: 130px">组织名称<span>*</span></label>
	    <div class="layui-input-inline">
	     	 <input type="text" name="org_name" lay-verify="required"  autocomplete="off"  class="layui-input">
	    </div>
  	</div>
  	<div class="layui-inline" >
  		<label class="layui-form-label">所属行业 <span>*</span></label>
	    <div class="layui-input-inline">
	      <select name="industry" lay-verify="required">
	      	<option value="">请选择</option>
	      	<c:forEach items="${industryList }" var="industryMap">
	      		<option value="${industryMap.dd_item_code }">${industryMap.dd_item_name }</option>
	      	</c:forEach>
	      </select>
	    </div>
  	</div>
  </div>
   <div class="layui-form-item">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label" style="width: 130px">联系电话</label>
	    <div class="layui-input-inline">
	     	 <input type="text" name="phone"  autocomplete="off"  class="layui-input">
	    </div>
  	</div>
  	<div class="layui-inline" >
  		<label class="layui-form-label">公司地址</label>
	    <div class="layui-input-inline">
	     	<input type="text" name="address" autocomplete="off"  class="layui-input">
	    </div>
  	</div>
  </div>
  <div class="layui-form-item">
  	<div class="layui-inline" style="width: 50%">
  		<label class="layui-form-label" style="width: 130px">是否限制人数 <span>*</span></label>
	    <div class="layui-input-inline">
	     	<select name="is_limit" lay-verify="required" lay-filter="limitChange">
	      		<option value="">请选择</option>
	      		<option value="1">是</option>
	      		<option value="0">否</option>
	      </select>
	    </div>
  	</div>
  	<div class="layui-inline" style="display: none;" id="limit_num_div">
  		<label class="layui-form-label">限制人数 <span>*</span></label>
	    <div class="layui-input-inline">
	     	<input type="number" id="limit_num" name="limit_num" autocomplete="off"  class="layui-input">
	    </div>
  	</div>
  </div>
  
  <div class="layui-form-item layui-form-text">
  	<label class="layui-form-label">企业介绍</label>
  	<div class="layui-input-block">
     	<textarea id="org_detail" name="org_detail" style="width: 100%;height: 100%" lay-ignore></textarea>
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
		}).use([ 'form','webplus' ], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var form = layui.form;
		    
		    var ke = KindEditor.create('#org_detail',{
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
		  //监听归属县区选择
		    form.on('select(countyChange)', function(data){
		    	if(data.value == ''){
		    		return false;
		    	}
		    	$.ajax({
					type: "POST",
					url: '<%=basePath%>org/getAreaListByCountyId.do?tm='+new Date().getTime(),
			    	data: {county_id : data.value},
					dataType:'json',
					cache: false,
					success: function(data){
						$("#area_id").html('<option value="">请选择</option>');
						 $.each(data, function(i, dvar){
								$("#area_id").append("<option value="+dvar.org_id+">"+dvar.org_name+"</option>");
						 });
						form.render('select');
					}
				});
		    });
		    form.on('select(limitChange)', function(data){
		    	limitChange(data.value);
		    	
		    });
		    
		    form.on('submit(saveSubmit)', function(data){
				webplus.doAjax('<%=basePath%>org/saveOrg',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		    
		    function limitChange(value){
		    	if(value == 1){
		    		$('#limit_num_div').show();
		    		$('#limit_num').attr('lay-verify', 'required');
		    	}else{
		    		$('#limit_num_div').hide();
		    		$('#limit_num').val('');
		    		$('#limit_num').removeAttr('lay-verify');
		    	}
		    }
		});

	</script>
</body>
</html>