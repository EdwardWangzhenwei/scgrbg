
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
public class GrwdService {
	/**
	 * @function 查询未读消息
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public List<Map> queryByPage(DataMsgBus arg1) throws Exception {
		
		DbSvr db = DbSvr.getDbService("hddbtx");
		
		arg1.put("state", "未读");	
			
		arg1.put("list_type", "个人消息");	
		Map userInfo=(Map) SessUtil.getRequest().getSession().getAttribute("USERINFO");
		// 从userinfo里面取出用户信息与数据库的增加人比较
		Object user_name = userInfo.get("user_name");
		Object user_id = userInfo.get("user_id");
		Object user_secret = userInfo.get("user_secret");
		arg1.put("user_id", user_id);
		arg1.put("user_secret", user_secret);
		/*System.err.println("执行到Service");*/
		StringBuffer s = new StringBuffer("select d.mess_id,d.title,d.security,d.sender,d.send_time,d.subsystem,d.state,d.message,d.open_type,d.open_url from public_message d"
				+ " where 1=1 ");
		s.append(" and $between(d.security,0,user_secret)");
		s.append(" and $equal(d.state,state)");
		
		s.append(" and $equal(d.list_type,list_type)");
		s.append(" and $equal(d.receiver_id,user_id)");
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
	 * @function 根据ID更新接待信息记录的方法
	 * @param arg1
	 */
	/*public void update(DataMsgBus bus) throws Exception {
		Object bt = bus.get("bt");
		Object mj = bus.get("mj");
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
		
	}*/
	
	
	
	
	
	/**
	 * 
	 * @function 根据ID做删除
	 * @param bus
	 */
	public void deleteById(DataMsgBus bus){
		StringBuffer s = new StringBuffer("delete from jd_jdxx where jdxx_id=").append(bus.get("jdxx_id"));
		DbSvr.getDbService(null).execute(String.valueOf(s));
	}


