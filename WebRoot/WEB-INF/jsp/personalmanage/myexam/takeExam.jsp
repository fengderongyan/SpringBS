<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<link rel="stylesheet" href="static/layuiadmin/othercss/css/common/openshow.css" media="all"/>
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
	<input type="hidden" name="exam_info_id" id="exam_info_id" value="${param.id }">
	<input type="hidden" name="question_count" id="question_count" value="${param.question_count }">
	<input type="hidden" name="seq_num" id="seq_num" value="${examQuestionMap.seq_num }">
	<div class="layui-form-item layui-form-text" id="exam_question_div_id" style="height: 350px">
		<label class="layui-form-label" style="white-space: normal;height: auto!important;">${examQuestionMap.seq_num}、${examQuestionMap.question_info }（${examQuestionMap.type_name }）</label>
		<div class="layui-input-block" style="white-space: normal!important;height: auto!important;border: 1px solid; border-color: #e6e6e6" >
			<input type="hidden" name="type" id="type" value="${examQuestionMap.type }">
			<input type="hidden" name="question_info_id" id="question_info_id" value="${examQuestionMap.id }">
			<input type="hidden" name="answer" id="answer" value="${examQuestionMap.answer }">
		   <c:choose>
		   		<c:when test="${examQuestionMap.type == '100302'}">
		   		   <c:if test="${examQuestionMap.optionA != null && examQuestionMap.optionA != ''  }">
				   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="my_answer" value="A" title=" A、${examQuestionMap.optionA}" ></div>
				   </c:if>
				   <c:if test="${examQuestionMap.optionB != null && examQuestionMap.optionB != ''  }">
				   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="my_answer" value="B" title="B、${examQuestionMap.optionB}" ></div>
				   </c:if>
				   <c:if test="${examQuestionMap.optionC != null && examQuestionMap.optionC != ''  }">
				   	 <div style="white-space: normal!important;height: auto!important;">&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="my_answer" value="C" title="C、${examQuestionMap.optionC}" ></div>
				   </c:if>
				   <c:if test="${examQuestionMap.optionD != null && examQuestionMap.optionD != ''  }">
				   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="my_answer" value="D" title="D、${examQuestionMap.optionD}" ></div>
				   </c:if>
				   <c:if test="${examQuestionMap.optionE != null && examQuestionMap.optionE != ''  }">
				   	 <div>&nbsp;&nbsp;<input lay-skin="primary" type="checkbox" name="my_answer" value="E" title="E、${examQuestionMap.optionE}" ></div>
				   </c:if>
		   		</c:when>
		   		<c:otherwise>
		   			<c:if test="${examQuestionMap.optionA != null && examQuestionMap.optionA != ''  }">
				   	 <div>&nbsp;&nbsp;<input type="radio" name="my_answer" value="A" title=" A、${examQuestionMap.optionA}" ></div>
				   </c:if>
				   <c:if test="${examQuestionMap.optionB != null && examQuestionMap.optionB != ''  }">
				   	 <div>&nbsp;&nbsp;<input type="radio" name="my_answer" value="B" title="B、${examQuestionMap.optionB}" ></div>
				   </c:if>
				   <c:if test="${examQuestionMap.optionC != null && examQuestionMap.optionC != ''  }">
				   	 <div>&nbsp;&nbsp;<input type="radio" name="my_answer" value="C" title="C、${examQuestionMap.optionC}" ></div>
				   </c:if>
				   <c:if test="${examQuestionMap.optionD != null && examQuestionMap.optionD != ''  }">
				   	 <div>&nbsp;&nbsp;<input type="radio" name="my_answer" value="D" title="D、${examQuestionMap.optionD}" ></div>
				   </c:if>
				   <c:if test="${examQuestionMap.optionE != null && examQuestionMap.optionE != ''  }">
				   	 <div>&nbsp;&nbsp;<input type="radio" name="my_answer" value="E" title="E、${examQuestionMap.optionE}" ></div>
				   </c:if>
		   		</c:otherwise>
		   </c:choose>
		</div>
	</div>
	<div class="layui-form-item">
		
		<table style="width: 100%">
			<tr>
				<td style="width: 60%">
					<button class="layui-btn layui-btn-sm" id="doNext" lay-filter="doNext">下一题</button>
					<button class="layui-btn layui-btn-sm" id="doSubmit" lay-filter="doSubmit">提 交</button>
				</td>
				<td><div id="shengyu_time" style="color: red"></div></td>
			</tr>
		</table>
		
	</div>
	<div class="layui-form-item" style="display:none;">
		<button class="layui-btn" id="saveSubmit" lay-submit="" lay-filter="saveSubmit">提交</button>
	</div>
