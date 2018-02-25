package cn.com.huadi.aos.hdoa.dict.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.Eso;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.view.DataMsgBus;

public class DictService {

	
	public int save(DataMsgBus bus) throws Exception{
		DbSvr db = DbSvr.getDbService(null);
		
		Object conTable = bus.get("con_table");
		Object conTableId = bus.get("con_tableid");
		Object dictType = bus.get("dict_type");
		Object dictName = bus.get("dict_name");
		Object dictValue = bus.get("dict_value");
		Object dictOrder = bus.get("dict_order");
		Object ssdwid = bus.get("ssdwid");
		Object userId = bus.get("user_id");
		
		int maxId = getMaxId();
		StringBuffer fwKey=new StringBuffer(""); 
		fwKey.append("INSERT INTO gw_dict ( dictt_id ");
		StringBuffer fwVaule=new StringBuffer("");
		fwVaule.append(" VALUES ("+maxId);
		
		
		if(dictType!=null){
			fwKey.append(",dict_type");
			fwVaule.append(",'"+dictType+"'");
		}
		if(dictName!=null){
			fwKey.append(",dict_name");
			fwVaule.append(",'"+dictName+"'");
		}
		if(dictValue!=null){
			fwKey.append(",dict_value");
			fwVaule.append(",'"+dictValue+"'");
		}
		if(dictOrder!=null){
			fwKey.append(",dict_order");
			fwVaule.append(",'"+dictOrder+"'");
		}
		if(ssdwid!=null){
			fwKey.append(",ssdwid");
			fwVaule.append(",'"+ssdwid+"'");
		}
		if(userId!=null){
			fwKey.append(",user_id");
			fwVaule.append(","+userId);
		}
		if(conTable!=null){
			fwKey.append(",con_table");
			fwVaule.append(","+conTable);
		}
		if(conTableId!=null){
			fwKey.append(",con_tableid");
			fwVaule.append(","+conTableId);
		}
		fwKey.append(")");
		fwVaule.append(")");
		
		db.execute(fwKey.toString()+fwVaule.toString());
		
		return maxId;
	}
	
	/**
	 * 获取字典项
	 * @param wjxsid
	 * @return
	 */
	public List<Map> queryDictByType(Map m) {
		Object ssdwid = m.get("ssdwid");
		Object dictType = m.get("dict_type");
		Object userId=m.get("user_id");
		DbSvr dbSvr = DbSvr.getDbService(null);
		List<Map> res = null;
		String sql="select t.dict_value code,t.dict_value name from gw_dict t where t.ssdwid=? and t.dict_type=? and t.user_id=?";
		res = dbSvr.executeQuery(sql, new Object[] { ssdwid,dictType ,userId});
		return res;
	}
	
	public Map queryById(DataMsgBus bus){
		StringBuffer s = new StringBuffer("select * from gw_dict")
			.append(" where dictt_id= ?");
		return DbSvr.getDbService(null).getOneRecorder(String.valueOf(s), bus.get("dictt_id"));
	}
	
	//条件查询，有需要请追加
	public List<Map> queyByCondition(DataMsgBus bus){
		StringBuffer s = new StringBuffer("select * from gw_dict where 1=1 ");
		
		s.append(" and $equal(user_id,user_id)");
		s.append(" and $equal(ssdwid,ssdwid)");
		s.append(" and $equal(dict_type,dict_type)");
		
		s.append(" order by dict_order");
		return DbSvr.getDbService(null).getListResultByPage(bus, new SqlInfo(String.valueOf(s)));
	}
	
	public List<Map> queyByCondition2(DataMsgBus bus){
		StringBuffer s = new StringBuffer("select con_tableid code,dict_name name from gw_dict where 1=1 ");
		
		s.append(" and $equal(user_id,user_id)");
		s.append(" and $equal(dict_type,dict_type)");
		
		s.append(" order by dict_order");
		Map m=new HashMap();
		m.put("user_id", bus.get("user_id"));
		m.put("dict_type", bus.get("dict_type"));
		Eso e=new SqlInfo(s.toString()).getEso(m);
		return DbSvr.getDbService("gwsys").executeQuery(e);
	}
	
