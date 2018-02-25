package cn.com.huadi.aos.dict.service;

import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;

public class DictCommonService {
	/**
	 * 根据分组码和字典码查询信息
	 * @param groupCode
	 * @param dictCode
	 * @return
	 */
	public Map getDictionaryInfoByCode(String groupCode, String dictCode) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryInfoByCode");
		Map<String, Object> map = db.getOneRecorder(sql, new Object[]{groupCode,dictCode});
		return map;
	}
	/**
	 * 根据分组编码查询字典码
	 * @param groupCode
	 * @return
	 */
	public List<Map> getDictionaryInfoByGroupCode(String groupCode) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryInfoByGroupCode");
		List<Map> listMap = db.getListResult(sql, new Object[]{groupCode});
		return listMap;
	}
	/**
	 * 根据分组名查询字典项
	 * @param groupName
	 * @return
	 */
	public List<Map> getDictionaryByName(String groupName) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryInfoByGroupName");
		List<Map> listMap = db.getListResult(sql, new Object[]{groupName});
		return listMap;
	}
	
	/**
	 * 根据分组码密级查询字典码
	 * @param groupCode
	 * @param user_secret
	 * @return
	 */
	public List<Map> getDictionaryByGroupNameAndSecret(String groupName,int user_secret) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryByGroupNameAndSecret");
		List<Map> listMap = db.getListResult(sql, new Object[]{groupName,String.valueOf(user_secret)});
		return listMap;
	}
	/**
	 * 根据字典分类名、字典分组名查询字典项信息
	 * @param categoryName
	 * @param groupName
	 * @return
	 */
	public List<Map> getDictionaryNameByGroupName(String categoryName,String groupName) {
		DbSvr db = DbSvr.getDbService(null);
		String sql = db.getPureSQL("dictionary.getDictionaryNameByGroupName");
		List<Map> listMap = db.getListResult(sql, new Object[]{categoryName,groupName});
		return listMap;
	}
}
