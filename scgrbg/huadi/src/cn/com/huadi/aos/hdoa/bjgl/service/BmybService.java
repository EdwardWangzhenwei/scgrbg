/**
 * 
 */
package cn.com.huadi.aos.hdoa.bjgl.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet;
import cn.com.huadi.aos.hdoa.common.util.GwBsUtil2;

import com.aisino.platform.core.event.Sys;
import com.aisino.platform.db.Crud;
import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.Eso;
import com.aisino.platform.db.Page;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.db.Urud;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.DataMsgBus;

/**
 * @author Edward
 * @function 个人待办的Service实现，操作数据库
 * @date 2017年9月11日
 */
public class BmybService {
	/**
	 * @function 查询会议登记(加上权限判断：userinfo里的用户是否与JD_JDXX里的增加人一致);要求记录密级要小于等于当前登录人的密级
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public List<Map> queryByPage(DataMsgBus arg1) throws Exception {
		
		DbSvr db = DbSvr.getDbService("hddbtx");
		
		arg1.put("state", "已办");	
		arg1.put("do_or_read", "待办");	
		arg1.put("list_type", "部门待办");	
		Map userInfo=(Map) SessUtil.getRequest().getSession().getAttribute("USERINFO");
		// 从userinfo里面取出用户信息与数据库的增加人比较
		Object user_name = userInfo.get("user_name");
		Object user_id = userInfo.get("user_id");
		Object dept_id = userInfo.get("dept_id");
		Object user_secret = userInfo.get("user_secret");
		arg1.put("dept_id", dept_id);
		arg1.put("user_secret", user_secret);
		/*System.err.println("执行到Service");*/
		StringBuffer s = new StringBuffer("select d.list_id,d.title,d.security,d.send_unit,d.sender,d.send_time,d.subsystem,d.state,d.finish_time,d.open_type,d.open_url from public_todolist d"
				+ " where 1=1 ");
		s.append(" and $between(d.security,0,user_secret)");
		s.append(" and $equal(d.state,state)");
		s.append(" and $equal(d.do_or_read,do_or_read)");
		s.append(" and $equal(d.list_type,list_type)");
		s.append(" and $equal(d.receive_unit_id,dept_id)");
		s.append(" and $like(d.subsystem,subsystem)");
		s.append(" and $like(d.title,title)");
		
	
		s.append(" order by d.finish_time desc");
		
		return db.getListResultByPage(arg1, new SqlInfo(String.valueOf(s)));
	}
	
	
	
	
	
	/**
	 * @function 根据序列获取ID方法
	 */
	/*private int getMaxId(){
		int id =0; 
		Map res = DbSvr.getDbService(null).getOneRecorder("select seq_jdxx.NEXTVAL as id  from dual ", null);
		//Map res = DbSvr.getDbService(null).getOneRecorder("select nvl(max(hy_id)+1,1) as id from hy_jbxx ", null);
		if(res!=null){
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}
		return id;
	}*/

	/**
	 * @function 根据ID查询接待信息记录
	 * @param jdid
	 * @return
	 */
	/*public Map queryById(DataMsgBus bus){
		StringBuffer s = new StringBuffer("select * from jd_jdxx")
			.append(" where jdxx_id= ?");
		return DbSvr.getDbService(null).getOneRecorder(String.valueOf(s), bus.get("jdxx_id"));
	}
*/
	/**
	 * @function 根据ID更新接待信息记录的方法
	 * @param arg1
	 */
	public void update(DataMsgBus bus) throws Exception {
		Object bt = bus.get("bt");
		/*Object mj = bus.get("mj");*/
		Object nr = bus.get("nr");
		Object bz = bus.get("bz");
		Object jdid = bus.get("jdxx_id");
		
			DbSvr dbSvr = DbSvr.getDbService("jdsys");
			
			StringBuffer sUpdate = new StringBuffer("UPDATE jd_jdxx SET bt={},nr={},bz={} ")
			.append("where jdxx_id={}");
			String param1="bt,nr,bz,jdxx_id";
			Object[] values1=new Object[]{bt,nr,bz,jdid};
			Urud urud1=new Urud(sUpdate.toString());
			urud1.define(param1,values1);
			dbSvr.execute(urud1.getUpdateEso());
		
	}
	

	



	

	/**
	 * @function 根据ID查询记录状态。
	 * @param arg1
	 * @return
	 */
	public int queryStateById(DataMsgBus bus) {
		int flag=0;
		StringBuffer s = new StringBuffer("select count(zt) as num from jd_jdxx");
		s.append(" where 1=1");
		s.append(" and zt!=");
		s.append("'未上报'");
		
		s.append(" and jdxx_id= ?");
		Map<String, Object> res = DbSvr.getDbService(null).getOneRecorder(String.valueOf(s),bus.get("jdxx_id"));
		if(res.get("num")!=null){
			flag = Integer.parseInt(String.valueOf(res.get("num")));
		}

		return flag;

	}
	
	
}
