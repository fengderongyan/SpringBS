<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = 
			
			path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<%@ include file="/static/web/webinclude.jsp" %>
		<title></title>
	</head>
	<body>
		<div class="per_title">荣誉证书</div>
		<div class="honor_content">
			<div class="honor_line justify">
				<div class="honor_name">姓名</div>
				<div class="honor_txt">${honorMap.name }</div>
			</div>
			<div class="honor_line justify">
				<div class="honor_name">职位</div>
				<div class="honor_txt">${honorMap.station }</div>
			</div>
			<div class="honor_line justify">
				<div class="honor_name">发证日期</div>
				<div class="honor_txt">${honorMap.certificate_begain_date }</div>
			</div>
			<div class="honor_line justify">
				<div class="honor_name">证书到期时间</div>
				<div class="honor_txt">${honorMap.certificate_end_date }</div>
			</div>
			<div class="honor_line justify">
				<div class="honor_name">证书编号</div>
				<div class="honor_txt">${honorMap.certificate_num }</div>
			</div>
			<div class="honor_line justify">
				<div class="honor_name">证书名称</div>
				<div class="honor_txt">${honorMap.certificate_name }</div>
			</div>
		</div>
	</body>
</html>
