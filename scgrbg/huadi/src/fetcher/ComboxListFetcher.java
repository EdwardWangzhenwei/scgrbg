package fetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.com.huadi.aos.dict.service.DictiListService;

import cn.com.huadi.aos.hdoa.systemManagement.wjxs.service.WJXSService;

import com.aisino.platform.core.Plugin;
import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.ValueInf;
import com.aisino.platform.view.basicHelp.ListFetcher;

public class ComboxListFetcher  extends Plugin implements ListFetcher,ValueInf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void setValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		//System.out.println("sertValue");
	}


	@Override
	public List<Map> getOptions(DataMsgBus arg0) {
		return new WJXSService().queryMb(arg0);
	    
	}
	public DictiListService getDictService(){
		DictiListService dict = new DictiListService();
		return dict;
	}
	

}
