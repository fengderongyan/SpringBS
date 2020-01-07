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
		<label class="layui-form-label" style="width: 120px">试卷类型<span>*</span></label>
		<div class="layui-input-inline">
		    <select name="exam_type" id="exam_type" lay-verify="required" disabled="disabled">
		    	<option value="">请选择</option>
		    	<option value="0" ${examMap.exam_type == 0 ? 'selected' : '' }>模拟试卷</option>
		    	<option value="1" ${examMap.exam_type == 1 ? 'selected' : '' }>正式试卷</option>
		    </select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">标题<span>*</span></label>
		<div class="layui-input-inline">
			<input class="layui-input" name="title" value="${examMap.title }" readonly="readonly" autocomplete="off" lay-verify="required">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">总分<span>*</span></label>
		<div class="layui-input-inline" >
			<input class="layui-input" name="total_score" autocomplete="off" value="100" readonly="readonly">
		</div>
			<div class="layui-form-mid layui-word-aux">分</div>
	</div>
	<div class="layui-form-item" >
		<label class="layui-form-label" style="width: 120px">时长<span>*</span></label>
		<div class="layui-input-inline">
			<input class="layui-input" name="time_num" value="${examMap.time_num }" readonly="readonly" autocomplete="off" lay-verify="required">
		</div>
		<div class="layui-form-mid layui-word-aux">分钟</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">适用行业<span>*</span></label>
		<div class="layui-input-inline">
			<select name="industry" id="industry" required="required" disabled="disabled">
				<c:forEach items="${industryList }" var="industryMap">
					<option value="${industryMap.dd_item_code }" ${examMap.industry == industryMap.dd_item_code ? 'selected' : '' }>${industryMap.dd_item_name }</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">面向县区</label>
		<div class="layui-input-inline">
			<select name="county_id" id="county_id" disabled>
				<option value="">${(examMap.county_name == null || examMap.county_namee == '')?'全部':examMap.county_name}</option>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">面向园区</label>
		<div class="layui-input-inline">
			<select name="area_id" id="area_id" disabled>
				<option value="">${(examMap.area_name == null || examMap.area_name == '')?'全部':examMap.area_name}</option>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">面向企业</label>
		<div class="layui-input-inline">
			<select name="org_id" id="org_id" lay-filter="orgChange" disabled>
				<option value="">${(examMap.org_name == null || examMap.org_name == '')?'全部':examMap.org_name}</option>
			</select>
		</div>
	</div>
	<span id="dz_span_id" style="display: none">
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">定制单选题数</label>
		<div class="layui-input-inline">
			<input class="layui-input" name="dz_danxuan" id="dz_danxuan" value="${examMap.dz_danxuan}" readonly="readonly" autocomplete="off">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">定制多选题数</label>
		<div class="layui-input-inline">
			<input class="layui-input" name="dz_duoxuan" id="dz_duoxuan" value="${examMap.dz_danxuan}" readonly="readonly" autocomplete="off">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">定制判断题数</label>
		<div class="layui-input-inline">
			<input class="layui-input" name="dz_panduan" id="dz_panduan" value="${examMap.dz_danxuan}" readonly="readonly" autocomplete="off">
		</div>
	</div>
	</span>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">在线考试</label>
		<div class="layui-input-inline" style="height: 36px;border: 1px solid; border-color: #e6e6e6">
	    	<input type="radio" name="flag_online" value="1" title="是" ${examMap.flag_online == 1 ? 'checked':'' }>
      		<input type="radio" name="flag_online" value="0" title="否" ${examMap.flag_online == 0 ? 'checked':'' }>
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
			if(!webplus.isEmpty('${examMap.org_id}')){
				$('#dz_span_id').show();
			}		    
		   
		});

	</script>
</body>
</html>