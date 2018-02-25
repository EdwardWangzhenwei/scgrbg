/*******************************************************************************
 * 文件名：SessionTimeoutFilter.java
 *
 * Copyright 2007 HD Co.[www.huadi.com.cn]
 *
 * 作者：Huadi
 *
 * 创建日期：2007-09-11
 *
 * 说明：主要设计类为SessionTimeoutFilter
 ******************************************************************************/
package cn.com.huadi.aos.hdoa.common.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;

import cn.com.huadi.aos.hdoa.common.util.DESUtil;
import cn.com.huadi.aos.hdoa.login.service.LoginService;

import com.aisino.platform.exception.BusinessException;
import com.aisino.platform.util.SessUtil;


/**
 * 该类为session过滤器，判断有没有session和有没有登陆系统
 *
 * @author Huadi
 *
 * @version 1.0 2007-09-11
 */
public class SessionTimeoutFilter implements Filter {

	/**
	 * 过滤配置
	 */
	private FilterConfig filterConfig;
	private static SessionTimeoutFilter instance;
	public static SessionTimeoutFilter getIntance(){
		return instance;
	}
	/**
	 * 过滤器是否有效
	 */
	private String enable;

	/**
	 * 出现没有session或没有登陆系统时，跳转页面url
	 */
	private String redirectURL;

	/**
	 * 过滤排除文件，以"#"分割
	 */
	private String exceptFiles;

	/**
	 * 登陆后用户信息在session中的标识常量
	 */
	private String userSession;
	/**
	 * 过滤排除文件类型，以"#"分割
	 */
	private String exceptFileTypes;
	/**
	 * 初始化参数
	 */
	public SessionTimeoutFilter() {
		filterConfig = null;
		enable = null;
		redirectURL = null;
		exceptFiles = null;
		exceptFileTypes = null;
		userSession = null;
		instance=this;
	}




