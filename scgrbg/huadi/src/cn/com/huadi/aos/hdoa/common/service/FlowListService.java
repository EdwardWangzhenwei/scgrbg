package cn.com.huadi.aos.hdoa.common.service;

import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.DataMsgBus;

/**
 * 流程追踪
 * 
 * @author lwf
 * 
 */
public class FlowListService {
	public List<Map> queryFlowListByWjbm(String wjbm) {
		if(wjbm!=null){
			DbSvr dbSvr = DbSvr.getDbService(null);
			String sql = "select fl_id,ssdwid,wjbm,name,to_char(time,'yyyy-mm-dd HH24:mi:ss') as time,action from gw_flow_list where wjbm=? order by time ";
			List<Map> list = dbSvr.executeQuery(sql, new Object[] { wjbm });
			dbSvr.release();
			return list;
		}else{
			return null;
		}
	}
	public List<Map> queryFlowListByWjbm(DataMsgBus bus) {
		if(bus.get("wjbm")!=null){
			DbSvr dbSvr = DbSvr.getDbService(null);
			String sql = "select fl_id,ssdwid,wjbm,name,to_char(time,'yyyy-mm-dd HH24:mi:ss') as time,action from gw_flow_list where  $equal(wjbm,wjbm) order by time ";
			List<Map> list = dbSvr.getListResultByPage(bus, new SqlInfo(sql));
			dbSvr.release();
			return list;
		}else{
			return null;
		}
	}
	/**
	 * 只查询流程环节记录，不查询用户操作（四院测试要求：流程记录不要同用户操作混淆）
	 * @param bus
	 * @return
	 */
	public List<Map> queryFlowListByWjbm2(DataMsgBus bus) {
		if(bus.get("wjbm")!=null){
			DbSvr dbSvr = DbSvr.getDbService(null);
			String sql = "select fl_id,ssdwid,wjbm,name,to_char(time,'yyyy-mm-dd HH24:mi:ss') as time,action "
					+ "from gw_flow_list "
					+ "where (n_id is not null or (n_id is null and (action like '%拟稿%' or action like '%登记%' or action like '%收回%'))) "
					+ "and $equal(wjbm,wjbm) order by time ";
			List<Map> list = dbSvr.getListResultByPage(bus, new SqlInfo(sql));
			dbSvr.release();
			return list;
		}else{
			return null;
		}
	}

	/**
	 * 插入flow_list记录
	 * 
	 * @param m
	 * @param bus
	 */
	public void insertFlowlist(DataMsgBus bus, DbSvr dbSvr) {
		Object wjbm = bus.get("wjbm");
		Object n_id = bus.get("n_id");
		Object action = bus.get("action");
		Map userInfo = (Map) bus.get("userInfo");
		int maxFlowInstance = getMaxFlowListId();
		StringBuffer insertFlowListKeySql = new StringBuffer("");
		insertFlowListKeySql.append("INSERT INTO gw_flow_list ( fl_id ");
		StringBuffer insertFlowListValueSql = new StringBuffer("");
		insertFlowListValueSql.append(" VALUES (").append(maxFlowInstance);
		if (wjbm != null) {
			insertFlowListKeySql.append(",wjbm");
			insertFlowListValueSql.append("," + wjbm);
		}
		if (n_id != null) {
			insertFlowListKeySql.append(",n_id");
			insertFlowListValueSql.append("," + n_id);
		}
		if (action != null) {
			insertFlowListKeySql.append(",action");
			insertFlowListValueSql.append(",'" + action + "'");
		}
		insertFlowListKeySql.append(",TIME");
		insertFlowListValueSql.append(",sysdate");
		if (userInfo != null) {
			if (userInfo.get("user_name") != null) {
				insertFlowListKeySql.append(",name");
				insertFlowListValueSql.append(",'" + userInfo.get("user_name")
						+ "'");
			}
		}

		insertFlowListKeySql.append(")");
		insertFlowListValueSql.append(")");
		String insertFlowListSql = insertFlowListKeySql.toString()
				+ insertFlowListValueSql.toString();
		dbSvr.execute(insertFlowListSql);
	}
	
	/**
	 * 插入flow_list记录(非Plugin环境)
	 * 
	 * @param m
	 * @param bus
	 * @throws Exception 
	 */
	public int insertFlowlist(Map bus) throws Exception {
		Object wjbm = bus.get("wjbm");
		Object n_id = bus.get("n_id");
		Object action = bus.get("action");
		Object name = bus.get("name");
		Map userInfo = (Map) bus.get("userInfo");
		int maxFlowInstance = getMaxFlowListId();
		StringBuffer insertFlowListKeySql = new StringBuffer("");
		insertFlowListKeySql.append("INSERT INTO gw_flow_list ( fl_id ");
		StringBuffer insertFlowListValueSql = new StringBuffer("");
		insertFlowListValueSql.append(" VALUES (").append(maxFlowInstance);
		if (wjbm != null) {
			insertFlowListKeySql.append(",wjbm");
			insertFlowListValueSql.append("," + wjbm);
		}
		if (n_id != null) {
			insertFlowListKeySql.append(",n_id");
			insertFlowListValueSql.append("," + n_id);
		}
		if (action != null) {
			insertFlowListKeySql.append(",action");
			insertFlowListValueSql.append(",'" + action + "'");
		}
		insertFlowListKeySql.append(",TIME");
		insertFlowListValueSql.append(",sysdate");
		if (name != null) {
			insertFlowListKeySql.append(",name");
			insertFlowListValueSql.append(",'" + name
					+ "'");
		}
		insertFlowListKeySql.append(")");
		insertFlowListValueSql.append(")");
		String insertFlowListSql = insertFlowListKeySql.toString()
				+ insertFlowListValueSql.toString();
		
		DbSvr dbSvr=DbSvr.getDbService(null);
		try {
			dbSvr.setAutoCommit(false);
			dbSvr.execute(insertFlowListSql);
			dbSvr.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbSvr.rollback();
			throw new Exception();
		}finally{
			dbSvr.release();
		}
		return maxFlowInstance;
	}
	
