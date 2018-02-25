package cn.com.huadi.aos.hdoa.common.service;

import java.util.Map;

import com.aisino.platform.db.DbSvr;

public class DefineJdid {

	
	public static int getMaxId(){
		int id =0; 
		Map res = DbSvr.getDbService(null).getOneRecorder("select seq_jdxx.NEXTVAL as id  from dual ", null);
		//Map res = DbSvr.getDbService(null).getOneRecorder("select nvl(max(dzwj_id)+1,1) as id from JD_DZWJ", null);
		if(res!=null){
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}
		return id;
	}
}
