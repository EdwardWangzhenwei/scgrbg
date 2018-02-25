package cn.com.huadi.aos.hdoa.common.filter;

import java.io.IOException;

/*  4:   */ import javax.servlet.FilterChain;
/*  5:   */ import javax.servlet.FilterConfig;
/*  6:   */ import javax.servlet.ServletException;
/*  7:   */ import javax.servlet.ServletRequest;
/*  8:   */ import javax.servlet.ServletResponse;
/*  9:   */ import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 10:   */ import org.apache.commons.logging.Log;
import org.jasig.cas.client.session.SessionMappingStorage;
import org.jasig.cas.client.session.SingleSignOutHandler;
/* 11:   */ import org.jasig.cas.client.util.AbstractConfigurationFilter;
/* 12:   */ 
/* 13:   */ public final class SingleSignOutFilter
/* 14:   */   extends AbstractConfigurationFilter
/* 15:   */ {
/* 16:41 */   private static final SingleSignOutHandler handler = new SingleSignOutHandler();
/* 17:   */   
/* 18:   */   public void init(FilterConfig filterConfig)
/* 19:   */     throws ServletException
/* 20:   */   {
/* 21:44 */     if (!isIgnoreInitConfiguration())
/* 22:   */     {
/* 23:45 */       handler.setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
/* 24:46 */       handler.setLogoutParameterName(getPropertyFromInitParams(filterConfig, "logoutParameterName", "logoutRequest"));
/* 25:   */     }
/* 26:48 */     handler.init();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setArtifactParameterName(String name)
/* 30:   */   {
/* 31:52 */     handler.setArtifactParameterName(name);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setLogoutParameterName(String name)
/* 35:   */   {
/* 36:56 */     handler.setLogoutParameterName(name);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setSessionMappingStorage(SessionMappingStorage storage)
/* 40:   */   {
/* 41:60 */     handler.setSessionMappingStorage(storage);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
/* 45:   */     throws IOException, ServletException
/* 46:   */   {
/* 47:64 */     HttpServletRequest request = (HttpServletRequest)servletRequest;
                HttpServletResponse response= (HttpServletResponse)servletResponse;
/* 48:66 */     if (handler.isTokenRequest(request))
/* 49:   */     {
/* 50:67 */       handler.recordSession(request);
/* 51:   */     }else if(handler.isLogoutRequest(request)){
	                  handler.destroySession(request);
	                  return;
                } else if (request.getParameter("logoutRequest")!=null){
/* 57:   */     request.getSession().invalidate();
/* 58:71 */     response.sendRedirect(request.getRequestURL().toString());
                return;
/* 59:   */       }
/* 60:73 */       this.log.trace("Ignoring URI " + request.getRequestURI());
/* 62:76 */       filterChain.doFilter(servletRequest, servletResponse);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void destroy() {}
/* 66:   */   
/* 67:   */   protected static SingleSignOutHandler getSingleSignOutHandler()
/* 68:   */   {
/* 69:84 */     return handler;
/* 70:   */   }
/* 71:   */ }



/* Location:           D:\work_main\siChuan\work_s\schy\webapp\WEB-INF\lib\cas-client-core-3.2.0.jar

* Qualified Name:     org.jasig.cas.client.session.SingleSignOutFilter

* JD-Core Version:    0.7.0.1

*/