package cn.com.huadi.aos.hdoa.unit.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.com.huadi.aos.hdoa.unit.service.UnitService;

import com.aisino.platform.core.Plugin;
import com.aisino.platform.exception.BusinessException;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.AbstractForm;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.listener.FormCreateListener;
import com.aisino.platform.view.listener.SubmitListener;

public class UnitPlugin extends Plugin implements FormCreateListener,
		SubmitListener {

	public UnitService getUnitService() {
		return new UnitService();
	}

	@Override
	public void setValue(String arg0, String arg1) {
	}

	@Override
	public void doSubmitAction(AbstractForm arg0, DataMsgBus arg1) {
		try{
		if (arg1.isAction("loaduser")) {
			/*HttpSession session = SessUtil.getRequest().getSession();
			Map userInfo = (Map) session.getAttribute("USERINFO");
			arg1.put("userInfo", userInfo);*/
			List<Map> list = getUnitService().queryUsersByDeptId(arg1);
			String selecteduser=arg1.getString("selecteduser");
			String[] ids=null;
			if(selecteduser!=null&&!"".equals(selecteduser)){
				ids=selecteduser.split(",");
			}
			List<Map> temp = new ArrayList<Map>();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					for(int j=0;j<ids.length;j++){
						String id=String.valueOf(list.get(i).get("code"));
						if(id.equals(ids[j])){
							//list.remove(i);
							temp.add(list.get(i));
						}else{
							continue;
						}
					}
				}
				list.removeAll(temp);
				arg0.updateWidgetLocalData("leftBox", list);
			}else{
				arg0.updateWidgetLocalData("leftBox", new ArrayList());				
			}
		}
		if (arg1.isAction("selectUnitByUserName")) {
			List<Map> list = getUnitService().queryUnitNameByUserName(arg1);
			String unitNames="";
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					unitNames+=","+(String)list.get(i).get("unit_name");
				}
			}
			arg0.updateWidgetLocalData("unitName", "".equals(unitNames)?"":unitNames.substring(1));
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BusinessException("执行操作错误，请联络系统管理员!");
		}
	}
	@Override
	public void onFormCreate(AbstractForm arg0, DataMsgBus arg1) {
		//初始化已选择人员数据
		try{
		List<Map> list=this.getUnitService().queryUserInfoByUserIds(arg1);
		arg0.updateWidgetLocalData("rightBox", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BusinessException("执行操作错误，请联络系统管理员!");
		}
	}
}
