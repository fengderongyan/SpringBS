<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<body>

  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">修改密码</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">当前密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="oldPassword" id="oldPassword" lay-verify="required" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="password" lay-verify="pass" autocomplete="off" id="password" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">6到16个字符</div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">确认新密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="repassword" lay-verify="repass" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn" lay-submit lay-filter="setmypass">确认修改</button>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  </div>
  <script>
  layui.config({
    base: 'static/layuiadmin/' //静态资源所在路径
  }).extend({
    webplus: 'lib/webplus'
  }).use(['webplus', 'form'], function(){
	  var $ = layui.$
	  ,form = layui.form
	  ,webplus = layui.webplus;
	  
	  //自定义验证
	  form.verify({
	    nickname: function(value, item){ //value：表单的值、item：表单的DOM对象
	      if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
	        return '用户名不能有特殊字符';
	      }
	      if(/(^\_)|(\__)|(\_+$)/.test(value)){
	        return '用户名首尾不能出现下划线\'_\'';
	      }
	      if(/^\d+\d+\d$/.test(value)){
	        return '用户名不能全为数字';
	      }
	    }
	    
	    //我们既支持上述函数式的方式，也支持下述数组的形式
	    //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
	    ,pass: [
	      /^[\S]{6,12}$/
	      ,'密码必须6到12位，且不能出现空格'
	    ]
	    
	    //确认密码
	    ,repass: function(value){
	      if(value !== $('#password').val()){
	        return '两次密码输入不一致';
	      }
	    }
	  });
	  form.on('submit(setmypass)', function(obj){
		var params = {password: $("#password").val(),oldPassword:$("#oldPassword").val()};
		webplus.ajaxBase('<%=basePath%>sysuser/updatePwd',params,'','', function(data){
			if(data.appcode == 1){
				webplus.alertSuccess("修改成功");
				webplus.doAjax('logout','','','0','',function(data){
					webplus.delToken();
					location.href = '<%=basePath%>login_toLogin';
				});
			}else{
				webplus.alertError(data.appmsg);
			}
		})
	    return false;
	 });
  });
  </script>
</body>
</html>