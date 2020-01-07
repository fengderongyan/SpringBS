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
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div class="per_title">设置</div>
		<div class="setting" id="app">
			<!-- <div class="set_item justifyC" v-on:click="change_pwd">
				<i class="iconfont icon-ziyuanxhdpi"></i>
				<span>修改密码</span>
			</div> -->
			<div class="set_item justifyC" v-on:click="concat">
				<i class="iconfont icon-002dianhua"></i>
				<span>联系我们</span>
			</div>
			<a href="about_us.html" class="set_item justifyC">
				<i class="iconfont icon-gerenzhongxin"></i>
				<span>关于我们</span>
			</a>
			<div class="set_item justifyC" v-on:click="quit">
				<i class="iconfont icon-tuichu4"></i>
				<span>退出登录</span>
			</div>
			
		</div>
		<script type="text/javascript">
			var lock = false;
			var set = new Vue({
				el: '#app',
				data: {

				},
				methods: {
					concat:function(){
						layer.open({
							type: 1,
							skin: 'layui-layer-demo', //样式类名
							title:'联系电话',
							closeBtn: 0, //不显示关闭按钮
							anim: 2,
							offset: '100px',
							shadeClose: true, //开启遮罩关闭
							content: `<div class="concat_us justifyC">
				400-400-400
			</div>`
						});
					},
					
					change_pwd: function() {
						if(!lock){
							lock = true;
							layer.open({
								type: 1,
								title: '修改手机号码',
								area: ['520px', '300px'], //宽高
								offset: '100px',
								content: `
								<div class="change_num">
									<form method="post"  class='directionC'>
										<div class="change_item justifyC">
											<span>手机号</span>
											<input type="" name="" id="" value="" />
										</div>
										<div class="change_item justifyC">
											<span>新密码</span>
											<input type="" name="" id="" value="" />
										</div>
										<div class="change_item justifyC">
											<span>确认密码</span>
											<input type=" " name="" id="" value="" />
										</div>
										<button type="submit" class="right_btn">确认提交</button>
									</form>
								</div>`
							});
						}
						
					},

					quit: function() {
							layer.confirm('确定退出登陆吗？', {
								btn: ['确定', '取消'] //按钮
							}, function() {//确定退出
								$.ajax({
										type : 'post',
										url : '<%=basePath%>logout',
										//async:false,//这一步很重要
										timeout:30000,
										dataType :'json',
										success : function(data) {//&quot;
											window.location.href = '<%=basePath%>';
										},
										error : function(jqXHR, textStatus, errorThrown) {
											
										}
								});
								
								
							}, function() {
								
						 });
					}
				}
			})
		</script>
	</body>
</html>
