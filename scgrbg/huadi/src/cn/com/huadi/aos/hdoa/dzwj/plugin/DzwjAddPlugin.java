package cn.com.huadi.aos.hdoa.dzwj.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.com.huadi.aos.hdoa.dict.service.DictService;

import com.aisino.platform.core.Plugin;
import com.aisino.platform.exception.BusinessException;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.AbstractForm;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.basicWidget.fetcher.SelectOptionGetter;
import com.aisino.platform.view.listener.FormCreateListener;
import com.aisino.platform.view.listener.SubmitListener;

public class DzwjAddPlugin extends Plugin implements FormCreateListener,SelectOptionGetter,
SubmitListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6390487436695933337L;

	@Override
	public void doSubmitAction(AbstractForm arg0, DataMsgBus bus) {
		// TODO Auto-generated method stub
		
		String dictValue=bus.getString("yjnr");
		
		HttpSession session = SessUtil.getRequest().getSession();
		Map userinfo = (Map) session.getAttribute("USERINFO");
		int unitid = 0;
		int userid = 0;
		if (userinfo != null) {
			userid = Integer.parseInt(String.valueOf(userinfo.get("user_id")));
			unitid  = Integer.parseInt(String.valueOf(userinfo.get("unit_id")));
			bus.put("ssdwid",unitid);
			bus.put("user_id", userid);
		}
		bus.put("dict_type", "常用意见");
		bus.put("dict_value", dictValue);
		if (bus.isAction("saveDict")) {
			try{
				int id=this.getDictService().save(bus);
				arg0.setReturn(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BusinessException("操作错误，请联络系统管理员");
			}
		}
	}

	@Override
	public void onFormCreate(AbstractForm arg0, DataMsgBus arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public DictService getDictService(){
		return new DictService();
	}

	@Override
	public List<Map> getOptions(DataMsgBus arg0) {
		// TODO Auto-generated method stub
		HttpSession session=SessUtil.getRequest().getSession();
		Map userInfo=(Map) session.getAttribute("USERINFO");
		Map m=new HashMap();
		m.put("ssdwid", userInfo.get("unit_id"));
		m.put("user_id", userInfo.get("user_id"));
		m.put("dict_type", "常用意见");
		return getDictService().queryDictByType(m);
	}
}
