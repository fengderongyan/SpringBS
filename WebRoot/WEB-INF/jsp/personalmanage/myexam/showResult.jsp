<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		<span style="font-size:20px;width:100%;text-align:center;line-height: 1">${param.title }</span>
		<span style="width:100%;display:flex;justify-content:flex-end;font-size:14px;line-height: 1.5">得分：${score }</span>
	</div>
	<c:choose>
		<c:when test="${resultList != null && resultList.size() > 0}">
			<c:forEach items="${resultList }" var="resultMap" varStatus="status">
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label" style="white-space: normal;height: auto!important;">
						${status.index + 1}、${resultMap.question_info }（${resultMap.type_name }）<span>${resultMap.flag_right }，答案（${resultMap.answer}）</span>
					</label>
					<div class="layui-input-block" style="white-space: normal!important;height: auto!important;border: 1px solid; border-color: #e6e6e6" >
						
					   <c:choose>
					   		<c:when test="${resultMap.type == '100302'}">
					   		   <c:if test="${resultMap.optionA != null && resultMap.optionA != ''  }">
							   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="${resultMap.id }"  value="${resultMap.id }" disabled="disabled" ${fn:contains(resultMap.my_answer, 'A')?'checked':''} title=" A、${resultMap.optionA}" ></div>
							   </c:if>
							   <c:if test="${resultMap.optionB != null && resultMap.optionB != ''  }">
							   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="${resultMap.id }" value="${resultMap.id }" disabled="disabled" ${fn:contains(resultMap.my_answer, 'B')?'checked':''} title="B、${resultMap.optionB}" ></div>
							   </c:if>
							   <c:if test="${resultMap.optionC != null && resultMap.optionC != ''  }">
							   	 <div style="white-space: normal!important;height: auto!important;">&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="myAnswer" value="${resultMap.id }" disabled="disabled" ${fn:contains(resultMap.my_answer, 'C')?'checked':''} title="C、${resultMap.optionC}" ></div>
							   </c:if>
							   <c:if test="${resultMap.optionD != null && resultMap.optionD != ''  }">
							   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="${resultMap.id }" value="${resultMap.id }" disabled="disabled" ${fn:contains(resultMap.my_answer, 'D')?'checked':''} title="D、${resultMap.optionD}" ></div>
							   </c:if>
							   <c:if test="${resultMap.optionE != null && resultMap.optionE != ''  }">
							   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="${resultMap.id }" value="${resultMap.id }" disabled="disabled" ${fn:contains(resultMap.my_answer, 'E')?'checked':''} title="E、${resultMap.optionE}" ></div>
							   </c:if>
					   		</c:when>
					   		<c:otherwise>
							   <c:if test="${resultMap.optionA != null && resultMap.optionA != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="${resultMap.id }" value="${resultMap.id }" title=" A、${resultMap.optionA}" disabled="disabled" ${fn:contains(resultMap.my_answer, "A")?"checked":""}></div>
							   </c:if>
							   <c:if test="${resultMap.optionB != null && resultMap.optionB != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="${resultMap.id }" value="${resultMap.id }" title="B、${resultMap.optionB}" disabled="disabled" ${fn:contains(resultMap.my_answer, "B")?"checked":""}></div>
							   </c:if>
							   <c:if test="${resultMap.optionC != null && resultMap.optionC != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="${resultMap.id }" value="${resultMap.id }" title="C、${resultMap.optionC}" disabled="disabled" ${fn:contains(resultMap.my_answer, "C")?"checked":""}></div>
							   </c:if>
							   <c:if test="${resultMap.optionD != null && resultMap.optionD != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="${resultMap.id }" value="${resultMap.id }" title="D、${resultMap.optionD}" disabled="disabled" ${fn:contains(resultMap.my_answer, "D")?"checked":""}></div>
							   </c:if>
							   <c:if test="${resultMap.optionE != null && resultMap.optionE != ''  }">
							   	 <div>&nbsp;&nbsp;<input type="radio" name="${resultMap.id }" value="${resultMap.id }" title="E、${resultMap.optionE}" disabled="disabled" ${fn:contains(resultMap.my_answer, "E")?"checked":""}></div>
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
		    form.render();
		});

	</script>
</body>
</html>