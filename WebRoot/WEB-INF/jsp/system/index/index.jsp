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
  <title>安全平台后台管理系统</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="shortcut icon" type="image/x-icon" href="static/login_web/images/favicon.ico">
  <link rel="stylesheet" href="static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="static/layuiadmin/style/admin.css" media="all">
  <link rel="stylesheet" href="static/layuiadmin/style/font-awesome/css/font-awesome.min.css" media="all"/>
  <script src="https://pv.sohu.com/cityjson?ie=utf-8"></script>
  <style>
#LAY-system-side-menu>li>a{padding-top:0px;}
a{text-decoration:none;}
#LAY-system-side-menu .layui-nav-child>dd>a,
#LAY_app .layui-nav>li>a{text-decoration:none;}
#DownMenu a{padding-top: 10px;padding-bottom: 10px;}
#DownMenu a:hover{text-decoration:none;}
.layadmin-side-shrink .layui-side-menu .layui-nav > .layui-nav-item:hover .layui-nav-child{
	display: inline-block;
	position: fixed;
	left: 60px;
	min-width: 200px;
	padding: 0;
	width:auto;
}
.layadmin-side-shrink .layui-side-menu .layui-nav > .layui-nav-item:hover cite{
	display:none;
}
.layadmin-side-shrink .layui-side-menu .layui-nav > .layui-nav-item:hover .layui-nav-child a{
	padding-left:20px;
}
.layadmin-side-shrink .layui-side-menu .layui-nav > .layui-nav-item:hover .layui-nav-child i{
	left:-10px;
}
.layadmin-side-shrink .layui-side-menu .layui-nav >.layui-nav-item:hover .layui-nav-child cite{
	display: inline-block;
	width: auto;
	padding: 0;
	text-indent:0;
}
</style>
</head>
<body class="layui-layout-body">
  <div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
      <div class="layui-header">
        <!-- 头部区域 -->
        <ul class="layui-nav layui-layout-left">
          <li class="layui-nav-item layadmin-flexible" lay-unselect>
            <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
              <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
            </a>
          </li>
         <!--  <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="http://www.layui.com/admin/" target="_blank" title="前台">
              <i class="layui-icon layui-icon-website"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" layadmin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-3"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <input type="text" placeholder="搜索..." autocomplete="off" class="layui-input layui-input-search" layadmin-event="serach" lay-action="template/search.html?keywords="> 
          </li> -->
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" layadmin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-3"></i>
            </a>
          </li>
        </ul>
        <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
          
          <!-- <li class="layui-nav-item" lay-unselect>
            <a lay-href="app/message/index.html" layadmin-event="message" lay-text="消息中心">
              <i class="layui-icon layui-icon-notice"></i>  
              
              如果有新消息，则显示小圆点
              <span class="layui-badge-dot"></span>
            </a>
          </li> -->
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="theme">
              <i class="layui-icon layui-icon-theme"></i>
            </a>
          </li>
          <!-- <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="lockScreen">
              <i class="layui-icon layui-icon-password"></i>
            </a>
          </li> -->
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="fullscreen">
              <i class="layui-icon layui-icon-screen-full"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;">
              <cite>${USERROL.USERNAME }</cite>
            </a>
            <dl class="layui-nav-child">
              <dd><a lay-href="<%=basePath %>main/updatePassword.do">修改密码</a></dd>
              <hr>
              <dd layadmin-event="logout" style="text-align: center;"><a>退出</a></dd>
            </dl>
          </li>
          
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" ><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
          <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
            <a href="javascript:;" layadmin-event="more"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
        </ul>
      </div>
      
      <!-- 侧边菜单 -->
      <div class="layui-side layui-side-menu">
        <div class="layui-side-scroll">
          <div class="layui-logo" onclick="goMainIndex()" style="cursor: pointer;">
            <span>安全平台后台管理</span>
          </div>
          
          <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
          	<c:forEach items="${menuList}" var="menu1">
				<c:if test="${menu1.hasMenu && '1' == menu1.MENU_STATE}">
					<li data-name="${menu1.MENU_ID }" class="layui-nav-item">
					 <a href="javascript:;" lay-tips="${menu1.MENU_NAME }" lay-direction="2">
		                <i class="${menu1.MENU_ICON }"></i>
		                <cite>${menu1.MENU_NAME }</cite>
		             </a>
		             <c:if test="${'[]' != menu1.subMenu}">
		             	<dl class="layui-nav-child">
		             	<c:forEach items="${menu1.subMenu}" var="menu2">
		             		<c:if test="${menu2.hasMenu && '1' == menu2.MENU_STATE && '[]' == menu2.subMenu}">
				               <dd data-name="${menu2.MENU_ID }">
				                  <a lay-href="${menu2.MENU_URL }"><i class="${menu2.MENU_ICON }"></i>${menu2.MENU_NAME }</a>
				                </dd>
		             		</c:if>
		             		<c:if test="${menu2.hasMenu && '1' == menu2.MENU_STATE && '[]' != menu2.subMenu}">
				               <dd data-name="${menu2.MENU_ID }">
				               		<a href="javascript:;"><i class="${menu2.MENU_ICON }"></i>${menu2.MENU_NAME }</a>
				               		<dl class="layui-nav-child">
				               			<c:forEach items="${menu2.subMenu}" var="menu3">
				               				<c:if test="${menu3.hasMenu && '1' == menu3.MENU_STATE}">
				               					<dd><a lay-href="${menu3.MENU_URL }"><i class="${menu3.MENU_ICON }"></i>${menu3.MENU_NAME }</a></dd>
				               				</c:if>
				               			</c:forEach>
					                    
					                </dl>
				               </dd>
		             		</c:if>
		             	</c:forEach>
		             	</dl>
		             </c:if>
		           </li>
				</c:if>
			</c:forEach>
          
          </ul>
        </div>
      </div>

      <!-- 页面标签 -->
      <div class="layadmin-pagetabs" id="LAY_app_tabs">
        <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-down">
          <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
            <li class="layui-nav-item" lay-unselect>
              <a href="javascript:;"></a>
              <dl class="layui-nav-child layui-anim-fadein">
                <dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
                <dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
                <dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
          <ul class="layui-tab-title" id="LAY_app_tabsheader">
            <li lay-id="mainIndex.html" lay-attr="mainIndex.html" class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
          </ul>
        </div>
      </div>
      
      
      <!-- 主体内容 -->
      <div class="layui-body" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show">
          <iframe src="<%=basePath %>main/mainIndex.do" frameborder="0" class="layadmin-iframe"></iframe>
        </div>
      </div>
      <!-- 辅助元素，一般用于移动设备下遮罩 -->
      <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
  </div>
<script src="static/layuiadmin/layui/layui.js"></script>
<script src="static/layuiadmin/otherjs/lib/md5.js"></script>
  <script>
  layui.config({
    base: 'static/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index', //主入口模块
    webplus: '../lib/webplus' //主入口模块
  }).use([ 'index','webplus'], function() {
	  var $ = layui.$, 
	  form = layui.form,
	  webplus=layui.webplus,
	  admin = layui.admin;
	  webplus.initMain();
	  //退出
	  admin.events.logout = function(){
		  webplus.doAjax('logout','','你确定注销当前用户退出系统吗？','0','',function(data){
				webplus.delToken();
				location.href = '<%=basePath%>login_toLogin';
			});
	  };
	  console.log(returnCitySN);

  });
  function goMainIndex(){
	  window.location.href="<%=basePath %>main/index.do";
  }
  </script>
</body>
</html>


