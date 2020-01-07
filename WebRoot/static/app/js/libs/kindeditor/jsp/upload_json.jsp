<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.springframework.web.multipart.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="com.pro.utils.PropertiesHelper" %>
<%@ page import="com.pro.tfs.TFSUtil" %>
<%@ page import="com.pro.tfs.UpLoadUtil6" %>

<%
/**
 * KindEditor JSP
 * 
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 * 
 */
	

	try{
	//定义允许上传的文件扩展名
	HashMap<String, String> extMap = new HashMap<String, String>();
	extMap.put("image", "gif,jpg,jpeg,png,bmp");
	extMap.put("flash", "swf,flv");
	extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
	extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

	//最大文件大小
	long maxSize = 1000000;

	response.setContentType("text/html; charset=UTF-8");

	if(!ServletFileUpload.isMultipartContent(request)){
		//out.println(getError("请选择文件。"));
		return;
	}


	String dirName = request.getParameter("dir");
	if (dirName == null) {
		dirName = "image";
	}

	FileItemFactory factory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setHeaderEncoding("UTF-8");
	List items = upload.parseRequest(request);
	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	Iterator item = multipartRequest.getFileNames();
	Iterator itr = items.iterator();
	
	while (item.hasNext()) {
		String fileName = (String) item.next();

     	MultipartFile file = multipartRequest.getFile(fileName);
     
	     if (file.getSize() > maxSize) {
	         out.println(getError("上传文件大小超过限制。"));
	         return;
	     }
	   //检查扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
				out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return;
			}
			
			String tfsFileName="http://118.144.87.71/v1/tfs/"+UpLoadUtil6.uploadMethod6(file);
			
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", tfsFileName);
			out.println(obj.toJSONString());
		}
	
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	

%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>