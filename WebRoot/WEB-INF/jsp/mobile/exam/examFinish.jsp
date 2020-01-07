<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = 
			
			path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		
		<link rel="stylesheet" type="text/css" href="static/mobileexam/skins/flat/red.css" />
		<link rel="stylesheet" type="text/css" href="static/mobileexam/css/style.css"/>
		<script src="static/mobileexam/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="static/mobileexam/js/icheck.min.js" type="text/javascript" charset="utf-8"></script>
		<title></title>
		<style type="text/css">
			.container{
				width: 100%;
				
			}
		</style>
	</head>
	<body>
		<div class="complate directionC">
			<img src="static/mobileexam/images/examFinish.png" width="50px">
			<span>您已完成答题，谢谢参与！</span>
		</div>
	</body>
</html>
