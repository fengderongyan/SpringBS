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
		<div class="integral_content directionA">
			<h3>${total }</h3>
			<span>积分</span>
		</div>
		<div class="inte_list">
			<c:forEach items="${myPointList }" var="myPointMap">
				<div class="inte_item justifyS">
					<div class="inte_left direction">
						<div>${myPointMap.title }</div>
						<span>${myPointMap.context }</span>
						<span>已获${myPointMap.have_point }分/上限${myPointMap.max_point }分</span>
					</div>
					<c:if test="${myPointMap.flag_finish == 1 }">
						<button type="button" class="finish_btn" style="cursor: default;">已完成</button>
					</c:if>
					<c:if test="${myPointMap.flag_finish == 0 }">
						<button type="button" class="right_btn" style="cursor: default;">未完成</button>
					</c:if>
				</div>
			</c:forEach>
			
		</div>
	</body>
</html>
