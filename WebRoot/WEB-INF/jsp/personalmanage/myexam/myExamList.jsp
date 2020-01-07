<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<style>
.layui-table-tool{
	display: none;
}
</style>
<body>
  <div class="layui-fluid">
		<div class="layui-card">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto" id="searchForm">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">标题</label>
						<div class="layui-input-block">
							<input class="layui-input" name="title" id="title" value="" autocomplete="off">
						</div>
					</div>
					<div class="layui-inline">
						<button class="layui-btn layuiadmin-btn-sm" lay-submit
							lay-filter="searchSubmit" >查询</button>
					</div>
				</div>
			</div>
			<div class="layui-card-body">
				<table id="dataList" lay-filter="dataList" ></table>
			</div>
		</div>
	</div>
<div type="text/html" id="trTool" style="display:none">
	{{# if(d.flag_answer == 0) { }}
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="take_exam"><i class="layui-icon layui-icon-edit"></i>答题</a>
	{{# } }}
	{{# if(d.flag_answer == 1) { }}
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="show_result"><i class="layui-icon layui-icon-edit"></i>查看成绩</a>
	{{# } }}
</div>
<div type="text/html" id="flagAnswerTr" style="display:none">
	{{# if(d.flag_answer == 0) { }}
	未考
	{{# } }}
	{{# if(d.flag_answer == 1) { }}
	已考
	{{# } }}
</div>

<script>
	layui.config({
		base :  'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus  : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus', 'layer'], function() {
		var $ = layui.$, 
		form = layui.form, 
		table = layui.table,
		webplus=layui.webplus;
		var layer = layui.layer;
		var cols=[ [ {
			type : 'numbers',
			title : '序号',
			width : 50
		},{
			field : 'id',
			hide:true
		},{
			field : 'flag_edit',
			hide:true
		},{
			field : 'flag_answer',
			hide:true
		},{
			field : 'title',
			title : '标题',
			width : 500,
			align : 'center'
		},{
			field : 'exam_type',
			title : '试卷类型',
			align : 'center'
		},{
			field : 'total_score',
			title : '总分',
			align : 'center'
		},{
			field : 'time_num',
			title : '时长',
			align : 'center'
		},{
			field : 'industry_name',
			title : '适用行业',
			align : 'center'
		},{
			title : '是否考试',
			toolbar : '#flagAnswerTr',
			align : 'center'
		},{
			title : '操作',
			toolbar : '#trTool',
			width : 130,
			align : 'left',
			fixed: 'right'
		} ] ];
		
		webplus.init('<%=basePath%>myexam/getMyExamList?exam_type=' + ${param.exam_type},cols);
		 //行单击事件
		  table.on('row(dataList)', function(obj){
			  obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
		  });
	      
		 //监听行工具事件
		  table.on('tool(dataList)', function(obj){
		    var data = obj.data;
		    //判断操作的按钮类别  需要在标签上配置  lay-event='判断的类型值'
		    switch(obj.event){
			    case 'take_exam':
		    		//打开详情弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    		   var url = '<%=basePath%>myexam/takeExam?seq_num=1&id='+ data.id + '&time_num=' + data.time_num + '&question_count=' + data.question_count;
		    		   var time = data.time_num * 60 * 1000;
		    		   layer.open({
				            type: 2,
				            time: time,//n秒后触发关闭事件
				            area: ['700px', '550PX'],//配置宽高
				            shade:0.4, //遮罩透明度
				            id:'addinfo-iframe',  //iframeID值
				            title: data.title, //弹出框头部信息
				            content: url,  //打开的页面地址
				            btnAlign: 'c',  //底部按钮居中
				            btn: ['确定'],  //底部按钮信息
				            success:function (layero,index) {  
				            	//给底部按钮加上小图标和颜色
				            	$("body").find(".layui-layer-btn0").html('<i class="layui-icon layui-icon-ok"></i> 提交试卷');
				            },
				            yes:function(index){
				            	//点击提交按钮触发子页面的提交表单事件
				            	$("#addinfo-iframe>iframe").contents().find('[lay-submit]').click();
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
				            	webplus.doReturnAjax('<%=basePath%>myexam/saveExam', {exam_info_id : data.id},'','','text');
				            	$("body").find('[lay-submit]').click();
				            	webplus.alertWarn('考试结束！');
				            }
				            
				        });
		    	   }
	   			break;
	   			
		    	case 'show_result':
		    		//打开详情弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    		 webplus.openWindowBase('<%=basePath%>myexam/showResult?id=' + data.id + '&title=' + data.title, '查看成绩', '700', '', '1');
		    	   }
		   		break;
		    }
		  });

		});
	</script>
  
  
  </script>
</body>
</html>
