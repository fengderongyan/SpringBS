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
<div class="layui-fluid">
		<div class="layui-card">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto" id="searchForm">
				<div class="layui-form-item">
					<div class="layui-inline" style="width: 180px">
						<label class="layui-form-label">县区</label>
						<div class="layui-input-block" style="width: 130px">
							<select name="county_id" id="county_id" lay-filter="countyChange" ${USERROL.getRole().getROLE_LEV() >= '3'?'disabled':''}>
								<option value="">全部</option>
								<c:forEach items="${countyList }" var="countyMap">
									<option value="${countyMap.org_id }" 
										${USERROL.getOrganization().getCounty_id() == countyMap.org_id?'selected':''}>${countyMap.org_name }
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="layui-inline" style="width: 180px">
						<label class="layui-form-label">园区</label>
						<div class="layui-input-block" style="width: 130px">
							<select name="area_id" id="area_id" lay-filter="areaChange" ${USERROL.getRole().getROLE_LEV() >= '4'?'disabled':''}>
								<option value="">全部</option>
							</select>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">企业</label>
						<div class="layui-input-block">
							<select name="org_id" id="org_id" lay-filter="orgChange" ${USERROL.getRole().getROLE_LEV() >= '5'?'disabled':''}>
								<option value="">全部</option>
							</select>
						</div>
					</div>
				<div class="layui-inline">
					<button class="layui-btn layuiadmin-btn-sm" id="search">统计图</button>
					<button class="layui-btn layuiadmin-btn-sm" id="searchChange">统计表</button>
				</div>
				</div>
			</div>
			<iframe frameborder="0" id="iframeSrc" src="" style="margin:0 auto;width:100%;height:500px;"></iframe>
		</div>
	</div>
	<script>
		var $;
		layui.config({
			base :  'static/layuiadmin/' //静态资源所在路径
		}).extend({
			webplus : 'lib/webplus', //主入口模块
			echarts : 'lib/extend/echarts'
		}).use(['webplus', 'echarts', 'form'], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var echarts=layui.echarts;
		    var form=layui.form;
		    
			var role_lev = '${USERROL.getRole().getROLE_LEV()}';
		    
		    if(role_lev >= '3'){//县公司人员
		    	countyChange('${USERROL.getOrganization().getCounty_id()}');
		    }
		    if(role_lev >= '4'){
		    	areaChange('${USERROL.getOrganization().getArea_id()}');
		    }
		    if(role_lev >= '5'){
		    	orgChange('${USERROL.getOrganization().getOrg_id()}');
		    }
		    
		    form.on('select(countyChange)', function(data){
		    	countyChange(data.value)
		    });
		    
		    
		    form.on('select(areaChange)', function(data){
		    	areaChange(data.value)
		    });
	
		    //县区改变
		    function countyChange(value){
		    	if(webplus.isEmpty(value)){
		    		$("#area_id").html('<option value="">全部</option>');
		    		$("#org_id").html('<option value="">全部</option>');
		    		form.render('select');
		    		return false;
		    	}
				var area_id = '${USERROL.getOrganization().getArea_id()}';
				var url = '<%=basePath%>exam/getAreaList.do?tm='+new Date().getTime();
		    	var param = {county_id : value};
				webplus.doAjax(url, param,'','0','',function(data){
					$("#area_id").html('<option value="">全部</option>');
					 $.each(data, function(i, dvar){
						 if(role_lev >= '4' && area_id == dvar.org_id){
							 $("#area_id").append("<option value="+dvar.org_id+" selected>"+dvar.org_name+"</option>");
						 }else{
							 $("#area_id").append("<option value="+dvar.org_id+" >"+dvar.org_name+"</option>");
						 }
							
					 });
					form.render('select');
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
		    	var org_id = '${USERROL.getOrganization().getOrg_id()}';
		    	var url = '<%=basePath%>exam/getOrgList.do?tm='+new Date().getTime();
		    	var param = {area_id : value, industry : industry};
		    	webplus.doAjax(url, param,'','0','',function(data){
					$("#org_id").html('<option value="">全部</option>');
					 $.each(data, function(i, dvar){
						 if(role_lev >= '5' && org_id == dvar.org_id){
							 $("#org_id").append("<option value="+dvar.org_id+" selected>"+dvar.org_name+"</option>");
						 }else{
							 $("#org_id").append("<option value="+dvar.org_id+" >"+dvar.org_name+"</option>");
						 }
					 });
					 form.render('select');
				});
		    	
		    }
		    resultChartIframe();
		    function resultChartIframe(){
		    	var county_id = $('#county_id').val();
		    	var area_id = $('#area_id').val();
		    	var org_id = $('#org_id').val();
		    	var title = '${param.title}';
		    	var id = '${param.id}';
		    	$('#iframeSrc').attr("src", "<%=basePath%>/exam/resultChartIframe?county_id=" + county_id + 
		    			"&area_id=" + area_id + "&org_id=" + org_id + '&title=' + title + '&id=' + id);
		    }
		    
		    function resultTableIframe(){
		    	var county_id = $('#county_id').val();
		    	var area_id = $('#area_id').val();
		    	var org_id = $('#org_id').val();
		    	var title = '${param.title}';
		    	var id = '${param.id}';
		    	$('#iframeSrc').attr("src", "<%=basePath%>/exam/resultTableIframe?county_id=" + county_id + 
		    			"&area_id=" + area_id + "&org_id=" + org_id + '&title=' + title + '&id=' + id);
		    }
		    
		    $('#search').click(function(){
		    	resultChartIframe();
		    });
		    
		    $('#searchChange').click(function(){
		    	resultTableIframe();
		    	
		    });
		    
		});

	</script>
</body>
</html>