	public void update(DataMsgBus bus){
		StringBuffer s1 = new StringBuffer("update gw_dict set ");
		StringBuffer s2 = new StringBuffer(" where 1=1 ");
		int count = 0;
		if(bus.get("dict_name")!=null && !"".equals(bus.get("dict_name"))){
			if(count > 0){
				s1.append(",dict_name='").append(bus.get("dict_name")).append("'");
			} else {
				s1.append("dict_name='").append(bus.get("dict_name")).append("'");
			}
			count ++;
		}
		if(bus.get("dict_value")!=null && !"".equals(bus.get("dict_value"))){
			if(count > 0){
				s1.append(",dict_value='").append(bus.get("dict_value")).append("'");
			} else {
				s1.append("dict_value='").append(bus.get("dict_value")).append("'");
			}
			count ++;
		}
		if(bus.get("dict_order")!=null && !"".equals(bus.get("dict_order"))){
			if(count > 0){
				s1.append(",dict_order=").append(bus.get("dict_order"));
			} else {
				s1.append("dict_order=").append(bus.get("dict_order"));
			}
			count ++;
		}
		
		if(bus.get("dictt_id")!=null && !"".equals(bus.get("dictt_id"))){
			s2.append(" and dictt_id=").append(bus.get("dictt_id"));
		}
		DbSvr.getDbService(null).execute(String.valueOf(s1).concat(String.valueOf(s2)));
	}
	
	public void deleteById(DataMsgBus bus){
		StringBuffer s = new StringBuffer("delete from gw_dict where dictt_id=").append(bus.get("dictt_id"));
		DbSvr.getDbService(null).execute(String.valueOf(s));
	}
	
	public boolean fwxsAdd(DataMsgBus bus){
		DbSvr db = DbSvr.getDbService(null);
		db.setAutoCommit(false);
		try{
			StringBuffer s = new StringBuffer("delete from gw_dict where user_id=")
				.append(bus.get("user_id"));
			if(bus.get("dict_type")!=null && !"".equals(bus.get("dict_type"))){
				s.append(" and dict_type='").append(bus.get("dict_type")).append("'");
			}
			db.execute(s.toString());
			
			String param = (String)bus.get("param");
			System.out.println(param);
			if(param!=null && !"".equals(param)){
				if(param.lastIndexOf(";") == param.length()-1){
					param = param.substring(0, param.length()-1);
				}
				System.out.println(param);
				String[] arr = param.split(";");
				for(int i=0; i<arr.length; i++){
					String[] arr1 = arr[i].split(",");
					StringBuffer s1 = new StringBuffer("insert into gw_dict (dictt_id,dict_type")
						.append(",dict_name,dict_value,dict_order,ssdwid,user_id,con_table,con_tableid)");
					StringBuffer s2 = new StringBuffer(" values(seq_dict.NEXTVAL,'").append(bus.get("dict_type"))
						.append("','").append(arr1[1]).append("','").append(arr1[1]).append("',").append(i+1)
						.append(",").append(bus.get("ssdwid")).append(",").append(bus.get("user_id"))
						.append(",'").append(bus.get("con_table")).append("',").append(arr1[0]).append(")");
					db.execute(s1.toString().concat(s2.toString()));
				}
			}
			db.commit();
			db.release();
			return true;
		} catch(Exception e){
			db.rollback();
			db.release();
			return false;
		}
	}
	
	private int getMaxId(){
		int id =0; 
		Map res = DbSvr.getDbService(null).getOneRecorder("select seq_dict.NEXTVAL as id  from dual ", null);
		if(res!=null){
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}
		return id;
	}
}
