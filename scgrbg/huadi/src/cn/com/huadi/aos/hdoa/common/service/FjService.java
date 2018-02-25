/**
 * 
 */
package cn.com.huadi.aos.hdoa.common.service;

import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.Urud;
import com.aisino.platform.view.DataMsgBus;

/**
 * @author qinyalin
 *
 * 2017年7月13日
 */
public class FjService {
	/**
	 * 通过会议id,关联表,关联表id查附件集
	 * @param bus
	 * @return
	 */
	public  List<Map>  QueryFjById(DataMsgBus bus){
			/*Integer jdxx_id=bus.getString("jdxx_id");*/
			Integer jdxx_id=bus.getInteger("jdxx_id");
			String CON_TABLE=bus.getString("CON_TABLE");
			DbSvr db=DbSvr.getDbService("hysys");
			StringBuffer sbf=new StringBuffer();
			sbf.append("select dzwj_id,dzwjms,lrsj,lrr,file_catalog,CON_TABLE_ID from jd_dzwj where CON_TABLE=? and CON_TABLE_ID=?");
			List<Map> list=db.getListResult(sbf.toString(),CON_TABLE, jdxx_id);
			return list;
	}
	/**
	 * 通过电子文件id查附件
	 * @param bus
	 * @return
	 */
	public Map QueryById(DataMsgBus bus){
		String dzwj_id=bus.getString("dzwj_id");
		DbSvr db=DbSvr.getDbService("jdsys");
		String sql="select dzwj_id,dzwjms,lrsj,lrr,file_catalog,CON_TABLE_ID from jd_dzwj where dzwj_id=?";
		return db.getOneRecorder(sql, dzwj_id);
}
	public  List<Map>  QueryFjByFaId(DataMsgBus bus){
		String fa_id=bus.getString("fa_id");
		String CON_TABLE=bus.getString("union_table");
		DbSvr db=DbSvr.getDbService("jdsys");
		StringBuffer sbf=new StringBuffer();
		sbf.append("select dzwj_id,dzwjms,lrsj,lrr,file_catalog,CON_TABLE_ID from jd_dzwj where CON_TABLE=? and CON_TABLE_ID=? and is_del<>1");
		List<Map> list=db.getListResult(sbf.toString(),CON_TABLE, fa_id);
		return list;
}
	/**
	 * 文件id删除
	 */
	public void DeleteFjById(DataMsgBus bus){
		String dzwj_id=bus.getString("dzwj_id");
		DbSvr db=DbSvr.getDbService("jdsys");
		StringBuffer sbf=new StringBuffer();
		sbf.append("update jd_dzwj set is_del=1 where dzwj_id={}");
		String param1="dzwj_id";
		Object[] values1=new Object[]{dzwj_id};
		Urud urud1=new Urud(sbf.toString());
		urud1.define(param1,values1);
		db.execute(urud1.getUpdateEso());
		db.commit();
		db.release();
	}
	/**
	 * 会议id删除
	 */
	public void deleteByHyId(DataMsgBus bus){
		Integer jdxx_id=bus.getInteger("jdxx_id");
		DbSvr db=DbSvr.getDbService("jdsys");
		StringBuffer sbf=new StringBuffer();
		sbf.append("update hy_dzwj set is_del=1 where CON_TABLE_ID='"+jdxx_id).append("'");
		String param1="jdxx_id";
		Object[] values1=new Object[]{jdxx_id};
		Urud urud1=new Urud(sbf.toString());
		urud1.define(param1,values1);
		db.execute(urud1.getUpdateEso());
		db.commit();
		db.release();
	}
}
