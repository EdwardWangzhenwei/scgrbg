package cn.com.huadi.aos.hdoa.unit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.DataMsgBus;

/**
 * 单位树构建
 * 
 * @author lwf
 * 
 */
public class UnitTreeService {

	/**
	 * 获取tree数据
	 * 
	 * @param bus
	 * @return
	 */
	public List<Map> getTreeData(DataMsgBus bus) {
		HttpSession session = SessUtil.getRequest().getSession();
		Map userInfo=(Map) session.getAttribute("USERINFO");
		String topid = "";
		String dept_id = String.valueOf(userInfo.get("dept_id"));
		
		String sql1="select code from (select t.unit_id code from priv_unit t where t.unit_flag=1 start with t.unit_id = ? connect by t.unit_id = prior t.uper_unit_id order siblings by t.unit_sort) where rownum=1";
		Map<String,Object> map1=DbSvr.getDbService("acc_sys").getOneRecorder(sql1, dept_id);
		if(map1!=null){
			topid=map1.get("code").toString();
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select level,t.unit_id code,decode(t.abate,1,t.unit_name,t.unit_name||'(无效)') name,'false' leaf, t.uper_unit_id fatherid from priv_unit t start with"
				+ " t.unit_id=? connect by prior t.unit_id=t.uper_unit_id  order siblings by t.unit_sort ");

		List<Map> listMap = DbSvr.getDbService("acc_sys").getListResult(
				sql.toString(), new Object[]{topid});
		// 第一级
		List<Map> rootMap = new ArrayList<Map>();
		if (listMap != null && listMap.size() > 0) {
			for (int i = 0; i < listMap.size(); i++) {
				Map map = listMap.get(i);
				if (topid.equals(String.valueOf(map.get("code")))) {
					if (getListMap(map.get("code").toString(), listMap).size() > 0) {
						map.put("leaf", false);
						map.put("children",
								getListMap(map.get("code").toString(), listMap));
					} else {
						map.put("leaf", true);
						map.put("children", null);
					}
					rootMap.add(map);
				}
			}
		}
		return rootMap;
	}
	/**
	 * 获取tree数据
	 * 
	 * @param bus
	 * @return
	 */
	public List<Map> getTreeNolimitData(DataMsgBus bus) {
		HttpSession session = SessUtil.getRequest().getSession();
		Map userInfo=(Map) session.getAttribute("USERINFO");
		//String topid = String.valueOf(userInfo.get("unit_id"));
		String topid=bus.getString("deptid");
		StringBuffer sql = new StringBuffer();
		sql.append("select level,t.unit_id code,decode(t.abate,1,t.unit_name,t.unit_name||'(无效)') name,'false' leaf, t.uper_unit_id fatherid from priv_unit t start with"
				+ " t.unit_id=? connect by prior t.unit_id=t.uper_unit_id  order siblings by t.unit_sort ");
		
		List<Map> listMap = DbSvr.getDbService("acc_sys").getListResult(
				sql.toString(), new Object[]{topid});
		// 第一级
		List<Map> rootMap = new ArrayList<Map>();
		if (listMap != null && listMap.size() > 0) {
			for (int i = 0; i < listMap.size(); i++) {
				Map map = listMap.get(i);
				if (topid.equals(String.valueOf(map.get("code")))) {
					if (getListMap(map.get("code").toString(), listMap).size() > 0) {
						map.put("leaf", false);
						map.put("children",
								getListMap(map.get("code").toString(), listMap));
					} else {
						map.put("leaf", true);
						map.put("children", null);
					}
					rootMap.add(map);
				}
			}
		}
		return rootMap;
	}

	/**
	 * 分级加载tree数据
	 * 
	 * @param bus
	 * @return
	 */
	public List<Map> getTreeData2(DataMsgBus bus) {
		Integer topid = bus.getInteger("current_unit_id");
		Integer reloadId = bus.getInteger("reloadId");
		DbSvr dbSvr = DbSvr.getDbService(null);
		Map m = dbSvr.getOneRecorder(
				"select uper_unit_id from priv_unit where unit_id=?",
				new Object[] { topid });
		String s = "";// 此查询条件过滤兄弟节点
		if (reloadId != null) {
			s = "";
		} else {
			s = " and t.unit_id=" + topid;
			reloadId = Integer.parseInt(m.get("uper_unit_id").toString());
		}

		/*
		 * StringBuffer sql = new StringBuffer(
		 * "select t.unit_id code,t.unit_name name,'false' leaf, t.uper_unit_id fatherid from priv_unit t where t.abate=1 "
		 * ); if(reloadId!=null){ sql.append("and t.uper_unit_id=?"); }
		 * sql.append(" order by t.unit_sort ");
		 */

		StringBuffer sql = new StringBuffer(
				"select distinct temp.code,decode(temp.abate,1,temp.name,temp.name||'(失效)') name,temp.fatherid,decode(u.unit_id,null,1,0) leaf,temp.unit_sort "
						+ " from priv_unit u,(select t.unit_id code,t.unit_name name,t.abate,t.uper_unit_id fatherid,t.unit_sort from priv_unit t where ");
		if (reloadId != null) {
			sql.append(" t.uper_unit_id=").append(reloadId).append(s);
		}
		sql.append(" ) temp where u.uper_unit_id(+)=temp.code");

		sql.append(" order by temp.unit_sort,temp.code ");
		List<Map> listMap = dbSvr.getListResult(sql.toString(), null);
		/*
		 * List<Map> rootMap = new ArrayList<Map>(); if (listMap != null &&
		 * listMap.size() > 0) { for (int i = 0; i < listMap.size(); i++) { Map
		 * map = listMap.get(i); if (map.get("code").equals(topid)) { List<Map>
		 * l=DbSvr.getDbService(null).getListResult(sql, new Object());
		 * map.put("leaf", true); map.put("children", null); rootMap.add(map); }
		 * } } return rootMap;
		 */
		return listMap;
	}

	/**
	 * 处理以及组成tree
	 * 
	 * @param id
	 * @param listMap
	 * @return
	 */
	public List<Map> getListMap(String id, List<Map> listMap) {
		List<Map> rootMap = new ArrayList<Map>();
		if (listMap != null && listMap.size() > 0) {
			for (int i = 0; i < listMap.size(); i++) {
				Map map = listMap.get(i);
				if (map.get("fatherid").toString().equals(id)) {
					if (getListMap(map.get("code").toString(), listMap).size() > 0) {
						map.put("leaf", false);
						map.put("children",
								getListMap(map.get("code").toString(), listMap));
					} else {
						map.put("leaf", true);
						map.put("children", null);
					}
					rootMap.add(map);
					// listMap.remove(i);
				}
			}
		}
		return rootMap;
	}
}
