package cn.com.huadi.aos.hdoa.common.service;

import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.view.DataMsgBus;

public class LiuyanService {

	public List<Map> queryLiuyan(DataMsgBus arg1) {
		// TODO Auto-generated method stub
		DbSvr dbSvr=DbSvr.getDbService(null);
		Object wjbm=arg1.get("wjbm");
		/*String sql="select distinct to_char(n.n_send_date,'yyyy-mm-dd HH24:mi:ss') n_send_date,n.fi_objectid,n.n_sender,replace(tmp.exectorname,',','、') exectorname,n.n_lys"
				+ " from gzl_node n,(select wm_concat(nb.N_EXECUTORNAME) exectorname,wm_concat(nb.n_lys) n_lys"
				+ ",nb.n_uperid,nb.fi_objectid from gzl_node nb where nb.n_lys is not null"
				+ " and  $equal(nb.fi_objectid,wjbm)  group by nb.n_uperid,nb.fi_objectid) tmp"
				+ " where n.n_uperid=tmp.n_uperid and  n.fi_objectid=tmp.fi_objectid and n.n_lys is not null"
				+ " order by n_send_date desc";*/
		String sql="select distinct to_char(n.n_send_date,'yyyy-mm-dd HH24:mi:ss') n_send_date,n.fi_objectid,n.n_sender,n.n_executorname exectorname,n.n_lys"
				+ " from gzl_node n,(select wm_concat(nb.n_lys) n_lys"
				+ ",nb.n_uperid,nb.fi_objectid from gzl_node nb where nb.n_lys is not null"
				+ " and  $equal(nb.fi_objectid,wjbm)  group by nb.n_uperid,nb.fi_objectid) tmp"
				+ " where n.n_uperid=tmp.n_uperid and  n.fi_objectid=tmp.fi_objectid and n.n_lys is not null"
				+ " order by n_send_date desc";
		List<Map> list=dbSvr.getListResultByPage(arg1, new SqlInfo(sql));
		return list;
	}
}