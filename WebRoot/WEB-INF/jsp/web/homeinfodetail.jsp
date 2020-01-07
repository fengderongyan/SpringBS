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
		<title>最新资讯详情</title>
	</head>
	<body>
		<div class="detail_content">
			<div class="detail_title">安全教育 > > 最新资讯 > > 最新资讯详情</div>
			<div class="detail_name">
				${infodetail.title }
			</div>
			<div class="detail_time">
				<span>${infodetail.rec_date }</span>
				<span>${infodetail.name }</span>
			</div>
			<div class="detail_txt">
				${infodetail.content }
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				window.setTimeout(function(){
					$.ajax({
							type : 'post',
							url : '<%=basePath%>web/index/getMyPoint',
							data : {info_id : '${infodetail.id}', type : 2},
							//async:false,//这一步很重要
							timeout:30000,
							dataType :'json',
							success : function(data) {//&quot;
							},
							error : function(jqXHR, textStatus, errorThrown) {
							}
					});
				},10000);
			});
			
		</script>
	</body>
</html>