</form>
	<script>
		var $;
		var seq_num = ${examQuestionMap.seq_num};
		layui.config({
			base :  'static/layuiadmin/' //静态资源所在路径
		}).extend({
			webplus : 'lib/webplus', //主入口模块
		}).use(['form', 'webplus', 'util', 'layer'], function() {
			$ = layui.$;
		    var webplus=layui.webplus;
		    var util=layui.util;
		    var layer=layui.layer;
		    var form = layui.form;
		    var time_num = ${param.time_num};//获取考试时长
		    var tmsp = new Date().getTime() + time_num * 60 * 1000;//获取考试结束时间戳
		    
		    util.countdown(tmsp, new Date().getTime(), function(date, serverTime, timer){
		        var str = date[1] + '时' +  date[2] + '分' + date[3] + '秒';
		        layui.$('#shengyu_time').html('距离考试结束还有：'+ str);
		    });
		    
		    
		    //下一题点击事件
			$('#doNext').click(function(){
				$('#doNext').attr('disabled', 'disabled');
				var question_count = $('#question_count').val();
				if(seq_num >= question_count){
					webplus.alertWarn('已是最后一题！');
					$('#doNext').removeAttr('disabled');
					return false;
				}
				var type = $('#type').val();
				var my_answer = '';
		    	if(type == 100302){
			    	$("input[name='my_answer']:checked").each(function(){
			    		my_answer += $(this).val();
			    	});
		    	}else{
		    		my_answer = $("input[name='my_answer']:checked").val();
		    	}
				if(webplus.isEmpty(my_answer)){
					webplus.alertWarn('请选择您的答案！');
					$('#doNext').removeAttr('disabled');
					return false;
				}
				$('#seq_num').val(seq_num);
				seq_num++;
				var url = '<%=basePath%>myexam/takeExamAjax.do?tm=' +new Date().getTime();
		    	var param = {seq_num:seq_num,
		    				 id:$('#exam_info_id').val(),
		    				 question_info_id:$('#question_info_id').val(),
		    				 my_answer:my_answer,
		    				 type:type,
		    				 answer:$('#answer').val()};
		    	var result = webplus.doReturnAjax(url, param,'','','text');
		    	$('#exam_question_div_id').html(result);
		    	$('#doNext').removeAttr('disabled');
		    	form.render();
				return false;
				
			});
		    
			$('#doSubmit').click(function(){
				var type = $('#type').val();
				var my_answer = '';
		    	if(type == 100302){
			    	$("input[name='my_answer']:checked").each(function(){
			    		my_answer += $(this).val();
			    	});
		    	}else{
		    		my_answer = $("input[name='my_answer']:checked").val();
		    	}
				if(webplus.isEmpty(my_answer)){
					webplus.alertWarn('请选择您的答案！');
					return false;
				}
				var question_count = $('#question_count').val();
				$('#seq_num').val(seq_num);
				
				var lock = false;
				if(seq_num < question_count){
					layer.confirm('您还未全部答完，确定提交试卷吗？',function(){
						webplus.showLoading();
						if(!lock){
							lock = true;
							seq_num++;
							var exam_info_id = $('#exam_info_id').val();
					    	var param = {seq_num:seq_num,
					    				 id:$('#exam_info_id').val(),
					    				 exam_info_id:$('#exam_info_id').val(),
					    				 question_info_id:$('#question_info_id').val(),
					    				 my_answer:my_answer,
					    				 type:type,
					    				 answer:$('#answer').val()};
							webplus.doReturnAjax('<%=basePath%>myexam/subExam', param,'','','text');
							webplus.hideLoading();
			        		parent.layer.closeAll();
						}
	            	});
					return false;
				}else{
					webplus.showLoading();
					if(!lock){
						lock = true;
						seq_num++;
						var exam_info_id = $('#exam_info_id').val();
				    	var param = {seq_num:seq_num,
				    				 id:$('#exam_info_id').val(),
				    				 exam_info_id:$('#exam_info_id').val(),
				    				 question_info_id:$('#question_info_id').val(),
				    				 my_answer:my_answer,
				    				 type:type,
				    				 answer:$('#answer').val()};
						webplus.doReturnAjax('<%=basePath%>myexam/subExam', param,'','','text');
						webplus.hideLoading();
		        		parent.layer.closeAll();
					}
					return false;
				}
				
			});
		
		});
		
	</script>
</body>
</html>