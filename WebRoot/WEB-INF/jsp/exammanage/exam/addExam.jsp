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
		    <select name="exam_type" id="exam_type" lay-verify="required">
		    	<option value="">请选择</option>
		    	<option value="0">模拟试卷</option>
		    	<option value="1">正式试卷</option>
		    </select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">标题<span>*</span></label>
		<div class="layui-input-inline">
			<input class="layui-input" name="title" autocomplete="off" lay-verify="required">
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
			<input class="layui-input" name="time_num" value="30" autocomplete="off" lay-verify="required">
		</div>
		<div class="layui-form-mid layui-word-aux">分钟</div>
	</div>
	<div class="layui-form-item" >
		<label class="layui-form-label" style="width: 120px">批量生成</label>
		<div class="layui-input-inline">
			<select name="batch_exam" id="batch_exam">
		    	<option value="">请选择</option>
		    	<option value="5">5套</option>
		    	<option value="10">10套</option>
		    	<option value="20">20套</option>
		    </select>
		</div>
	</div>
	<c:if test="${USERROL.getRole().getROLE_LEV() <= '4' }">
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px">适用行业<span>*</span></label>
			<div class="layui-input-inline">
				<select name="industry" id="industry" required="required">
					<c:forEach items="${industryList }" var="industryMap">
						<option value="${industryMap.dd_item_code }">${industryMap.dd_item_name }</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</c:if>
	<c:if test="${USERROL.getRole().getROLE_LEV() >= '5' }">
		<input type="hidden" name="industry" id="industry" value="${USERROL.getOrganization().getIndustry() }">
	</c:if>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">面向县区</label>
		<div class="layui-input-inline">
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
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">面向园区</label>
		<div class="layui-input-inline">
			<select name="area_id" id="area_id" lay-filter="areaChange" ${USERROL.getRole().getROLE_LEV() >= '4'?'disabled':''}>
				<option value="">全部</option>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">面向企业</label>
		<div class="layui-input-inline">
			<select name="org_id" id="org_id" lay-filter="orgChange" ${USERROL.getRole().getROLE_LEV() >= '5'?'disabled':''}>
				<option value="">全部</option>
			</select>
		</div>
	</div>
	<span id="dz_span_id" style="display: none">
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">定制单选题数</label>
		<div class="layui-input-inline">
			<input class="layui-input" name="dz_danxuan" id="dz_danxuan" value="0" autocomplete="off">
		</div>
		<div class="layui-form-mid layui-word-aux" id="dz_danxuan_num_div">共0题</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">定制多选题数</label>
		<div class="layui-input-inline">
			<input class="layui-input" name="dz_duoxuan" id="dz_duoxuan" value="0" autocomplete="off">
		</div>
		<div class="layui-form-mid layui-word-aux" id="dz_duoxuan_num_div">共0题</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">定制判断题数</label>
		<div class="layui-input-inline">
			<input class="layui-input" name="dz_panduan" id="dz_panduan" value="0" autocomplete="off">
		</div>
	</div>
	</span>
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: 120px">在线考试</label>
		<div class="layui-input-inline" style="height: 36px;border: 1px solid; border-color: #e6e6e6">
	    	<input type="radio" name="flag_online" value="1" title="是" checked>
      		<input type="radio" name="flag_online" value="0" title="否" >
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
		    
		    form.on('select(orgChange)', function(data){
		    	orgChange(data.value)
		    });
	
		    form.on('submit(saveSubmit)', function(data){
		    	var dz_danxuan_input = $('#dz_danxuan').val();
		    	var dz_duoxuan_input = $('#dz_duoxuan').val();
		    	var dz_panduan_input = $('#dz_panduan').val();
		    	if(dz_danxuan_input > dz_danxuan){
		    		webplus.alertWarn('定制单选不能大于可选题目数');
		    		return false;
		    	}
		    	if(dz_duoxuan_input > dz_duoxuan){
		    		webplus.alertWarn('定制多选不能大于可选题目数');
		    		return false;
		    	}
		    	if(dz_panduan_input > dz_panduan){
		    		webplus.alertWarn('定制判断不能大于可选题目数');
		    		return false;
		    	}
		    	
				webplus.doAjax('<%=basePath%>exam/saveExam',data.field,'','','1')
				//阻止表单默认提交
			    return false;
		 	});
		    
		    
		    //县区改变
		    function countyChange(value){
		    	if(webplus.isEmpty(value)){
		    		$("#area_id").html('<option value="">全部</option>');
		    		$("#org_id").html('<option value="">全部</option>');
		    		orgChange('');
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
			    	orgChange('');
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
		    var dz_danxuan = 0;
		    var dz_duoxuan = 0;
		    var dz_panduan = 0;
		    function orgChange(value){
		    	if(webplus.isEmpty(value)){
		    		$('#dz_span_id').hide();
		    		$('#dz_danxuan').val('0');
		    		$('#dz_duoxuan').val('0');
		    		$('#dz_panduan').val('0');
		    	}else{
		    		$('#dz_span_id').show();
		    		var url = '<%=basePath%>exam/getDzInfo.do?tm='+new Date().getTime();
			    	var param = {org_id : value};
			    	//获取企业定制题目信息
			    	webplus.doAjax(url, param,'','0','',function(data){
			    		dz_danxuan = data.dz_danxuan;
			    		dz_duoxuan = data.dz_duoxuan;
			    		dz_panduan = data.dz_panduan;
						$('#dz_danxuan_num_div').html('共' + dz_danxuan + '题可选');
						$('#dz_duoxuan_num_div').html('共' + dz_duoxuan + '题可选');
						$('#dz_panduan_num_div').html('共' + dz_panduan + '题可选');
					});
		    	}
		    }
		    
		    
		   
		});

	</script>
</body>
</html>