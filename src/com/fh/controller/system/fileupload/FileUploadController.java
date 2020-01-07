package com.fh.controller.system.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.controller.base.BaseController;
import com.fh.util.UploadImgUtil;
import com.fh.util.alibaba.MultipartUploadSample;


@Controller
@RequestMapping("fileUpload")
public class FileUploadController extends BaseController{


	/**
	 * 图片上传
	 * 
	 * @return
	 */
	@CrossOrigin
	@ResponseBody
	@RequestMapping("upload")
	public Map<String,Object> upload(HttpServletRequest req,@RequestParam MultipartFile file) throws IOException {
		//上传到阿里云OSS
		File sampleFile=null;
		if(file.getSize()>10000){
			CommonsMultipartFile cf= (CommonsMultipartFile)file; 
			DiskFileItem diskFileItem = (DiskFileItem)cf.getFileItem(); 
			sampleFile = diskFileItem.getStoreLocation();
		}else{
			String path=req.getSession().getServletContext().getRealPath("/upload");
			sampleFile=new File(path+file.getOriginalFilename());
			file.transferTo(sampleFile);
		}
		String oldName=file.getOriginalFilename();
		String suffix=oldName.substring(oldName.lastIndexOf("."));
	    Map map=UploadImgUtil.uploadImgs(sampleFile, true, req,suffix);		
		//上传到本地
		
	
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//		String dateStr = dateFormat.format(new Date());
//		String dateUrl =dateStr.split("-")[0]+"/"+dateStr.split("-")[1]+"/"+dateStr.split("-")[2]+"/";
//		logger.debug(dateUrl);
//		String upload_path=PropertiesHelper.getPropertiesValue("upload_path");
//		String path = upload_path+dateUrl;
//		String originalFileName=file.getOriginalFilename();
//		String fileName=DateHelper.getImageName()+originalFileName.substring(originalFileName.indexOf("."));
//		File uploadFile = new File(path,fileName); // 判断文件夹是否存在,如果不存在则创建文件夹
//		if (!uploadFile.exists()) {
//			uploadFile.mkdirs();
//		}
//		file.transferTo(uploadFile);
//		logger.debug("/upload/"+dateUrl+fileName);	
//	
//		Map<String, Object> map = new HashMap<String, Object>();		
//		map.put("flag", 1);
//		map.put("imgUrl", dateUrl+fileName);
//		
//		map.put( "code", 0);
//		map.put( "msg", "图片上传成功");
		return map;
	}
	
	/**
	 * 描述：视频上传
	 * @param req
	 * @param file
	 * @return
	 * @throws IOException
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@CrossOrigin
	@ResponseBody
	@RequestMapping("uploadVideo")
	public Map<String,Object> uploadVideo(HttpServletRequest req,@RequestParam MultipartFile file) throws IOException {
		//上传到阿里云OSS
		File sampleFile=null;
		if(file.getSize()>10000){
			CommonsMultipartFile cf= (CommonsMultipartFile)file; 
			DiskFileItem diskFileItem = (DiskFileItem)cf.getFileItem(); 
			sampleFile = diskFileItem.getStoreLocation();
		}else{
			String path=req.getSession().getServletContext().getRealPath("/upload");
			sampleFile=new File(path+file.getOriginalFilename());
			file.transferTo(sampleFile);
		}
		String oldName=file.getOriginalFilename();
		String suffix=oldName.substring(oldName.lastIndexOf("."));
	    Map map=UploadImgUtil.uploadVideo(sampleFile, true, req,suffix);		
		
		return map;
	}
	
	/**
	 * 描述：视频分片上传
	 * @param req
	 * @param file
	 * @return
	 * @throws IOException
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@CrossOrigin
	@ResponseBody
	@RequestMapping("uploadPartVideo")
	public Map<String,Object> uploadPartVideo(HttpServletRequest req,@RequestParam MultipartFile file) throws IOException {
		String oldName=file.getOriginalFilename();
		String suffix=oldName.substring(oldName.lastIndexOf("."));
	    Map map=UploadImgUtil.uploadPartVideo(file, true, req,suffix);
		 //MultipartUploadSample.uploadVideo(file);
		return null;
	}
	
}
