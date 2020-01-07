<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<link rel="stylesheet" href="static/layuiadmin/othercss/css/common/openshow.css" media="all"/>
<body>
<form class="layui-form layui-form-pane" action="" id="saveForm">
	<div class="layui-form-item layui-form-text" id="exam_question_div_id" >
		<label class="layui-form-label" style="display:flex;justify-content:space-between">
			添加问答题
			<div>
				<input type="button" class="layui-btn layui-btn-normal layui-btn-xs" value="继续添加" id="doAdd"/>
				<input type="button" class="layui-btn layui-btn-normal layui-btn-xs" value="打印" id="doPrint"/>
			</div>
		</label>
	    <div class="layui-input-block">
	      <textarea name="wdt" placeholder="请输入内容" class="layui-textarea"></textarea>
	      <div style="margin-top: 2px;margin-bottom: 8px"><input type="button" class="layui-btn layui-btn-danger layui-btn-xs" style="height: auto;" value="删除" onclick="doDel(this);"/></div>
	    </div>
	</div>
</form>
	<script>
		layui.config({
			base :  'static/layuiadmin/' //静态资源所在路径
		}).extend({
			webplus : 'lib/webplus', //主入口模块
		}).use(['webplus', 'layer'], function() {
		    var webplus=layui.webplus;
		    var $ = layui.$;
		    var id = '${param.id}';
		    var title = '${param.title}';
		    $('#doAdd').click(function(){
		    	$('#exam_question_div_id').append(
		    			'<div class="layui-input-block">' +
		    		      '<textarea name="wdt" placeholder="请输入内容" class="layui-textarea"></textarea>' +
		    		      '<div style="margin-top: 2px;margin-bottom: 8px"><input type="button" class="layui-btn layui-btn-danger layui-btn-xs" style="height: auto;" value="删除" onclick="doDel(this);"/></div>' +
		    		    '</div>'		
		    	);
		    });
		    
		    $('#doPrint').click(function(){
		    	var wdt = '';
		    	$(".layui-textarea").each(function(){
		    		wdt = wdt + ';-springbs-;' + $(this).val();
		    	});
		    	var uuid = webplus.doReturnAjax("<%=basePath%>exam/printExam", {id:id,title:title,wdt:wdt},'','','text');
		    	window.open("<%=basePath%>exam/downLoadZip?uuid=" + uuid);
		    });
		});
		
		function doDel(obj){
			var textarea_length = $('.layui-textarea').length;
			if(textarea_length > 1){
				$(obj).parent().parent().remove();
			}
		}
	</script>
</body>
</html>