	/**
	 * 插入flow_list记录(非Plugin环境)
	 * 
	 * @param m
	 * @param bus
	 * @throws Exception 
	 */
	public void insertFlowlist(String wjbm,String actionUser,String action) throws Exception {
		int maxFlowInstance = getMaxFlowListId();
		StringBuffer insertFlowListKeySql = new StringBuffer("");
		insertFlowListKeySql.append("INSERT INTO gw_flow_list ( fl_id ");
		StringBuffer insertFlowListValueSql = new StringBuffer("");
		insertFlowListValueSql.append(" VALUES (").append(maxFlowInstance);
		if (wjbm != null) {
			insertFlowListKeySql.append(",wjbm");
			insertFlowListValueSql.append("," + wjbm);
		}
		if (action != null) {
			insertFlowListKeySql.append(",action");
			insertFlowListValueSql.append(",'" + action + "'");
		}
		if (actionUser != null) {
			insertFlowListKeySql.append(",name");
			insertFlowListValueSql.append(",'" + actionUser + "'");
		}
		insertFlowListKeySql.append(",TIME");
		insertFlowListValueSql.append(",sysdate");

		insertFlowListKeySql.append(")");
		insertFlowListValueSql.append(")");
		String insertFlowListSql = insertFlowListKeySql.toString()
				+ insertFlowListValueSql.toString();
		
		DbSvr dbSvr=DbSvr.getDbService(null);
		try {
			dbSvr.setAutoCommit(false);
			dbSvr.execute(insertFlowListSql);
			dbSvr.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbSvr.rollback();
			throw new Exception();
		}finally{
			dbSvr.release();
		}
	}
	public void updateFlowlist(Map flowlist) throws Exception {
		Object wjbm=flowlist.get("wjbm");
		Object flowlistid=flowlist.get("flowlistid");
		StringBuffer sUpdateFl = new StringBuffer("UPDATE gw_flow_list SET ");
		int flag = -1;

		if (wjbm != null) {
			if (flag < 0) {
				sUpdateFl.append("wjbm=" + wjbm);
			} else {
				sUpdateFl.append(",wjbm=" + wjbm);
			}
			flag++;
		} else {
			if (flag < 0) {
				sUpdateFl.append("wjbm=''");
			} else {
				sUpdateFl.append(",wjbm=''");
			}
			flag++;
		}
		sUpdateFl.append(" where fl_id=" + flowlistid);		
		DbSvr dbSvr=DbSvr.getDbService(null);
		dbSvr.execute(sUpdateFl.toString());
	}
	
	/**
	 * 插入flow_list记录(非Plugin环境)
	 * 
	 * @param m
	 * @param bus
	 * @throws Exception 
	 */
	public void insertFlowlist(String wjbm,String actionUser,String action,DbSvr dbSvr) throws Exception {
		int maxFlowInstance = getMaxFlowListId();
		StringBuffer insertFlowListKeySql = new StringBuffer("");
		insertFlowListKeySql.append("INSERT INTO gw_flow_list ( fl_id ");
		StringBuffer insertFlowListValueSql = new StringBuffer("");
		insertFlowListValueSql.append(" VALUES (").append(maxFlowInstance);
		if (wjbm != null) {
			insertFlowListKeySql.append(",wjbm");
			insertFlowListValueSql.append("," + wjbm);
		}
		if (action != null) {
			insertFlowListKeySql.append(",action");
			insertFlowListValueSql.append(",'" + action + "'");
		}
		if (actionUser != null) {
			insertFlowListKeySql.append(",name");
			insertFlowListValueSql.append(",'" + actionUser + "'");
		}
		insertFlowListKeySql.append(",TIME");
		insertFlowListValueSql.append(",sysdate");
		
		insertFlowListKeySql.append(")");
		insertFlowListValueSql.append(")");
		String insertFlowListSql = insertFlowListKeySql.toString()
				+ insertFlowListValueSql.toString();
		try {
			dbSvr.setAutoCommit(false);
			dbSvr.execute(insertFlowListSql);
			dbSvr.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbSvr.rollback();
			throw new Exception();
		}finally{
			dbSvr.release();
		}
	}

	private int getMaxFlowListId() {
		// TODO Auto-generated method stub
		int id = 0;
		Map res = DbSvr.getDbService(null).getOneRecorder(
				"select seq_flow_list.NEXTVAL as id  from dual ", null);
		if (res != null) {
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}
		return id;
	}
}
