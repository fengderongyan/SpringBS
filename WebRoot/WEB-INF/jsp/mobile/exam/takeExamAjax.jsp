<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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