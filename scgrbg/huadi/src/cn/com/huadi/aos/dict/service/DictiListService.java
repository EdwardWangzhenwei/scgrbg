package cn.com.huadi.aos.dict.service;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.aisino.platform.db.DbSvr;

public class DictiListService  {
	public DictiListService() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Map> getDictListByCode(String groupCode) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryByGroupCode");
		List<Map> listMap = db.getListResult(sql, new Object[]{groupCode});
		return listMap;
	}
}
