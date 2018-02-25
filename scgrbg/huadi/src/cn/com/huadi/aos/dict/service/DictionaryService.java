package cn.com.huadi.aos.dict.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aisino.platform.db.Crud;
import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.Eso;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.view.DataMsgBus;

public class DictionaryService {
	public DictionaryService() {
		super();
		// TODO  constructor stub
	}

	
	/**
	 * 根据id更新删除标志
	 * @param dictId
	 * @throws Exception
	 */
	public void deleteByID(String dictId) throws Exception {
		DbSvr dbSvr=DbSvr.getDbService("acc_sys");
		Crud crud = new Crud("DICT_DICTIONARY");
		crud.define("show_flag", new Object[] { 0 });
		crud.defineCondition("dict_id", new Object[] { dictId });
		Eso eso = crud.getUpdateEso();
		
		dbSvr.execute(eso);
		dbSvr.commit();
	}
	/**
	 * 保存
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public  int save(Map<String, Object> data) throws Exception{
		DbSvr dbSvr=DbSvr.getDbService("acc_sys");
		Object groupCode = data.get("groupCode");
		Object dictCode = data.get("dictCode");
		Object dictName = data.get("dictName");
		Object dictAbbreviation = data.get("dictAbbreviation");
		Object dictSinglecharCode = data.get("dictSinglecharCode");
		Object dictDoublecharCode = data.get("dictDoublecharCode");
		Object dictSpell = data.get("dictSpell");
		Object dictEnglish = data.get("dictEnglish");
		Object effectiveFlag = data.get("effectiveFlag");
		Object showFlag = 1;
		Object dictDescription = data.get("dictDescription");
		Object uperDictId = data.get("uperDictId");
		Object dictOrder = data.get("dictOrder");
		
		
		int res = getMaxId();
		String sInsertSQL="INSERT INTO DICT_DICTIONARY( ";
		String sValuesSQL=" VALUES (";
		String sISql = "dict_id";
		String sVSql = ""+res;
		
		if(groupCode != null){
			sISql = sISql+",group_code";
			sVSql = sVSql+",'"+groupCode+"'";
		}
		if(dictCode != null){
			sISql = sISql+",dict_code";
			sVSql = sVSql+",'"+dictCode+"'";
		}
		if(dictName != null){
			sISql = sISql+",dict_name";
			sVSql = sVSql+",'"+dictName+"'";
		}
		if(dictAbbreviation != null){
			sISql = sISql+",dict_abbreviation";
			sVSql = sVSql+",'"+dictAbbreviation+"'";
		}
		if(dictSinglecharCode != null){
			sISql = sISql+",dict_singlechar_code";
			sVSql = sVSql+",'"+dictSinglecharCode+"'";
		}
		if(dictDoublecharCode != null){
			sISql = sISql+",dict_doublechar_code";
			sVSql = sVSql+",'"+dictDoublecharCode+"'";
		}
		if(dictSpell != null){
			sISql = sISql+",dict_spell";
			sVSql = sVSql+","+dictSpell;
		}
		if(dictEnglish != null){
			sISql = sISql+",dict_english";
			sVSql = sVSql+","+dictEnglish;
		}
		if(effectiveFlag != null){
			sISql = sISql+",effective_flag";
			sVSql = sVSql+",1";
		}
		if(showFlag != null){
			sISql = sISql+",show_flag";
			sVSql = sVSql+","+showFlag;
		}
		if(dictDescription != null){
			sISql = sISql+",dict_description";
			sVSql = sVSql+",'"+dictDescription+"'";
		}
		if(dictOrder != null){
			sISql = sISql+",dict_order";
			sVSql = sVSql+","+dictOrder;
		}

		
		if(uperDictId != null){
			sISql = sISql+",uper_dict_id";
			sVSql = sVSql+","+uperDictId;
		}
		
		sInsertSQL = sInsertSQL+sISql+" )";
		sValuesSQL = sValuesSQL+sVSql+" )";
		String sql1 = sInsertSQL+sValuesSQL;
				
		
		System.out.println(sql1);
		dbSvr.execute(sql1);
		dbSvr.commit();
	    return res;
	}
	/**
	 * 更新 
	 * @param data
	 * @throws Exception
	 */
	public  void update(Map<String, Object> data) throws Exception{
		DbSvr dbSvr=DbSvr.getDbService("acc_sys");
		Object groupCode = data.get("groupCode");
		Object dictCode = data.get("dictCode");
		Object dictId = data.get("dictId");
		Object dictName = data.get("dictName");
		Object dictAbbreviation = data.get("dictAbbreviation");
		Object dictSinglecharCode = data.get("dictSinglecharCode");
		Object dictDoublecharCode = data.get("dictDoublecharCode");
		Object dictSpell = data.get("dictSpell");
		Object dictEnglish = data.get("dictEnglish");
		Object effectiveFlag = data.get("effectiveFlag");
		Object showFlag = data.get("showFlag");
		Object dictDescription = data.get("dictDescription");
		Object uperDictId = data.get("uperDictId");
		Object dictOrder = data.get("dictOrder");
		
		
		String sUpdateSQL = "UPDATE DICT_DICTIONARY SET ";
		String sUpdateColumn = null;
		String sWhere="where dict_id="+dictId;
		
		if (groupCode!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "group_code = '"+groupCode+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",group_code = '"+groupCode+"'";
			}
		}
		if (dictCode!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_code = '"+dictCode+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_code = '"+dictCode+"'";
			}
		}
		if (dictName!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_name = '"+dictName+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_name = '"+dictName+"'";
			}
		}
		if (dictAbbreviation!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_abbreviation = '"+dictAbbreviation+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_abbreviation = '"+dictAbbreviation+"'";
			}
		}
		if (dictSinglecharCode!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_singlechar_code = '"+dictSinglecharCode+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_singlechar_code = '"+dictSinglecharCode+"'";
			}
		}
		if (dictDoublecharCode!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_doublechar_code = '"+dictDoublecharCode+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_doublechar_code = '"+dictDoublecharCode+"'";
			}
		}
		if (dictSpell!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_spell = "+dictSpell;
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_spell = "+dictSpell;
			}
		}
		if (dictEnglish!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_english = "+dictEnglish;
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_english = "+dictEnglish;
			}
		}
		
		if (effectiveFlag!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "effective_flag ='"+effectiveFlag+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",effective_flag = '"+effectiveFlag+"'";
			}
		}
		if (showFlag!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "show_flag = "+showFlag;
			} else {
				sUpdateColumn = sUpdateColumn + ",show_flag = "+showFlag;
			}
		}
		if (dictDescription!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_description = '"+dictDescription+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_description ='"+dictDescription+"'";
			}
		}
		if (uperDictId!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "uper_dict_id = "+uperDictId;
			} else {
				sUpdateColumn = sUpdateColumn + ",uper_dict_id = "+uperDictId;
			}
		}
		if (dictOrder!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_order = '"+dictOrder+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_order = '"+dictOrder+"'";
			}
		}
		
		String sql=sUpdateSQL+sUpdateColumn+sWhere;
		      	dbSvr.execute(sql);
		      	dbSvr.commit();
	}
	
	/**
	 * 获取最新id值 
	 * @return
	 */
	private int getMaxId(){
		int id =0; 
		DbSvr dbSvr=DbSvr.getDbService("acc_sys");
		String sql = dbSvr.getPureSQL("dictionary.getNewDictionaryId");
		Number maxidNum = dbSvr.getNumberResult(sql,"1");
		id = Integer.valueOf(maxidNum.toString())+1;
	    return id;
	}


	/**
	 * 根据所选id获取字典信息
	 * @param dictId
	 * @return
	 */
	public Map getDictionaryInfoById(String dictId) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryInfoById");
		Map<String, Object> map = db.getOneRecorder(sql, dictId);
		return map;
	}
	/**
	 * 获取跟节点及其他字结点
	 * @param bus
	 * @return
	 */
	public List<Map> getTreeGroupData(DataMsgBus bus) {
		String groupCode = bus.getString("groupCode");
		String reloadId = bus.getString("reloadId");
		List<Map> rootMap = new ArrayList<Map>();
		if(reloadId!=null && !"".equals(reloadId)){
			if(groupCode.equals(reloadId)){
				rootMap = getTreeData(groupCode,"");//一级节点
			}else{
				rootMap = getTreeData(groupCode,reloadId);//其他节点
			}
		}else{
			// 默认加载跟节点
			Map<String, Object> mapGroup  = new HashMap<String, Object>();
			mapGroup.put("fatherid",null);
			mapGroup.put("leaf",false );
			mapGroup.put("children",null);
			mapGroup.put("code",groupCode);
			String sql = "select group_name from dict_group where group_code = ?";
			Map<String, Object> map  = DbSvr.getDbService("acc_sys").getOneRecorder(sql, groupCode);
			if(map!=null){
				mapGroup.put("name", map.get("group_name"));
			}
			rootMap.add(mapGroup);
		}
		return rootMap;
		
	}
	/**
	 *  分级加载节点
	 * @param groupCode
	 * @param reloadId
	 * @return
	 */
	public List<Map> getTreeData(String groupId,String reloadId) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryTreeByGroup");
		if(reloadId!=null && !"".equals(reloadId)){
			sql+="and UPER_DICT_ID="+reloadId;
		}else{
			sql+="and UPER_DICT_ID is null";
			
		}
		
		sql +=" order by dict.dict_code";
		List<Map> listMap = db.getListResult(sql,groupId);
		if(listMap!=null){
			for(Map m:listMap){
				String sql1 = "select * from DICT_DICTIONARY  where UPER_DICT_ID=? and show_flag=1 ";
				List<Map> maps = db.getListResult(sql1, m.get("code"));
				
				if(maps!=null){
					
					m.put("leaf", false);
				}else{
					m.put("leaf", true);
				}
				if(reloadId==null || "".equals(reloadId)){
					m.put("fatherid", groupId);
				}
			}
		}
		return listMap;
	}
	
	/**
	 * 根据分组码和字典码查询信息
	 * @param groupCode
	 * @param dictCode
	 * @return
	 */
	public Map getDictionaryInfoByCode(String groupCode, String dictCode) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryInfoByCode");
		Map<String, Object> map = db.getOneRecorder(sql, new Object[]{groupCode,dictCode});
		return map;
	}
	/**
	 * 判断要添加的列名是否存在
	 * @param groupCode  字典分类编码
	 * @param dictCode	 字典码
	 * @return
	 */
	public boolean isExist(String groupCode, String dictCode){
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryInfoByCode");
		Map<String, Object> map = db.getOneRecorder(sql, new Object[]{groupCode,dictCode});
		if(map == null||map.size()<1){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 查询父级信息是否为叶子结点
	 * @param dictId
	 * @return
	 */
	public List<Map> getUperIsLeafById(String dictId) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getUperIsLeafById");
		List<Map>  map = db.getListResult(sql, Integer.valueOf(dictId));
		return map;
	}
	/**
	 * 查询父级的信息
	 * @param dictId
	 * @return
	 */
	public List<Map>  getDictionaryInfoByUperId(String dictId) {
		DbSvr db = DbSvr.getDbService("acc_sys");
		String sql = db.getPureSQL("dictionary.getDictionaryInfoByUperId");
		List<Map> map = db.getListResult(sql, dictId);

		return map;
	}
	/**
	 * 增加dict_dictionary
	 */
	public void saveXml(DataMsgBus bus) throws Exception {

   DbSvr db=DbSvr.getDbService("acc_sys");

		Object group_code = bus.get("group_code");
		Object dict_code = bus.get("dict_code");
		Object dict_name = bus.get("dict_name");
		Object dict_abbreviation = bus.get("dict_abbreviation");
		Object dict_singlechar_code = bus.get("dict_singlechar_code");
		Object dict_doublechar_code = bus.get("dict_doublechar_code");
		Object dict_spell = bus.get("dict_spell");
		Object dict_english = bus.get("dict_english");
		Object effective_flag = bus.get("effective_flag");
		Object show_flag = bus.get("show_flag");
		Object dict_description = bus.get("dict_description");
		Object uper_dict_id = bus.get("uper_dict_id");
		Object dict_order = bus.get("dict_order");

		int  res = getMaxId();
		String sInsertSQL="INSERT INTO dict_dictionary( ";
		String sValuesSQL=" VALUES (";
		String sISql = "dict_id";
		String sVSql = ""+res;

							    if (group_code!=null) {
			sISql = sISql+",group_code";
			sVSql = sVSql+",'"+group_code+"'";
	    } 
				    if (dict_code!=null) {
			sISql = sISql+",dict_code";
			sVSql = sVSql+",'"+dict_code+"'";
	    } 
				    if (dict_name!=null) {
			sISql = sISql+",dict_name";
			sVSql = sVSql+",'"+dict_name+"'";
	    } 
				    if (dict_abbreviation!=null) {
			sISql = sISql+",dict_abbreviation";
			sVSql = sVSql+",'"+dict_abbreviation+"'";
	    } 
				    if (dict_singlechar_code!=null) {
			sISql = sISql+",dict_singlechar_code";
			sVSql = sVSql+",'"+dict_singlechar_code+"'";
	    } 
				    if (dict_doublechar_code!=null) {
			sISql = sISql+",dict_doublechar_code";
			sVSql = sVSql+",'"+dict_doublechar_code+"'";
	    } 
				    if (dict_spell!=null) {
			sISql = sISql+",dict_spell";
			sVSql = sVSql+",'"+dict_spell+"'";
	    } 
				    if (dict_english!=null) {
			sISql = sISql+",dict_english";
			sVSql = sVSql+",'"+dict_english+"'";
	    } 
				    if (effective_flag!=null) {
			sISql = sISql+",effective_flag";
			sVSql = sVSql+",'"+effective_flag+"'";
	    } 
				    if (show_flag!=null) {
			sISql = sISql+",show_flag";
			sVSql = sVSql+",'"+show_flag+"'";
	    } 
				    if (dict_description!=null) {
			sISql = sISql+",dict_description";
			sVSql = sVSql+",'"+dict_description+"'";
	    } 
				   if (uper_dict_id!=null ) {
	      sISql = sISql+",uper_dict_id";
			sVSql = sVSql+","+uper_dict_id;
	    }
				   if (dict_order!=null ) {
	      sISql = sISql+",dict_order";
			sVSql = sVSql+","+dict_order;
	    }

		sInsertSQL = sInsertSQL+sISql+" )";
		sValuesSQL = sValuesSQL+sVSql+" )";
		String sql = sInsertSQL+sValuesSQL;
		db.execute(sql);
		db.commit();
	  } 

	/**
	 * 修改dict_dictionary
	 */
	public void updateXml(DataMsgBus bus) throws Exception {

	  DbSvr db=DbSvr.getDbService("acc_sys");

	   Object id = bus.get("dict_id");
	   Object group_code = bus.get("group_code");
	   Object dict_code = bus.get("dict_code");
	   Object dict_name = bus.get("dict_name");
	   Object dict_abbreviation = bus.get("dict_abbreviation");
	   Object dict_singlechar_code = bus.get("dict_singlechar_code");
	   Object dict_doublechar_code = bus.get("dict_doublechar_code");
	   Object dict_spell = bus.get("dict_spell");
	   Object dict_english = bus.get("dict_english");
	   Object effective_flag = bus.get("effective_flag");
	   Object show_flag = bus.get("show_flag");
	   Object dict_description = bus.get("dict_description");
	   Object uper_dict_id = bus.get("uper_dict_id");
	   Object dict_order = bus.get("dict_order");
	    String sUpdateSQL = "UPDATE dict_dictionary SET ";
		String sUpdateColumn = null;
		String sWhere=" where dict_code='"+dict_code+"' and group_code='"+group_code+"'";

							    if (group_code!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "group_code = '"+group_code+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",group_code = '"+group_code+"'";
			}
		}
				    if (dict_code!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_code = '"+dict_code+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_code = '"+dict_code+"'";
			}
		}
				    if (dict_name!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_name = '"+dict_name+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_name = '"+dict_name+"'";
			}
		}
				    if (dict_abbreviation!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_abbreviation = '"+dict_abbreviation+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_abbreviation = '"+dict_abbreviation+"'";
			}
		}
				    if (dict_singlechar_code!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_singlechar_code = '"+dict_singlechar_code+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_singlechar_code = '"+dict_singlechar_code+"'";
			}
		}
				    if (dict_doublechar_code!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_doublechar_code = '"+dict_doublechar_code+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_doublechar_code = '"+dict_doublechar_code+"'";
			}
		}
				    if (dict_spell!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_spell = '"+dict_spell+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_spell = '"+dict_spell+"'";
			}
		}
				    if (dict_english!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_english = '"+dict_english+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_english = '"+dict_english+"'";
			}
		}
				    if (effective_flag!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "effective_flag = '"+effective_flag+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",effective_flag = '"+effective_flag+"'";
			}
		}
				    if (show_flag!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "show_flag = '"+show_flag+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",show_flag = '"+show_flag+"'";
			}
		}
				    if (dict_description!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_description = '"+dict_description+"'";
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_description = '"+dict_description+"'";
			}
		}
				   if (uper_dict_id!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "uper_dict_id = "+uper_dict_id;
			} else {
				sUpdateColumn = sUpdateColumn + ",uper_dict_id = "+uper_dict_id;
			}
		}
				   if (dict_order!=null) {
			if (sUpdateColumn == null) {
				sUpdateColumn = "dict_order = "+dict_order;
			} else {
				sUpdateColumn = sUpdateColumn + ",dict_order = "+dict_order;
			}
		}

		String sql=sUpdateSQL+sUpdateColumn+sWhere;
		db.execute(sql);	
		db.commit();
	  }
	/**
	 * 根据id更新删除标志
	 * @param dictId
	 * @throws Exception
	 */
	public void deleteXml(DataMsgBus bus) throws Exception {
		DbSvr db=DbSvr.getDbService("acc_sys");
		String sql12 = "update DICT_DICTIONARY set  show_flag=0  where dict_code = '" +bus.get("dict_code")+"' and group_code='"+bus.get("group_code")+"'";
		db.execute(sql12);
		db.commit();
	}
	//******************************************全部加载******************************************************************//
		/**
		 * 获取跟节点
		 * 
		 * @param bus
		 * @param rootid
		 * @return
		 */
		/*public List<Map> getTreeGroupData(DataMsgBus bus) {
			String groupCode = bus.getString("groupCode");
			String reloadId = bus.getString("reloadId");
			List<Map> rootMap = new ArrayList<Map>();
			// 第一级
			Map<String, Object> mapGroup  = new HashMap<String, Object>();
			mapGroup.put("fatherid",null);
			mapGroup.put("leaf",false );
			mapGroup.put("children",getTreeData(bus));
			mapGroup.put("code",groupCode);
			mapGroup.put("name", "行政区");
			rootMap.add(mapGroup);
			return rootMap;
			
		}*/
		/**
		 * 获取tree数据一级节点
		 * 
		 * @param bus
		 * @param rootid
		 * @return
		 */
		/*public List<Map> getTreeData(DataMsgBus bus) {
			String groupCode = bus.getString("groupCode");
			DbSvr db = DbSvr.getDbService("acc_sys");
			String sql = db.getPureSQL("dictionary.getDictionaryTreeByGroup");
			List<Map> listMap = db.getListResult(sql,groupCode);
			// 第一级
			List<Map> rootMap = new ArrayList<Map>();
			if (listMap != null && listMap.size() > 0) {
				for (int i = 0; i < listMap.size(); i++) {
					Map map = listMap.get(i);
					if (map.get("fatherid")==null) {
						List<Map> childrenMap = getListMap(map.get("code").toString(), listMap);
						if(childrenMap!=null && childrenMap.size()>0){
							map.put("leaf",false );
						}else{
							map.put("leaf",true );
						}
						map.put("children",childrenMap);
						
						rootMap.add(map);
					}
				}
			}
			return rootMap;
		}*/
		/**
		 * 处理以及组成tree
		 * 
		 * @param id
		 * @param listMap
		 * @return
		 */
		/*public List<Map> getListMap(String id, List<Map> listMap) {
			List<Map> rootMap = new ArrayList<Map>();
			if (listMap != null && listMap.size() > 0) {
				for (int i = 0; i < listMap.size(); i++) {
					Map map = listMap.get(i);
					if (map.get("fatherid")!=null && map.get("fatherid").toString().equals(id)) {
						List<Map> childrenMap = getListMap(map.get("code").toString(), listMap);
						if(childrenMap!=null && childrenMap.size()>0){
							map.put("leaf",false );
						}else{
							map.put("leaf",true );
						}
						map.put("children",childrenMap);
						rootMap.add(map);
						//listMap.remove(i);
					}
				}
			}
			return rootMap;
		}*/
	//************************************************************************************************************//
}
