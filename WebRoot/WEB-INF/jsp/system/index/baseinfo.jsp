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
  <meta charset="utf-8">
  <title>设置我的资料</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="static/layuiadmin/style/admin.css" media="all">
</head>
<body>

  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">设置我的资料</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">我的角色</label>
                <div class="layui-input-inline">
                  <input type="text" name="username" value="${user.getRole().getRole_name() }" readonly class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                  <input type="text" name="username" value="${user.getUSERNAME() }" readonly class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-inline">
                  <input type="text" name="nickname" value="${user.getNAME() }" readonly  class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">组织</label>
                <div class="layui-input-block">
                  <input type="text" name="org_name" value="${user.getOrganization().getOrg_name() }"  autocomplete="off" placeholder="请输入昵称" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">手机</label>
                <div class="layui-input-inline">
                  <input type="text" name="phone" value="${user.getPhone }" class="layui-input">
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="static/layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: 'static/layuiadmin/' //静态资源所在路径
  }).extend({
	  webplus : 'lib/webplus' //主入口模块
  }).use(['form', 'webplus'], function(){
	  
  });
  </script>
</body>
</html>