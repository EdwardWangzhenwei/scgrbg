package cn.com.huadi.aos.hdoa.dzwj.service;


public class GetFileContentType {

	 public static String getContentType(String dzwjm) {
	        String dzwjlx = null;
	        String dzwjkzm = dzwjm.substring(dzwjm.lastIndexOf(".")).toLowerCase();
	        if(".zip".equals(dzwjkzm) || ".rar".equals(dzwjkzm)){
	        	dzwjlx = "application/x-zip-compressed";
	        }else if(".doc".equals(dzwjkzm) || ".docx".equals(dzwjkzm)){
	        	dzwjlx = "application/msword";
	        }else if(".wps".equals(dzwjkzm)){
	        	dzwjlx = "application/kswps";
	        }else if(".xls".equals(dzwjkzm) || ".xlsx".equals(dzwjkzm)){
	        	dzwjlx = "application/vnd.ms-excel";
	        }else if(".et".equals(dzwjkzm)){
	        	dzwjlx = "application/kset";
	        }else if(".dps".equals(dzwjkzm)){
	        	dzwjlx = "application/ksdps";
	        }else if(".txt".equals(dzwjkzm)){
	        	dzwjlx = "text/plain";
	        }else if(".bmp".equals(dzwjkzm)){
	        	dzwjlx = "application/x-bmp";
	        }else if(".jpg".equals(dzwjkzm) || ".jpeg".equals(dzwjkzm)){
	        	dzwjlx = "image/jpeg";
	        }else if(".gif".equals(dzwjkzm)){
	        	dzwjlx = "image/gif";
	        }else if(".pdf".equals(dzwjkzm)){
	        	dzwjlx = "application/pdf";
	        }else {
	        	dzwjlx = "application/octet-stream";
	        }
	        return dzwjlx;
	    }
}
