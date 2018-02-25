 package cn.com.huadi.aos.dict.service;

import java.util.List;
import java.util.Map;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.view.DataMsgBus;

public class DictGroupService {
	
	
		// 字典管理列表--全查
		public List<Map> listAll(DataMsgBus arg1) throws Exception {
			String sql	= "select c.category_id,c.category_name,g.group_id,g.group_name,g.group_code from dict_category c,dict_group g where c.category_id = g.category_id and effective_flag != 0 order by g.group_order";
			List<Map> list = DbSvr.getDbService("acc_sys").getListResultByPage(arg1,new SqlInfo(sql));
			return list;

		}

		//获得字典管理表最大ID(添加用的ID,也可以随机数)
		public int getMaxId() throws Exception {
			return Integer.parseInt(DbSvr.getDbService("acc_sys").executeQueryOne("select nvl(max(group_id)+1,1) from dict_group", null).toString());
		}
		
		//判断列明是否存在
		public boolean isExist(String groupCode){
			String sql = "select group_id from dict_group where group_code = ?";
			Map<String,Object> map = DbSvr.getDbService("acc_sys").getOneRecorder(sql, groupCode);
			if(map == null || map.size()<1){
				return true;
			} else {
				return false;
			}
		}
		
		
		//字典管理表添加
		public void save(DataMsgBus arg1) throws Exception {
			DbSvr db = DbSvr.getDbService("acc_sys");
			int group_id= getMaxId()+1;
			String insert = "insert into dict_group (group_id";
			String values = ") values(" + group_id;
			
			if(arg1.getString("category_id") != null){
				insert += ",category_id";
				values += "," + arg1.getString("category_id");
			}
			if(arg1.getString("group_code") != null){
				insert += ",group_code";
				values += ",'" + arg1.getString("group_code")+"'";
			}
			if(arg1.getString("group_order") != null){
				insert += ",group_order";
				values += "," + arg1.getString("group_order");
			}
			if(arg1.getString("group_name") != null){
				insert += ",group_name";
				values += ",'" + arg1.getString("group_name") + "'";
			}
			
			String sql = insert + values + ")";
			System.out.println(sql);
			db.execute(sql);
			db.commit();
		}
		
		//根据ID查询
		public Map<String, Object> queryMapByID(String id) throws Exception {
			String sql = "select group_id,category_id,group_order,group_name,group_code from dict_group where group_id = ?";
			Map<String,Object> map = DbSvr.getDbService("acc_sys").getOneRecorder(sql, id);
			return map;
		}
		
		//字典管理表删除
		public void delete(String group_id) throws Exception{
			DbSvr db = DbSvr.getDbService("acc_sys");
			String sql = "update dict_group set effective_flag = 0 where group_id ="+group_id;
			db.execute(sql);
			db.commit();
			String sql1	= "select group_id,group_code from dict_group where group_id="+group_id;
			Map map = (Map) db.getOneRecorder(sql1, null);
			if(map!=null){
				String sql12 = "update dict_dictionary  set effective_flag = 0 where group_code = '" + map.get("group_code")+"'";
				db.execute(sql12);
				db.commit();
			}
		}
		
		//字典管理表修改
		public void update(DataMsgBus arg1) throws Exception {
				DbSvr db = DbSvr.getDbService("acc_sys");
				Map<String,Object> map = this.queryMapByID(arg1.getString("group_id"));
				
				if(map != null){
					StringBuffer sql = new StringBuffer("update dict_group set ");
					if(arg1.getString("category_id") != null){
						
						sql.append("category_id = ").append(arg1.getString("category_id")).append(",");
					}
					if(arg1.getString("group_code") != null){
						
						sql.append("group_code = '").append(arg1.getString("group_code")).append("',");
					}
					if(arg1.getString("group_name") != null){
						
						sql.append("group_name = '").append(arg1.getString("group_name")).append("',");
					}
					if(arg1.getString("group_order") != null){
						
						sql.append("group_order = '").append(arg1.getString("group_order")).append("'");
					}
					sql.append(" where group_id = ").append(arg1.getString("group_id"));	
					db.execute(sql.toString());
					db.commit();
				}
			}
		
