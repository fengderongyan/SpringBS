<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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