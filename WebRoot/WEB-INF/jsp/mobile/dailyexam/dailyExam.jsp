<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = 
			
			path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		
		<link rel="stylesheet" type="text/css" href="static/mobileexam/skins/flat/red.css" />
		<link rel="stylesheet" type="text/css" href="static/mobileexam/css/style.css"/>
		<link rel="stylesheet" type="text/css" href="static/mobileexam/layer_mobile/need/layer.css"/>
		<script src="static/mobileexam/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="static/mobileexam/js/icheck.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="static/mobileexam/layer_mobile/layer.js" type="text/javascript" charset="utf-8"></script>
		<title></title>
	</head>
	<style>
		.btns{
			width: 100%;
			height: 40px;
			position: fixed;
			bottom: 0;
		}
		.btns>button{
			width: 50%;
			height: 40px;
			border-radius: 5px;
			background: #E22018;
			color: #fff;
			border: 1px solid #fff;
			box-sizing: border-box;
		}
	</style>
	<body>
		<div class="container">
			<img src="static/mobileexam/images/background.png" class="banner">
			<input type="hidden" name="seq_num" id="seq_num" value="${dailyExamMap.seq_num }">
			<input type="hidden" name="token" id="token" value="${token}">
			<span id="exam_span_id">
			<form class="answer direction" method="post">
               	<input type="hidden" name="type" id="type" value="${dailyExamMap.type }">
				<input type="hidden" name="answer" id="answer" value="${dailyExamMap.answer }">
				<span class="ans_title">${dailyExamMap.seq_num}、${dailyExamMap.question_info }(${dailyExamMap.type_name })</span>
				<c:choose>
			   		<c:when test="${dailyExamMap.type == '100302'}">
			   			<c:if test="${dailyExamMap.optionA != null && dailyExamMap.optionA != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="A">
								<span>A、${dailyExamMap.optionA}</span>
							</div>
			   			</c:if>
			   			<c:if test="${dailyExamMap.optionB != null && dailyExamMap.optionB != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="B">
								<span>B、${dailyExamMap.optionB}</span>
							</div>
			   			</c:if>
			   			<c:if test="${dailyExamMap.optionC != null && dailyExamMap.optionC != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="C">
								<span>C、${dailyExamMap.optionC}</span>
							</div>
			   			</c:if>
			   			<c:if test="${dailyExamMap.optionD != null && dailyExamMap.optionD != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="D">
								<span>D、${dailyExamMap.optionD}</span>
							</div>
			   			</c:if>
			   			<c:if test="${dailyExamMap.optionE != null && dailyExamMap.optionE != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="E">
								<span>E、${dailyExamMap.optionE}</span>
							</div>
			   			</c:if>
			   		</c:when>
			   		<c:otherwise>
			   			<c:if test="${dailyExamMap.optionA != null && dailyExamMap.optionA != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="A">
								<span>A、${dailyExamMap.optionA}</span>
							</div>
			   			</c:if>
			   			<c:if test="${dailyExamMap.optionB != null && dailyExamMap.optionB != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="B">
								<span>B、${dailyExamMap.optionB}</span>
							</div>
			   			</c:if>
			   			<c:if test="${dailyExamMap.optionC != null && dailyExamMap.optionC != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="C">
								<span>C、${dailyExamMap.optionC}</span>
							</div>
			   			</c:if>
			   			<c:if test="${dailyExamMap.optionD != null && dailyExamMap.optionD != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="D">
								<span>D、${dailyExamMap.optionD}</span>
							</div>
			   			</c:if>
			   			<c:if test="${dailyExamMap.optionE != null && dailyExamMap.optionE != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="E">
								<span>E、${dailyExamMap.optionE}</span>
							</div>
			   			</c:if>
			   		</c:otherwise>
				</c:choose>
			<span id="answer_span_id" style="color: red;font-size: 14px;display: none">正确答案：${dailyExamMap.answer}</span>
			</form>
			</span>
			<!-- 提交按钮 -->
			<div class="holder"></div>
			<div class="btns justify">
				<button type="button" class="justifyC" id="doNext" onclick="doNext();">下一题</button>
				<button type="button" class="justifyC" id="showResult" onclick="showResult();">查看答案</button>
			</div>
		</div>
	</body>
	<script>
		$(document).ready(function() {
			change_sty();
		});
		function change_sty () {
			$('input').iCheck({
				checkboxClass: 'icheckbox_flat-red',
				radioClass: 'iradio_flat-red'
			});
		}
		var seq_num = ${dailyExamMap.seq_num};
		function doNext(){
			$('#doNext').attr('disabled', 'disabled');
			if(seq_num >= 5){//最后一题提交信息
				layer.open({
					content: '已是最后一题！'
					,btn: '我知道了'
				});
				return false;
			}
			var type = $('#type').val();
			/* var my_answer = '';
	    	if(type == 100302){
		    	$("input[name='my_answer']:checked").each(function(){
		    		my_answer += $(this).val();
		    	});
	    	}else{
	    		my_answer = $("input[name='my_answer']:checked").val();
	    	}
			if(my_answer == undefined || my_answer == ''){
				layer.open({
				    content: '请选择您的答案！'
				    ,skin: 'msg'
				    ,time: 2 //2秒后自动关闭
			  	});
				$('#doNext').removeAttr('disabled');
				return false;
			} */
			$('#seq_num').val(seq_num);
			seq_num++;
			var url = '<%=basePath%>mobile/app/dailyExamAjax.do?tm=' +new Date().getTime();
	    	var param = {seq_num:seq_num,
	    				 answer:$('#answer').val()};
	    	layer.open({type: 2,content: '加载中'});
	    	$.ajax({
				url : url,
				data : param,
				type : 'post',
				headers:{'token': '${token}'},
				dataType : 'text',
				success : function(data) {
					$('#exam_span_id').html(data);
					change_sty();//加载按钮样式
			    	layer.closeAll();
			    	$('#doNext').removeAttr('disabled');
				},
				error : function() {
					layer.open({
					    content: '请求异常！'
					    ,skin: 'msg'
					    ,time: 2 //2秒后自动关闭
				  	});	
					$('#doNext').removeAttr('disabled');
				}
			});
		}
		
		function showResult(){
			var type = $('#type').val();
			var my_answer = '';
	    	if(type == 100302){
		    	$("input[name='my_answer']:checked").each(function(){
		    		my_answer += $(this).val();
		    	});
	    	}else{
	    		my_answer = $("input[name='my_answer']:checked").val();
	    	}
			if(my_answer == undefined || my_answer == ''){
				layer.open({
				    content: '请选择您的答案！'
				    ,skin: 'msg'
				    ,time: 2 //2秒后自动关闭
			  	});
				return false;
			}else{
				$('#answer_span_id').show();
			}
			
		}
		
	</script>
</html>
