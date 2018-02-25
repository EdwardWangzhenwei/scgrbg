package cn.com.huadi.aos.dict.service;

import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.view.DataMsgBus;

public class DictCategoryService {

	// 业务分组管理列表--全查
	public List<Map> groupList(DataMsgBus bus) throws Exception {
		String sql	= "select category_id,category_name,category_code, category_description from dict_category order by category_id";
		List<Map> list = DbSvr.getDbService("acc_sys").getListResultByPage(bus,new SqlInfo(sql));
		return list;
	}
	// 业务分组管理列表--全查
	public List<Map> qureyAllList() throws Exception {
		String sql	= "select category_id code,category_name name from dict_category order by category_id";
		List<Map> list = DbSvr.getDbService("acc_sys").getListResult(sql, null);
		return list;
	}
	// 业务分组管理列表--条件查询
	public List<Map> groupListByParam(DataMsgBus arg1) throws Exception {
		
		StringBuffer sql = new StringBuffer("select category_id,category_code,category_name, category_description from dict_category where 1=1");
		
		sql.append(" and $equal(category_code,category_code)");
		sql.append(" and $like(category_name,ywfzmc)");
		sql.append(" order by category_id ");
		
		List<Map> list = DbSvr.getDbService("acc_sys").getListResultByPage(arg1, new SqlInfo(sql.toString()));
		return list;
	
	}
	
	//根据ID查询
	public Map<String, Object> queryMapByID(String id) throws Exception {
		String sql = "select cg.category_id,cg.category_code,cg.category_name,cg.category_description,g.group_order from dict_category cg,dict_group g where cg.category_id = ?";
		Map<String,Object> map = DbSvr.getDbService("acc_sys").getOneRecorder(sql, id);
		return map;
	}
	
	//获得最大ID
	public int getMaxId(){
		return Integer.parseInt((DbSvr.getDbService("acc_sys").executeQueryOne("select nvl(max(CATEGORY_ID)+1,1) from DICT_CATEGORY", null)).toString());
	}
	
	// 业务分组管理列表--添加
	public void save(DataMsgBus arg1) throws Exception {
		DbSvr db = DbSvr.getDbService("acc_sys");
		int category_id = getMaxId() + 1;
		String insert = "insert into dict_category (category_id";
		String values = ") values(" + category_id;
		//String sql2 = "insert into dict_group set group_order = ";
		if (arg1.getString("category_name") != null) {
			insert += ",category_name";
			values += ",'" + arg1.getString("category_name") + "'";
		}
		if (arg1.getString("category_description") != null) {
			insert += ",category_description";
			values += ",'" + arg1.getString("category_description") + "'";
		}
		if (arg1.getString("category_code") != null) {
			insert += ",category_code";
			values += ",'" + arg1.getString("category_code") + "'";
		}
		String sql1 = insert + values + ")";
		db.execute(sql1);
		db.commit();
	}

	// 业务分组管理列表--修改
	public void update(DataMsgBus arg1) throws Exception {
		DbSvr db = DbSvr.getDbService("acc_sys");
			Map<String,Object> map = this.queryMapByID(arg1.getString("category_id"));
			
			if(map != null){
				
				StringBuffer sql1 = new StringBuffer("update dict_category set ");
				if(arg1.getString("category_name") != null){
				
					sql1.append("category_name = '").append(arg1.getString("category_name")).append("',");
				}
				
				if(arg1.getString("category_code") != null){
					
					sql1.append("category_code = '").append(arg1.getString("category_code")).append("',");
				}
				
				//if(arg1.getString("category_description") != null){
					
					sql1.append("category_description = '").append(arg1.getString("category_description")).append("'");
				//}


				sql1.append(" where category_id = ").append(arg1.getString("category_id"));	

	
				System.out.println(sql1);
				db.execute(sql1.toString());
				db.commit();
			}
		}	
	

	// 业务分组管理列表--删除
	public void deleteById(String category_id) throws Exception{
		DbSvr 	db = DbSvr.getDbService("acc_sys");
			String sq1	= "select group_id,category_id,group_code from dict_group where category_id=? ";
			List<Map> list1 = db.getListResult(sq1, category_id);
			if(list1!=null && list1.size()>0){
				for(Map m:list1){
					String sql11 = "delete from dict_dictionary where group_code = '" + m.get("group_code")+"'";
					db.execute(sql11);
					db.commit();
				}
				String sql12 = "delete from  dict_group where category_id = " + category_id;
				db.execute(sql12);
				db.commit();
			}
			
		String sql2 = "delete from dict_category where category_id = " + category_id;
		db.execute(sql2);
		db.commit();
		
		}
	

