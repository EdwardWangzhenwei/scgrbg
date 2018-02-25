/**
 * 
 */
package cn.com.huadi.aos.hdoa.common.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import cn.com.huadi.aos.hdoa.common.util.UuidUtil;

import com.aisino.platform.db.DbSvr;

/**
 * @author qinyalin
 *
 * 2017年7月13日
 */
public class FileUploaderService {
	
	public Object save(Map<String,Object> data){
		Object dzwjms=data.get("dzwjms");
		Object file_catalog = data.get("file_catalog");
		Object file_name = data.get("file_name");
		Object dzwjlx = data.get("dzwjlx");
		Object size = data.get("size");
		Object lrr = data.get("lrr");
		Object unitId = data.get("unitId");
		Object con_table=data.get("CON_TABLE");
		Object con_table_id=data.get("CON_TABLE_ID");
		DbSvr db=DbSvr.getDbService("jdsys");
		
		Integer dzwj_id=getMaxId();
		/*String dzwj_id = UuidUtil.Uuid();*/
		StringBuffer s1=new StringBuffer();
		StringBuffer s2=new StringBuffer();
		
		s1.append("insert into jd_dzwj ( dzwj_id");
		s2.append(" values (").append(dzwj_id).append("");
		
	/*	if(unitId!=null){
			s1.append(",ssdwid");
			s2.append(",'").append(unitId).append("'");
		}*/
		if(dzwjms!=null){
			s1.append(",dzwjm,dzwjms");
			s2.append(",'").append(dzwjms).append("','").append(dzwjms).append("'");
		}
		if(dzwjlx !=null){
			s1.append(",dzwjlx");
			s2.append(",'").append(dzwjlx).append("'");
		}
		if(size !=null){
			s1.append(",dzwj_size");
			s2.append(",'").append(size).append("'");
		}
		if(lrr !=null){
			s1.append(",lrr");
			s2.append(",'").append(lrr).append("'");			
		}
		if(file_catalog !=null){
			s1.append(",file_catalog");
			s2.append(",'").append(file_catalog).append("'");			
		}
		if(file_name !=null){
			s1.append(",file_name");
			s2.append(",'").append(file_name).append("'");			
		}
		if (con_table != null) {
			s1.append(",CON_TABLE");
			s2.append(",'").append(con_table).append("'");
		}
		if (con_table_id != null) {
			s1.append(",CON_TABLE_ID");
			s2.append(",").append(con_table_id).append("");
		}
		if (true) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Calendar dCurrDate = Calendar.getInstance();
			String sBeginDate = formatter.format(dCurrDate.getTime());
				s1.append(",lrsj )");
				s2.append(",TO_DATE('").append(sBeginDate).append("','%Y-%m-%d %H:%M:%S'))");
		}		
		String sql=s1.toString()+s2.toString();
		db.execute(sql);
		db.commit();
		db.release();
		return dzwj_id;
	}
	
	/**
	 * 获取dzwj_id值
	 * 
	 * @param db
	 * @return
	 */
	public int getMaxId(){
		int id =0; 
		Map res = DbSvr.getDbService(null).getOneRecorder("select seq_jddzwj.NEXTVAL as id  from dual ", null);
		//Map res = DbSvr.getDbService(null).getOneRecorder("select nvl(max(dzwj_id)+1,1) as id from JD_DZWJ", null);
		if(res!=null){
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}
		return id;
	}
	

}
