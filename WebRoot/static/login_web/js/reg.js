$(document).ready(function(){	
	pageSize();	
	$(window).resize(function(){
		pageSize();		
	});
	
})
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
function pageSize(){
	if ($(window).width()<=1000){	
			$(".container").css("width","1000px");
		}else if($(window).width()>=1920){
			$(".container").css("width","1920px");		
		}else{	
			$(".container").css("width",($(window).width())+"px");	
		}
	}
