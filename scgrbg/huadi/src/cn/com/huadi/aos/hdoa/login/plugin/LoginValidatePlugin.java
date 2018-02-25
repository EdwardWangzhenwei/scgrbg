package cn.com.huadi.aos.hdoa.login.plugin;

import java.util.Map;

import javax.servlet.http.HttpSession;


import cn.com.huadi.aos.hdoa.login.service.LoginService;

import com.aisino.platform.core.Plugin;
import com.aisino.platform.db.DbSvr;
import com.aisino.platform.exception.BusinessException;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.AbstractForm;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.NullForm;
import com.aisino.platform.view.listener.FormCreateListener;
import com.aisino.platform.view.listener.SubmitListener;

public class LoginValidatePlugin extends Plugin implements FormCreateListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	@Override
	public void setValue(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onFormCreate(AbstractForm form, DataMsgBus bus ) {
		// TODO Auto-generated method stub
		  HttpSession session = SessUtil.getRequest().getSession();
		  if(session!=null&&session.getAttribute("USERINFO")==null){	
			//String homeurl =(String)RegServiceServlet.SYSCON.get("homeloginlink");
			//form.updateWidgetValue("homeloginurl",  homeurl);   //("homeloginurl", homeurl);
		//form.preExecuteJs("window.location.href='pt/res/hdoa/sessionCheck.jsp';");
			//form.executeJs("alert('登录信息过期请重新登录!');");
			form.forward("sessionout", null, null);
		//	  form.executeJs("alert('登录信息过期请重新登录!');if (opener==null){alert('1');top.location.href='/HDOA/canvas?formid=login';}else{alert('2');opener.top.location.href='/HDOA/canvas?formid=login';window.open('','_top');window.close();}");
		    return ;
		  }
		  else {
			 // System.out.println("鐧诲綍浜嗙櫥褰曚簡");
			
			  form.setReturn("true");
		  }
	}


//	public void onFormCreate(NullForm nullForm, DataMsgBus bus) {
//		// TODO Auto-generated method stub
//		  HttpSession session = SessUtil.getRequest().getSession();
//		  if(session!=null&&session.getAttribute("USERINFO")==null){	
//			String homeurl =(String)RegServiceServlet.SYSCON.get("homeloginlink");
//			form.updateWidgetValue("homeloginurl",  homeurl);   //("homeloginurl", homeurl);
//			form.show("relogin", bus);
//		    return;
//		  }
//		  else {
//			 // System.out.println("鐧诲綍浜嗙櫥褰曚簡");
//			
//			  form.setReturn("true");
//		  }
//	}



}
