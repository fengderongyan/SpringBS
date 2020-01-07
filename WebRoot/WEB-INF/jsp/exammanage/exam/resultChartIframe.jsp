<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<link rel="stylesheet" href="static/layuiadmin/othercss/css/common/openshow.css" media="all"/>
<script type="text/javascript" src="static/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="static/kindeditor/lang/zh-CN.js"></script>

<body>
<div id="main" style="height: 477px;width: 800px"></div>
<script>
	var $;
	layui.config({
		base :  'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus : 'lib/webplus', //主入口模块
		echarts : 'lib/extend/echarts'
	}).use(['webplus', 'echarts', 'form'], function() {
		$ = layui.$;
	    var webplus=layui.webplus;
	    var echarts=layui.echarts;
	    
	    var echartsApp = [];
	    var option = {
        	    title: {
        	        text: '${param.title}',
        	        subtext: '安全365'
        	    },
        	    tooltip: {
        	        trigger: 'axis',
        	        axisPointer: {
        	            type: 'shadow'
        	        }
        	    },
        	    legend: {
        	        data: ['总人数','通过人数','未通过人数']
        	    },
        	    grid: {
        	        left: '3%',
        	        right: '4%',
        	        bottom: '3%',
        	        containLabel: true
        	    },
        	    xAxis: {
        	        type: 'value',
        	        boundaryGap: [0, 0.01]
        	    },
        	    yAxis: {
        	        type: 'category',
        	        data: ${orgnizationNameList}
        	    },
        	    series: [
        	    	{
        	            name: '总人数',
        	            type: 'bar',
        	            data: ${totalNumList}
        	        },
        	        {
        	            name: '通过人数',
        	            type: 'bar',
        	            data: ${passNumList}
        	        },
        	        {
        	            name: '未通过人数',
        	            type: 'bar',
        	            data: ${noPassNumList}
        	        }
        	    ]
        	};
	    var dom = document.getElementById("main");
	    var myChart = echarts.init(dom, layui.echartsTheme);
	    myChart.setOption(option, true);
	});

</script>
</body>
</html>