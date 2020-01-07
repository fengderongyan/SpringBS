<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	String basePath = 
			
			path + "/";
%>
<html lang="en">
<head>
	<base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
    
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>网站后台-登录</title>
    <link rel="shortcut icon" type="image/x-icon" href="static/login_web/images/favicon.ico">
    <script type="text/javascript" src="static/login_web/js/layui.js"></script>
	<link rel="stylesheet" type="text/css" href="static/login_web/css/layui.css" rel="stylesheet" type="text/css">
	<link href="static/login_web/css/reg.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="static/login_web/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	<script src="static/login_web/js/reg.js" type="text/javascript"></script>
</head>
<body>
	<div class="container2">
  	  <div class="header"> 
  	  </div>
   	 <div class="main">
   	 	<input type="hidden" id="id_roleType" value="${roleType }"/>
    	<div class="loginform">
    		<div style="width:0;height:0;overflow: hidden;">
    			<input type="text" name="loginname1">
    			<input type="password" name="password">
    		</div>
        	<div class="blank"></div>
        	<div class="blank"></div>
        	<div class="blank"></div>
        	<div class="blank"></div>
        	<div class="blank"></div>
        	<div class="blank"></div>
        	<div class="loginform-options">
            	<ul>
                	<li><p><input type="text"  id="loginname" name="loginname" placeholder="请输入用户名" value="" ></p></li>
                	<li><p><input type="password" id="password" name="password" placeholder="请输入密码" value=""></p></li>
                </ul>
                
            </div>
            <div class="blank">
            	<div class="update-psw">
            		记住密码&nbsp;<input name="check" id="saveid" type="checkbox" onclick="savePwd();"/>
            	</div>
            </div>
          <div class="loginform-submit" style="margin-left:164px;"><a href="javascript:;" onclick="login()">
          
          <input type="image" src="static/login_web/images/anniu.png"></a>
          </div>
           <!--   <div class="btn"><a href="/manage/login/register1.jsp">立即注册>></a></div>  -->
        </div>
        </div>
        <div class="clear"></div><div class="blank"></div>
        <div class="copyright" style="color: black;">
        	版权所有&copy;：江苏沙光鱼软件科技有限公司
        </div>
    </div>
    
	</div>
  	
	</div>




<script src="static/login_web/js/jquery.inputbox.js"></script> 

<script type="text/javascript">

if (window != top)
	top.location.href = location.href;

var layer;
layui.use('layer', function() {
	layer = layui.layer;
})

$(function(){
	$("#loginname").keyup(function (e) {//捕获文档对象的按键弹起事件  
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	login();
	    }  
	}); 
	$("#password").keyup(function (e) {//捕获文档对象的按键弹起事件  
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	login();
	    }  
	});
	//记住密码
	var loginname = $.cookie('loginname');
	var password = $.cookie('password');
	if (typeof(loginname) != "undefined"
			&& typeof(password) != "undefined") {
		$("#loginname").val(loginname);
		$("#password").val(password);
		$("#saveid").attr("checked", true);
		$("#code").focus();
	}
});
function login() {
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
		url : "<%=basePath%>login_login",
		type : 'post',
		//async:false,
		data : {
			loginname : loginname,
			password : password
		},
		dataType : 'json',
		success : function(data) {
			if("success" == data.result){
				saveCookie();
				window.setTimeout(location.href="<%=basePath%>main/index",1000);
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

function savePwd() {
	if (!$("#saveid").attr("checked")) {
		$.cookie('loginname', '', {
			expires : -1
		});
		$.cookie('password', '', {
			expires : -1
		});
		$("#loginname").val('');
		$("#password").val('');
	}
}

function saveCookie() {
	if ($("#saveid").attr("checked")) {
		$.cookie('loginname', $("#loginname").val(), {
			expires : 7
		});
		$.cookie('password', $("#password").val(), {
			expires : 7
		});
	}
}
</script>
</body>
</html>