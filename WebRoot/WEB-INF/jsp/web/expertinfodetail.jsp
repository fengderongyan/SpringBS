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
		<title>专家详情</title>
	</head>
	<body>
		<div class="professor_content directionC">
			<img src="${infodetail.photo }" alt="" class="pro_avatra">
			<span class="professor_name">${infodetail.name }</span>
			<div class="professor_about">
				<span>性别：${infodetail.sex }</span>
				<span>${infodetail.introduce }</span>
			</div>
			<div class="professor_des">
				${infodetail.content }
			</div>
			
			
			<div class="curse_list">
				<div class="curse_left justify">
					<c:forEach items="${videoList }" var="videoMap">
						<div class="thumb_img">
							<img src="${videoMap.cover }"  class="curse_cover">
							<div class="start_btn justifyC">
							</div>
						</div>
						<div class="curse_txt directionAr">
							<div>${videoMap.title }</div>
							<span>${videoMap.rec_date }</span>
							<span>${videoMap.name }</span>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</body>
</html>
