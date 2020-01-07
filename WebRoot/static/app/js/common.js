/* 创建 滑动 */
function createBxSlider() {
	var $slider = $(".bxslider");
	var option = {
		auto: false,
		infiniteLoop: false,
		controls: true,
		autoHover: false,
		pager: false,
		nextText: "",
		prevText: "",
		startSlide: 1, //当前 第几个
		slideMargin: 0,
		minSlides: 1,
		moveSlides: 1
	};

	$slider.bxSlider(option);
}
var reg = {
	phone: /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/,				/* 手机号码  */
	NumericLetters: /^[a-zA-Z0-9]+$/,				/* 数字字幕  */
	ChineseChars: /^[\u4E00-\u9FFF]+$/,				/* 汉字 */
	digit: /^.{2,18}$/,				/*   */
	pwd: /^[a-zA-Z0-9]{6,20}$/,				/* 密码  */
	email: /\w@\w*\.\w/,
	code: /^[0-9]{6}$/				/* 6位数字  */
}

//打开layer窗口
function openLayerWindowWH(title, url,width,height){
	layer.open({
        type : 2,
        title : title,
        skin: 'layer-open-window-my',
        offset: '5%',
        shadeClose : true,
        shade : 0.5,
        area : [ width, height ],
        content : url //iframe的url
	}); 
}

function openLayerWindow(title, url){
	openLayerWindowWH(title, url,'90%','70%')
}
//按esc关闭弹出层
$(document).keyup(function (e) {
    var key = e.which;
    if (key == 27) {
        if (layer) {
            layer.closeAll();
        }
    }
})


//根据pid获取字典数据 显示到showId
function getCodePidData(pid, showId, defaultId){
	getCodePidData(pid, showId, defaultId, undefined)
}
function getCodePidData(pid, showId, defaultId, form){
	var str = "<option value=''>请选择</option>";
	$("#"+showId).html(str);
	if (pid==""){
		if(form!=undefined)
		form.render('select'); //刷新select选择框渲染
	} else {
		$.ajax({
			url : '/manage/getCodePidList.do',
			data : {"pid": pid},
			type : 'get',
			async : true,
			dataType : 'json',
			success : function(data) {
				if (data.flag == '1') {
					$.each(data.data, function (i, item){
//					console.log(item);
						str += "<option value='"+item.id+"'>"+item.name+"</option>";
					});
					$("#"+showId).html(str);
					$("#"+showId).val(defaultId);
					if(form!=undefined)
					form.render('select'); //刷新select选择框渲染
				}
			},
			error : function() {
				
			}
		});
	}
}

//根据pid获取字典数据 显示到showId value 显示remark
function getCodePidData2(pid, showId, defaultId){
	getCodePidData2(pid, showId, defaultId, undefined)
}
function getCodePidData2(pid, showId, defaultId, form){
	var str = "<option value=''>请选择</option>";
	$("#"+showId).html(str);
	if (pid==""){
		if(form!=undefined){
			form.render('select'); //刷新select选择框渲染
			$('#'+showId).parent().find('input').removeAttr("readonly");
		}
	} else {
		$.ajax({
			url : '/manage/getCodePidList.do',
			data : {"pid": pid},
			type : 'get',
			async : true,
			dataType : 'json',
			success : function(data) {
				if (data.flag == '1') {
					$.each(data.data, function (i, item){
//					console.log(item);
						str += "<option value='"+item.remark+"'>"+item.name+"</option>";
					});
					$("#"+showId).html(str);
					$("#"+showId).val(defaultId);
					if(form!=undefined){
						form.render('select'); //刷新select选择框渲染
					}
				}
			},
			error : function() {
				
			}
		});
	}
}


//根据parentId获取地址数据 显示到showId form(layui form)
function getPlaceList(parentId,showId, defaultId){
	getPlaceList(parentId,showId, defaultId, undefined)
}
function getPlaceList(parentId,showId, defaultId, form){
	var str = "<option value=''>请选择</option>";
	$("#"+showId).html(str);
	if (parentId==""){
		if(form!=undefined)
		form.render('select'); //刷新select选择框渲染
	} else {
		$.ajax({
			url : '/manage/getPlaceList.do',
			data : {"parentId": parentId},
			type : 'get',
			async : true,
			dataType : 'json',
			success : function(data) {
				if (data.flag == '1') {
					$.each(data.data, function (i, item){
//					console.log(item);
						str += "<option value='"+item.id+"'>"+item.name+"</option>";
					});
					$("#"+showId).html(str);
					$("#"+showId).val(defaultId);
					if(form!=undefined)
					form.render('select'); //刷新select选择框渲染
				}
			},
			error : function() {
				
			}
		});
	}
}

