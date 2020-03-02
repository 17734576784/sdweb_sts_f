<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="gbk"%>
<%@page import="com.kesd.common.WebConfig"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>文件下载</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	

  </head>
  
  <body>
    <%
    FileInputStream in = null;
    try{
    	String fileName = request.getParameter("fileName");
    	if (fileName != null) {
    		String rpath = WebConfig.real_basepath + "\\download\\" + fileName;
    		
    		File fileLoad = new File(rpath);
    		if (fileLoad.exists()) {
    			OutputStream  o = response.getOutputStream();
    			byte b[] = new byte[500];
    			response.reset();
    			response.setContentType("application/rar");
    			response.setHeader("content-disposition","attachment; filename=" + fileName);
    			long fileLength = fileLoad.length();
    			String length1 = String.valueOf(fileLength);
    			response.setHeader("Content_Length", length1);
    			in = new FileInputStream(fileLoad);
    			int n;
    			while ((n = in.read(b)) != -1) {
    				o.write(b, 0, n);
    			}
    			in.close();
    			out.clear();
    			out = pageContext.pushBody();
    		} else {
    			
    			out.println("文件不存在！");
    		}
    	}
    }catch(Exception e){
    	in.close();
    	out.clear();
    	out = pageContext.pushBody();
    }
    %>
  </body>
</html>