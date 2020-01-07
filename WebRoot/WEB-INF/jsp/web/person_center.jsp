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
		<title>个人中心</title>
		<%@ include file="/static/web/webinclude.jsp" %>
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/web/header.jsp"></jsp:include>
		<!-- 个人中心部分 -->
		<div class="person_content justify" id="person_center">
			<div class="person_left direction">
				<div class="person_ac directionA">
					<img src="static/web/img/header.jpg" >
					<span>${USERROL.getUSERNAME() }</span>
				</div>
				<ul class="per_tab">
					<template v-for="(tab,index) in tab_list">
						<li :class="nb==index?'per_active':''" v-on:click='click_tab(index)'>
							<img :src="tab.src" >
							<span>{{tab.txt}}</span>
						</li>
					</template>
				</ul>
			</div>
			
			
			<div class="person_right" v-for="(item,index) in per_iframe">
				<iframe class="per_iframe" :src="item.url" scrolling="auto"  v-if="nb==index" frameborder="0"></iframe>
			</div>
		</div>
		<!-- 个人中心部分 -->
		
		<jsp:include page="/WEB-INF/jsp/web/footer.jsp"></jsp:include>	
		<script type="text/javascript">
			var person=new Vue({
				el:'#person_center',
				data:{
					tab_list:[
						{src:'static/web/img/per01.png',txt:'个人信息'},
						{src:'static/web/img/per02.png',txt:'我的积分'},
						{src:'static/web/img/per03.png',txt:'我的课程'},
						{src:'static/web/img/per09.png',txt:'模拟考试'},
						{src:'static/web/img/per04.png',txt:'正式考试'},
						{src:'static/web/img/per06.png',txt:'意见反馈'},
						{src:'static/web/img/per07.png',txt:'荣誉证书'},
						{src:'static/web/img/per08.png',txt:'系统设置'}
					],
					per_iframe:[
						{url:'web/personal/info'},
						{url:'web/personal/integral'},
						{url:'web/personal/curse?page=1&limit=5'},
						{url:'web/personal/exam?exam_type=0&page=1&limit=10'},
						{url:'web/personal/exam?exam_type=1&page=1&limit=10'},
						{url:'web/personal/suggestion'},
						{url:'web/personal/honor'},
						{url:'web/personal/setting'}
					],
					nb:''
				},
				methods:{
					click_tab:function(index){
						var that=this;
						that.nb=index;
					}
				}
			})
		</script>
	</body>
</html>
