package com.fh.controller.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.mobile.MobileInformationService;
import com.fh.service.mobile.MobileLoginService;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringHelper;
import com.fh.util.Tools;

/**
 * 描述：APP端信息接口
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Controller
@RequestMapping(value="/mobile/app")
public class MobileInformationController extends BaseController {
	
	@Autowired
	private MobileInformationService mobileInformationService;
	
	
	/**
	 * 描述：获取最新动态
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	@RequestMapping(value="/homepage/getBannerList")
	@ResponseBody
	public Map getBannerList(){
		return mobileInformationService.getBannerList();
	}
	
	/**
	 * 描述：获取资讯详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	@RequestMapping(value="/homepage/getBannerInfo")
	@ResponseBody
	public Map getBannerInfo(){
		int result = mobileInformationService.updateBannerReadNum();
		System.out.println("bannerUpdateReadResult : " + result);
		return mobileInformationService.getBannerInfo();
	}
	
	/**
	 * 描述：获取法律法规
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	@RequestMapping(value="/homepage/getLawList")
	@ResponseBody
	public Map  getLawList(){
		return mobileInformationService.getLawList();
	}
	
	
	/**
	 * 描述：获取法律法规详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	@RequestMapping(value="/homepage/getLawInfo")
	@ResponseBody
	public Map  getLawInfo(){
		int result = mobileInformationService.updateLawReadNum();
		System.out.println("lawUpdateReadResult : " + result);
		return mobileInformationService.getLawInfo();
	}
	
	/**
	 * 描述：获取事故案例列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	@RequestMapping(value="/homepage/getAccidentList")
	@ResponseBody
	public Map  getAccidentList(){
		return mobileInformationService.getAccidentList();
	}
	
	/**
	 * 描述：获取事故案例详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	@RequestMapping(value="/homepage/getAccidentInfo")
	@ResponseBody
	public Map  getAccidentInfo(){
		int result = mobileInformationService.updateAccidentReadNum();
		System.out.println("updateAccidentReadNum : " + result);
		return mobileInformationService.getAccidentInfo();
	}
	
	/**
	 * 描述：获取安全常识列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/homepage/getSafeList")
	@ResponseBody
	public Map  getSafeList(){
		return mobileInformationService.getSafeList();
	}
	
	
	/**
	 * 描述：获取安全常识详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/homepage/getSafeInfo")
	@ResponseBody
	public Map  getSafeInfo(){
		int result = mobileInformationService.updateSafeReadNum();
		System.out.println("updateAccidentReadNum : " + result);
		return mobileInformationService.getSafeInfo();
	}
	
	/**
	 * 描述：获取搜索资讯信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/homepage/getSearchInfoList")
	@ResponseBody
	public Map  getSearchInfoList(){
		return mobileInformationService.getSearchInfoList();
	}
	
	/**
	 * 描述：获取搜索列表资讯详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/homepage/getInfo")
	@ResponseBody
	public Map  getInfo(){
		int result = mobileInformationService.updateInfoReadNum();
		System.out.println("getInfo : " + result);
		return mobileInformationService.getInfo();
	}
	
	/**
	 * 描述：积分校验保存
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/addPoint")
	@ResponseBody
	public Map  addPoint(){
		int total_point = 0;
		int flag_exists = 0;
		int type = 0;
		Map pointResult = mobileInformationService.getPointsResult();
		System.out.println("===========================pointResult :  " + pointResult);
		if(pointResult != null &&  pointResult.size() > 0){
			String tp = StringHelper.get(pointResult, "total_point");
			String fe = StringHelper.get(pointResult, "flag_exists");
			String ty = StringHelper.get(pointResult, "type");
			if(tp == null) tp = "0";
			if(fe == null) fe = "0"; 
			if(ty == null) ty = "0";
			total_point = Integer.valueOf(tp);
			flag_exists = Integer.valueOf(fe);
			type = Integer.valueOf(ty);
		}
		if(flag_exists > 0)
			return ResultUtils.returnOk();
		if(type == 2 && total_point < 5 ){
			mobileInformationService.saveMypoints();
			return ResultUtils.returnOk();
		}
		if(type == 3 && total_point < 15){
			mobileInformationService.saveMypoints();
			return ResultUtils.returnOk();
		}
		if(type == 4 && total_point < 5){
			mobileInformationService.saveMypoints();
			return ResultUtils.returnOk();
		}
		return ResultUtils.returnOk();
	}
	
	/**
	 * 描述：个人信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/myInfo/myDetail")
	@ResponseBody
	public Map myDetail(){
		return mobileInformationService.getMyInfo();
	}
	
	/**
	 * 描述：获取我的课程
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/myInfo/getMyLessonList")
	@ResponseBody
	public Map  getMyLessonList(){
		return mobileInformationService.getMyLessonsList();
	}
	
	/**
	 * 描述：获取课程详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/myInfo/getVideoDetail")
	@ResponseBody
	public Map  getVideoDetail(){
		return mobileInformationService.getDetail();
	}
	
	
	/**
	 * 描述：建议反馈
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/myInfo/subProposal")
	@ResponseBody
	public Map  subProposal(){
		return mobileInformationService.subProposal();
	}
	
	/**
	 * 描述：我的证书
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/myInfo/myCertificate")
	@ResponseBody
	public Map myCertificate(){
		return mobileInformationService.getMyCertificateInfo();
	}
	
	/**
	 * 描述：获取我的消息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/myInfo/getMyMessageList")
	@ResponseBody
	public Map  getMyMessageList(){
		return mobileInformationService.getMyMessageList();
	}
	
	
	/**
	 * 描述：联系我们电话号码
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-30
	 */
	@RequestMapping(value="/myInfo/getPhone")
	@ResponseBody
	public Map  getPhone(){
		return mobileInformationService.getPhoneList();
	}
	
	/**
	 * 描述：修改密码
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/myInfo/updatePwd")
	@ResponseBody
	public Map updatePwd(){
		return mobileInformationService.updatePwd();
	}
	
	
	
}
	
 