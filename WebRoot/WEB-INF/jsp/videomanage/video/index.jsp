<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/static/layuiadmin/include.jsp" %>
	<title>视频上传</title>
	<link rel="stylesheet" type="text/css" href="oss-h5-upload-js-direct/style.css"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
</head>
<body>
<br>
<form name=theform>
</form>
<h4>您所选择的文件：</h4>
<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>

<br/>

<div id="container">
	<a id="selectfiles" href="javascript:void(0);" class='btn'>选择视频</a>
	<a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
</div>

<pre id="console"></pre>

</body>
<script type="text/javascript" src="oss-h5-upload-js-direct/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="oss-h5-upload-js-direct/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="oss-h5-upload-js-direct/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="oss-h5-upload-js-direct/lib/base64.js"></script>
<script type="text/javascript" src="oss-h5-upload-js-direct/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="oss-h5-upload-js-direct/upload.js"></script>
<script>
	var layer;
	layui.config({
		base : 'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus','layer' ], function() {
		var $ = layui.$, 
		form = layui.form, 
		webplus = layui.webplus;
		layer = layui.layer;
	});
	function zTreeOnClick(path) {
	    $("#video", parent.document).val(path);
	    $("#videoSrc", parent.document).attr("src", path);
        parent.layer.closeAll();
	};
	
</script>

</html>


