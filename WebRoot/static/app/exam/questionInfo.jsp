<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/manage/index_common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no">
    <title>在线教育系统</title>
    <link rel="stylesheet" type="text/css" href="/app/js/libs/mui/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="/app/css/global.css">
    <link rel="stylesheet" type="text/css" href="/app/css/style.css">
    <link rel="stylesheet" type="text/css" href="/app/css/media.css">
    <link rel="stylesheet" type="text/css" href="/app/css/mui-loading.css">
    
</head>
<body style="background-color:#FFF;">
    <input type="hidden" id="examId" value="${model.id }">
    <section class="main c-con">
    	<section class="kaoshi">
            <section class="kaoti-items">
            	<header class="kta-header"><label id="num" style="font-size:20px;">1</label>. <label id="questionTitle" style="font-size:20px;"></label></header>
                <div class="kta-item" style="font-size:20px;">
                	
                	<input type="hidden" id="chooseOption" value="">
                	<ul id="id_questionUl">
                    	<li style="font-size:20px;"><p></p>A.<label id="optionA"></label><span>A,</span></li>
                    	<li style="font-size:20px;"><p></p>B.<label id="optionB"></label><span>B,</span></li>
                    	<li id="questionCli" style="font-size:20px;"><p></p>C.<label id="optionC"></label><span>C,</span></li>
                    	<li id="questionDli" style="font-size:20px;"><p></p>D.<label id="optionD"></label><span>D,</span></li>
                    	<li id="questionEli" style="font-size:20px;"><p></p>E.<label id="optionE"></label><span>E,</span></li>
                    </ul>
                    
                    <!-- 
		        	<label><input type="checkbox" value="A"/>A、<label id="optionA"></label></label>	<br /><br />
		        	
		        	<label><input type="checkbox" value="B"/>B、<label id="optionB"></label> </label><br />	<br />
		        	
		        	
		        	<label id="id_optionC"><input type="checkbox" value="C"/>C、<label id="optionC"></label></label>	<br /><br />
		        	
		        	
		        	<label id="id_optionD"><input type="checkbox" value="D"/>D、<label id="optionD"></label> </label>	<br /><br />
		        	
		        	
		        	<label id="id_optionE"><input type="checkbox" value="E"/>E、<label id="optionE"></label></label>
		        	 -->
                </div>
            </section>
        </section>
    </section>
    <div>
    
   	<div class="kaoti-btn">
       	<!-- <a href="#">上一题</a> -->
       	<a href="javascript:submitAnswer();" id="nextQuestion" style="width:100%;">下一题</a>
       	<input type="hidden" id="questionNum" value="0">
       	<input type="hidden" id="type" value="">
    </div>
     
    <script src="/app/js/jquery-1.11.1.min.js"></script>
    <script src="/app/js/libs/mui/js/mui.min.js"></script>
    <script src="/app/js/fun.js"></script>
    <script src="/app/js/common.js"></script>
    <script src="/app/js/mui-loading.js"></script>
    <script type="text/javascript">
    	var questionList;
    	$(function(){
    		questionList=${questionList};
    		showQuestion();
    	});
    	
    	function submitAnswer(){
    		var questionNum=$("#questionNum").val();
    		var questionId=questionList[questionNum].id;
    		var answer = '';
    		if('多选' != $('#type').val()) {
        		var num = 0;
        		$("#id_questionUl li").each(function(){
        			if($(this).hasClass('on')){
        				num += 1;
        			}
           		});
           		if(num > 1) {
           			mui.toast("单选、判断只能选择一个选项！");
        			return;
             	}
       		}
       		
    		$("#id_questionUl li").each(function(){
    			if($(this).hasClass('on')){
    				answer += $(this).find('span').text();
    			}
    		});
    		if(answer==''){
    			mui.toast("请选择答案！");
    		}else{
    			mui.showLoading("正在加载..","div"); //加载文字和类型，plus环境中类型为div时强制以div方式显示  
    			$.ajax({
        			url : '/api/submitAnswer.do',
        			data : {examId:$('#examId').val(),questionId:questionId,answer:answer},
        			type : 'post',
        			async : false,
        			dataType : 'json',
        			success : function(data) {
        				  
        				if (data.error_code == 0) {
        					if(questionNum<questionList.length-1){
        						mui.hideLoading();//隐藏后的回调函数
        			    		$("#questionNum").val(questionNum*1+1);
        			    		if(questionNum*1+1==questionList.length-1){
        			    			$("#nextQuestion").html("结束考试");
        			    		}
        			    		$(".kaoti-items").find("ul li").removeClass("on");
        			    		//去掉选中
        			    		/* $("input[type='checkbox']:checked").each(function(){
					    			$(this).attr("checked",false);
					    		}); */
        			    		showQuestion();
        		    		}else{
        		    			//location.href="/app/exam/examInfoMsg.jsp?msg=答题结束";
        		    			location.href="/api/getExamResult.do?id="+$('#examId').val();
        		    		}
        				} else {
        					
        				}
        			},
        			error : function() {

        			}
        		});
    		}
    	}
    	
    	function showQuestion(){
    		var questionNum=$("#questionNum").val();
    		$("#num").html(questionNum*1+1);
    		$("#type").val(questionList[questionNum].type);
    		var questionType = "（" + questionList[questionNum].type + "）";
    		$("#chooseOption").val('');
    		$("#questionTitle").html(questionList[questionNum].subject + questionType);
    		$("#optionA").html(questionList[questionNum].optionsA);
    		$("#optionB").html(questionList[questionNum].optionsB);
    		//cd选项可能没有
    		var optionC = questionList[questionNum].optionsC;
    		var optionD = questionList[questionNum].optionsD;
    		var optionE = questionList[questionNum].optionsE;
    		if("" != optionC) {
    			$("#questionCli").show();
    			$("#optionC").html(optionC);
       		}else{
       			$("#questionCli").hide();
           	}
       		if("" != optionD ) {
       			$("#questionDli").show();
       			$("#optionD").html(optionD);
          	}else {
          		$("#questionDli").hide();
           	}
       		if("" != optionE ) {
       			$("#questionEli").show();
       			$("#optionE").html(optionE);
          	}else {
          		$("#questionEli").hide();
           	}
    	}
    	
    </script>
</body>
</html>