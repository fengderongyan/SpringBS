<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/manage/index_common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no">
    <title>在线教育系统</title>
    <link rel="stylesheet" type="text/css" href="./css/global.css">
    <link rel="stylesheet" href="css/swiper.min.css">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="stylesheet" type="text/css" href="./css/media.css">

</head>
<body>
    <header class="header clear">
        <h3><a href="#">在线考试</a></h3>
    
    </header>
    <section class="header-blank c-con"></section>  
    <section class="main c-con">
    	<section class="kaoshi">
        	<section class="ks-tab clear">
            	<ul>
                	<li class="on"><p>待考</p></li>
                	<li><p>已考</p></li>
                </ul>
            </section>
            <jsp:useBean id="now" class="java.util.Date" scope="page"/>
            <div class="ks-list">
            	<ul>
                	<c:forEach var="temp" items="${xpageBean.recordList }">
	                	<li>
		                	<a href="/app/examInfo.do?id=${temp.id }">
		                    	<h3>
		                    	<c:if test="${now<temp.beginDate }">
			                    	<p class="notstarted">未开始</p>
		                    	</c:if>
		                    	<c:if test="${now>temp.beginDate&&now<temp.endDate }">
			                    	<p class="processing">进行中</p>
		                    	</c:if>
		                    	<c:if test="${now>temp.endDate }">
			                    	<p class="over">已结束</p>
		                    	</c:if>
		                    	${temp.title }</h3>
		                    	<c:set var="answer" value="${temp.answerInfos }"></c:set>
		                    	<p><span>${fn:length(answer) } 人已参与</span>考试时间：<fmt:formatDate value="${temp.beginDate }" pattern="yyyy-MM-dd"/>-<fmt:formatDate value="${temp.endDate }" pattern="yyyy-MM-dd"/> </p>                        
		                    </a>
	                    </li>
                	</c:forEach>
                </ul>
            </div>
            <div class="ks-list">
            	<ul>
            		<c:forEach var="temp" items="${ypageBean.recordList }">
	                	<li>
	                	<a href="/app/examInfo.do?id=${temp.examInfo.id }">
		                    	<h3>
		                    	<c:if test="${now<temp.examInfo.beginDate }">
			                    	<p class="notstarted">未开始</p>
		                    	</c:if>
		                    	<c:if test="${now>temp.examInfo.beginDate&&now<temp.examInfo.endDate }">
			                    	<p class="processing">进行中</p>
		                    	</c:if>
		                    	<c:if test="${now>temp.examInfo.endDate }">
			                    	<p class="over">已结束</p>
		                    	</c:if>
		                    	${temp.examInfo.title }</h3>
		                    	<c:set var="answer" value="${temp.examInfo.answerInfos }"></c:set>
		                    	<p><span>${fn:length(answer) } 人已参与</span>考试时间：<fmt:formatDate value="${temp.examInfo.beginDate }" pattern="yyyy-MM-dd"/>-<fmt:formatDate value="${temp.examInfo.endDate }" pattern="yyyy-MM-dd"/> </p>                        
		                    </a>
	                    </li>
                    </c:forEach>
                </ul>
            </div>
        </section>
    </section>
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/fun.js"></script>
</body>
</html>