//根据cityId，areaId获取商圈数据 显示到showId form(layui form)
function getBasicTradeAreaList(cityId, areaId, showId, defaultId){
	getBasicTradeAreaList(cityId, areaId, showId, defaultId, undefined);
}
function getBasicTradeAreaList(cityId, areaId, showId, defaultId, form){
	var str = "<option value=''>请选择</option>";
	$("#"+showId).html(str);
	if (cityId==""){
		if(form!=undefined)
		form.render('select'); //刷新select选择框渲染
	} else {
		$.ajax({
			url : '/manage/getBasicTradeAreaList.do',
			data : {"sysPlaceInfoByCityId.id": cityId, "sysPlaceInfoByAreaId.id": areaId, "pageSize":500},
			type : 'get',
			async : true,
			dataType : 'json',
			success : function(data) {
				if (data.flag == '1') {
					$.each(data.data, function (i, item){
//					console.log(item);
						str += "<option value='"+item.id+"'>"+item.name+"</option>";
					});
					$("#"+showId).html(str);
					$("#"+showId).val(defaultId);
					if(form!=undefined)
					form.render('select'); //刷新select选择框渲染
				}
			},
			error : function() {
				
			}
		});
	}
}
//根据cityId，areaId获取小区数据 显示到showId form(layui form)
function getBasicVillageList(cityId, areaId, tradeAreaId, showId, defaultId){
	getBasicVillageList(cityId, areaId, tradeAreaId, showId, defaultId, undefined);
}
function getBasicVillageList(cityId, areaId, tradeAreaId, showId, defaultId, form){
	var str = "<option value=''>请选择</option>";
	$("#"+showId).html(str);
	if (cityId==""){
		if(form!=undefined)
		form.render('select'); //刷新select选择框渲染
	} else {
		$.ajax({
			url : '/manage/getBasicVillageList.do',
			data : {"sysPlaceInfoByCityId.id": cityId, "sysPlaceInfoByAreaId.id": areaId, "basicTradeArea.id": tradeAreaId, "pageSize":500},
			type : 'get',
			async : true,
			dataType : 'json',
			success : function(data) {
				if (data.flag == '1') {
					$.each(data.data, function (i, item){
//					console.log(item);
						str += "<option value='"+item.id+"'>"+item.name+"</option>";
					});
					$("#"+showId).html(str);
					$("#"+showId).val(defaultId);
					if(form!=undefined)
					form.render('select'); //刷新select选择框渲染
				}
			},
			error : function() {
				
			}
		});
	}
}
//根据cityId获取地铁数据 显示到showId form(layui form)
function getBasicMetroList(cityId, showId, defaultId){
	getBasicMetroList(cityId, showId, defaultId, undefined);
}
function getBasicMetroList(cityId, showId, defaultId, form){
	var str = "<option value=''>请选择</option>";
	$("#"+showId).html(str);
	if (cityId==""){
		if(form!=undefined)
		form.render('select'); //刷新select选择框渲染
	} else {
		$.ajax({
			url : '/manage/getBasicMetroInfoList.do',
			data : {"sysPlaceInfoByCityId.id": cityId},
			type : 'get',
			async : true,
			dataType : 'json',
			success : function(data) {
				if (data.flag == '1') {
					$.each(data.data, function (i, item){
//					console.log(item);
						str += "<option value='"+item.id+"'>"+item.name+"</option>";
					});
					$("#"+showId).html(str);
					$("#"+showId).val(defaultId);
					if(form!=undefined)
					form.render('select'); //刷新select选择框渲染
				}
			},
			error : function() {
				
			}
		});
	}
}

//格式化日期
function formatYMDHMS(timestamp)
{
  //timestamp是整数，否则要parseInt转换,不会出现少个0的情况
	var time = new Date(timestamp);
	var year = time.getFullYear();
	var month = time.getMonth()+1;
	var date = time.getDate();
	var hours = time.getHours();
	var minutes = time.getMinutes();
	var seconds = time.getSeconds();
	return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes)+':'+add0(seconds);
}
function formatYMD(timestamp)
{
	//timestamp是整数，否则要parseInt转换,不会出现少个0的情况
	var time = new Date(timestamp);
	var year = time.getFullYear();
	var month = time.getMonth()+1;
	var date = time.getDate();
	return year+'-'+add0(month)+'-'+add0(date);
}
function formatYMDHM(timestamp)
{
	//timestamp是整数，否则要parseInt转换,不会出现少个0的情况
	var time = new Date(timestamp);
	var year = time.getFullYear();
	var month = time.getMonth()+1;
	var date = time.getDate();
	var hours = time.getHours();
	var minutes = time.getMinutes();
	var seconds = time.getSeconds();
	return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes);
}

function daojishi(intDiff){
	
	/*var now=new Date();
	var end=new Date(2018,7,18,12,18,00);//结束的时间：年，月，日，分，秒（月的索引是0~11）
	两个时间相减,得到的是毫秒ms,变成秒
	var result=Math.floor(end-now)/1000;
	*/
    window.setInterval(function(){
    var day=0,
        hour=0,
        minute=0,
        second=0;//时间默认值        
    if(intDiff > 0){
        day = Math.floor(intDiff / (60 * 60 * 24));
        hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
        minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
        second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
    }
    if (minute <= 9) minute = '0' + minute;
    if (second <= 9) second = '0' + second;
    //$('#day_show').html(day+"天");
    $('#hour_show').html('<s id="h"></s>'+hour+'时');
    $('#minute_show').html('<s></s>'+minute+'分');
    $('#second_show').html('<s></s>'+second+'秒');
    intDiff--;
    }, 1000);
}

function add0(m){return m<10?'0'+m:m }

$(function() {
	$(".btn-return").click(function(){
		window.history.go(-1);
	});
})

function isHidden(oDiv){
    var vDiv = document.getElementById(oDiv);
    vDiv.style.display = (vDiv.style.display == 'none')?'block':'none';
}