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
			<form class="answer direction" method="post">
				<c:forEach items="${examResultList }" var="examResultMap">
					<span class="ans_title">
						${examResultMap.seq_num}、${examResultMap.question_info }(${examResultMap.type_name })
						<span style="color: red;font-size: 13px;font-weight: normal;">${examResultMap.flag_right }，答案（${examResultMap.answer}）</span>
					</span>
					<c:choose>
				   		<c:when test="${examResultMap.type == '100302'}">
				   			<c:if test="${examResultMap.optionA != null && examResultMap.optionA != ''  }">
				   				<div class="answer_item">
									<input type="checkbox" name="${examResultMap.id }" value="A" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'A')?'checked':''}>
									<span>A、${examResultMap.optionA}</span>
								</div>
				   			</c:if>
				   			<c:if test="${examResultMap.optionB != null && examResultMap.optionB != ''  }">
				   				<div class="answer_item">
									<input type="checkbox" name="${examResultMap.id }" value="B" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'B')?'checked':''}>
									<span>B、${examResultMap.optionB}</span>
								</div>
				   			</c:if>
				   			<c:if test="${examResultMap.optionC != null && examResultMap.optionC != ''  }">
				   				<div class="answer_item">
									<input type="checkbox" name="${examResultMap.id }" value="C" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'C')?'checked':''}>
									<span>C、${examResultMap.optionC}</span>
								</div>
				   			</c:if>
				   			<c:if test="${examResultMap.optionD != null && examResultMap.optionD != ''  }">
				   				<div class="answer_item">
									<input type="checkbox" name="${examResultMap.id }" value="D" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'D')?'checked':''}> 
									<span>D、${examResultMap.optionD}</span>
								</div>
				   			</c:if>
				   			<c:if test="${examResultMap.optionE != null && examResultMap.optionE != ''  }">
				   				<div class="answer_item">
									<input type="checkbox" name="${examResultMap.id }" value="E" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'E')?'checked':''}>
									<span>E、${examResultMap.optionE}</span>
								</div>
				   			</c:if>
				   		</c:when>
				   		<c:otherwise>
				   			<c:if test="${examResultMap.optionA != null && examResultMap.optionA != ''  }">
				   				<div class="answer_item">
									<input type="radio" name="${examResultMap.id }" value="A" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'A')?'checked':''}>
									<span>A、${examResultMap.optionA}</span>
								</div>
				   			</c:if>
				   			<c:if test="${examResultMap.optionB != null && examResultMap.optionB != ''  }">
				   				<div class="answer_item">
									<input type="radio" name="${examResultMap.id }" value="B" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'B')?'checked':''}>
									<span>B、${examResultMap.optionB}</span>
								</div>
				   			</c:if>
				   			<c:if test="${examResultMap.optionC != null && examResultMap.optionC != ''  }">
				   				<div class="answer_item">
									<input type="radio" name="${examResultMap.id }" value="C" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'C')?'checked':''}>
									<span>C、${examResultMap.optionC}</span>
								</div>
				   			</c:if>
				   			<c:if test="${examResultMap.optionD != null && examResultMap.optionD != ''  }">
				   				<div class="answer_item">
									<input type="radio" name="${examResultMap.id }" value="D" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'D')?'checked':''}>
									<span>D、${examResultMap.optionD}</span>
								</div>
				   			</c:if>
				   			<c:if test="${examResultMap.optionE != null && examResultMap.optionE != ''  }">
				   				<div class="answer_item">
									<input type="radio" name="${examResultMap.id }" value="E" disabled="disabled" ${fn:contains(examResultMap.my_answer, 'E')?'checked':''}>
									<span>E、${examResultMap.optionE}</span>
								</div>
				   			</c:if>
				   		</c:otherwise>
					</c:choose>
				</c:forEach>
			</form>
			</span>
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
		
	</script>
</html>