		//条件查询
		public List<Map> listByParam(DataMsgBus arg1) throws Exception {
			
			StringBuffer sql = new StringBuffer("select cg.category_id,cg.category_name,g.group_id,g.group_name,g.group_code from dict_category cg,dict_group g where cg.category_id = g.category_id and effective_flag != 0");
			
			sql.append(" and $equal(cg.category_id,category_id)");
			sql.append(" and $like(g.group_name,zdfzmc)");
			sql.append(" and $like(g.group_code,zdflbm)");
			sql.append(" order by group_order");
			
			List<Map> list = DbSvr.getDbService("acc_sys").getListResultByPage(arg1, new SqlInfo(sql.toString()));
			return list;
		}
		/**
		 * 增加dict_group
		 */
		public void saveXml(DataMsgBus bus) throws Exception {

			DbSvr db=DbSvr.getDbService("acc_sys");

			Object group_id = bus.get("group_id");
			Object category_id = bus.get("category_id");
			Object group_order = bus.get("group_order");
			Object group_name = bus.get("group_name");
			Object group_code = bus.get("group_code");
			Object effective_flag = bus.get("effective_flag");
			Object createuserid = bus.get("createuserid");
			Object createdate = bus.get("createdate");

			int  res = getMaxId()+1;
			String sInsertSQL="INSERT INTO dict_group( ";
			String sValuesSQL=" VALUES (";
			String sISql = "group_id";
			String sVSql = ""+res;

								   if (category_id!=null ) {
		      sISql = sISql+",category_id";
				sVSql = sVSql+","+category_id;
		    }
					   if (group_order!=null ) {
		      sISql = sISql+",group_order";
				sVSql = sVSql+","+group_order;
		    }
					    if (group_name!=null) {
				sISql = sISql+",group_name";
				sVSql = sVSql+",'"+group_name+"'";
		    } 
					    if (group_code!=null) {
				sISql = sISql+",group_code";
				sVSql = sVSql+",'"+group_code+"'";
		    } 
					   if (effective_flag!=null ) {
		      sISql = sISql+",effective_flag";
				sVSql = sVSql+","+effective_flag;
		    }
					    if (createuserid!=null) {
				sISql = sISql+",createuserid";
				sVSql = sVSql+",'"+createuserid+"'";
		    } 
					    if (createdate!=null) {
				sISql = sISql+",createdate";
				sVSql = sVSql+",'"+createdate+"'";
		    } 

			sInsertSQL = sInsertSQL+sISql+" )";
			sValuesSQL = sValuesSQL+sVSql+" )";
			String sql = sInsertSQL+sValuesSQL;
			db.execute(sql);
			db.commit();
		  } 

		/**
		 * 修改dict_group
		 */
		public void updateXml(DataMsgBus bus) throws Exception {

		  DbSvr db=DbSvr.getDbService("acc_sys");

		   Object id = bus.get("group_id");
		   Object category_id = bus.get("category_id");
		   Object group_order = bus.get("group_order");
		   Object group_name = bus.get("group_name");
		   Object group_code = bus.get("group_code");
		   Object effective_flag = bus.get("effective_flag");
		   String sUpdateSQL = "UPDATE dict_group SET ";
			String sUpdateColumn = null;
			String sWhere=" where group_code='"+group_code+"' and category_id="+category_id;

								   if (category_id!=null) {
				if (sUpdateColumn == null) {
					sUpdateColumn = "category_id = "+category_id;
				} else {
					sUpdateColumn = sUpdateColumn + ",category_id = "+category_id;
				}
			}
					   if (group_order!=null) {
				if (sUpdateColumn == null) {
					sUpdateColumn = "group_order = "+group_order;
				} else {
					sUpdateColumn = sUpdateColumn + ",group_order = "+group_order;
				}
			}
					    if (group_name!=null) {
				if (sUpdateColumn == null) {
					sUpdateColumn = "group_name = '"+group_name+"'";
				} else {
					sUpdateColumn = sUpdateColumn + ",group_name = '"+group_name+"'";
				}
			}
					    if (group_code!=null) {
				if (sUpdateColumn == null) {
					sUpdateColumn = "group_code = '"+group_code+"'";
				} else {
					sUpdateColumn = sUpdateColumn + ",group_code = '"+group_code+"'";
				}
			}
					   if (effective_flag!=null) {
				if (sUpdateColumn == null) {
					sUpdateColumn = "effective_flag = "+effective_flag;
				} else {
					sUpdateColumn = sUpdateColumn + ",effective_flag = "+effective_flag;
				}
			}

			String sql=sUpdateSQL+sUpdateColumn+sWhere;
			db.execute(sql);	
			db.commit();
		  }
		//字典管理表删除
		public void deleteXml(DataMsgBus bus){
			DbSvr db = DbSvr.getDbService("acc_sys");
			Object category_id = bus.get("category_id");
			Object group_code = bus.get("group_code");
			String sql = "update dict_group set effective_flag = 0 where category_id ="+category_id+"and group_code ='"+group_code+"'";
			db.execute(sql);
			db.commit();
			String sql12 = "update dict_dictionary  set effective_flag = 0 where group_code = '" + group_code+"'";
			db.execute(sql12);
			db.commit();
		}
}