	/**
	 * 检查有没有session和有没有登陆系统，正常返回true，否则false
	 *
	 * @param request 页面请求
	 *
	 * @return 是否正常
	 * @throws UnsupportedEncodingException 
	 */
	private boolean checkSession(HttpServletRequest request)  {
		String username = request.getRemoteUser();
		
		/*AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
		String username = principal.getName();*/
		if(username!=null&&request.getSession().getAttribute("USERINFO")==null){
			try {
				username=new String(username.getBytes(),"UTF-8");
			} catch (UnsupportedEncodingException e1) {
			}
			Map resultInfo = null;
			Map userInfo=null;
			try {
				//密码解密
//				userpwd = DESUtil.encrypt(userpwd);
				// 按登录账号名密码
				resultInfo = this.getLoginService().queryUserByAccountName(
						username);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BusinessException("数据库操作错误，请联络系统管理员!");
			}
			if (resultInfo != null) {
				if (resultInfo.get("result").toString().equals("ok")) {
					// 获得用户信息,保存在session中
					userInfo = (Map) resultInfo.get("data");
					int unit_flag = Integer.parseInt(String.valueOf(userInfo
							.get("unit_flag")));
					// 当前登录人直属部门ID和部门名称
					/*userInfo.put("dept_id", userInfo.get("unit_id"));
					userInfo.put("dept_name", userInfo.get("unit_name"));*/
					int id=	Integer.parseInt(userInfo.get("unit_id").toString());
					
					Map upUnit = this.getLoginService().queryUnitByDeptid(id);
					if (upUnit != null) {
						userInfo.put("unit_id", upUnit.get("unit_id"));
						userInfo.put("unit_name", upUnit.get("unit_name"));
						userInfo.put("unit_name_short", upUnit.get("unit_name_short"));
						//在用户登录信息中添加单位统一社会信用代码、内设机构代码--20160407 lwf---start
						userInfo.put("credit_code", upUnit.get("credit_code"));
						userInfo.put("subunit_code", upUnit.get("subunit_code"));						
						//在用户登录信息中添加单位统一社会信用代码、内设机构代码--20160407 lwf---end
						userInfo.put("unit_jc", upUnit.get("unit_jc"));//单位简称
					}else{
						return false;
						
					}
					upUnit = this.getLoginService().queryDeptByDeptid(id);
					if (upUnit != null) {
						userInfo.put("dept_id", upUnit.get("unit_id"));
						userInfo.put("dept_name", upUnit.get("unit_name"));
						userInfo.put("dept_name_short", upUnit.get("unit_name_short"));
						userInfo.put("dept_jc", upUnit.get("unit_jc"));//部门简称
					}
					else{
						userInfo.put("dept_id",userInfo.get("unit_id"));
						userInfo.put("dept_name",userInfo.get("unit_name"));
						userInfo.put("dept_name_short",userInfo.get("unit_name_short"));
						userInfo.put("dept_jc",userInfo.get("unit_jc"));
					}
					
					 upUnit = this.getLoginService().querySelfByDeptid(id);
					if (upUnit != null) {
						userInfo.put("group_id", upUnit.get("unit_id"));
						userInfo.put("group_name", upUnit.get("unit_name"));
						userInfo.put("group_name_short", upUnit.get("unit_name_short"));
						userInfo.put("group_jc", upUnit.get("unit_jc"));//部门简称
					}
					else{
						userInfo.put("group_id", userInfo.get("dept_id"));
						userInfo.put("group_name", userInfo.get("dept_name"));
						userInfo.put("group_name_short", userInfo.get("dept_name_short"));
						userInfo.put("group_jc", userInfo.get("dept_jc"));//部门简称
					}
					HttpSession session = request.getSession();
					session.putValue("USERINFO", userInfo);
					return true;
		}
		
			
			}}
		
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url=request.getRequestURL().toString();
		requestURI = requestURI.substring(contextPath.length());
	    String queryString = request.getQueryString();
		String exceptFileList[] = exceptFiles.split("#");
		requestURI =requestURI +"?"+queryString;
		String referer = request.getHeader("referer");
		System.out.println("requestURI:"+requestURI);
		String exceptFileTypesList[] = exceptFileTypes.split("#");
		//ajax的页面 根据referer过滤不做校验的
	    if(requestURI.indexOf("/submit?")>=0){
	    	//判断是否排除的文件或目录
			for (int i = 0; i < exceptFileList.length; i++) {
				if (referer.indexOf(exceptFileList[i])>=0) {
					return true;
				}
			}
	    }
		//判断是否排除的类型
		for (int i = 0; i < exceptFileTypesList.length; i++) {
			String type ="";
			if(requestURI.lastIndexOf(".")>=0){
				int last = requestURI.lastIndexOf("?");
				int begin = requestURI.lastIndexOf(".");
				if(last>begin){
			      type = requestURI.substring(requestURI.lastIndexOf("."), requestURI.lastIndexOf("?")); 
			  }
			}
			if (exceptFileTypesList[i].equals(type)) {
				return true;
			}
		}
		//判断是否排除的文件或目录
		for (int i = 0; i < exceptFileList.length; i++) {
			if (requestURI.equals(exceptFileList[i])) {
				return true;
			}
			//添加对某一目录下所有访问都不限制
			else if(requestURI.indexOf(exceptFileList[i])>=0){
				return true ;
			}
		}
	
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("The Session is null.......................");
		} else if (session.getAttribute(userSession) == null) {
			System.out.println("The Session isn't null, but user is null.......................");
		}
		return session != null && (session.getAttribute(userSession) != null);
	}

	/**
	 * 过滤终止时，回收资源
	 */
	public void destroy() {
		filterConfig = null;
		enable = null;
		exceptFiles = null;
		exceptFileTypes = null;
		userSession = null;
	}

	/**
	 * 开始进行过滤
	 *
	 * @param request 页面请求request
	 * @param response 页面响应response
	 * @param filterChain 过滤链
	 *
	 * @exception IOExcepion, ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (enable.equals("false")) {
			filterChain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String contextPath = httpRequest.getContextPath();
		String basePath = httpRequest.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ contextPath;
		
//		if (checkSession(httpRequest) && checkRole(httpRequest)) {
		if (checkSession(httpRequest))  {
			filterChain.doFilter(request, response);
		} else {
		//httpRequest.getRequestDispatcher(basePath+redirectURL).forward(request, response);
			//getRequestDispatcher(basePath + redirectURL)。forward(request, response);
			if (httpRequest.getHeader("x-requested-with") != null && httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				httpResponse.setHeader("sessionstatus", "timeout");
			}
			else{
		     httpResponse.sendRedirect(basePath+redirectURL);
		 }
		}
	}

	/**
	 * 设置初始化参数
	 *
	 * @param filterConfig 过滤配置
	 *
	 * @exception ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		enable = this.filterConfig.getInitParameter("enable");
		if (enable == null) {
			enable = "true";
		}

		redirectURL = this.filterConfig.getInitParameter("redirectURL");
		if (redirectURL == null) {
			redirectURL = "/";
		}

		exceptFiles = this.filterConfig.getInitParameter("exceptFiles");
		if (exceptFiles == null) {
			exceptFiles = "";
		}
		exceptFileTypes = this.filterConfig.getInitParameter("exceptFileTypes");
		if (exceptFileTypes == null) {
			exceptFileTypes = "";
		}
		
		if (userSession == null) {
			userSession = "USERINFO";
		}
	}
	public LoginService getLoginService() {
		return new LoginService();
	}

}