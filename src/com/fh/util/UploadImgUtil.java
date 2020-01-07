package com.fh.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.fh.simple.ImageSample;
import com.fh.simple.MultipartUploadSample;

public class UploadImgUtil {

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> uploadImg(File pic,
			boolean isMD5, HttpServletRequest req) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("/yyyy/MM/dd/");
		Date date = new Date();
		String dateStr = dateFormat.format(new Date());
		
		String uploadPath = "upload"+dateStr;
		
		String pic_name=DateHelper.getImageName();
		
		String filePath =ImageSample.imageUpload(pic, uploadPath+pic_name);
		resMap.put( "imgUrl", filePath);
		resMap.put( "code", 0);
		resMap.put( "msg", "图片上传成功");
		Map map=new HashMap();
		map.put("src", filePath);
		map.put("title", pic_name);
		resMap.put( "data", map);
		//uploadPath = "mobile/upload"+dateStr;
		//ImageUtil.picOutImage(uploadPath+pic_name, filePath,0.3F);
		
		resMap.put("flag", "1");
		return resMap;
	}

	public static boolean checkPic(String imgType) {
		imgType = imgType.toLowerCase();
		return ".jpg.bmp.png".contains(imgType);
	}
	
	
	
	
	
	
	@SuppressWarnings("rawtypess")
	public static Map<String, Object> uploadImgs(File pic,
			boolean isMD5, HttpServletRequest req, String suffix) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("/yyyy/MM/dd/");
		Date date = new Date();
		String dateStr = dateFormat.format(new Date());
		
		String uploadPath = "upload"+dateStr;
		
		String pic_name=DateHelper.getImageName();
		
		String filePath =ImageSample.imageUpload(pic, uploadPath+pic_name+suffix);
		resMap.put( "imgUrl", filePath);
		resMap.put( "url", filePath);
		resMap.put( "picName", pic_name);
		resMap.put( "code", 0);
		resMap.put( "error", 0);
		resMap.put( "error_code", 0);
		resMap.put( "msg", "图片上传成功");
		//Map map=new HashMap();
		//map.put("src", filePath);
		//map.put("title", pic_name);
		//resMap.put( "data", map);
		//uploadPath = "mobile/upload"+dateStr;
		//ImageUtil.picOutImage(uploadPath+pic_name, filePath,0.3F);
		
		resMap.put("flag", "1");
		return resMap;
	}
	
	@SuppressWarnings("rawtypess")
	public static Map<String, Object> uploadVideo(File video,
			boolean isMD5, HttpServletRequest req, String suffix) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("/yyyy/MM/dd/");
		Date date = new Date();
		String dateStr = dateFormat.format(new Date());
		
		String uploadPath = "upload"+dateStr;
		
		String pic_name=DateHelper.getImageName();
		
		String filePath = ImageSample.videoUpload(video, uploadPath+pic_name+suffix);
		resMap.put( "imgUrl", filePath);
		resMap.put( "url", filePath);
		resMap.put( "picName", pic_name);
		resMap.put( "code", 0);
		resMap.put( "error", 0);
		resMap.put( "error_code", 0);
		resMap.put( "msg", "图片上传成功");
		//Map map=new HashMap();
		//map.put("src", filePath);
		//map.put("title", pic_name);
		//resMap.put( "data", map);
		//uploadPath = "mobile/upload"+dateStr;
		//ImageUtil.picOutImage(uploadPath+pic_name, filePath,0.3F);
		
		resMap.put("flag", "1");
		return resMap;
	}
	
	@SuppressWarnings("rawtypess")
	public static Map<String, Object> uploadPartVideo(MultipartFile video,
			boolean isMD5, HttpServletRequest req, String suffix) throws IOException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("/yyyy/MM/dd/");
		Date date = new Date();
		String dateStr = dateFormat.format(new Date());
		
		String uploadPath = "upload"+dateStr;
		
		String path = video.getOriginalFilename();
		System.out.println("===========================path===" + path);
		
		String pic_name=DateHelper.getImageName();
		//String filePath = MultipartUploadSample.upLoad(path);
		String filePath = MultipartUploadSample.multipartVidesUpload(video, uploadPath+pic_name+suffix);  
		resMap.put( "imgUrl", filePath);
		resMap.put( "url", filePath);
		resMap.put( "picName", pic_name);
		resMap.put( "code", 0);
		resMap.put( "error", 0);
		resMap.put( "error_code", 0);
		resMap.put( "msg", "图片上传成功");
		//Map map=new HashMap();
		//map.put("src", filePath);
		//map.put("title", pic_name);
		//resMap.put( "data", map);
		//uploadPath = "mobile/upload"+dateStr;
		//ImageUtil.picOutImage(uploadPath+pic_name, filePath,0.3F);
		
		resMap.put("flag", "1");
		return resMap;
	}




}
