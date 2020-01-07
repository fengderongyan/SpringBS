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
<div><button class="layui-btn layuiadmin-btn-sm" id="doPrint" onclick="doPrint();">打印</button></div>
<div id="printDiv">
<table class="layui-table" lay-size="sm">
	<c:choose>
		<c:when test="${flag_emp == 1 }">
			<tr style="font-weight: bold;">
				<td>姓名</td>
				<td>职位</td>
				<td>得分</td>
			</tr>
			<c:forEach items="${resultList }" var="resultMap">
				<tr>
					<td>${resultMap.username }</td>
					<td>${resultMap.station }</td>
					<td>${resultMap.score }</td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr style="font-weight: bold;">
				<td>单位</td>
				<td>总人数</td>
				<td>通过人数</td>
				<td>未通过人数</td>
				<td>通过率</td>
			</tr>
			<c:forEach items="${resultList }" var="resultMap">
				<tr>
					<td>${resultMap.org_name }</td>
					<td>${resultMap.total_cnt }</td>
					<td>${resultMap.pass_num }</td>
					<td>${resultMap.no_pass_num }</td>
					<td>${resultMap.pass_rate }</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>
</div>
<script>
	
	function doPrint(){
    	var headstr = "<html><head><title></title></head><body>";  
    	var footstr = "</body>";  
    	var printData = document.getElementById("printDiv").innerHTML; //获得 div 里的所有 html 数据
    	var oldstr = document.body.innerHTML;  
    	document.body.innerHTML = printData;  
    	window.print();  
    	document.body.innerHTML = oldstr; 
    	return false;
    }

</script>
</body>
</html>