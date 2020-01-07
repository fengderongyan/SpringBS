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
		<title>安全教育</title>
		<base href="<%=basePath%>">
		<%@ include file="/static/web/webinclude.jsp" %>
		<style>
			.professor_img{
				width:60px;
 				height:60px;
 				overflow:hidden;
 				display:flex;
 				justify-content:center;
 				align-items:center;
			}
			.professor_img>img{
				height:100%;
			}
		</style>
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/web/header.jsp"></jsp:include>
		<div class="container">
			<!-- 顶部标题 -->
			<div class="top_title directionA">
				<div class='index_title' onclick="openHomeInfoDetail('${homeInfoList[0].id }')">
					${homeInfoList[0].title }
				</div>
				<div class='second_title justifyC'>
					<span onclick="openHomeInfoDetail('${homeInfoList[1].id }')">${homeInfoList[1].title }</span>
				</div>
				<span class="line"></span>
			</div>
			<!-- 轮播图 -->
			<div class="top_banner swiper-container">
				<div class="swiper-wrapper">
					<div class="swiper-slide justifyC" onclick="openHomeInfoDetail('${homeInfoList[2].id }')" style="cursor: pointer;">
						<img src="${homeInfoList[2].cover }">
						<div class="notice">${homeInfoList[2].title }</div>
					</div>
					<div class="swiper-slide justifyC" onclick="openHomeInfoDetail('${homeInfoList[3].id }')" style="cursor: pointer;">
						<img src="${homeInfoList[3].cover }">
						<div class="notice">${homeInfoList[3].title }</div>
					</div>
					<div class="swiper-slide justifyC" onclick="openHomeInfoDetail('${homeInfoList[4].id }')" style="cursor: pointer;">
						<img src="${homeInfoList[4].cover }">
						<div class="notice">${homeInfoList[4].title }</div>
					</div>
				</div>
				<!-- 如果需要分页器 -->
				<div class="swiper-pagination"></div>

				<!-- 如果需要导航按钮 -->
				<div class="swiper-button-prev"></div>
				<div class="swiper-button-next"></div>
			</div>

			<div class="information justifyC">
				<div class="info_left">
					<div class="info_title justifyS">
						<span class="title_txt">最新资讯</span>
						<span class="title_more" onclick="openHomeInfoList();">打开</span>
					</div>
					<div class="news_list justify">
						<c:forEach items="${homeInfoList}" var="homeInfoMap" begin="5">
							<a onclick="openHomeInfoDetail('${homeInfoMap.id }')">${homeInfoMap.title }</a>
						</c:forEach>
					</div>
					<div class="advers">
						<img src="static/web/img/adver1.png">
						<img src="static/web/img/adver2.png">
					</div>
				</div>
				<!-- 天气预报 -->
				<div class="info_right">
					<div class="top_location">
						江苏>连云港>市区
					</div>
					<div class="weather_list">
						<div class="date justifyAr" id="date">
							
						</div>
						<div class="weather justify" id="weather"></div>
						<img src="static/web/img/weather.png"  class="weather_img">
					</div>
					<div class="top_location">
					安全生产重于泰山
					</div>
				</div>
			</div>
			<!-- 案例列表 -->
			<div class="ed_list justify">
				<div class="ed_item">
					<div class="info_title justifyS">
						<span class="title_txt">事故案例</span>
						<a class="title_more" onclick="openAccidentList()">打开</a>
					</div>
					<div class="ed_content direction">
						<c:forEach items="${accidentList }" var="accidentMap">
							<a onclick="openAccidentDetail('${accidentMap.id }')">${accidentMap.title }</a>
						</c:forEach>
					</div>
					<img src="static/web/img/zhishi.png" class="weather_img">
				</div>
				<div class="ed_item">
					<div class="info_title justifyS">
						<span class="title_txt">教学课程</span>
						<a class="title_more" onclick="openLessionList()">打开</a>
					</div>
					<div class="ed_content direction">
						<c:forEach items="${lessonList }" var="lessonMap">
							<a onclick="openLessionDetail('${lessonMap.id }')">${lessonMap.title }</a>
						</c:forEach>
					</div>
					
				</div>
				<div class="leader_img">
					<img src="static/web/img/leader.png" >
					<div>习近平：发展坚决不能以牺牲安全为代价</div>
				</div>
			</div>
			
			<div class="ed_list justify">
				<div class="ed_item">
					<div class="info_title justifyS">
						<span class="title_txt">专家简介</span>
						<a class="title_more" onclick="openExpertList()">打开</a>
					</div>
					<div class="ed_content direction">
						<c:forEach items="${expertInfoList }" var="expertInfoMap">
							<a onclick="openExpertDetail('${expertInfoMap.id }')" class="justifyS professor">
								<div class="pro_info">
									<span>${expertInfoMap.name }</span>
									<div>${expertInfoMap.introduce }</div>
								</div>
								<div class='professor_img'>
									<img src="${expertInfoMap.photo }" >
								</div>
								
							</a>
						</c:forEach>
					</div>
				</div>
				<div class="ed_item">
					<div class="info_title justifyS">
						<span class="title_txt">法律法规</span>
						<a class="title_more" onclick="openLawInfoList()">打开</a>
					</div>
					<div class="ed_content direction">
						<c:forEach items="${lawInfoList }" var="lawInfoMap">
							<a onclick="openLawInfoDetail('${lawInfoMap.id}')">${lawInfoMap.title }</a>
						</c:forEach>
					</div>
				</div>
				<div class="ed_item">
					<div class="info_title justifyS">
						<span class="title_txt">安全常识</span>
						<span class="title_more" onclick="openSafeInfoList()">打开</span>
					</div>
					<div class="ed_content direction">
						<c:forEach items="${safeInfoList }" var="safeInfoMap">
							<a onclick="openSafeInfoDetail('${safeInfoMap.id}')">${safeInfoMap.title }</a>
						</c:forEach>
					</div>
				</div>
			</div>
			<img src="static/web/img/bottom-img.png"  class="bottom_img">
			
		</div>
		<jsp:include page="/WEB-INF/jsp/web/footer.jsp"></jsp:include>	
		<script type="text/javascript">
			loadTopWindow();//跳转至此的页面应该为顶级页面
			function loadTopWindow(){
			    if (window.top!=null && window.top.document.URL!=document.URL){
			        window.top.location= document.URL; 
			    }
			}
			var swiper = new Swiper('.top_banner', {
				loop: true,
				autoplay: {
					delay: 3000,
					stopOnLastSlide: false,
					disableOnInteraction: false,
				},
				pagination: {
					el: '.swiper-pagination',
				},
				navigation: {
					nextEl: '.swiper-button-next',
					prevEl: '.swiper-button-prev',
				},
			});
			
			$(function() {
				// 天气接口
				$.ajax({
					type: "GET",
					url: "<%=basePath%>web/index/getWeather",
					dataType:"json",
					success: function(data) {
						var dates = [];
						var temp = [];
						var weather = [];
						var direct = [];
						var result=data.result.future;
						for (var i=0;i<result.length;i++) {
							dates.push(result[i].date.substr(5, 2).concat('月') + result[i].date.substr(8, 2).concat('日'));
							temp.push(result[i].temperature);
							weather.push(result[i].weather);
							direct.push(result[i].direct);
						}
						console.log(dates,temp,weather,direct)
						for (var i = 0; i < 3; i++) {
							$('#date').append('<div class="directionC"><span class="week_info">' + dates[i] + '</span><span class="date_info">' + temp[i] +
								'</span></div>')
						}
						$('#weather').append('<div class="wea_item">' + dates[0].substr(dates[0].length - 6) +
							'</div><div class="wea_item">' + temp[0] + '</div><div class="wea_item">' + weather[0] +
							'</div><div class="wea_item">' + direct[0] + '</div>');
					},
					error: function(error) {
						console.log(error)
					}
				});
			})
			
			//打开最新资讯
			function openHomeInfoList(){
				window.open("<%=basePath%>web/index/homeInfo?page=1&limit=15");
			}
			
			//打开资讯详情
			function openHomeInfoDetail(id){
				window.open("<%=basePath%>web/index/homeInfoDetail?id=" + id);
			}
			
			//事故案例
			function openAccidentList(){
				window.open("<%=basePath%>web/index/accident?page=1&limit=15");
			}
			function openAccidentDetail(id){
				window.open("<%=basePath%>web/index/accidentDetail?id=" + id);
			}
			//教学课程
			function openLessionList(){
				window.open("<%=basePath%>web/index/lesson?page=1&limit=15");
			}
			function openLessionDetail(id){
				window.open("<%=basePath%>web/index/lessonDetail?id=" + id);
			}
			//专家介绍
			function openExpertList(){
				window.open("<%=basePath%>web/index/expertInfo?page=1&limit=15");
			}
			function openExpertDetail(id){
				window.open("<%=basePath%>web/index/expertInfoDetail?id=" + id);
			}
			
			//打开法律法规
			function openLawInfoList(){
				window.open("<%=basePath%>web/index/lawInfo?page=1&limit=15");
			}
			
			//打开法律法规
			function openLawInfoDetail(id){
				window.open("<%=basePath%>web/index/getLawInfoDetail?id=" + id);
			}
			
			//打开安全常识
			function openSafeInfoList(){
				window.open("<%=basePath%>web/index/safeInfo?page=1&limit=15");
			}
			
			//打开法律法规
			function openSafeInfoDetail(id){
				window.open("<%=basePath%>web/index/getSafeInfoDetail?id=" + id);
			}
		</script>
	</body>
</html>
