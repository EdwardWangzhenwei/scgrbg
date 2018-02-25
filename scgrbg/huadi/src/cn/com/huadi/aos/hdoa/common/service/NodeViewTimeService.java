package cn.com.huadi.aos.hdoa.common.service;

import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.view.DataMsgBus;

public class NodeViewTimeService {
	
	/**
	 * 查询指定文件的所有查看记录,将结果集按“查看人  第一次查看   最后一次查看”格式显示
	 * @param arg1
	 * @return
	 */
	public List<Map> queryAllNodeViewTime(DataMsgBus arg1) {
		// TODO Auto-generated method stub
		Object wjbm=arg1.get("wjbm");
		String sql="select a.n_vt_username,to_char(a.n_vt_time, 'YYYY-MM-DD HH24:MI:SS') as begintime,"
				+ "to_char(b.n_vt_time, 'YYYY-MM-DD HH24:MI:SS') as endtime from "
				+ "(select n_id, n_vt_username, n_vt_userid, n_vt_time, wjbm from "
				+ "gzl_node_viewtime where n_vt_flag = '第一次' and $equal(wjbm,wjbm)  ) a,"
				+ "(select n_id, wjbm, n_vt_username, n_vt_userid, n_vt_time from "
				+ "gzl_node_viewtime where n_vt_flag = '最后一次' and $equal(wjbm,wjbm)  ) b "
				+ "where a.n_id = b.n_id(+) and a.n_vt_username = b.n_vt_username(+) "
				/*+ "order by a.n_id,b.n_vt_time desc";*/
				+ "order by a.n_vt_time ";
		DbSvr dbSvr=DbSvr.getDbService(null);
		return dbSvr.getListResultByPage(arg1, new SqlInfo(sql));
	}
	/**
	 * 查询当前登录人在当前节点记录上的查看记录
	 * @param arg1
	 * @return
	 */
	public List<Map> queryNodeViewTime(DataMsgBus arg1) {
		// TODO Auto-generated method stub
		Object n_id=arg1.get("n_id");
		Map userInfo=(Map) arg1.get("userInfo");
		Object n_vt_userid=userInfo.get("user_id");
		String sql="SELECT n_vt_id, n_id, n_vt_username, n_vt_userid, n_vt_time, n_vt_flag FROM gzl_node_viewtime"
				+ " WHERE 1 = 1 AND n_id = ? AND n_vt_userid = ? order by n_id desc, n_vt_time desc";
		DbSvr dbSvr=DbSvr.getDbService(null);
		return dbSvr.executeQuery(sql, new Object[]{n_id,n_vt_userid});
	}
	
	public void insertNodeViewTime(DataMsgBus bus){
		DbSvr dbSvr=DbSvr.getDbService(null);
		Object n_id = bus.get("n_id");
		if(n_id!=null){
			Object n_vt_flag = bus.get("n_vt_flag");
			Object wjbm = bus.get("wjbm");
			Map userInfo = (Map) bus.get("userInfo");
			int maxNodeViewTimeId = getMaxNodeViewTimeId();
			StringBuffer insertNodeViewTimeKeySql = new StringBuffer("");
			insertNodeViewTimeKeySql
					.append("INSERT INTO gzl_node_viewtime ( N_VT_ID,N_VT_TIME ");
			StringBuffer insertNodeViewTimeValueSql = new StringBuffer("");
			insertNodeViewTimeValueSql.append(" VALUES (").append(maxNodeViewTimeId)
					.append(wjbm).append(",sysdate ");
			if (n_id != null) {
				insertNodeViewTimeKeySql.append(",n_id");
				insertNodeViewTimeValueSql.append("," + n_id);
			}
			if (n_vt_flag != null) {
				insertNodeViewTimeKeySql.append(",n_vt_flag");
				insertNodeViewTimeValueSql.append(",'" + n_vt_flag + "'");
			}
			if (wjbm != null) {
				insertNodeViewTimeKeySql.append(",wjbm");
				insertNodeViewTimeValueSql.append("," + wjbm);
			} 
			if (userInfo != null) {
				if (userInfo.get("user_name") != null) {
					insertNodeViewTimeKeySql.append(",N_VT_USERNAME");
					insertNodeViewTimeValueSql.append(",'" + userInfo.get("user_name")
							+ "'");
				}
				if (userInfo.get("user_id") != null) {
					insertNodeViewTimeKeySql.append(",N_VT_USERID");
					insertNodeViewTimeValueSql.append("," + userInfo.get("user_id"));
				}
			}
	
			insertNodeViewTimeKeySql.append(")");
			insertNodeViewTimeValueSql.append(")");
			String insertNodeViewTimeSql = insertNodeViewTimeKeySql.toString()
					+ insertNodeViewTimeValueSql.toString();
			dbSvr.execute(insertNodeViewTimeSql);
		}
	}
	
	public void updateNodeViewTime(DataMsgBus bus){
		Object n_vt_id=bus.get("n_vt_id");
		StringBuffer sUpdateSql = new StringBuffer("UPDATE gzl_node_viewtime SET N_VT_TIME=sysdate");
		if(n_vt_id!=null){			
			sUpdateSql.append(" where n_vt_id=" + n_vt_id);
			DbSvr.getDbService(null).execute(sUpdateSql.toString());
		}
	}
	
	/**
	 * 获取gzl_node_viewtime表id
	 * 
	 * @return
	 */
	private int getMaxNodeViewTimeId() {
		int id = 0;
		Map res = DbSvr.getDbService(null).getOneRecorder(
				"select seq_node_viewtime.NEXTVAL as id  from dual ", null);
		if (res != null) {
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}
		return id;
	}	
	
	/**
	 * 查询指定文件所有流经操作节点的操作人员信息（现用于判断是否需要记录查看时间用）
	 * @param bus
	 * @return
	 */
	public List<Map> queryNodesByWjbm(DataMsgBus bus){
		DbSvr dbSvr=DbSvr.getDbService(null);
		Object wjbm=bus.get("wjbm");
		Object userId=((Map)bus.get("userInfo")).get("user_id");
		String sql="select t.n_id,t.n_executor,t.n_executorname from GZL_NODE t "
				+ "where t.fi_objectid=? and t.n_executor=? order by t.n_id desc";
		List<Map> list=dbSvr.executeQuery(sql, new Object[]{wjbm,userId});
		return list;
	}
}
