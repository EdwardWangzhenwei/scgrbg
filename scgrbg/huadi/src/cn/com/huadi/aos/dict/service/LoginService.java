package cn.com.huadi.aos.dict.service;

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
	public Map queryUserByAccountName(String adminAccountName,String pwd) throws Exception{
		Object[] params = new Object[1];
		params[0] = adminAccountName;
		Map<String,Object> result = new HashMap();
	    List<Map> map = DbSvr.getDbService("acc_sys").queryIdForList("login.queryadmin", params);
	    //如果管理员账号不存在
	    if(map ==null){
	    	result.put("result", "error");
	    	result.put("message", "您输入的管理员账号不存在，请重新输入");
	    	
	    }
	    else{
	    	boolean flag = false;
	    	String adminpwd = String.valueOf(map.get(0).get("administrator_password"));
	    	int lockflag = Integer.parseInt(String.valueOf(map.get(0).get("lock_flag")));
	    	StringBuffer sql = new StringBuffer();
	    	String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
	    	sql.append("update PRIV_ADMINMANAGER set lock_flag = # ,lock_time=to_date('").append(
	    	nowdate+"','%Y-%m-%d %H:%M:%S') where ADMINISTRATOR_ACCOUNT_NAME='")
	    	.append(adminAccountName).append("'");
	    	if(adminpwd.equals(pwd)){
	    		//如果错误次数小于等于5次则登陆成功
	    		if(lockflag<5){
	    			//更新错误次数为0
	    			sql = new StringBuffer(sql.toString().replace("#","0"));
	    			DbSvr.getDbService("acc_sys").execute(sql.toString());
	    			result.put("result", "ok");
	    			result.put("data", map.get(0));
	    		}
	    		else{ 
	    			result.put("result", "error");
		    		result.put("message", "密码错误次数超过5次，该账号已锁定，请联系管理员解锁");
	    		}
	    	}
	    	else{
	    		
	    		lockflag = lockflag+1;
	    		int exp = 5-lockflag;
	    		//更新错误次数+1
	    		
	    		sql = new StringBuffer(sql.toString().replace("#",lockflag+""));
	    		DbSvr.getDbService("acc_sys").execute(sql.toString());
	    		if(exp>0){
	    		result.put("result", "error");
	    		result.put("message", "密码错误，您还有"+exp+"次尝试机会，连续输入错误5次，您的账户将被锁定");
	    		}
	    		else{
		    	  result.put("result", "error");
			      result.put("message", "密码错误次数超过5次，该账号已锁定，请联系管理员解锁");
	    		}
		    	
	    		
	    	}
	    	
	  
	    }
	    return result;
	}
	//查询用户账号是否存在，如果存在 返回用户详细信息 不存在 返回null 为从门户接口登录使用
	
}
