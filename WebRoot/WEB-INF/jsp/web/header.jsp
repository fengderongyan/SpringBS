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
<div class="container" id="header">
	<a class="header" href="">
		<!-- 顶部banner -->
		<div class="banner">
			<img src="static/web/img/top.png">
		</div>
		<div class="tabbar justifyC">
			<div class="tab_list justifyS">
				<a href="<%=basePath %>" class="tab_item">首页</a>
				<a href="<%=basePath%>web/index/lesson?page=1&limit=15" class="tab_item" target="_blank">教学课程</a>
				<a href="<%=basePath%>web/index/safeInfo?page=1&limit=15" target="_blank" class="tab_item">安全常识</a>
				<a href="<%=basePath%>web/index/lawInfo?page=1&limit=15" target="_blank" class="tab_item">法律法规</a>
				<a href="#" onclick="goZqkx()" class="tab_item">政企快线</a>
				<a href="<%=basePath%>web/index/accident?page=1&limit=15" target="_blank" class="tab_item">事故案例</a>
			</div>
			<div class="fun_btn ">
				<button type="button" onclick="searchClick();">
					<i class="iconfont icon-xiazai17"></i>
					搜索
				</button>
				<span id="login_span_id">
					<c:if test="${USERROL == null }">
						<button type="button" class="login_btn">
							用户登录
						</button>
					</c:if>
					<c:if test="${USERROL != null }">
						<button type="button" onclick="personClick();">
							个人中心
						</button>
					</c:if>
				</span>
			</div>
		</div>
	</a>
	<div class="modal modal_hidden"></div>
	<div class="login_box modal_hidden  directionAr">
		<span class="login_title">用户登录</span>
		<form action="" id="login_form">
			<div class="login_item justifyC">
				<span>用户名</span>
				<input type="text" autocomplete="off" name="loginname" id="loginname" value="" placeholder="请输入用户名" />
			</div>
			<div class="login_item justifyC">
				<span>密码</span>
				<input type="password" autocomplete="off" name="password" id="password" value="" placeholder="请输入密码" />
			</div>
		</form>
		<button class="register" onclick="loginClick();">登录</button>
		<div class="close_btn justifyC">
			<span class="iconfont icon-guanbi"></span>
		</div>
	</div>
</div>

	<script type="text/javascript">
		$('.login_btn').click(function(){
			$('.login_box').removeClass('modal_hidden');
			$('.modal').removeClass('modal_hidden');
		})
		$('.close_btn').click(function(){
			$('.login_box').addClass('modal_hidden');
			$('.modal').addClass('modal_hidden');
		})
		
		$(function(){
			$("#loginname").keyup(function (e) {//捕获文档对象的按键弹起事件  
			    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
			    	 loginClick();
			    }  
			}); 
			$("#password").keyup(function (e) {//捕获文档对象的按键弹起事件  
			    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
			    	 loginClick();
			    }  
			}); 
		});
		function loginClick() {
			var loginname = $("#loginname").val().trim();
		
			var password = $("#password").val();
		
			if (loginname == '' || loginname == '请输入用户名') {
				layer.tips('请输入用户名！', '#loginname', {
					tips : [ 3, '#FF5080' ]
				});
				return;
			}
			if (password == '' || password == '请输入密码') {
				layer.tips('请输入密码！', '#password', {
					tips : [ 3, '#FF5080' ]
				});
				return;
			}
			layer.msg('正在登陆...', {
				icon : 16,
				shade : 0.5,
				time : 20000
			});
			var basePath = '<%=basePath%>';
			$.ajax({
				url : basePath + 'login_login',
				type : 'post',
				async:false,
				data : {
					loginname : loginname,
					password : password,
					flag_personal : 1
				},
				dataType : 'json',
				success : function(data) {
					if("success" == data.result){
						window.setTimeout(location.href= basePath + "web/personal",1000);
						
					}else if("usererror" == data.result){
						layer.msg("用户名或密码有误", {
							icon : 5
						});
						$("#loginname").focus();
					}else{
						layer.msg("缺少参数", {
							icon : 5
						});
						$("#loginname").focus();
					}
				},
				error : function() {
				}
			});
		}
		
		function personClick(){
			window.location.href="<%=basePath%>web/personal";
		}
		
		function goZqkx(){
			var userrol = '${USERROL}';
			if(userrol != null && userrol != undefined && userrol != ''){
				window.open("<%=basePath%>web/index/getZqkxRpt");
			}else{
				layer.msg("请选登录", {
					icon : 5
				});
				return false;
			} 
		}
		
		//搜索按钮
		function searchClick(){
			window.open("<%=basePath%>web/index/search");
		}
		
	</script>