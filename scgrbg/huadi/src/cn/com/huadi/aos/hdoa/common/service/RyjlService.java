package cn.com.huadi.aos.hdoa.common.service;

import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.view.DataMsgBus;

/**
 * 记录发送页面待选人员框中默认显示当前登录人最近传递过的10个人
 * @author hd
 *
 */
public class RyjlService {
	/**
	 * 查询最后记录的10个人
	 * @param bus
	 * @return
	 */
	public List<Map> query(DataMsgBus bus){
		DbSvr dbSvr=DbSvr.getDbService("gwsys");
		Object czr_id=bus.get("user_id");
		String sql="SELECT DISTINCT tmp . code ,tmp . name,tmp.title"
				+ " FROM (SELECT u . user_id code ,u . user_name NAME ,"
				+ " u . user_name title FROM gw_ryjl r ,priv_user u WHERE r . bxzr_id = u . user_id"
				+ " AND czr_id = ? ORDER BY czsj DESC ) tmp WHERE rownum < 10 ";
		List<Map> list=dbSvr.executeQuery(sql,new Object[]{czr_id});
		return list;
	}
	
	/**
	 * 新增常用人员记录
	 * @param bus
	 */
	public void addRyjl(Map bus){
		Object czr_id = bus.get("czr_id");
		Object bxzr_id = bus.get("bxzr_id");

		DbSvr dbSvr = DbSvr.getDbService(null);
		String insert = "insert into GW_ryjl (ryjl_id";
		int ryjl_id = getMaxIdForRyjl();
		String values = ") values(" + ryjl_id;
		if (czr_id != null) {
			insert += ",czr_id";
			values += "," + czr_id;
		}

		if (bxzr_id != null) {
			insert += ",bxzr_id";
			values += "," + bxzr_id;
		}
		
		insert += ",czsj";
		values += ",sysdate" ;

		String sql = insert + values + ")";
		dbSvr.execute(sql);		
	}
	
	private int getMaxIdForRyjl() {
		int id = 0;
		Map res = DbSvr.getDbService(null).getOneRecorder(
				"select seq_ryjl.NEXTVAL as id  from dual ", null);
		if (res != null) {
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}
		return id;
	}
}
