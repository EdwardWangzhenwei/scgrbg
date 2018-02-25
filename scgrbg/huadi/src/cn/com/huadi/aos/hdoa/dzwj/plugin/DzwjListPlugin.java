package cn.com.huadi.aos.hdoa.dzwj.plugin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.com.huadi.aos.dict.service.DictCommonService;
import cn.com.huadi.aos.hdoa.dzwj.service.DzwjService;

import com.aisino.platform.core.Plugin;
import com.aisino.platform.db.Page;
import com.aisino.platform.exception.BusinessException;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.AbstractForm;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.listener.FormCreateListener;
import com.aisino.platform.view.listener.SubmitListener;

public class DzwjListPlugin extends Plugin implements FormCreateListener,SubmitListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6390487436695933337L;

	@Override
	public void doSubmitAction(AbstractForm arg0, DataMsgBus bus) {
		// TODO Auto-generated method stub
		List<Map> result = new ArrayList();
		HttpSession session = SessUtil.getRequest().getSession();
		Map userInfo = (Map) session.getAttribute("USERINFO");
		try {
		if (bus.isAction("queryByBus")) {
			
				Page.setDefaultPageSize(bus, null, 15);
				result = this.getDzwjService().queryByBusByPage(bus);
				mjConvert(result);
				sizeConvert(result);
				arg0.updateWidgetValue("list", result);
			
		}
		
		if (bus.isAction("delete")) {
			
				Object id = bus.get("dzwj_id");
				bus.put("userInfo", userInfo);
				if(id!=null){
					 this.getDzwjService().deleteById(bus);
					 arg0.setReturn("ok");
				}
			
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new BusinessException("操作错误，请联络系统管理员");
	}
	}

	@Override
	public void onFormCreate(AbstractForm arg0, DataMsgBus bus) {
		// TODO Auto-generated method stub
		
		List<Map> result = new ArrayList();
		try {
//			HttpSession session = SessUtil.getRequest().getSession();
//			Map userinfo = (Map) session.getAttribute("USERINFO");
//			int unitid = 0;
//			if (userinfo != null) {
//				unitid  = Integer.parseInt(String.valueOf(userinfo.get("unit_id")));
//				bus.put("ssdwid",unitid);
//			}
			Page.setDefaultPageSize(bus, null, 15);
			result = this.getDzwjService().queryByBusByPage(bus);
			mjConvert(result);
			sizeConvert(result);
			arg0.updateWidgetValue("list", result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException("操作错误，请联络系统管理员");
		}
	}

	@Override
	public void setValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public DzwjService getDzwjService(){
		return new DzwjService();
	}
	
	public DictCommonService getDictCommonService(){
		return new DictCommonService();
	}
	public void mjConvert(List<Map> list){
		if(list!=null&&list.size()>0){
			for(Map m:list){
				if(m.get("mj")!=null){					
					Object mj=getDictCommonService().getDictionaryInfoByCode("mj", String.valueOf(m.get("mj"))).get("dict_name");			
					m.put("mj", mj);
					continue;
				}
			}
		}
	}
	public void sizeConvert(List<Map> list){
		if(list!=null&&list.size()>0){
			DecimalFormat df = new DecimalFormat("##0.00");
			for(Map m:list){
				Object dzwj_size=m.get("dzwj_size");	
				if(dzwj_size!=null){
					double size=Double.parseDouble(String.valueOf(dzwj_size));
					String unit="B";
					if(size/1024<999){
						size/=1024;
						unit="KB";
					}else if(size/(1024*1024)<999){
						size/=(1024*1024);
						unit="MB";
					}else if(size/(1024*1024*1024)<999){
						size/=(1024*1024*1024);
						unit="GB";
					}
					m.put("dzwj_size", df.format(size)+unit);
					continue;
				}
			}
		}
	}
}
