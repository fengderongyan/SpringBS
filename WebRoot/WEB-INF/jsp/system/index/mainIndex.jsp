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
  
  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md8">
        <div class="layui-row layui-col-space15">
          <div class="layui-col-md6">
            <div class="layui-card">
              <div class="layui-card-header">快捷方式</div>
              <div class="layui-card-body">
                
                <div class="layui-carousel layadmin-carousel layadmin-shortcut">
                  <div carousel-item>
                    <ul class="layui-row layui-col-space10">
                      <li class="layui-col-xs3">
                        <a lay-href="<%=basePath%>informationmanage/informationManageList.do">
                          <i class="layui-icon layui-icon-read"></i>
                          <cite>首页资讯</cite>
                        </a>
                      </li>
                      <li class="layui-col-xs3">
                        <a lay-href="<%=basePath%>informationSafeManageController/informationManageList.do">
                          <i class="layui-icon layui-icon-vercode"></i>
                          <cite>安全常识</cite>
                        </a>
                      </li>
                      <li class="layui-col-xs3">
                        <a lay-href="<%=basePath%>informationLawManage/informationManageList">
                          <i class="layui-icon layui-icon-template-1"></i>
                          <cite>法律法规</cite>
                        </a>
                      </li>
                      <li class="layui-col-xs3">
                        <a lay-href="<%=basePath%>informationAccidentManage/informationManageList">
                          <i class="layui-icon layui-icon-fire"></i>
                          <cite>事故案例</cite>
                        </a>
                      </li>
                      <li class="layui-col-xs3">
                        <a lay-href="<%=basePath%>videomanage/video/informationManageList">
                          <i class="layui-icon layui-icon-play"></i>
                          <cite>视频信息管理</cite>
                        </a>
                      </li>
                      <li class="layui-col-xs3">
                        <a lay-href="<%=basePath%>videomanage/expert/expertManageList">
                          <i class="layui-icon layui-icon-tabs"></i>
                          <cite>专家信息管理</cite>
                        </a>
                      </li>
                      <li class="layui-col-xs3">
                        <a lay-href="<%=basePath%>question/questionList">
                          <i class="layui-icon layui-icon-survey"></i>
                          <cite>题库管理</cite>
                        </a>
                      </li>
                      <li class="layui-col-xs3">
                        <a lay-href="<%=basePath%>exam/examList.do">
                          <i class="layui-icon layui-icon-file"></i>
                          <cite>试卷管理</cite>
                        </a>
                      </li>
                    </ul>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
          <div class="layui-col-md6">
            <div class="layui-card">
              <div class="layui-card-header">试卷统计</div>
              <div class="layui-card-body">

                <div class="layui-carousel layadmin-carousel layadmin-backlog">
                  <div carousel-item>
                    <ul class="layui-row layui-col-space10">
                      <li class="layui-col-xs6">
                        <a class="layadmin-backlog-body">
                          <h3>模拟试卷量</h3>
                          <p><cite>${mnksMap.exam_cnt }</cite></p>
                        </a>
                      </li>
                      <li class="layui-col-xs6">
                        <a class="layadmin-backlog-body">
                          <h3>参与人次</h3>
                          <p><cite>${mnksMap.exam_user_cnt }</cite></p>
                        </a>
                      </li>
                      <li class="layui-col-xs6">
                        <a class="layadmin-backlog-body">
                          <h3>正式试卷量</h3>
                          <p><cite>${zsksMap.exam_cnt }</cite></p>
                        </a>
                      </li>
                      <li class="layui-col-xs6">
                        <a class="layadmin-backlog-body">
                          <h3>参与人次</h3>
                          <p><cite>${zsksMap.exam_user_cnt }</cite></p>
                        </a>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="layui-col-md12">
            <div class="layui-card">
              <div class="layui-card-header">综合统计</div>
              <div class="layui-card-body">
                
                <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview">
                  <div carousel-item id="LAY-index-dataview">
                    <div><i class="layui-icon layui-icon-loading1 layadmin-loading"></i></div>
                    <div></div>
                    <div></div>
                  </div>
                </div>
                
              </div>
            </div>
            <div class="layui-card">
              <div class="layui-tab layui-tab-brief layadmin-latestData">
                <ul class="layui-tab-title">
                  <li class="layui-this">热门资讯</li>
                </ul>
                <div class="layui-tab-content">
                  <div class="layui-tab-item layui-show">
                    <table id="LAY-index-topSearch"></table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="layui-col-md4">
        <div class="layui-card">
          <div class="layui-card-header">用户信息</div>
          <div class="layui-card-body layui-text">
            <table class="layui-table">
              <colgroup>
                <col width="100">
                <col>
              </colgroup>
              <tbody>
                <tr>
                  <td>用户名</td>
                  <td>
                    ${USERROL.getUSERNAME()}
                  </td>
                </tr>
                <tr>
                  <td>当前角色</td>
                  <td>
                     ${USERROL.getRole().getROLE_NAME()}
                 </td>
                </tr>
                <tr>
                  <td>归属组织</td>
                  <td>${USERROL.getOrganization().getOrg_name()}</td>
                </tr>
                <tr>
                  <td style="width: 60px">上次登录时间</td>
                  <td style="padding-bottom: 0;">
                     ${USERROL.getLAST_LOGIN()}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        
        <div class="layui-card">
          <div class="layui-card-header">模拟考试</div>
          <div class="layui-card-body layadmin-takerates" style="padding:10px;10px;">
            <div class="layui-progress" lay-showPercent="yes" >
              <h3>通过率</h3>
              <span>&nbsp;&nbsp;</span><div class="layui-progress-bar" lay-percent="${ratePd.moni_pass_rate }" ></div>
            </div>
          </div>
        </div>
        
        <div class="layui-card">
          <div class="layui-card-header">上次正式考试</div>
          <div class="layui-card-body layadmin-takerates" style="padding:10px;10px;">
            <div class="layui-progress" lay-showPercent="yes">
              <h3>通过率</h3>
              <div class="layui-progress-bar" lay-percent="${ratePd.zhengshi_pass_rate }"></div>
            </div>
            <div class="layui-progress" lay-showPercent="yes">
              <h3>参与率</h3>
              <div class="layui-progress-bar layui-bg-red" lay-percent="${ratePd.join_rate }"></div>
            </div>
          </div>
        </div>
        
