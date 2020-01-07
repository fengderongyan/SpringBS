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
		<div class="per_title">意见反馈</div>
		<div class="directionA">
			<textarea rows="" cols="" placeholder="请输入您想反馈的内容..." class="textarea" id="content"></textarea>
			<button type="button" class="right_btn" onclick="doSub();">确认提交</button>
		</div>
	</body>
	<script type="text/javascript">
		function doSub(){
			var content = $("#content").val();
			$.ajax({
				type : 'post',
				url : '<%=basePath%>web/personal/subProposal',
				data: {content : content},
				//async:false,//这一步很重要
				timeout:30000,
				dataType :'json',
				success : function(data) {//&quot;
					if(data.errorCode == 0){
						layer.alert('您的意见已提交！', function(index){
		   					layer.close(index);
		   					window.location.reload();
		   				});
					}else{
						layer.alert('提交异常，请稍后重试！', function(index){
		   					layer.close(index);
		   				});
					}
					
				},
				error : function(jqXHR, textStatus, errorThrown) {
					layer.alert('网络异常，请稍后重试！', function(index){
	   					layer.close(index);
	   				});
				}
		});
		}
	</script>
</html>
