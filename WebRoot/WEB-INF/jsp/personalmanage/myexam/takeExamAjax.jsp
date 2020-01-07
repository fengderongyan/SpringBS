<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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