/**
 * Created by Administrator on 2017/10/13.
 */

// 水流效果
var p1 = {
    inter: 0,
    count: 0,
    ele: ".ani_path_p1",
    animate: false
};

var e1 = {
    inter: 0,
    count: 0,
    ele: ".ani_path_e1",
    animate: false
};

var e2_booster_pump = {
    inter: 0,
    count: 0,
    ele: ".ani_path_e2_booster_pump",
    /*ctrl_1: $(".e2_ctrl"),
    $ctrl_2: $(".booster_pump_ctrl"),*/
    /*ctrl_1: false,
    ctrl_2: false,*/
    animate: false
};

var dur = 250, dasharray = 5;

function startAnimate(p) {
    /*if(p.ele == e2_booster_pump.ele) {
        if(!p.ctrl_1 || !p.ctrl_2) {
            return false;
        }
    }*/

    p.inter = setInterval(function () {
        if (p.count >= 20) {
           p.count = 0;
        }
        p.count += dasharray / 2;
        $(p.ele).css({
            "stroke-dasharray": dasharray,
            "stroke-dashoffset": p.count
        })
    }, dur);
}

function stopAnimate(p) {
    if (p.inter) {
        clearInterval(p.inter);
    }

    $(p.ele).css({
        "stroke-dasharray": 0,
        "stroke-dashoffset": 0
    });
}

/*$(function () {
    // P1 控制 水流
    $(document).on("click", "#sketch-map .p1_ctrl", function (e) {
    	var name = "P1";
    	var status ="1";
    	if(!p1.animate) {
    		changeStatus(status,name);
            startAnimate(p1);
            //发送一条信息，开启P1
        } else {
        	status ="0";
        	changeStatus(status,name);
            stopAnimate(p1);
            //发送一条信息，关闭P1
        }

        p1.animate = !p1.animate;
    });

    // E1 控制 水流
    $(document).on("click", "#sketch-map .e1_ctrl", function (e) {
    	var name = "E1";
    	var status ="1";
        if(!e1.animate) {
        	changeStatus(status,name);
            startAnimate(e1);
            //发送一条信息，开启E1
        } else {
        	status ="0";
        	changeStatus(status,name);
            stopAnimate(e1);
            //发送一条信息，关闭E1
        }
        e1.animate = !e1.animate;
    });

    //e2 点击
    $(document).on("click", "#sketch-map .e2_ctrl", function (e) {
    	var name = "E2";
    	var status ="1";
        if(!e2_booster_pump.animate) {
        	changeStatus(status,name);
            startAnimate(e2_booster_pump);
          //发送一条信息，开启E2
        } else {
        	status ="0";
        	changeStatus(status,name);
            stopAnimate(e2_booster_pump);
          //发送一条信息，关闭E2
        }
        e2_booster_pump.animate = !e2_booster_pump.animate;

        e2_booster_pump.ctrl_1 = !e2_booster_pump.ctrl_1;

        if(e2_booster_pump.ctrl_1 && e2_booster_pump.ctrl_2) {
            startAnimate(e2_booster_pump);
        } else {
            stopAnimate(e2_booster_pump);
        }
    });

    //e2 增压泵 点击
    $(document).on("click", "#sketch-map .booster_pump_ctrl", function (e) {
        e2_booster_pump.ctrl_2 = !e2_booster_pump.ctrl_2;

        if(e2_booster_pump.ctrl_1 && e2_booster_pump.ctrl_2) {
            startAnimate(e2_booster_pump);
        } else {
            stopAnimate(e2_booster_pump);
        }
    });
})*/

//startAnimate(e1);
//初始化原理图的P1/E1/E2
function initStatus(){
	var e1Status = $("#device1").val();
	var e2Status = $("#device2").val();
	var p1Status = $("#device3").val();
	//00是关闭，01是开启
	if(e1Status=="01"){
		startAnimate(e1);
		e1.animate = !e1.animate;
	}
	//0是关闭，1是开启
	if(e2Status=="01"){
		startAnimate(e2_booster_pump);
		e2_booster_pump.animate = !e2_booster_pump.animate;
	}
	//0是关闭，1是开启
	if(p1Status =="01"){
		startAnimate(p1);
		p1.animate = !p1.animate;
	}
}