	/**
	 * @function 这是消息提醒时调用的后台方法
	 * @param arg1
	 */
	public String queryParamMsg() {
		// TODO Auto-generated method stub
		DbSvr db = DbSvr.getDbService("hddbtx");
		
		
		/*Map userInfo=(Map) SessUtil.getRequest().getSession().getAttribute("USERINFO");
		// 从userinfo里面取出用户信息与数据库的增加人比较
		Object user_name = userInfo.get("user_name");
		Object user_id = userInfo.get("user_id");
		Object user_secret = userInfo.get("user_secret");*/
		
		/*System.err.println("执行到Service");*/
		StringBuffer s = new StringBuffer("select d.mess_id,d.title,d.security,d.sender,d.send_time,d.subsystem,d.state,d.message,d.open_type,d.open_url from public_message d"
				+ " where 1=1 ");
		/*s.append(" and $between(d.security,0,user_secret)");
		s.append(" and $equal(d.state,state)");
		
		s.append(" and $equal(d.list_type,list_type)");
		s.append(" and $equal(d.receiver_id,user_id)");
		s.append(" and $like(d.subsystem,subsystem)");
		s.append(" and $like(d.title,title)");
		
	
		s.append(" order by d.mess_id desc");*/
		
		return "OK";
	}
	/**
	 * @function 这是消息提醒时调用的后台方法
	 * @param arg1
	 */
	@SuppressWarnings("finally")
	public List<Map> queryListWarn(int unitid,int deptid,int user_id,int user_secret) {
		// TODO Auto-generated method stub
		DbSvr db = DbSvr.getDbService("hddbtx");
		
		
		/*Map userInfo=(Map) SessUtil.getRequest().getSession().getAttribute("USERINFO");
		// 从userinfo里面取出用户信息与数据库的增加人比较
		Object user_name = userInfo.get("user_name");
		Object user_id = userInfo.get("user_id");
		Object user_secret = userInfo.get("user_secret");*/
		int userid=Integer.parseInt(String.valueOf(user_id));
		/*System.err.println("执行到Service");*/
		List<Map> list = null;
		try{
		StringBuffer s = new StringBuffer("select d.mess_id,d.title,d.security,d.send_unit,d.sender,d.send_time,d.subsystem,d.state,d.message,d.open_type,d.open_url from public_message d"
				+ " where 1=1 ");
		s.append("AND NVL(d.security,0)<=");
		s.append(user_secret);
		s.append(" AND (d.receiver_id=");
		s.append(userid);
		
		s.append(" OR d.receive_unit_id=");
		s.append(deptid);
		
		s.append(" OR d.receive_unit_id=");
		s.append(unitid);
		
		s.append(") AND d.state=");
		s.append("'未读'");
	/*	s.append(" AND d.list_type=");
		s.append("'个人消息'");*/
		/*s.append(" and $between(d.security,0,user_secret)");
		s.append(" and $equal(d.state,state)");
		
		s.append(" and $equal(d.list_type,list_type)");
		s.append(" and $equal(d.receiver_id,user_id)");
		s.append(" and $like(d.subsystem,subsystem)");
		s.append(" and $like(d.title,title)");*/
		
		
		s.append(" order by d.send_time desc");
		list = db.getListResult(String.valueOf(s), null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.release();
			return list;
		}
		
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
	 * @function 根据ID查询查看状态。
	 * @param arg1
	 * @return
	 */
	@SuppressWarnings("finally")
	public int queryStateById(int id) {
		int flag=0;
		StringBuffer s = new StringBuffer("select count(viewer_id) as num from public_message");
		s.append(" where 1=1");
		
		
		s.append(" and mess_id= ?");
		DbSvr dbSvr = DbSvr.getDbService(null);
		try{
		Map<String, Object> res = dbSvr.getOneRecorder(String.valueOf(s),id);
		if(res.get("num")!=null && Integer.parseInt(res.get("num").toString())!=0){
			flag = 1;
		}
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			dbSvr.release();
			return flag;
		}
		
	}

	
	/**
	 * @function 根据ID更新viewer，view_time viewer_id的值
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
		
		if (view_time != null) {
			hyKey.append(",view_time=");
			hyKey.append("to_date('" + view_time + "','%Y-%m-%d %H:%M')");
		}
		
		if (state != null) {
			hyKey.append(",state=");
			hyKey.append("'" + state + "'");
		}
		
		if (mess_id != null) {
			hyKey.append(" WHERE mess_id=");
			hyKey.append(mess_id);
		}
		
		dbSvr.execute(hyKey.toString());
	}
	/**
	 * @function 根据ID更新viewer，view_time viewer_id的值  JSP页面调用的方法
	 * @param arg1
	 */
	public void updateStateById(int id,Map userInfo) {
		// TODO Auto-generated method stub
		/*Map userInfo = (Map) arg1.get("userInfo");*/
		String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm");
		userInfo.put("view_time",nowdate);
		Object viewer_id = null;
	/*	System.err.println("Service执行更新");*/
		Object viewer = null;
		if(userInfo != null){
			// 从userinfo里面取出用户信息与数据库的增加人比较
			viewer = userInfo.get("user_name");
			viewer_id = userInfo.get("user_id");
			Object user_secret = userInfo.get("user_secret");
			userInfo.put("user_secret", user_secret);
		}
		Object view_time = userInfo.get("view_time");
		Object mess_id = id;
		Object state = "已读";
		userInfo.put("state", state);
		
		DbSvr dbSvr = DbSvr.getDbService("hddbtx");
		
		/*System.err.println("这是一个更新的方法public_message");*/
		
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
		
		if (mess_id != null) {
			hyKey.append(" WHERE mess_id=");
			hyKey.append(mess_id);
		}
		
		try{
			dbSvr.setAutoCommit(true);
			dbSvr.execute(hyKey.toString());
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			dbSvr.release();
		}
	}


	/**
	 * @function 更新记录的view状态时获取到该记录的main_table,primary_key_value到arg1中
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
	/**
	 * @function 更新记录的view状态时获取到该记录的main_table,primary_key_value到arg1中    JSP页面调用的方法
	 * @param arg1
	 */
	@SuppressWarnings("finally")
	public Map getKeyById(int id) {
		// TODO Auto-generated method stub
		DbSvr dbSvr = DbSvr.getDbService("hddbtx");
		int mess_id = id;
		StringBuffer xxKey = new StringBuffer("");
		xxKey.append("SELECT main_table,primary_key_value FROM public_message ");
		xxKey.append(" WHERE mess_id=");
		xxKey.append(mess_id);
		Map arg1=null;
		try{
		Map<String, Object> map = dbSvr.getOneRecorder(xxKey.toString(), null);
		String main_table = (String) map.get("main_table");
		int primary_key_value = (int) map.get("primary_key_value");
		arg1=new HashMap<String,String>();
		
		arg1.put("main_table", main_table);
		arg1.put("primary_key_value", primary_key_value);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbSvr.release();
			return arg1;
		}
	}


	/**
	 * @function 根据key信息查询public_message里面是否有对应状态的值
	 * @param arg1
	 * @return
	 */
	public int queryStateByKey(DataMsgBus bus) {
		// TODO Auto-generated method stub
		int flag=0;
		StringBuffer s = new StringBuffer("select count(viewer_id) as num from public_message");
		s.append(" where 1=1");
	/*	System.err.println("Service执行查询");*/
		
		s.append(" and main_table= ?");
		s.append(" and primary_key_value= ?");
		Map<String, Object> res = DbSvr.getDbService(null).getOneRecorder(String.valueOf(s),bus.get("main_table"),bus.get("primary_key_value"));
		if(res.get("num")!=null && Integer.parseInt(res.get("num").toString())!=0){
			flag = 1;
		}

		return flag;
	}


	/**
	 * @function 根据key更新相应的view信息
	 * @param arg1
	 */
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
			hyKey.append("'"+main_table+"'");
		}
		
		if (primary_key_value != null) {
			hyKey.append(" and primary_key_value=");
			hyKey.append(primary_key_value);
		}
		
		dbSvr.execute(hyKey.toString());
	}
	
}