		/**
		 * 增加dict_category
		 */
		public void saveXml(DataMsgBus bus) throws Exception {
		
		DbSvr db=DbSvr.getDbService("acc_sys");
		
			Object category_name = bus.get("category_name");
			Object category_description = bus.get("category_description");
			Object category_code = bus.get("category_code");
		
			int  res = getMaxId()+1;
			String sInsertSQL="INSERT INTO dict_category( ";
			String sValuesSQL=" VALUES (";
			String sISql = "category_id";
			String sVSql = ""+res;
		
								    if (category_name!=null) {
				sISql = sISql+",category_name";
				sVSql = sVSql+",'"+category_name+"'";
		    } 
					    if (category_description!=null) {
				sISql = sISql+",category_description";
				sVSql = sVSql+",'"+category_description+"'";
		    } 
					    if (category_code!=null) {
				sISql = sISql+",category_code";
				sVSql = sVSql+",'"+category_code+"'";
		    } 
		
			sInsertSQL = sInsertSQL+sISql+" )";
			sValuesSQL = sValuesSQL+sVSql+" )";
			String sql = sInsertSQL+sValuesSQL;
			db.execute(sql);
			db.commit();
		  } 
		
		/**
		 * 修改dict_category
		 */
		public void updateXml(DataMsgBus bus) throws Exception {
		
			DbSvr db=DbSvr.getDbService("acc_sys");
		
		   Object category_name = bus.get("category_name");
		   Object category_description = bus.get("category_description");
		   Object category_code = bus.get("category_code");
		    String sUpdateSQL = "UPDATE dict_category SET ";
			String sUpdateColumn = null;
			String sWhere=" where category_code='"+category_code+"'";
		
								    if (category_name!=null) {
				if (sUpdateColumn == null) {
					sUpdateColumn = "category_name = '"+category_name+"'";
				} else {
					sUpdateColumn = sUpdateColumn + ",category_name = '"+category_name+"'";
				}
			}
					    if (category_description!=null) {
				if (sUpdateColumn == null) {
					sUpdateColumn = "category_description = '"+category_description+"'";
				} else {
					sUpdateColumn = sUpdateColumn + ",category_description = '"+category_description+"'";
				}
			}
					    if (category_code!=null) {
				if (sUpdateColumn == null) {
					sUpdateColumn = "category_code = '"+category_code+"'";
				} else {
					sUpdateColumn = sUpdateColumn + ",category_code = '"+category_code+"'";
				}
			}
		
			String sql=sUpdateSQL+sUpdateColumn+sWhere;
			db.execute(sql);	
			db.commit();
		  }
		//字典管理表删除
		public void deleteXml(DataMsgBus bus) {
			DbSvr 	db = DbSvr.getDbService("acc_sys");
			Object category_code = bus.get("category_code");
			
			StringBuffer sql = new StringBuffer();
			sql.append(" select category_id,category_name,category_code, category_description from dict_category ");
			sql.append(" where ");
			sql.append(" and $equal(category_code,category_code)" );
			
			Map map = (Map) db.getOneRecorder(sql.toString(), null);
			if(map!=null){
				String sq1	= "select group_id,category_id,group_code from dict_group where category_id=? ";
				List<Map> list1 = db.getListResult(sq1,map.get("category_id"));
				if(list1!=null && list1.size()>0){
					for(Map m:list1){
						String sql11 = "delete from dict_dictionary where group_code = " + m.get("group_code");
						db.execute(sql11);
						db.commit();
					}
					String sql12 = "delete from  dict_group where category_id = " + map.get("category_id");
					db.execute(sql12);
					db.commit();
				}
				
			}
			String sql2 = "delete from dict_category where category_code = '" + category_code+"'";
			db.execute(sql2);
			db.commit();
		}
		/**
		 * 是否已经添加了字典
		 * @param id
		 * @return
		 */
		public int isExistGroup(String id) {
			String sql	= "select group_id,category_id,group_code  from dict_group where effective_flag = 1 and category_id =? ";
			List<Map> list = DbSvr.getDbService("acc_sys").getListResult(sql, id);
			if(list!=null && list.size()>0){
				return 1;
			}else{
				return 0;
				
			}
			
		}
}
