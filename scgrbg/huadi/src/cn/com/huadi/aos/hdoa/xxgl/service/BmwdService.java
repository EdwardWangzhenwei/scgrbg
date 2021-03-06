/**
 * 
 */
package cn.com.huadi.aos.hdoa.xxgl.service;

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
import com.aisino.platform.util.DateUtil;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.DataMsgBus;

/**
 * @author Edward
 * @function 个人待办的Service实现，操作数据库
 * @date 2017年9月11日
 */
public class BmwdService {
	/**
	 * @function 查询会议登记(加上权限判断：userinfo里的用户是否与JD_JDXX里的增加人一致);要求记录密级要小于等于当前登录人的密级
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public List<Map> queryByPage(DataMsgBus arg1) throws Exception {
		
		DbSvr db = DbSvr.getDbService("hddbtx");
		
		arg1.put("state", "未读");	
		
		arg1.put("list_type", "部门消息");	
		Map userInfo=(Map) SessUtil.getRequest().getSession().getAttribute("USERINFO");
		// 从userinfo里面取出用户信息与数据库的增加人比较
		Object user_name = userInfo.get("user_name");
		Object user_id = userInfo.get("user_id");
		Object dept_id = userInfo.get("dept_id");
		Object user_secret = userInfo.get("user_secret");
		Object unit_id = userInfo.get("unit_id");
		arg1.put("unit_id", unit_id);
		arg1.put("dept_id", dept_id);
		arg1.put("user_secret", user_secret);
		/*System.err.println("执行到Service");*/
		StringBuffer s = new StringBuffer("select d.mess_id,d.title,d.security,d.sender,d.send_time,d.subsystem,d.state,d.message,d.open_type,d.open_url from public_message d"
				+ " where 1=1 ");
		s.append(" and $between(d.security,0,user_secret)");
		s.append(" and $equal(d.state,state)");
		s.append(" and $equal(d.do_or_read,do_or_read)");
		
		s.append(" and ($equal(d.receive_unit_id,dept_id)");
		s.append(" or $equal(d.receive_unit_id,unit_id))");
		s.append(" and $like(d.subsystem,subsystem)");
		s.append(" and $like(d.title,title)");
		
	
		s.append(" order by d.send_time desc");
		
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
	 * 
	 * @function 根据ID上报数据
	 * @param arg1
	 */
	public List<Map> queryListMsg(int deptid,int user_secret) {
		
		
		DbSvr db = DbSvr.getDbService("hddbtx");
		
		
		/*Map userInfo=(Map) SessUtil.getRequest().getSession().getAttribute("USERINFO");
		// 从userinfo里面取出用户信息与数据库的增加人比较
		Object user_name = userInfo.get("user_name");
		Object user_id = userInfo.get("user_id");
		Object user_secret = userInfo.get("user_secret");*/
		
	/*	System.err.println("这是部门未读信息执行到Service");
		System.err.println("这是部门信息："+deptid);*/
		StringBuffer s = new StringBuffer("select d.mess_id,d.title,d.security,d.sender,d.send_time,d.subsystem,d.state,d.message,d.open_type,d.open_url from public_message d"
				+ " where 1=1 ");
		s.append("AND d.security<=");
		s.append(user_secret);
		s.append(" AND d.receive_unit_id=");
		s.append(deptid);
		s.append(" AND d.state=");
		s.append("'未读'");
		/*s.append(" AND d.list_type=");
		s.append("'部门消息'");*/
		/*s.append(" and $between(d.security,0,user_secret)");
		s.append(" and $equal(d.state,state)");
		
		s.append(" and $equal(d.list_type,list_type)");
		s.append(" and $equal(d.receiver_id,user_id)");
		s.append(" and $like(d.subsystem,subsystem)");
		s.append(" and $like(d.title,title)");*/
		
		
		s.append(" order by d.send_time desc");
		return db.getListResult(String.valueOf(s), null);
		
	}
	
	
	





