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
		<label class="layui-form-label">试题类型<span>*</span></label>
		<div class="layui-input-block">
		    <select name="type" id="type" lay-filter="typeChange" lay-verify="required" required="required">
		    	<option value="100301">单选</option>
		    	<option value="100302">多选</option>
		    	<option value="100303">判断</option>
		    </select>
		</div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">题目<span>*</span></label>
		<div class="layui-input-block">
			<textarea class="layui-textarea" name="question_info"></textarea>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">教材来源</label>
		<div class="layui-input-block">
			<input class="layui-input" name="source" lay-verify="required">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">选项A</label>
		<div class="layui-input-block">
			<input class="layui-input" name="optionA">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">选项B</label>
		<div class="layui-input-block">
			<input class="layui-input" name="optionB">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">选项C</label>
		<div class="layui-input-block">
			<input class="layui-input" name="optionC">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">选项D</label>
		<div class="layui-input-block">
			<input class="layui-input" name="optionD">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">选项E</label>
		<div class="layui-input-block">
			<input class="layui-input" name="optionE">
		</div>
	</div>
	
	<div class="layui-form-item" pane>
		<label class="layui-form-label">正确答案<span>*</span></label>
		<div id="answer_div_id" class="layui-input-block">
				<input type="radio" name="answer" value="A" title="A">
				<input type="radio" name="answer" value="B" title="B">
				<input type="radio" name="answer" value="C" title="C">
				<input type="radio" name="answer" value="D" title="D">
				<input type="radio" name="answer" value="E" title="E">
		</div>
	</div>
	
	<div class="layui-form-item">
		<label class="layui-form-label">适用行业<span>*</span></label>
		<div class="layui-input-block">
			<select name="industry" id="industry" required="required">
				<c:forEach items="${industryList }" var="industryMap">
					<option value="${industryMap.dd_item_code }">${industryMap.dd_item_name }</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">面向县区</label>
		<div class="layui-input-block">
			<select name="county_id" id="county_id" lay-filter="countyChange">
				<option value="">全部</option>
				<c:forEach items="${countyList }" var="countyMap">
					<option value="${countyMap.org_id }">${countyMap.org_name }
					</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">面向园区</label>
		<div class="layui-input-block">
			<select name="area_id" id="area_id" lay-filter="areaChange">
				<option value="">全部</option>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">面向企业</label>
		<div class="layui-input-block">
			<select name="org_id" id="org_id">
				<option value="">全部</option>
			</select>
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
		}).use(['form', 'webplus'], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var form = layui.form;
		    
			//试卷类型改变
		    form.on('select(typeChange)', function(data){
		    	typeChange(data.value)
		    });
			
		    form.on('select(countyChange)', function(data){
		    	countyChange(data.value)
		    });
		    
		    form.on('select(areaChange)', function(data){
		    	areaChange(data.value)
		    });
	
		    form.on('submit(saveSubmit)', function(data){
		    	var type = $('#type').val();
		    	if(type == '100302'){
		    		var ai = '';
			    	$("input[name='myAnswer']:checked").each(function(){
			    		ai += $(this).val();
			    	});
			    	$('#answer').val(ai);
		    	}
		    	
		    	if (!data.field.answer){
		    		webplus.alertError('请选择正确答案！');
		    		return false;
		        }
		    	if (!data.field.question_info){
		    		webplus.alertError('请输入题目！');
		    		return false;
		        }
		    	
				webplus.doAjax('<%=basePath%>question/saveQuestion',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		    
		    //试题类型改变
		    function typeChange(value){
		    	if(value == '100301' || value == '100303'){
		    		$('#answer_div_id').html('<input type="radio" name="answer" value="A" title="A">'+
		    				'<input type="radio" name="answer" value="B" title="B">'+
		    				'<input type="radio" name="answer" value="C" title="C">'+
		    				'<input type="radio" name="answer" value="D" title="D">'+
		    				'<input type="radio" name="answer" value="E" title="E">');
		    	}else if(value == '100302'){
		    		$('#answer_div_id').html('<input type="checkbox" name="myAnswer" value="A" title="A" lay-skin="primary">'+
		    				'<input type="checkbox" name="myAnswer" value="B" title="B" lay-skin="primary"> '+
		    				'<input type="checkbox" name="myAnswer" value="C" title="C" lay-skin="primary"> '+
		    				'<input type="checkbox" name="myAnswer" value="D" title="D" lay-skin="primary"> '+
		    				'<input type="checkbox" name="myAnswer" value="E" title="E" lay-skin="primary"> ' +
		    				'<input type="hidden" id="answer" name="answer" value="">');
		    	}
		    	form.render();
		    	
		    }
		    
		    //县区改变
		    function countyChange(value){
		    	if(webplus.isEmpty(value)){
		    		$("#area_id").html('<option value="">全部</option>');
		    		$("#org_id").html('<option value="">全部</option>');
		    		form.render('select');
		    		return false;
		    	}
		    	$.ajax({
					type: "POST",
					url: '<%=basePath%>question/getAreaList.do?tm='+new Date().getTime(),
			    	data: {county_id : value},
					dataType:'json',
					cache: false,
					success: function(data){
						$("#area_id").html('<option value="">全部</option>');
						 $.each(data, function(i, dvar){
								$("#area_id").append("<option value="+dvar.org_id+">"+dvar.org_name+"</option>");
						 });
						form.render('select');
					}
				});
		    }
		    
		    //园区改变
		    function areaChange(value){
		    	if(webplus.isEmpty(value)){
		    		$("#org_id").html('<option value="">全部</option>');
		    		form.render('select');
		    		return false;
		    	}
		    	var industry = $('#industry').val();
		    	$.ajax({
					type: "POST",
					url: '<%=basePath%>question/getOrgList.do?tm='+new Date().getTime(),
			    	data: {area_id : value, industry : industry},
					dataType:'json',
					cache: false,
					success: function(data){
						$("#org_id").html('<option value="">全部</option>');
						 $.each(data, function(i, dvar){
								$("#org_id").append("<option value="+dvar.org_id+">"+dvar.org_name+"</option>");
						 });
						form.render('select');
					}
				});
		    }
		    
		    
		   
		});

	</script>
</body>
</html>