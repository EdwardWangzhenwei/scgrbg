package cn.com.huadi.aos.hdoa.login.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.util.DateUtil;


public class LoginService {
	public LoginService() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Map queryUserByAccountName(String userAccountName,String pwd) throws Exception{
		Object[] params = new Object[1];
		params[0] = userAccountName;
		Map<String,Object> result = new HashMap();
		DbSvr db = DbSvr.getDbService("acc_sys");
	    List<Map> map = db.queryIdForList("login.queryuser", params);
	  //如果账号不存在
	    if(map ==null){
	    	result.put("result", "error");
	    	result.put("message", "您输入的用户不存在，请重新输入");
	    }
	    else{
	    	String adminpwd = String.valueOf(map.get(0).get("user_password"));
	    	int lockflag = Integer.parseInt(String.valueOf(map.get(0).get("lock_flag")));
	    	StringBuffer sql = new StringBuffer();
	    	String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
	    	sql.append("update PRIV_USER set lock_flag = # ,lock_time=to_date('").append(
	    	nowdate+"','%Y-%m-%d %H:%M:%S') where USER_ACCOUNT_NAME='")
	    	.append(userAccountName).append("'");
	    	
	    	//获取系统配置文件中定义的连续登陆错误次数参数
	    	int def_lock_falg = 5;
	    	if(adminpwd.equals(pwd)){
	    		//如果错误次数小于5次则登陆成功
	    	
	    		if(lockflag<def_lock_falg){
	    			//更新错误次数为0
	    			sql = new StringBuffer(sql.toString().replace("#","0"));
	    			db.execute(sql.toString());
	    			result.put("result", "ok");
	    			result.put("data", map.get(0));
	    		}
	    		else{ 
	    			result.put("result", "error");
		    		result.put("message", "密码错误次数超过"+def_lock_falg+"次，该用户已锁定，请联系管理员解锁");
	    		}
	    	}
	    	else{
	    		
	    		lockflag = lockflag+1;
	    		int exp = def_lock_falg-lockflag;
	    		//更新错误次数+1
	    		
	    		sql = new StringBuffer(sql.toString().replace("#",lockflag+""));
	    		db.execute(sql.toString());
	    		if(exp>0){
	    		result.put("result", "error");
	    		result.put("message", "密码错误，您还有"+exp+"次尝试机会，连续输入错误"+def_lock_falg+"次，用户将被锁定");
	    		}
	    		else{
		    	  result.put("result", "error");
			      result.put("message", "密码错误次数超过"+def_lock_falg+"次，用户已锁定，请联系管理员解锁");
	    		}
	    	}
	    	
	  
	    }
	    
	    db.commit();
	    db.release();
	    
	    return result;
	}
	
	
	public Map queryUserByAccountName(String userAccountName) throws Exception{
		Object[] params = new Object[1];
		params[0] = userAccountName;
		Map<String,Object> result = new HashMap();
		DbSvr db = DbSvr.getDbService("acc_sys");
	    @SuppressWarnings("rawtypes")
		List<Map> map = db.queryIdForList("login.queryuser", params);
	  //如果账号不存在
	    if(map ==null){
	    	result.put("result", "error");
	    	result.put("message", "用户不存在");
	    }
	    else{
	    	int lockflag = Integer.parseInt(String.valueOf(map.get(0).get("lock_flag")));
	    	StringBuffer sql = new StringBuffer();
	    	String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
	    	sql.append("update PRIV_USER set lock_flag = # ,lock_time=to_date('").append(
	    	nowdate+"','%Y-%m-%d %H:%M:%S') where USER_ACCOUNT_NAME='")
	    	.append(userAccountName).append("'");
	    	
	    	result.put("result", "ok");
			result.put("data", map.get(0));
	    }
	    db.commit();
	    db.release();
	    
	    return result;
	}
	public Map queryUnitByDeptid(int deptid) {
		// TODO Auto-generated method stub
		Object[] params = new Object[1];
		params[0] = deptid;
		Map<String,Object> result = new HashMap();
		DbSvr db=DbSvr.getDbService("acc_sys");
	    List<Map> map = db.queryIdForList("login.queryunit", params);
	    if(map!=null){
	    	if(map.size()>0){
	    		/*return map.get(0);*/
	    		//添加登录人所在组织机构递归拼接字符串 lwf update 20160903
	    		boolean flag=false;
	    		Map m=null;
	    		Map m2=null;
	    		String allunits="";
	    		for(int i=0;i<map.size();i++){
	    			m2=map.get(i);
	    			if(!flag&&"1".equals(String.valueOf(m2.get("unit_flag")))){
	    				m=m2;
	    				m.put("unit_name_short", m2.get("unit_name"));
	    				flag=true;
	    			}
	    			if(flag){
	    				allunits=m2.get("unit_name")+"-"+allunits;
	    				m2=null;
	    			}
	    		}
	    		if(flag){
	    			allunits=(String) allunits.subSequence(0,allunits.length()-1);
	    		}
	    		if(m!=null){
	    			m.put("unit_name", allunits);
	    		}
	    		db.commit();
	    		db.release();
	    		return m;
	    	}
	    	
	    	else {
	    		db.commit();
	    		db.release();
	    		return null;}
	    }
	    else {
	    	db.commit();
    		db.release();
	    	return null;}
		
	}
	
