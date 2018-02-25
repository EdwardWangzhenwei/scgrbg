package cn.com.huadi.aos.hdoa.common.util;



import java.io.UnsupportedEncodingException;

public class StringUtil {
	/** * 获取字符串的长度，如果有中文，则每个中文字符计为2位 * @param value 指定的字符串 * @return 字符串的长度 */
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]"; /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) { /* 获取一个字符 */
			String temp = value.substring(i, i + 1); /* 判断是否为中文字符 */
			if (temp.matches(chinese)) { /* 中文字符长度为2 */
				valueLength += 2;
			} else { /* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}
	public static String subStr(String str, int subSLength)    
	               throws UnsupportedEncodingException{   
	           if (str == null)    
	              return "";    
	         else{   
	               int tempSubLength = subSLength;//截取字节数  
	              String subStr = str.substring(0, str.length()<subSLength ? str.length() : subSLength);//截取的子串    
	              int subStrByetsL = subStr.getBytes("GBK").length;//截取子串的字节长度   
	               //int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度   
	                             // 说明截取的字符串中包含有汉字    
	              while (subStrByetsL > tempSubLength){    
	                int subSLengthTemp = --subSLength;  
	                  subStr = str.substring(0, subSLengthTemp>str.length() ? str.length() : subSLengthTemp);    
	                  subStrByetsL = subStr.getBytes("GBK").length;  
	                  //subStrByetsL = subStr.getBytes().length;  
	              }    
	              return subStr;   
	           }  
       } 
	//截取字符串长度(中文2个字节，半个中文显示一个)  
	public static String subTextString(String str,int len){  
	   if(str.length()<len/2)return str;  
	    int count = 0;  
	    StringBuffer sb = new StringBuffer();  
	    String[] ss = str.split("");  
	   for(int i=1;i<ss.length;i++){  
	        count+=ss[i].getBytes().length>1?2:1;  
	       sb.append(ss[i]);  
	       if(count>=len)break;  
	   }  
	    //不需要显示...的可以直接return sb.toString();  
	    return (sb.toString().length()<str.length())?sb.append("...").toString():str;  
	}  
	public static String convertHtmlString(String data){
		  data = data.replace("&","&amp;");
	      data =  data.replace("<", "&lt;");
	      data = data.replace(">", "&gt;");
	    data = data.replace("\n", "<br/>");
	   // data = data.replace("\"", "\\\"");
	    return data;
	}
	
	/**
	 * 拆分字符串
	 * @param source
	 * @param div
	 * @return
	 */
    public static String[] split(String source, String div)
    {
        int arynum = 0;
        int intIdx = 0;
        int intIdex = 0;
        int div_length = div.length();
        if(source.compareTo("") != 0)
        {
            if(source.indexOf(div) != -1)
            {
                intIdx = source.indexOf(div);
                int intCount = 1;
                do
                {
                    if(source.indexOf(div, intIdx + div_length) != -1)
                    {
                        intIdx = source.indexOf(div, intIdx + div_length);
                        arynum = intCount;
                    } else
                    {
                        arynum += 2;
                        break;
                    }
                    intCount++;
                } while(true);
            } else
            {
                arynum = 1;
            }
        } else
        {
            arynum = 0;
        }
        intIdx = 0;
        intIdex = 0;
        String returnStr[] = new String[arynum];
        if(source.compareTo("") != 0)
        {
            if(source.indexOf(div) != -1)
            {
                intIdx = source.indexOf(div);
                returnStr[0] = source.substring(0, intIdx);
                int intCount = 1;
                do
                {
                    if(source.indexOf(div, intIdx + div_length) != -1)
                    {
                        intIdex = source.indexOf(div, intIdx + div_length);
                        returnStr[intCount] = source.substring(intIdx + div_length, intIdex);
                        intIdx = source.indexOf(div, intIdx + div_length);
                    } else
                    {
                        returnStr[intCount] = source.substring(intIdx + div_length, source.length());
                        break;
                    }
                    intCount++;
                } while(true);
            } else
            {
                returnStr[0] = source.substring(0, source.length());
                return returnStr;
            }
        } else
        {
            return returnStr;
        }
        return returnStr;
    }
    /**
     * 得到两个字符串相同的子串
     * @param first
     * @param second
     * @return
     */
	 public static String longestCommonSubstring(String first, String second) {
		 String tmp = "";
		 String max = "";
		 for (int i=0; i < first.length(); i++){
			 for (int j = 0; j < second.length(); j++){
				 for (int k = 1; (k+i) <= first.length() && (k+j) <= second.length(); k++){
					 if (first.substring(i, k + i).equals(second.substring(j, k + j))){
						 tmp = first.substring(i, k + i);
					 }
					 else{
						 if (tmp.length() > max.length())
							 max = tmp;
						 tmp = "";
					 }
				 }
				 if (tmp.length() > max.length())
					 max = tmp;
				 tmp = "";
			 }
		 }
		 return max;       
	 }
	 /**
	  * 得到两个字符串相同的子串,如果有相同子串返回true,否则返回false
	  * 根据业务需求，至少匹配两个字符
	  * @param first
	  * @param second
	  * @return
	  */
	 public static boolean longestCommonSubstring2(String first, String second) {
		 String tmp = "";
		 String max = "";
		 for (int i=0; i < first.length(); i++){
			 for (int j = 0; j < second.length(); j++){
				 for (int k = 1; (k+i) <= first.length() && (k+j) <= second.length(); k++){
					 if (first.substring(i, k + i).equals(second.substring(j, k + j))){
						 tmp = first.substring(i, k + i);
					 }
					 else{
						 if (tmp.length() > max.length())
							 max = tmp;
						 tmp = "";
					 }
				 }
				 if (tmp.length() > max.length())
					 max = tmp;
				 tmp = "";
			 }
		 }
		 if("".equals(max)||(!"".equals(max)&&max.length()<2)||"意见".equals(max)||"领导".equals(max)){
			 return false;
		 }else{
			 return true;
		 }
	 }
}