	/**
	 * @function 根据ID查询查看状态。
	 * @param arg1
	 * @return
	 */
	public int queryStateById(DataMsgBus bus) {
		int flag=0;
		StringBuffer s = new StringBuffer("select count(viewer_id) as num from public_message");
		s.append(" where 1=1");
		
		
		s.append(" and mess_id= ?");
		Map<String, Object> res = DbSvr.getDbService(null).getOneRecorder(String.valueOf(s),bus.get("mess_id"));
		if(res.get("num")!=null && Integer.parseInt(res.get("num").toString())!=0){
			flag = 1;
		}

		return flag;

	}

	
	/**
	 * @function 根据ID更新viewer，view_time viewer_id的值;更新state为“已读”
	 * @param arg1
	 */
	public void updateStateById(DataMsgBus arg1) {
		// TODO Auto-generated method stub
		Map userInfo = (Map) arg1.get("userInfo");
		String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm");
		arg1.put("view_time",nowdate);
		Object viewer_id = null;
		/*System.err.println("Service执行更新");*/
		Object viewer = null;
		if(userInfo != null){
			// 从userinfo里面取出用户信息与数据库的增加人比较
			viewer = userInfo.get("user_name");
			viewer_id = userInfo.get("user_id");
			Object user_secret = userInfo.get("user_secret");
			arg1.put("user_secret", user_secret);
		}
		Object view_time = arg1.get("view_time");
		Object mess_id = arg1.get("mess_id");
		Object state = "已读";
		arg1.put("state", state);
		
		DbSvr dbSvr = DbSvr.getDbService("hddbtx");
		
		
		
		StringBuffer hyKey = new StringBuffer("");
		hyKey.append("UPDATE public_message SET ");
		
		if (viewer_id != null) {
			hyKey.append("viewer_id=");
			hyKey.append(viewer_id);
		}
		if (viewer != null) {
			hyKey.append(",viewer=");
			hyKey.append("'" + viewer + "'");
		}
		
		if (state != null) {
			hyKey.append(",state=");
			hyKey.append("'" + state + "'");
		}
		
		if (view_time != null) {
			hyKey.append(",view_time=");
			hyKey.append("to_date('" + view_time + "','%Y-%m-%d %H:%M')");
		}
		
		if (mess_id != null) {
			hyKey.append(" WHERE mess_id=");
			hyKey.append(mess_id);
		}
		
		dbSvr.execute(hyKey.toString());
	}


	/**
	 * @function 根据ID获取指定部门的main_table
	 * @param arg1
	 */
	public void getKeyById(DataMsgBus arg1) {
		// TODO Auto-generated method stub
		DbSvr dbSvr = DbSvr.getDbService("hddbtx");
		Object mess_id = arg1.get("mess_id");
		StringBuffer xxKey = new StringBuffer("");
		xxKey.append("SELECT main_table,primary_key_value FROM public_message ");
		xxKey.append(" WHERE mess_id=");
		xxKey.append(mess_id);
		Map<String, Object> map = dbSvr.getOneRecorder(xxKey.toString(), null);
		String main_table = (String) map.get("main_table");
		int primary_key_value = (int) map.get("primary_key_value");
		arg1.put("main_table", main_table);
		arg1.put("primary_key_value", primary_key_value);
	}


	public int queryStateByKey(DataMsgBus bus) {
		// TODO Auto-generated method stub
		int flag=0;
		StringBuffer s = new StringBuffer("select count(viewer_id) as num from public_message");
		s.append(" where 1=1");
		/*System.err.println("Service执行查询");*/
		
		s.append(" and main_table= ?");
		s.append(" and primary_key_value= ?");
		Map<String, Object> res = DbSvr.getDbService(null).getOneRecorder(String.valueOf(s),bus.get("main_table"),bus.get("primary_key_value"));
		if(res.get("num")!=null && Integer.parseInt(res.get("num").toString())!=0){
			flag = 1;
		}

		return flag;
	}


	public void updateStateByKey(DataMsgBus arg1) {
		// TODO Auto-generated method stub
		Map userInfo = (Map) arg1.get("userInfo");
		String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm");
		arg1.put("view_time",nowdate);
		Object viewer_id = null;
		/*System.err.println("Service执行更新");*/
		Object viewer = null;
		if(userInfo != null){
			// 从userinfo里面取出用户信息与数据库的增加人比较
			viewer = userInfo.get("user_name");
			viewer_id = userInfo.get("user_id");
			Object user_secret = userInfo.get("user_secret");
			arg1.put("user_secret", user_secret);
		}
		Object view_time = arg1.get("view_time");
		Object main_table = arg1.get("main_table");
		Object primary_key_value = arg1.get("primary_key_value");
		Object state = "已读";
		arg1.put("state", state);
		
		DbSvr dbSvr = DbSvr.getDbService("hddbtx");
		
		
		
		StringBuffer hyKey = new StringBuffer("");
		hyKey.append("UPDATE public_message SET ");
		
		if (viewer_id != null) {
			hyKey.append("viewer_id=");
			hyKey.append(viewer_id);
		}
		if (viewer != null) {
			hyKey.append(",viewer=");
			hyKey.append("'" + viewer + "'");
		}
		
		if (view_time != null) {
			hyKey.append(",view_time=");
			hyKey.append("to_date('" + view_time + "','%Y-%m-%d %H:%M')");
		}
		
		if (state != null) {
			hyKey.append(",state=");
			hyKey.append("'" + state + "'");
		}
		
		if (main_table != null) {
			hyKey.append(" WHERE main_table=");
			hyKey.append(main_table);
		}
		
		if (primary_key_value != null) {
			hyKey.append(" and primary_key_value=");
			hyKey.append("'" + primary_key_value + "'");
		}
		
		dbSvr.execute(hyKey.toString());
	}
	
}
