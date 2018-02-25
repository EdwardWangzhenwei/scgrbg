package cn.com.huadi.aos.hdoa.unit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.view.DataMsgBus;

public class UnitService {
	public UnitService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Map> queryUsersByDeptId(DataMsgBus bus) {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		Integer deptid = bus.getInteger("deptid");
		Integer user_secret = bus.getInteger("user_secret");
		System.err.println(user_secret);
		return dbSvr.getListResult("select u.user_id code,u.user_name name,u.user_name titie from priv_user u,priv_unit_user uu where u.user_id=uu.user_id and u.user_secret>=? and uu.unit_id=? order by u.user_sort desc ",new Object[] { user_secret,deptid });
	}
	public List<Map> queryUnitNameByUserName(DataMsgBus bus) {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		String userName = bus.getString("userName");
		return dbSvr.getListResult("select distinct t.unit_name from priv_unit t,priv_user u, priv_unit_user uu where u.user_id = uu.user_id and uu.unit_id = t.unit_id and u.user_name like '%?%'",new Object[] { userName });
	}
	
	public List<Map> queryUserInfoByUserIds(DataMsgBus bus) {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		String userids = bus.getString("initselecteduser");
		StringBuffer sql=new StringBuffer("");
		sql.append("select user_id code,user_name name from priv_user where user_id in(").append(userids).append(")");
		return dbSvr.getListResult(sql.toString(),null);
	}
	public List<Map> queryUserInfoByUserIdsOrder(DataMsgBus bus) {
		DbSvr dbSvr = DbSvr.getDbService("gwsys");
		String frrId = bus.getString("frr_id");
		StringBuffer sql=new StringBuffer("");
		sql.append("select pu.user_id code, pu.user_name name from priv_user pu,gzl_user_frr uf where pu.user_id=uf.user_id and uf.frr_id = ? order by uf.frr_user_id");
		return dbSvr.getListResult(sql.toString(),new Object[]{frrId});
	}
	/**
	 * 查询单位或部门下的人员包括人员委托情况
	 * @param bus
	 * @return
	 */
	public List<Map> queryUsersByWTAndDeptId(DataMsgBus bus) {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		Integer deptid = bus.getInteger("deptid");
		Integer mj = bus.getInteger("mj");
		return dbSvr.getListResult("select case when ww.wcwt_id is not null and ww.wtr is not null then ww.wtrid else t.user_id end code, case when ww.wcwt_id is not null and ww.wtr is null and ww.wc_kssj >= sysdate and ww.wc_jssj > sysdate then t.user_name || '（外出未委托）' when ww.wcwt_id is not null and ww.wtr is not null and ww.wc_kssj >= sysdate and ww.wc_jssj > sysdate then t.user_name || '（委托给' || ww.wtr || '）' else t.user_name  end name, t.titie titie from  ( select u.user_id user_id, u.user_name user_name, u.user_name titie from priv_user u, priv_unit_user uu where u.user_id = uu.user_id and uu.unit_id = ? and u.user_secret >= ? order by u.user_sort) t, (select * from gw_wcwt w where w.wc_kssj >= sysdate and w.wc_jssj > sysdate) ww where t.user_id=ww.lrrid(+)",new Object[] { deptid,mj });
	}
	
	/**
	 * 查询人员包括人员委托情况
	 * @param bus
	 * @return
	 */
	public List<Map> queryUsersByWT(DataMsgBus bus) {
		DbSvr dbSvr = DbSvr.getDbService("gwsys");
		Integer mj = bus.getInteger("mj");
		Integer fr_id = bus.getInteger("fr_id");
		return dbSvr.getListResult("select distinct u.user_id code, case when ww.wcwt_id is not null and ww.wtr is null and ww.wc_kssj >= sysdate and ww.wc_jssj > sysdate then u.user_name || '（外出未委托）' when ww.wcwt_id is not null and ww.wtr is not null and ww.wc_kssj >= sysdate and ww.wc_jssj > sysdate then u.user_name || '（委托给' || ww.wtr || '）' else u.user_name end name, u.user_name titie, u.frr_user_id from (select distinct pu.*, f.fr_id, fu.frr_user_id from gzl_flowrole f, GZL_FR_ROLE r, GZL_FRR_FLOWROLE fr, GZL_USER_FRR fu, priv_user pu where f.fr_id = fr.fr_id and fr.frr_id = r.frr_id and fr.frr_id = fu.frr_id and fu.user_id = pu.user_id) u, priv_unit_user uu, (select * from gw_wcwt w where w.wc_kssj >= sysdate and w.wc_jssj > sysdate) ww where u.user_id = uu.user_id and ww.lrrid(+) = u.user_id and u.user_secret >= ? and u.fr_id = ? order by u.frr_user_id",new Object[] { mj,fr_id });
	}
	/**
	 * 递归查询本单位及下级单位
	 * @param bus
	 * @return
	 */
	public List<Map> querySubDeptListByUnitId(DataMsgBus bus) {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		Integer deptid = bus.getInteger("unit_id");
		return dbSvr
				.getListResult("select unit_id as code ,unit_name as name from  priv_unit start with unit_id =?  and abate=1 connect by prior unit_id = uper_unit_id  order SIBLINGS BY PRIV_UNIT.UNIT_SORT",
						new Object[] { deptid });
	}
	/**
	 * 单位领导
	 * @param arg1
	 * @return
	 */
	public List<Map> queryLeaderByUnitIdAndType(DataMsgBus arg1) {
		DbSvr dbSvr = DbSvr.getDbService("acc_sys");
		String dept_id = arg1.getString("dept_id");
		String yhfw = arg1.getString("yhfw");
		StringBuffer sql=new StringBuffer("");
		Object[] param=null;
		if("其他处室意见".equals(yhfw)){
			sql.append("select distinct u.user_id code,u.user_name name,u.user_name titie from PRIV_UNIT_LEADER u where unit_id != ?");
			param=new Object[]{dept_id};
		}else{
			sql.append("select distinct u.user_id code,u.user_name name,u.user_name titie from PRIV_UNIT_LEADER u where unit_id= ? and LEADER_TYPE=?");
			param=new Object[]{dept_id,yhfw};
		}
		return dbSvr.getListResult(sql.toString(),param);
	}

	public List<Map> queryLeaderByUnitIdAndType2(DataMsgBus arg1) {
		DbSvr dbSvr = DbSvr.getDbService("gwsys");
		String yhfw = arg1.getString("yhfw");
		StringBuffer sql=new StringBuffer("");
		
		sql.append("SELECT DISTINCT u . user_id code ,u . user_name NAME ,u . user_name titie"
				+ " FROM gw_yhlb_user yu,priv_user u WHERE yu.user_id=u.user_id and yu.yhlb_id=?");
		
		return dbSvr.getListResult(sql.toString(),new Object[]{yhfw});
	}
	
}
