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
<html lang="en">
<head>
	<base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no">
    <title>在线教育系统</title>
    <link rel="stylesheet" type="text/css" href="static/app/css/global.css">
    <link rel="stylesheet" type="text/css" href="static/app/js/libs/mui/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="static/app/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/app/css/media.css">
	<link rel="stylesheet" type="text/css" href="static/app/css/mui-loading.css">
	<link rel="stylesheet" type="text/css" href="static/mobileexam/layer_mobile/need/layer.css"/>
</head>
<body>
	<input type="hidden" id="exam_info_id" value="${examInfoMap.id }">
    <section class="main c-con">
    	<section class="kaoshi-about">
        	<header>
        		<!-- 
            	<h2 style="color:#df3031">${model.title }</h2>
            	 -->
            </header>
            <section class="ksa-cont" style="margin-top:30px; height:726px;">
            	<div class="ksa-list clear">
                	<ul>
                    	<li>
                        	<div class="kl-icon"><img src="static/app/images/emp-03.png" alt=" "></div>
                        	<div class="kl-about">
                            	<h3>试卷总分</h3>
                                <p>${examInfoMap.total_score }分</p>
                            </div>
                        </li>
                    	<li>
                        	<div class="kl-icon"><img src="static/app/images/emp-04.png" alt=" "></div>
                        	<div class="kl-about">
                            	<h3>及格分</h3>
                                <p>60分</p>
                            </div>
                        </li>
                    	<li>
                        	<div class="kl-icon"><img src="static/app/images/emp-05.png" alt=" "></div>
                        	<div class="kl-about">
                            	<h3>总题数</h3>
                                <p>${examInfoMap.question_count }题</p>
                            </div>
                        </li>
                        <li>
                        	<div class="kl-icon"><img src="static/app/images/emp-06.png" alt=" "></div>
                        	<div class="kl-about">
                            	<h3>考试时长</h3>
                                <p>${examInfoMap.time_num }分钟</p>
                            </div>
                        </li>
                        <c:choose>
						   <c:when test="${examInfoMap.score!= null && examInfoMap.score != ''}"> 
						        <li>
		                        	<a href="javaScript:" id="toViewGrade" class="kl-btn">查看成绩</a>
		                        </li>      
						   </c:when>
						   <c:otherwise>
						    	<li>
						    		<a href="javaScript:" id="toTakeExam" class="kl-btn">开始答题</a>
		                        </li>
						   </c:otherwise>
						</c:choose>
						
                        <c:if test="${examInfoMap.score!= null && examInfoMap.score != ''}">
	                        <li>
	                        	<div class="kl-icon" style="width:100px;font-weight: normal;font-size: 1.2rem;">考试得分</div>
	                        	<div class="kl-about">
	                                ${examInfoMap.score }分
	                            </div>
	                        </li>
                        </c:if>
                        
                    </ul>
                </div>
            </section>
        </section>
    </section>
    
    <script src="static/app/js/jquery-1.11.1.min.js"></script>
    <script src="static/app/js/libs/mui/js/mui.min.js"></script>
    <script src="static/app/js/mui-loading.js"></script>
    <script src="static/mobileexam/layer_mobile/layer.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
    	$("#toViewGrade").on("click", function(){
    		layer.open({type: 2,content: '加载中',shadeClose:false});
    		location.href= "<%=basePath%>mobile/app/getExamResult.do?id="+$('#exam_info_id').val() + '&token=' + '${token}';
    	});
    	$("#toTakeExam").on("click", function(){
    		layer.open({type: 2,content: '加载中',shadeClose:false});
    		location.href= "<%=basePath%>mobile/app/toTakeExam.do?id="+$('#exam_info_id').val() + "&seq_num=1" + 
				"&question_count=" + ${examInfoMap.question_count} + "&time_num=" + ${examInfoMap.time_num}+ '&token=' + '${token}';
    	});
  		/* function toViewGrade(){
  			//layer.open({type: 2,content: '加载中'});
  			
  		}

  		function toTakeExam(){
  			//layer.open({type: 2,content: '加载中'}); 
  			location.href= "mobile/app/toTakeExam.do?id="+$('#exam_info_id').val() + "&seq_num=1" + 
  					"&question_count=" + ${examInfoMap.question_count} + "&time_num=" + ${examInfoMap.time_num}+ '&token=' + '${token}';
 	  	} */
    </script>
</body>
</html>