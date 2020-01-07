<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/manage/index_common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no">
    <title>考试系统</title>
    <link rel="stylesheet" type="text/css" href="/app/css/global.css">
    <link rel="stylesheet" href="/app/css/swiper.min.css">
    <link rel="stylesheet" type="text/css" href="/app/css/style.css">
    <link rel="stylesheet" type="text/css" href="/app/css/media.css">

</head>
<body>
    <section class="main c-con">
    	<section class="kaoshi-about" style="background-color: #c71c1c;">
        	<header class="ksa-header">
        		考试完成积分+5；
            	你的得分：${totalScore }
            </header>
        </section>
        <section class="kaoti-items" style="text-align: left; padding-bottom: 100px">
        		<span><p>错误的问题</p></span>
				<c:forEach var="temp" items="${questionList}">
		        	<span>${temp.seqNum}. ${temp.subject}<p style="color:red">（正确答案：${temp.answer}）</p></span> <br />
		        	<c:if test="${temp.optionsA != '' }">
		        	<label><input type="checkbox" ${fn:contains(temp.myAnswer, 'A')?'checked="checked"':''} />A、 ${temp.optionsA }</label> <br />
		        	</c:if>
		        	<c:if test="${temp.optionsB != '' }">
		        	<label><input type="checkbox" ${fn:contains(temp.myAnswer, 'B')?'checked="checked"':''}/>B、 ${temp.optionsB }</label> <br />
		        	</c:if>
		        	<c:if test="${temp.optionsC != '' }">
		        	<label><input type="checkbox" ${fn:contains(temp.myAnswer, 'C')?'checked="checked"':''}/>C、 ${temp.optionsC }</label> <br />
		        	</c:if>
		        	<c:if test="${temp.optionsD != '' }">
		        	<label><input type="checkbox" ${fn:contains(temp.myAnswer, 'D')?'checked="checked"':''}/>D、 ${temp.optionsD }</label> <br />
		        	</c:if>
		        	<c:if test="${temp.optionsE != '' }">
		        	<label><input type="checkbox" ${fn:contains(temp.myAnswer, 'E')?'checked="checked"':''}/>E、 ${temp.optionsE }</label> <br />
		        	</c:if>
				</c:forEach>
        </section>
    </section>
       
</body>
</html>