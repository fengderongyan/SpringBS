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
	</head>
	<body>
		<div class="per_title">个人信息</div>
		<div class="top_info directionA">
			<img src="static/web/img/header.jpg"  class="top_avatra">
			<div class="top_name">${USERROL.getUSERNAME() }</div>
		</div>
		<div class="info_content">
			<span class="des_info">工厂介绍</span>
			<div class="des_detail">
				${USERROL.getOrganization().getOrg_detail() }
			</div>
		</div>
	</body>
</html>