<!--         <div class="layui-card">
          <div class="layui-card-header">产品动态</div>
          <div class="layui-card-body">
            <div class="layui-carousel layadmin-carousel layadmin-news" data-autoplay="true" data-anim="fade" lay-filter="news">
              <div carousel-item>
                <div><a href="http://fly.layui.com/docs/2/" target="_blank" class="layui-bg-red">layuiAdmin 快速上手文档</a></div>
                <div><a href="http://fly.layui.com/vipclub/list/layuiadmin/" target="_blank" class="layui-bg-green">layuiAdmin 会员讨论专区</a></div> 
                <div><a href="http://www.layui.com/admin/#get" target="_blank" class="layui-bg-blue">获得 layui 官方后台模板系统</a></div>
              </div>
            </div>
          </div>
        </div> -->

        <div class="layui-card">
          <div class="layui-card-header">
          	 历史上的今天
          </div>
          <div class="layui-card-body layui-text layadmin-text" style="height: 300px;padding:10px;10px;">
          	<c:forEach items="${hisOfTodayList}" var="hisOfTodayMap">
          		<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
          		<span>${hisOfTodayMap.des }<span>
          		<br/>
          	</c:forEach>
          </div>
        </div>
      </div>
      
    </div>
  </div>
  <script>
  layui.config({
    base: 'static/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'console', 'admin', 'carousel', 'echarts', 'table'], function(){

	  var $ = layui.$
	    ,admin = layui.admin
	    ,carousel = layui.carousel
	    ,echarts = layui.echarts
	    ,table = layui.table;
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
	    var echartsApp = [], options = [
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
	          //orient: 'vertical',垂直排列
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
	          //orient: 'vertical',垂直排列
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
	    });
	    
	    
	  var cols=[ [ {
			type : 'numbers',
			title : '序号',
			fixed : 'left'
		},{
			field : 'id',
			hide:true
		},{
			field: 'title', 
			title: '标题', 
			minWidth: 120
		},{
			field: 'read_num', 
			title: '浏览人次', 
			minWidth: 120, 
			sort: true}] ];
	  //今日热搜
	  table.render({
	      elem: '#LAY-index-topSearch'
	      ,url: '<%=basePath%>main/getHotInfoList.do'
	      ,page: false
	      ,cols: cols
	      ,skin: 'line'
	  });
	  
  });
  </script>
</body>
</html>

