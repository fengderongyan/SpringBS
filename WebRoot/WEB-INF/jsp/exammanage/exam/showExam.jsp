<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<link rel="stylesheet" href="static/layuiadmin/othercss/css/common/openshow.css" media="all"/>
<script type="text/javascript" src="static/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="static/kindeditor/lang/zh-CN.js"></script>
<style>
	.layui-form-checkbox span{
		white-space: pre-wrap;
    	text-overflow: inherit;
    	overflow: inherit;
	}
	.layui-form-checkbox{
		margin-left:10px!important;
		width:100%;
	}
	.layui-form-item span{
		top:0;
	}
</style>
<body>
<form class="layui-form layui-form-pane" action="" id="saveForm">
	<div style="display:flex;flex-direction:column">
		<span style="font-size:20px;width:100%;text-align:center;line-height: 2">${param.title }&nbsp;&nbsp;
		<a style="font-size: 13px;color: red;cursor: pointer;${param.flag_online == 1 ? 'display: none;' : ''}" id="printExam">打印试卷</a>
		</span>
		
	</div>
	<c:choose>
		<c:when test="${examQuestionList != null && examQuestionList.size() > 0}">
			<c:forEach items="${examQuestionList }" var="examQuestionMap" varStatus="status">
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label" style="white-space: normal;height: auto!important;">${status.index + 1}、${examQuestionMap.question_info }（${examQuestionMap.type_name }）</label>
					<div class="layui-input-block" style="white-space: normal!important;height: auto!important;border: 1px solid; border-color: #e6e6e6" >
						
					   <c:choose>
					   		<c:when test="${examQuestionMap.type == '100302'}">
					   		   <c:if test="${examQuestionMap.optionA != null && examQuestionMap.optionA != ''  }">
							   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="myAnswer" value="${examQuestionMap.id }" title=" A、${examQuestionMap.optionA}" ></div>
							   </c:if>
							   <c:if test="${examQuestionMap.optionB != null && examQuestionMap.optionB != ''  }">
							   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="myAnswer" value="${examQuestionMap.id }" title="B、${examQuestionMap.optionB}" ></div>
							   </c:if>
							   <c:if test="${examQuestionMap.optionC != null && examQuestionMap.optionC != ''  }">
							   	 <div style="white-space: normal!important;height: auto!important;">&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="myAnswer" value="${examQuestionMap.id }" title="C、${examQuestionMap.optionC}" ></div>
							   </c:if>
							   <c:if test="${examQuestionMap.optionD != null && examQuestionMap.optionD != ''  }">
							   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="myAnswer" value="${examQuestionMap.id }" title="D、${examQuestionMap.optionD}" ></div>
							   </c:if>
							   <c:if test="${examQuestionMap.optionE != null && examQuestionMap.optionE != ''  }">
							   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="myAnswer" value="${examQuestionMap.id }" title="E、${examQuestionMap.optionE}" ></div>
							   </c:if>
					   		</c:when>
					   		<c:otherwise>
					   			<c:if test="${examQuestionMap.optionA != null && examQuestionMap.optionA != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="myAnswer" value="${examQuestionMap.id }" title=" A、${examQuestionMap.optionA}" ></div>
							   </c:if>
							   <c:if test="${examQuestionMap.optionB != null && examQuestionMap.optionB != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="myAnswer" value="${examQuestionMap.id }" title="B、${examQuestionMap.optionB}" ></div>
							   </c:if>
							   <c:if test="${examQuestionMap.optionC != null && examQuestionMap.optionC != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="myAnswer" value="${examQuestionMap.id }" title="C、${examQuestionMap.optionC}" ></div>
							   </c:if>
							   <c:if test="${examQuestionMap.optionD != null && examQuestionMap.optionD != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="myAnswer" value="${examQuestionMap.id }" title="D、${examQuestionMap.optionD}" ></div>
							   </c:if>
							   <c:if test="${examQuestionMap.optionE != null && examQuestionMap.optionE != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="myAnswer" value="${examQuestionMap.id }" title="E、${examQuestionMap.optionE}" ></div>
							   </c:if>
					   		</c:otherwise>
					   </c:choose>
					</div>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div class="layui-form-item">
				<div class="layui-input-inline">
				    <span>暂无信息</span>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="layui-form-item" style="display:none;">
		<button class="layui-btn"  lay-submit="" lay-filter="saveSubmit">提交</button>
	</div>
</form>
	<script>
		var $;
		layui.config({
			base :  'static/layuiadmin/' //静态资源所在路径
		}).extend({
			webplus : 'lib/webplus', //主入口模块
		}).use(['form', 'webplus'], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var form = layui.form;
		   $('#printExam').click(function(){
				 var id = '${param.id}';
				 var title = '${param.title}';
				 parent.layer.closeAll();
				 parent.layer.open({
			            type: 2,
			            area: ['1000px', '500px'],//配置宽高
			            shade:0.4, //遮罩透明度
			            title: title + '-问答题', //弹出框头部信息
			            content: "<%=basePath%>exam/addWdt?id=" + id + "&title=" + title,  //打开的页面地址
			            btnAlign: 'c',  //底部按钮居中
			        });
				 //window.open("<%=basePath%>exam/printExam?id=" + id + "&title=" + title);
				 
				 <%-- $.ajax({
						type : 'post',
						url : '<%=basePath%>exam/printExam',
						data : {id : id, title : title},
						timeout:30000,
						dataType :'json',
						success : function(data) {
							
						},
						error : function(jqXHR, textStatus, errorThrown) {
							alert("1221");
						}
				});
				 return false; --%>
				 
		   });
		});

	</script>
</body>
</html>