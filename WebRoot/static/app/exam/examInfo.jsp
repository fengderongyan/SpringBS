<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/manage/index_common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no">
	
    <title>在线教育系统</title>
    <link rel="stylesheet" type="text/css" href="/app/css/global.css">
    <link rel="stylesheet" type="text/css" href="/app/js/libs/mui/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="/app/css/style.css">
    <link rel="stylesheet" type="text/css" href="/app/css/media.css">
	<link rel="stylesheet" type="text/css" href="/app/css/mui-loading.css">
	
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
                        	<div class="kl-icon"><img src="/app/images/emp-03.png" alt=" "></div>
                        	<div class="kl-about">
                            	<h3>试卷总分</h3>
                                <p>${examInfoMap.total_score }分</p>
                            </div>
                        </li>
                    	<li>
                        	<div class="kl-icon"><img src="/app/images/emp-04.png" alt=" "></div>
                        	<div class="kl-about">
                            	<h3>及格分</h3>
                                <p>60分</p>
                            </div>
                        </li>
                    	<li>
                        	<div class="kl-icon"><img src="/app/images/emp-05.png" alt=" "></div>
                        	<div class="kl-about">
                            	<h3>总题数</h3>
                                <p>${examInfoMap.question_count }题</p>
                            </div>
                        </li>
                        <li>
                        	<div class="kl-icon"><img src="/app/images/emp-06.png" alt=" "></div>
                        	<div class="kl-about">
                            	<h3>考试时长</h3>
                                <p>${examInfoMap.time_num }分钟</p>
                            </div>
                        </li>
                        <c:choose>
						   <c:when test="${examInfoMap.score!= null && examInfoMap.score != ''}"> 
						        <li>
		                        	<a href="javaScript:" onclick="toViewGrade()" class="kl-btn">查看成绩</a>
		                        </li>      
						   </c:when>
						   <c:otherwise>
						    	<li>
						    		<a href="javaScript:" onclick="toTakeExam()"class="kl-btn">开始答题</a>
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
    
    <script src="/app/js/jquery-1.11.1.min.js"></script>
    <script src="/app/js/libs/mui/js/mui.min.js"></script>
    <script src="/app/js/mui-loading.js"></script>
    <script type="text/javascript">
    	var httpHref = "localhost:8080/SpringBS/";
  		function toViewGrade(){
  			mui.showLoading("正在加载..","div"); //加载文字和类型，plus环境中类型为div时强制以div方式显示  
  			location.href= httpHref + "mobile/app/getExamResult.do?id="+$('#examId').val();
  		}

  		function toTakeExam(){
  			mui.showLoading("正在加载..","div"); //加载文字和类型，plus环境中类型为div时强制以div方式显示  
  			location.href= httpHref + "/api/toTakeExam.do?id="+$('#exam_info_id').val();
 	  	}
    </script>
</body>
</html>