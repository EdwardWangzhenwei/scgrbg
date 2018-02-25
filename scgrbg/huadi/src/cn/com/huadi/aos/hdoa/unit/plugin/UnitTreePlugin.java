package cn.com.huadi.aos.hdoa.unit.plugin;

import java.util.List;
import java.util.Map;

import cn.com.huadi.aos.hdoa.unit.service.UnitTreeService;

import com.aisino.platform.view.AbstractForm;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.basicWidget.fetcher.SelectOptionGetter;
import com.aisino.platform.view.listener.SubmitListener;
/**
 * @author xueyang
 *2015-9-4下午02:39:54
 */
public class UnitTreePlugin implements SelectOptionGetter,
		SubmitListener {
	
	public UnitTreeService getUnitTreeService(){
		return new UnitTreeService();
	}

	@Override
	public List<Map> getOptions(DataMsgBus bus) {
		List<Map> res=getUnitTreeService().getTreeData(bus);
		return res;
	}

	@Override
	public void doSubmitAction(AbstractForm arg0, DataMsgBus arg1) {
	}

}
