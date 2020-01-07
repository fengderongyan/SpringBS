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
			<input type="hidden" name="exam_info_id" id="exam_info_id" value="${param.id }">
			<input type="hidden" name="question_count" id="question_count" value="${param.question_count }">
			<input type="hidden" name="seq_num" id="seq_num" value="${examQuestionMap.seq_num }">
			<input type="hidden" name="token" id="token" value="${token}">
			<div class="countdown_time justifyC">
				考试还剩：<span class="_h"></span>:
				<span class="_m"></span>:
				<span class="_s"></span>
			</div>
			<span id="exam_span_id">
			<form class="answer direction" method="post">
               	<input type="hidden" name="type" id="type" value="${examQuestionMap.type }">
				<input type="hidden" name="question_info_id" id="question_info_id" value="${examQuestionMap.id }">
				<input type="hidden" name="answer" id="answer" value="${examQuestionMap.answer }">
				<span class="ans_title">${examQuestionMap.seq_num}、${examQuestionMap.question_info }(${examQuestionMap.type_name })</span>
				<c:choose>
			   		<c:when test="${examQuestionMap.type == '100302'}">
			   			<c:if test="${examQuestionMap.optionA != null && examQuestionMap.optionA != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="A">
								<span>A、${examQuestionMap.optionA}</span>
							</div>
			   			</c:if>
			   			<c:if test="${examQuestionMap.optionB != null && examQuestionMap.optionB != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="B">
								<span>B、${examQuestionMap.optionB}</span>
							</div>
			   			</c:if>
			   			<c:if test="${examQuestionMap.optionC != null && examQuestionMap.optionC != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="C">
								<span>C、${examQuestionMap.optionC}</span>
							</div>
			   			</c:if>
			   			<c:if test="${examQuestionMap.optionD != null && examQuestionMap.optionD != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="D">
								<span>D、${examQuestionMap.optionD}</span>
							</div>
			   			</c:if>
			   			<c:if test="${examQuestionMap.optionE != null && examQuestionMap.optionE != ''  }">
			   				<div class="answer_item">
								<input type="checkbox" name="my_answer" value="E">
								<span>E、${examQuestionMap.optionE}</span>
							</div>
			   			</c:if>
			   		</c:when>
			   		<c:otherwise>
			   			<c:if test="${examQuestionMap.optionA != null && examQuestionMap.optionA != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="A">
								<span>A、${examQuestionMap.optionA}</span>
							</div>
			   			</c:if>
			   			<c:if test="${examQuestionMap.optionB != null && examQuestionMap.optionB != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="B">
								<span>B、${examQuestionMap.optionB}</span>
							</div>
			   			</c:if>
			   			<c:if test="${examQuestionMap.optionC != null && examQuestionMap.optionC != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="C">
								<span>C、${examQuestionMap.optionC}</span>
							</div>
			   			</c:if>
			   			<c:if test="${examQuestionMap.optionD != null && examQuestionMap.optionD != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="D">
								<span>D、${examQuestionMap.optionD}</span>
							</div>
			   			</c:if>
			   			<c:if test="${examQuestionMap.optionE != null && examQuestionMap.optionE != ''  }">
			   				<div class="answer_item">
								<input type="radio" name="my_answer" value="E">
								<span>E、${examQuestionMap.optionE}</span>
							</div>
			   			</c:if>
			   		</c:otherwise>
				</c:choose>
			</form>
			</span>
			<!-- 提交按钮 -->
			<div class="holder"></div>
			<div class="btns justify">
				<button type="button" class="justifyC" id="doNext" onclick="doNext();">下一题</button>
				<button type="button" class="justifyC" id="saveSubmit" onclick="saveSubmit();">提交</button>
			</div>
		</div>
	</body>
	<script>
		var end = new Date().getTime() + ${param.time_num}*60*1000;//倒计时使用
		$(document).ready(function() {
			change_sty();
			countTime();
		});
		function change_sty () {
			$('input').iCheck({
				checkboxClass: 'icheckbox_flat-red',
				radioClass: 'iradio_flat-red'
			});
		}
		var seq_num = ${examQuestionMap.seq_num};
		function doNext(){
			$('#doNext').attr('disabled', 'disabled');
			var question_count = $('#question_count').val();
			console.log("question_count:" + question_count);
			if(seq_num >= question_count){//最后一题提交信息
				layer.open({
					content: '已是最后一题！'
					,btn: '我知道了'
				});
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
			if(my_answer == undefined || my_answer == ''){
				layer.open({
				    content: '请选择您的答案！'
				    ,skin: 'msg'
				    ,time: 2 //2秒后自动关闭
			  	});
				$('#doNext').removeAttr('disabled');
				return false;
			}
			$('#seq_num').val(seq_num);
			seq_num++;
			var url = '<%=basePath%>mobile/app/toTakeExamAjax.do?tm=' +new Date().getTime();
	    	var param = {seq_num:seq_num,
	    				 id:$('#exam_info_id').val(),
	    				 question_info_id:$('#question_info_id').val(),
	    				 my_answer:my_answer,
	    				 exam_info_id:$('#exam_info_id').val(),
	    				 type:type,
	    				 answer:$('#answer').val()};
	    	layer.open({type: 2,content: '试题生成中',shadeClose:false});
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
		function saveSubmit(){
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
			}
			var question_count = $('#question_count').val();
			$('#seq_num').val(seq_num);
			var lock = false;
			if(seq_num < question_count){
				layer.open({
				    content: '您还未全部答完，确定提交试卷吗？'
				    ,btn: ['确定', '取消']
				    ,yes: function(index){
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
					    	$.ajax({
								url : '<%=basePath%>mobile/app/saveSubmit.do?tm=' + new Date().getTime(),
								data : param,
								type : 'post',
								headers:{'token': '${token}'},
								dataType : 'text',
								success : function(data) {
									window.location.href = "<%=basePath%>mobile/app/examFinish.do";
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
				    }
				});
				return false;
			}else{
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
					$.ajax({
						url : '<%=basePath%>mobile/app/saveSubmit.do?tm=' + new Date().getTime(),
						data : param,
						type : 'post',
						headers:{'token': '${token}'},
						dataType : 'text',
						success : function(data) {
							window.location.href = "<%=basePath%>mobile/app/examFinish.do";
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
				return false;
			}
			
			
		}
		
		function countTime() {
			//获取当前时间  
			var date = new Date();
			var now = date.getTime();
			//时间差  
			var leftTime = end - now;
			if(leftTime < 0) {
				window.location.href = "mobile/app/examFinish.do";
			}
			//定义变量 d,h,m,s保存倒计时的时间  
			var d, h, m, s;
			if(leftTime >= 0) {
				h = Math.floor(leftTime / 1000 / 60 / 60);
				m = Math.floor(leftTime / 1000 / 60 % 60);
				s = Math.floor(leftTime / 1000 % 60);
			}
			//将倒计时赋值到div中  
			if(h < 10 & h >= 0) {
				$("._h").html('0' + h);
			} else if(h >= 10) {
				$("._h").html(h);
			}
			if(m < 10 & m >= 0) {
				$("._m").html('0' + m);
			} else if(m >= 10) {
				$("._m").html(m);
			}
			if(s < 10 & s >= 0) {
				$("._s").html('0' + s);
			} else if(s >= 10) {
				$("._s").html(s);
			}
			//递归每秒调用countTime方法，显示动态时间效果  
			setTimeout(countTime, 1000);
		}
		
	</script>
</html>
