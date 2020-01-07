$(function() {
	$(".hd-back").on('click',function(){
		history.go(-1);
	});
	$('.ks-tab > ul > li').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$('.ks-list').css('display','none').eq($('.ks-tab > ul > li').index(this)).css('display','block');
	});
	var nums=$('.kaoti-hl-items li').length;
	var yushu=nums%4;
	for(var i=1; i<=yushu; i++){
		$('.kaoti-hl-items > ul > li:nth-last-of-type('+i+')').css('border-bottom','0')
	}
	$('.kta-item > ul > li').on('click',function(){
		if($(this).hasClass('on')){
			$(this).removeClass('on');
		}else {
			$(this).addClass('on');
		}
//		$(this).addClass('on').siblings().removeClass('on');
		
		$('.kta-item > input').val($(this).find('span').text());
	});
	$('.kta-mult-item > ul > li').on('click',function(){
		var val=$('.kta-mult-item > input').val();
		if($(this).hasClass('on')){
			$(this).removeClass('on');
			var selVal=$(this).find('span').text()+',';
			val=val.replace(selVal,'');
			$('.kta-mult-item > input').val(val);
		}else{
			$(this).addClass('on');
			$('.kta-mult-item > input').val(val+$(this).find('span').text()+',');
		}
	});
	
	$('.cy-tab > ul > li').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$('.cy-list').css('display','none').eq($('.cy-tab > ul > li').index(this)).css('display','block');
	});
	
	$('.dy-tab > ul > li').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$('.dy-item').css('display','none').eq($('.dy-tab > ul > li').index(this)).css('display','block');
	});
	$('.month-list > ul > li').on('click',function(){
		//var val=$('.kta-mult-item > input').val();
		if($(this).hasClass('on')){
			$(this).removeClass('on');
		}else{
			$(this).addClass('on');
		}
	});
	$('.dyjf-form-btn').on('click',function(){
		$('.float,.float > .online').css('display','block');
	})
	$('.month-submit').on('click',function(){
		$('.float,.float > .online').css('display','none');
	})
	$('.dyjf-tab > ul > li').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$('.dyjf-cont').css('display','none').eq($('.dyjf-tab > ul > li').index(this)).css('display','block');
	});
	$('.ms-tab > ul > li').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$('.ms-item').css('display','none').eq($('.ms-tab > ul > li').index(this)).css('display','block');
	});
	$('.forum-tab > ul > li').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		//$('.forum-cont').css('display','none').eq($('.forum-tab > ul > li').index(this)).css('display','block');
	});
	$('.center-tab > ul > li').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$('.center-cont').css('display','none').eq($('.center-tab > ul > li').index(this)).css('display','block');
	});
	window.onscroll=function(){
		var docscrollt=$(document).scrollTop();
		//alert(docscrollt);
		if(docscrollt>50){
			$('.header,.dk-header,.center-header').addClass('scrolltop');
			
		}else{
			$('.header,.dk-header,.center-header').removeClass('scrolltop');
		}
	};
	function resize(){
		var windowWidth=$(window).width();		
		var isSmallScreen=windowWidth<=480;
		/*$('.swiper-wrapper > .swiper-slide').each(function(i,item){
			var $item=$(item);			
			var imgSrc=$item.data(isSmallScreen? 'image-xs' : 'image-lg');
			$item.find("img").attr("src",imgSrc);
		})*/
		
	}
	$(window).on('resize',resize).trigger('resize');
	
})
function client(){
	if(window.innerWidth!=null){
		return{width:window.innerWidth,height:window.innerHeight}
	}
	else if(document.compatMode=="CSS1Compat"){
		return{width: document.documentElement.clientWidth,height:document.documentElement.clientHeight}
	}
	return{ width: document.body.clientWidth, height: document.body.clientHeight}
}
function clearDefault(el)
{
  if (el.defaultValue==el.value) el.value = "";
}
function resetDefault(el)
{
  if (isEmpty(el.value)) el.value=el.defaultValue;
}

function isEmpty(s) 
{
  if (s == null || s=="undefined" || s.length == 0)
    return true;
  return !/\S/.test(s);
}