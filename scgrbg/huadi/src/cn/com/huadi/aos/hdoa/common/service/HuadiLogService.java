package cn.com.huadi.aos.hdoa.common.service;

import java.util.Date;
import java.util.Map;

import com.aisino.platform.db.Crud;
import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.Eso;

/**
 * 记录权限系统操作日志 记录权限、业务系统登录日志 记录业务系统操作日志
 * 
 * @author lwf
 * 
 */
public class HuadiLogService {
	/**
	 * 添加业务操作日志 user_id、user_name、unit_name、unit_id从Session中USERINFO取值
	 * mac_address：当前操作终端的MAC地址 operate_name：操作名称，例：添加、修改 operate_object：操作内容
	 * subsystemname：子系统名称 operate_description：操作描述
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void businessLog(Map param) throws Exception {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		dbSvr.setAutoCommit(false);
		try {
			// 操作人ID
			Object user_id = param.get("user_id");
			// 操作人名称
			Object user_name = param.get("user_name");
			// 操作人单位
			Object unit_name = param.get("unit_name");
			// 操作人单位ID
			Object unit_id = param.get("unit_id");
			// MAC地址
			Object mac_address = param.get("mac_address");
			// IP地址
			Object ip_address = param.get("ip");
			// 操作事件
			Object sj = param.get("sj");
			// 操作名称
			Object operate_name = param.get("operate_name");
			// 记录内容
			Object operate_object = param.get("operate_object");
			// 子系统名称
			Object subsystemname = param.get("subsystemname");
			// 操作人-操作名称
			Object user_log_id = dbSvr.executeQueryOne(
					"select seq_user_log.nextval as id  from dual", null)
					.toString();
			Object operate_description = param.get("operate_description");
			String col="user_log_id;operate_date;user_id;ip;user_name;unit_id;unit_name;mac_address;sj;operate_name;"
					+ "operate_object;subsystemname;operate_description";
			Object[] values=new Object[]{user_log_id,new Date(),user_id,ip_address,user_name,unit_id,unit_name,mac_address,
					sj,operate_name,operate_object,subsystemname,operate_description};
			Crud crud = new Crud("hdoa.priv_user_log");
			crud.define(col, values);  
			Eso eso= crud.getInsertEso();
			dbSvr.execute(eso);
			dbSvr.commit();
		} catch (Exception e) {
			// TODO: handle exception
			dbSvr.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			dbSvr.release();
		}
	}

	/**
	 * 权限系统操作日志 administrator_account_name、administrator_id、unit_name、
	 * unit_id从Session中USERINFO取值 ip：从request中获取当前操作终端IP
	 * mac_address：当前操作终端的MAC地址 operate_name：操作名称，例：添加、修改 operate_object：操作内容
	 * subsystemname：子系统名称 operate_description：操作描述
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void privsysLog(Map param) throws Exception {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		dbSvr.setAutoCommit(false);
		try {
			Object administrator_account_name = param
					.get("administrator_account_name");
			Object administrator_id = param.get("administrator_id");
			Object unit_name = param.get("unit_name");
			Object unit_id = param.get("unit_id");
			Object sj = param.get("sj");
			Object ip = param.get("ip");
			Object operate_name = param.get("operate_name");
			Object mac_address = param.get("mac_address");
			Object operate_object = param.get("operate_object");
			Object subsystemname = param.get("subsystemname");
			Object operate_description = param.get("operate_description");

			StringBuffer logKey = new StringBuffer("");
			logKey.append("INSERT INTO priv_sysadminlog ( id,operate_date ");
			StringBuffer logVal = new StringBuffer("");
			logVal.append(" VALUES (seq_sysadminlog.nextval,sysdate ");

			if (administrator_account_name != null) {
				logKey.append(",administrator_account_name");
				logVal.append(",'" + administrator_account_name + "'");
			}
			if (administrator_id != null) {
				logKey.append(",administrator_id");
				logVal.append(",'" + administrator_id + "'");
			}
			if (unit_id != null) {
				logKey.append(",unit_id");
				logVal.append(",'" + unit_id + "'");
			}
			if (unit_name != null) {
				logKey.append(",unit_name");
				logVal.append(",'" + unit_name + "'");
			}
			if (mac_address != null) {
				logKey.append(",mac_address");
				logVal.append(",'" + mac_address + "'");
			}
			if (ip != null) {
				logKey.append(",ip");
				logVal.append(",'" + ip + "'");
			}
			if (sj != null) {
				logKey.append(",sj");
				logVal.append(",'" + sj + "'");
			}
			if (operate_name != null) {
				logKey.append(",operate_name");
				logVal.append(",'" + operate_name + "'");
			}
			if (operate_object != null) {
				logKey.append(",operate_object");
				logVal.append(",'" + operate_object + "'");
			}
			if (subsystemname != null) {
				logKey.append(",subsystemname");
				logVal.append(",'" + subsystemname + "'");
			}
			if (operate_description != null) {
				logKey.append(",operate_description");
				logVal.append(",'" + operate_description + "'");
			}
			logKey.append(")");
			logVal.append(")");
			dbSvr.execute(logKey.append(logVal).toString());
			dbSvr.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbSvr.rollback();
			throw e;
		} finally {
			dbSvr.release();
		}
	}

	/**
	 * 权限、业务系统登录日志
	 * user_account_name、user_id、unit_name、unit_id从Session中USERINFO取值
	 * ip：从request中获取当前操作终端IP mac_address：当前操作终端的MAC地址 operate_name：操作名称，例：添加、修改
	 * operate_object：操作内容 subsystemname：子系统名称 operate_description：操作描述
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void privsysAndBusinessLoginLog(Map param) throws Exception {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		dbSvr.setAutoCommit(false);
		try {
			Object user_account_name = param.get("user_account_name");
			Object user_id = param.get("user_id");
			Object unit_name = param.get("unit_name");
			Object unit_id = param.get("unit_id");
			Object sj = param.get("sj");
			Object ip = param.get("ip");
			Object operate_name = param.get("operate_name");
			Object mac_address = param.get("mac_address");
			Object operate_object = param.get("operate_object");
			Object subsystemname = param.get("subsystemname");
			Object operate_description = param.get("operate_description");
			Object type = param.get("type");

			StringBuffer logKey = new StringBuffer("");
			logKey.append("INSERT INTO priv_sysauditlog ( id,operate_date ");
			StringBuffer logVal = new StringBuffer("");
			logVal.append(" VALUES (seq_sysadminlog.nextval,sysdate ");

			if (user_account_name != null) {
				logKey.append(",user_account_name");
				logVal.append(",'" + user_account_name + "'");
			}
			if (user_id != null) {
				logKey.append(",user_id");
				logVal.append(",'" + user_id + "'");
			}
			if (unit_id != null) {
				logKey.append(",unit_id");
				logVal.append(",'" + unit_id + "'");
			}
			if (unit_name != null) {
				logKey.append(",unit_name");
				logVal.append(",'" + unit_name + "'");
			}
			if (mac_address != null) {
				logKey.append(",mac_address");
				logVal.append(",'" + mac_address + "'");
			}
			if (ip != null) {
				logKey.append(",ip");
				logVal.append(",'" + ip + "'");
			}
			if (sj != null) {
				logKey.append(",sj");
				logVal.append(",'" + sj + "'");
			}
			if (operate_name != null) {
				logKey.append(",operate_name");
				logVal.append(",'" + operate_name + "'");
			}
			if (operate_object != null) {
				logKey.append(",operate_object");
				logVal.append(",'" + operate_object + "'");
			}
			if (subsystemname != null) {
				logKey.append(",subsystemname");
				logVal.append(",'" + subsystemname + "'");
			}
			if (operate_description != null) {
				logKey.append(",operate_description");
				logVal.append(",'" + operate_description + "'");
			}
			if (type != null) {
				logKey.append(",type");
				logVal.append(",'" + type + "'");
			}
			logKey.append(")");
			logVal.append(")");
			dbSvr.execute(logKey.append(logVal).toString());
			dbSvr.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbSvr.rollback();
			throw new Exception();
		} finally {
			dbSvr.release();
		}
	}
}
