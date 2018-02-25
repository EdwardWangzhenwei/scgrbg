/**
 * 
 */
package cn.com.huadi.aos.hdoa.yjgl.plugin;


import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.com.huadi.aos.dict.service.DictCommonService;
import cn.com.huadi.aos.hdoa.bjgl.service.BmdbService;
import cn.com.huadi.aos.hdoa.bjgl.service.BmybService;
import cn.com.huadi.aos.hdoa.common.plugin.BasePlugin;
import cn.com.huadi.aos.hdoa.common.service.FlowListService;
import cn.com.huadi.aos.hdoa.common.service.HuadiLogService;
import cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet;
import cn.com.huadi.aos.hdoa.common.util.DataBus;
import cn.com.huadi.aos.hdoa.common.util.DictUtil;
import cn.com.huadi.aos.hdoa.dzwj.service.DzwjService;
import cn.com.huadi.aos.hdoa.yjgl.service.BmyyService;

import com.aisino.platform.core.Plugin;
import com.aisino.platform.db.Page;
import com.aisino.platform.exception.BusinessException;
import com.aisino.platform.util.DateUtil;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.AbstractForm;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.listener.FormCreateListener;
import com.aisino.platform.view.listener.SubmitListener;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author Edward
 * @function 维护个人已办的Plugin类
 * @date 2017年9月11日
 */
public class BmyyPlugin extends BasePlugin implements FormCreateListener,
SubmitListener {

	/** 
     * @function 页面提交事件的处理
	 * @see com.aisino.platform.view.listener.SubmitListener#doSubmitAction(com.aisino.platform.view.AbstractForm, com.aisino.platform.view.DataMsgBus)
	 */
	@Override
	public void doSubmitAction(AbstractForm arg0, DataMsgBus arg1) {
		HttpSession session = SessUtil.getRequest().getSession();
		Map userInfo = (Map) session.getAttribute("USERINFO");
			if(userInfo!=null){
				
				try {
					if (arg1.isAction("query")) {
						
						
							Object unit_id = userInfo.get("unit_id");
							Object user_id = userInfo.get("user_id");
							arg1.put("unit_id", unit_id);
							arg1.put("user_id", user_id);
								
							List<Map> list = this.getBmyyService().queryByPage(arg1);
							list = convert(list);
							arg0.updateWidgetValue("list0", list);
						
					}else if(arg1.isAction("page")){
						
							
								Object unit_id = userInfo.get("unit_id");
								Object user_id = userInfo.get("user_id");
								arg1.put("unit_id", unit_id);
								arg1.put("user_id", user_id);
								
								List<Map> list = this.getBmyyService().queryByPage(arg1);
								list = convert(list);
								arg0.updateWidgetValue("list0", list);
							
					}else if(arg1.isAction("getServer")){
					
					
						arg1.put("userInfo", userInfo);
						arg1=transferBus(arg1);
						String server = (String)RegServiceServlet.SYSCON.get("client_server");
						arg0.setReturn(server);				
					} 
				}
				catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BusinessException("保存失败，请联络系统管理员!");
			}
		 }
		
	}
	
	
	/**
	 * @function 页面初始化时显示数据的方法
	 * @date 2017年7月23日
	 */
	@Override
	public void onFormCreate(AbstractForm arg0, DataMsgBus arg1) {

		// TODO Auto-generated method stub
		try {
		HttpSession session = SessUtil.getRequest().getSession();
		Map userInfo=(Map) session.getAttribute("USERINFO");
		Page.setDefaultPageSize(arg1, null,10);
		if(userInfo!=null){
			
			
			arg0.updateWidgetLocalData("security", this.getDictCommonService().getDictionaryByGroupNameAndSecret(DictUtil.DICT_CODE_MJ, Integer.parseInt(String.valueOf(userInfo.get("user_secret")))));
			
			List<Map> list = this.getBmyyService().queryByPage(arg1);
			list = convert(list);
			//this.joinWH(list);
			arg0.updateWidgetValue("list0", list);		
		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("执行操作错误,请联系系统管理员");
		}
	
		
	}
	
	/**
	 * @function 处理会议类型的方法（从字典中取数据）
	 * @param list
	 * @param tab
	 * @return
	 */
	private List<Map> convert(List<Map> list){
		
		if(list!=null){
			for(Map data:list){
				 Object sender = data.get("sender");
				 Object send_unit = data.get("send_unit");
				 if(sender!=null){
					  data.put("sender",send_unit+" "+sender);
				 }
				 Object send_time = data.get("send_time");
				 if(send_time!=null){
					 data.put("send_time",send_time.toString().substring(0, 16));
				 }
				 Object finish_time = data.get("finish_time");
				 if(finish_time!=null){
					 data.put("finish_time",finish_time.toString().substring(0, 16));
				 }
			}
		}
		return list;
	}
	/**
	 * @function 设定值的方法 
	 */
	@Override
	public void setValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	// 获得BmybService对象
	public BmyyService getBmyyService() {
		return new BmyyService();
	}
	
	// 获得DzwjService对象
	public DzwjService getDzwjService(){
		return new DzwjService();
	}
	
	// 获得DictCommonService对象
	public DictCommonService getDictCommonService(){
		return new DictCommonService();
	}
	
}
