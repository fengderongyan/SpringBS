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
<title></title>
<meta name="renderer" content="webkit"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<link rel="stylesheet" href="static/layuiadmin/layui/css/layui.css"  media="all"/>
<link rel="stylesheet" href="static/layuiadmin/style/admin.css"media="all"/>
<link rel="stylesheet" href="static/layuiadmin/style/font-awesome/css/font-awesome.min.css" media="all"/>
<script src="static/layuiadmin/layui/layui.js"></script> 
</head>
<body>
  
 <div class="layui-card">
     <div class="layui-card-body" style="height: 1000px">
       <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview">
        	<div id="main1" style="height: 100%;width: 100%"></div>
        	<div id="main2" style="height: 100%;width: 100%"></div>
        	<div id="main3" style="height: 100%;width: 100%"></div>
       </div>
     </div>
 </div>
  <script>
  layui.config({
    base: 'static/layuiadmin/' //静态资源所在路径
  }).extend({
	  webplus : 'lib/webplus', //主入口模块
	  echarts : 'lib/extend/echarts'
  }).use(['webplus', 'echarts', 'form'], function(){
	  	var $ = layui.$;
	    var webplus=layui.webplus;
	    var echarts=layui.echarts;
	    
	    var series = [];
	    var seriesList = ${seriesList};
	    var seriesListJson = eval(seriesList);
	    for(var i = 0; i < seriesListJson.length; i++){
	    	series.push({
	            name: seriesListJson[i]['name'],
	            type: 'line',
	            smooth: true,
	            itemStyle:{normal: {areaStyle: {type: 'default'}}},
	            data: eval(seriesListJson[i]['data'])
	        });
	    }
	    var areaStudySeries = [];
	    var areaStudySeriesList = ${areaStudySeriesList};
	    var areaStudySeriesListJson = eval(areaStudySeriesList);
	    for(var i = 0; i < areaStudySeriesListJson.length; i++){
	    	areaStudySeries.push({
	            name: areaStudySeriesListJson[i]['name'],
	            type: 'bar',
	            smooth: true,
	            itemStyle:{normal: {areaStyle: {type: 'default'}}},
	            data: eval(areaStudySeriesListJson[i]['data'])
	        });
	    }
	    console.log(areaStudySeriesList);
	    var option1 = {
				        title: {
					          text: '各园区企业登录实况',
					          x: 'center',
					          textStyle: {
					            fontSize: 14
					          }
					        },
					        tooltip : {
					          trigger: 'axis'
					        },
					        legend: {
					          x:'right',
					          orient: 'vertical',//垂直排列
					          data:${areaNameList}
					        },
					        xAxis : [{
					          type : 'category',
					          boundaryGap : false,
					          data: ${hourList}
					        }],
					        yAxis : [{
					          type : 'value',
					          name:'企业登录个数'
					        }],
					        series : series
					 }
	    
	    
	    var dom = document.getElementById("main1");
	    var myChart = echarts.init(dom, layui.echartsTheme);
	    myChart.setOption(option1, true);
	    
	    var option2 = { 
			        	 title : {
				          text: '近一个月系统使用占比',
				          x: 'center',
				          textStyle: {
				            fontSize: 14
				          }
				        },
				        tooltip : {
				          trigger: 'item',
				          formatter: "{a} <br/>{b} : {c} ({d}%)"
				        },
				        legend: {
				          orient : 'vertical',
				          x : 'right',
				          data:${sysUseAreaList}
				        },
				        series : [{
				          name:'使用人次',
				          type:'pie',
				          radius : '55%',
				          center: ['50%', '50%'],
				          data:${sysUseSeriesList}
				        }]
				      }

		var dom = document.getElementById("main2");
		var myChart2 = echarts.init(dom, layui.echartsTheme);
		myChart2.setOption(option2, true);
		
		var option3 = {
		        title: {
			          text: '各园区活跃度',
			          x: 'center',
			          textStyle: {
			            fontSize: 14
			          }
			        },
			        tooltip : {
			          trigger: 'axis',
			          axisPointer: {
			              type: 'shadow'
			          }
			        },
			        legend: {
			          x:'right',
			          orient: 'vertical',//垂直排列
			          data:${areaStudyAreaNameList}
			        },
			        grid: {
			            left: '3%',
			            right: '4%',
			            bottom: '3%',
			            containLabel: true
			        },
			        xAxis : [{
			          type : 'category',
			          data: ${getAreaStudyDay}
			        }],
			        yAxis : [{
			          type : 'value',
			          boundaryGap: [0, 0.01],
			          name:'活跃度'
			        }],
			        series : areaStudySeries
			      }

		var dom = document.getElementById("main3");
		var myChart3 = echarts.init(dom, layui.echartsTheme);
		myChart3.setOption(option3, true);
	    
	    /* var echartsApp = [], options = [
	      //各园区企业登录实况
	      {
	        title: {
	          text: '各园区企业登录实况',
	          x: 'center',
	          textStyle: {
	            fontSize: 14
	          }
	        },
	        tooltip : {
	          trigger: 'axis'
	        },
	        legend: {
	          x:'right',
	          orient: 'vertical',//垂直排列
	          data:${areaNameList}
	        },
	        xAxis : [{
	          type : 'category',
	          boundaryGap : false,
	          data: ${hourList}
	        }],
	        yAxis : [{
	          type : 'value',
	          name:'企业登录个数'
	        }],
	        series : series
	      },
	      
	      //近一个月系统使用占比
	      { 
	        title : {
	          text: '近一个月系统使用占比',
	          x: 'center',
	          textStyle: {
	            fontSize: 14
	          }
	        },
	        tooltip : {
	          trigger: 'item',
	          formatter: "{a} <br/>{b} : {c} ({d}%)"
	        },
	        legend: {
	          orient : 'vertical',
	          x : 'left',
	          data:${sysUseAreaList}
	        },
	        series : [{
	          name:'使用人次',
	          type:'pie',
	          radius : '55%',
	          center: ['50%', '50%'],
	          data:${sysUseSeriesList}
	        }]
	      },
	      
	      //各园区活跃度
	      {
	        title: {
	          text: '各园区活跃度',
	          x: 'center',
	          textStyle: {
	            fontSize: 14
	          }
	        },
	        tooltip : {
	          trigger: 'axis',
	          axisPointer: {
	              type: 'shadow'
	          }
	        },
	        legend: {
	          x:'right',
	          orient: 'vertical',//垂直排列
	          data:${areaStudyAreaNameList}
	        },
	        grid: {
	            left: '3%',
	            right: '4%',
	            bottom: '3%',
	            containLabel: true
	        },
	        xAxis : [{
	          type : 'category',
	          data: ${getAreaStudyDay}
	        }],
	        yAxis : [{
	          type : 'value',
	          boundaryGap: [0, 0.01],
	          name:'活跃度'
	        }],
	        series : areaStudySeries
	      }
	    ]
	    
	    
	    
	    ,elemDataView = $('#LAY-index-dataview').children('div')
	    ,renderDataView = function(index){
	      echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
	      echartsApp[index].setOption(options[index]);
	      //window.onresize = echartsApp[index].resize;
	      admin.resize(function(){
	        echartsApp[index].resize();
	      });
	    };
	    
	    
	    //没找到DOM，终止执行
	    if(!elemDataView[0]) return;
	    
	    
	    
	    renderDataView(0);
	    
	    //监听数据概览轮播
	    var carouselIndex = 0;
	    carousel.on('change(LAY-index-dataview)', function(obj){
	      renderDataView(carouselIndex = obj.index);
	    });
	    
	    //监听侧边伸缩
	    layui.admin.on('side', function(){
	      setTimeout(function(){
	        renderDataView(carouselIndex);
	      }, 300);
	    });
	    
	    //监听路由
	    layui.admin.on('hash(tab)', function(){
	      layui.router().path.join('') || renderDataView(carouselIndex);
	    }); */
	    
  });
  </script>
</body>
</html>

