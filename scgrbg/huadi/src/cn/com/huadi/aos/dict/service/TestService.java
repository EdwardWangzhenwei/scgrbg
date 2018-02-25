package cn.com.huadi.aos.dict.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aisino.platform.db.Crud;
import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.Eso;
import com.aisino.platform.view.DataMsgBus;

public class TestService {
	public TestService() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * 获取tree数据一级节点
	 * 
	 * @param bus
	 * @param rootid
	 * @return
	 */
	public List<Map> getTreeData(DataMsgBus bus) {
		String groupCode = bus.getString("groupCode");
		String reloadId = bus.getString("reloadId");
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryTreeByGroup");
		if(reloadId!=null && !"".equals(reloadId)){
			sql+="and UPER_DICT_ID="+reloadId;
		}else{
			sql+="and UPER_DICT_ID is null";
			
		}
		List<Map> listMap = db.getListResult(sql,groupCode);
		// 第一级
		List<Map> rootMap = new ArrayList<Map>();
		if (listMap != null && listMap.size() > 0) {
			for (int i = 0; i < listMap.size(); i++) {
				Map map = listMap.get(i);
					map.put("leaf",false );
					map.put("children",null);
					rootMap.add(map);
			}
		}
		return rootMap;
	}

}
