<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = 
			
			path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<%@ include file="/static/web/webinclude.jsp" %>
		<link rel="stylesheet" href="static/layuiadmin/layui/css/layui.css"  media="all">
		<script src="static/layuiadmin/layui/layui.js" charset="utf-8"></script>
	</head>
	<body>
		<div class="per_title">考题考试</div>
		<div class="exam_list" id="exam_app">
			<div class="exam_item justifyS" id="exam_item_id">
				<div class="exam_txt" id="title" ></div>
				<span id="btn_span">
					<button type="button"  class="right_btn" onclick="takeExam()">开始答题</button>
				</span>
			</div>
		</div>
		<div id="pageDiv"></div>
		<script type="text/javascript">
			var layer;
			layui.use(['laypage', 'layer'], function(){
				var laypage = layui.laypage;
				layer = layui.layer;
				//完整功能
			  	laypage.render({
				    elem: 'pageDiv'
				    ,count: '${total}'
				    ,layout: ['count', 'prev', 'page', 'next', 'skip']
			  		,groups:5
			  		,limit : 10	
				    ,jump: function(obj){
				    	$.ajax({
								type : 'post',
								url : '<%=basePath%>web/personal/examList',
								data : {exam_type : '${param.exam_type}', page:obj.curr, limit : obj.limit},
								//async:false,//这一步很重要
								timeout:30000,
								dataType :'json',
								success : function(data) {
									$('#exam_app').html('');
									for(var i=0;i<data.length;i++){
										$('#title').html(data[i].title);
										if(data[i].flag_answer == 1){
											$('#exam_app').append(
													'<div class="exam_item justifyS" id="exam_item_id">' +
													'	<div class="exam_txt" id="title" >' + data[i].title + '</div>' +
													'	<button type="button" class="right_btn" onclick="showResult(&quot;' + 
															data[i].id + '&quot;, &quot;'+ data[i].title +'&quot;)">查看成绩</button>' +
													'</div>'
											);
										}else{
											$('#exam_app').append(
													'<div class="exam_item justifyS" id="exam_item_id">' +
													'	<div class="exam_txt" id="title" >' + data[i].title + '</div>' +
													'	<button type="button" class="right_btn" onclick="takeExam(&quot;' + 
															data[i].id + '&quot;, &quot;'+ data[i].time_num +'&quot;, &quot;' + 
															data[i].question_count+ '&quot;, &quot;' + data[i].title+ '&quot;)">开始答题</button>' +
													'</div>'
											);
										}
									}
								},
								error : function(jqXHR, textStatus, errorThrown) {
									
								}
						});
				    }
			  	});
				
			});
			function takeExam(id, time_num, question_count, title){
				var url = '<%=basePath%>myexam/takeExam?seq_num=1&id='+ id + '&time_num=' + time_num + '&question_count=' + question_count;
	    		var time = time_num * 60 * 1000;
				layer.open({
		            type: 2,
		            time: time,//n秒后触发关闭事件
		            area: ['700px', '550PX'],//配置宽高
		            shade:0.4, //遮罩透明度
		            id:'addinfo-iframe',  //iframeID值
		            title: title, //弹出框头部信息
		            content: url,  //打开的页面地址
		            success:function (layero,index) {  
						console.log(layero,index)
						$('#person_center').append(layero[0]);
		            },
		            yes:function(index){
						
		            },
		            cancel:function(index,layero){//右上角关闭触发事件
		            	var body = layer.getChildFrame('body', index);
		                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		                var seq_num = iframeWin.document.getElementById("seq_num").value;
		            	layer.confirm('考试还未结束，确定退出考试吗？',function(){
		            		//webplus.doReturnAjax('<%=basePath%>myexam/saveExam', {exam_info_id : data.id},'','','text');
		            		layer.close(index);
		            	});
		            	return false;
		            },
		            end:function(){//窗口关闭触发事件
		            	$.ajax({
								type : 'post',
								url : '<%=basePath%>myexam/saveExam',
								data : {exam_info_id : id},
								//async:false,//这一步很重要
								timeout:30000,
								dataType :'text',
								success : function(data) {
									layer.alert('考试结束！', function(index){
					   					layer.close(index);
					   					window.location.reload();
					   				});
								},
								error : function(jqXHR, textStatus, errorThrown) {
									
								}
						});
		            }
		            
		        });
				
				
			}
			function showResult(id, title){
				layer.open({
		            type: 2,
		            area: ['700px', '700px'],//配置宽高
		            shade:0.4, //遮罩透明度
		            id:'addinfo-iframe',  //iframeID值
		            title: '查看成绩', //弹出框头部信息
		            content: '<%=basePath%>myexam/showResult?id=' + id + '&title=' + title,  //打开的页面地址
		            success:function (layero,index) {  
						console.log(layero,index)
						$('#person_center').append(layero[0]);
		            }
		            
		        });
			}
		</script>
	</body>
</html>
