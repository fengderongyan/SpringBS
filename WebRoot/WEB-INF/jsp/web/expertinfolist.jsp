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
		<title>专家介绍</title>
	</head>
	<body>
		<div class="pro_content justify">
			<c:forEach items="${infoList }" var="infoMap">
				<div class="pro_item justify" onclick="expertInfoDetail('${infoMap.id}')">
					<img src="${infoMap.photo }" class="pro_img">
					<div class="pro_detail">
						<div class="pro_title justify">
							<div>${infoMap.name }</div>
						</div>
						<div class="pro_des">
							${infoMap.introduce }
						</div>
					</div>
				</div>
			</c:forEach>
			
		</div>
		<script type="text/javascript">
			function expertInfoDetail(id){
				window.open("<%=basePath%>web/index/expertInfoDetail?id=" + id);
			}		
		</script>
	</body>
</html>