	public Map queryDeptByDeptid(int deptid) {
		// TODO Auto-generated method stub
		Object[] params = new Object[1];
		params[0] = deptid;
		Map<String,Object> result = new HashMap();
		DbSvr db=DbSvr.getDbService("acc_sys");
	    List<Map> map = db.queryIdForList("login.queryunit", params);
	    if(map!=null){
	    	if(map.size()>0){
	    		boolean flag=false;
	    		Map m=null;
	    		Map m2=null;
	    		String allunits="";
	    		for(int i=0;i<map.size();i++){
	    			m2=map.get(i);
	    			if(!flag&&"0".equals(String.valueOf(m2.get("unit_flag")))){
	    				m=m2;
	    				m.put("unit_name_short", m2.get("unit_name"));
	    				flag=true;
	    			}
	    			if(flag){
	    				allunits=m2.get("unit_name")+"-"+allunits;
	    			}
	    		}
	    		if(flag){
	    			allunits=(String) allunits.subSequence(0, allunits.length()-1);
	    		}
	    		if(m!=null){
	    			m.put("unit_name", allunits);
	    		}
	    		db.commit();
	    		db.release();
	    		return m;
	    	}
	    	else {
	    		db.commit();
	    		db.release();
	    		return null;}
	    }
	    else {
	    	db.commit();
    		db.release();
	    	return null;}
		
	}
	
	
	public Map querySelfByDeptid(int deptid) {
		// TODO Auto-generated method stub
		Object[] params = new Object[1];
		params[0] = deptid;
		Map<String,Object> result = new HashMap();
		boolean flag=true;
		DbSvr db=DbSvr.getDbService("acc_sys");
	    List<Map> map = db.queryIdForList("login.queryunit", params);
	    if(map!=null){
	    	if(map.size()>0){
	    		Map m=null;
	    		String allunits="";
	    		for(int i=0;i<map.size();i++){
	    			Map m2=map.get(i);
	    			if(flag){
	    				m=m2;
	    			m.put("unit_name_short", m2.get("unit_name"));
	    			flag=false;
	    			}
	    			allunits=m2.get("unit_name")+"-"+allunits;
	    		}
	    		allunits=(String) allunits.subSequence(0, allunits.length()-1);
	    		if(m!=null){
	    			m.put("unit_name", allunits);
	    		}
	    		db.commit();
	    		db.release();
	    		return m;
	    	}
	    	else {
	    		db.commit();
	    		db.release();
	    		return null;}
	    }
	    else {
	    	db.commit();
    		db.release();
	    	return null;}
		
	}
}

