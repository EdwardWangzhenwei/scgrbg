package cn.com.huadi.aos.hdoa.dzwj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

import cn.com.huadi.aos.hdoa.buinessObject.DzwjInfo;
import cn.com.huadi.aos.hdoa.buinessObject.DzwjListInfo;
import cn.com.huadi.aos.hdoa.common.service.FlowListService;
import cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet;
import cn.com.huadi.aos.hdoa.common.util.ZipUtil;

import com.aisino.platform.db.DbSvr;
import com.aisino.platform.db.SqlInfo;
import com.aisino.platform.view.DataMsgBus;

public class DzwjService {

	public int save(DataMsgBus bus) throws Exception {
		DbSvr db = DbSvr.getDbService(null);

		Object ssdwid = bus.get("ssdwid");
		Object dzwjm = bus.get("dzwjm");
		Object dzwjms = bus.get("dzwjms");
		Object dzwjlx = bus.get("dzwjlx");
		Object dzwjsx = bus.get("dzwjsx");
		Object dzwj_size = bus.get("dzwj_size");
		// Object dzwjnr = bus.get("dzwjnr");
		Object file_catalog = bus.get("file_catalog");
		Object file_name = bus.get("file_name");
		Object mj = bus.get("mj");
		Object ydr = bus.get("ydr");
		Object ydzt = bus.get("ydzt");
		Object ydr_id = bus.get("ydr_id");
		Object ksydsj = bus.get("ksydsj");
		Object llr = bus.get("llr");
		Object llsj = bus.get("llsj");
		Object zt = bus.get("zt");
		Object con_table = bus.get("con_table");
		Object con_table_id = bus.get("con_table_id");

		int maxId = getMaxId();
		StringBuffer fwKey = new StringBuffer("");
		fwKey.append("INSERT INTO jd_dzwj ( dzwj_id ");
		StringBuffer fwVaule = new StringBuffer("");
		fwVaule.append(" VALUES (" + maxId);

		if (ksydsj != null) {
			fwKey.append(",ksydsj");
			fwVaule.append(",to_date('" + ksydsj + "','yyyy-mm-dd HH24:mi:ss')");
		}
		if (llsj != null) {
			fwKey.append(",llsj");
			fwVaule.append(",to_date('" + llsj + "','yyyy-mm-dd HH24:mi:ss')");
		}
		if (dzwjm != null) {
			fwKey.append(",dzwjm");
			fwVaule.append(",'" + dzwjm + "'");
		}
		if (dzwjms != null) {
			fwKey.append(",dzwjms");
			fwVaule.append(",'" + dzwjms + "'");
		}
		if (dzwjsx != null) {
			fwKey.append(",dzwjsx");
			fwVaule.append(",'" + dzwjsx + "'");
		}
		if (dzwj_size != null) {
			fwKey.append(",dzwj_size");
			fwVaule.append(",'" + dzwj_size + "'");
		}
		if (ssdwid != null) {
			fwKey.append(",ssdwid");
			fwVaule.append("," + ssdwid);
		}
		if (file_catalog != null) {
			fwKey.append(",file_catalog");
			fwVaule.append(",'" + file_catalog + "'");
		}
		if (file_name != null) {
			fwKey.append(",file_name");
			fwVaule.append(",'" + file_name + "'");
		}
		if (mj != null) {
			fwKey.append(",mj");
			fwVaule.append(",'" + mj + "'");
		}
		if (ydr != null) {
			fwKey.append(",ydr");
			fwVaule.append(",'" + ydr + "'");
		}
		if (llr != null) {
			fwKey.append(",llr");
			fwVaule.append(",'" + llr + "'");
		}
		if (zt != null) {
			fwKey.append(",zt");
			fwVaule.append(",'" + zt + "'");
		}
		if (dzwjlx != null) {
			fwKey.append(",dzwjlx");
			fwVaule.append(",'" + dzwjlx + "'");
		}
		if (con_table != null) {
			fwKey.append(",con_table");
			fwVaule.append(",'" + con_table + "'");
		}
		if (ydr_id != null) {
			fwKey.append(",ydr_id");
			fwVaule.append("," + ydr_id);
		}
		if (con_table_id != null) {
			fwKey.append(",con_table_id");
			fwVaule.append("," + con_table_id);
		}
		if (ydzt != null) {
			fwKey.append(",ydzt");
			fwVaule.append("," + ydzt);
		}
		fwKey.append(")");
		fwVaule.append(")");

		db.execute(fwKey.toString() + fwVaule.toString());

		return maxId;
	}

	// 在Plugin以外环境保存操作
	public int saveDzwj(Map m) throws Exception {
		DbSvr db = DbSvr.getDbService(null);

		//Object ssdwid = m.get("ssdwid");
		Object dzwjm = m.get("dzwjm");
		Object dzwjms = m.get("dzwjms");
		Object dzwjlx = m.get("dzwjlx");
		Object dzwjsx = m.get("dzwjsx");
		Object dzwj_size = m.get("dzwj_size");
		Object file_catalog = m.get("file_catalog");
		Object file_name = m.get("file_name");
		Object mj = m.get("mj");
		Object ydr = m.get("ydr");
		Object ydzt = m.get("ydzt");
		Object ydr_id = m.get("ydr_id");
		Object ksydsj = m.get("ksydsj");
		//Object llr = m.get("llr");
		Object llsj = m.get("llsj");
		Object zt = m.get("zt");
		Object con_table = m.get("con_table");
		Object con_table_id = m.get("con_table_id");
		
		//Map userInfo = (Map) SessUtil.getRequest().getSession().getAttribute("USERINFO");
		
		int maxId = getMaxId();
		StringBuffer fwKey = new StringBuffer("");
		fwKey.append("INSERT INTO jd_dzwj ( dzwj_id ");
		StringBuffer fwVaule = new StringBuffer("");
		fwVaule.append(" VALUES (" + maxId);

		if (ksydsj != null) {
			fwKey.append(",ksydsj");
			fwVaule.append(",to_date('" + ksydsj + "','yyyy-mm-dd HH24:mi:ss')");
		}
		if (llsj != null) {
			fwKey.append(",llsj");
			fwVaule.append(",to_date('" + llsj + "','yyyy-mm-dd HH24:mi:ss')");
		} else {
			fwKey.append(",llsj");
			fwVaule.append(",sysdate");
		}
		if (dzwjm != null) {
			fwKey.append(",dzwjm");
			fwVaule.append(",'" + dzwjm + "'");
		}
		if (dzwjms != null) {
			fwKey.append(",dzwjms");
			fwVaule.append(",'" + dzwjms + "'");
		}
		if (dzwjsx != null) {
			fwKey.append(",dzwjsx");
			fwVaule.append(",'" + dzwjsx + "'");
		}
		if (dzwj_size != null) {
			fwKey.append(",dzwj_size");
			fwVaule.append(",'" + dzwj_size + "'");
		}
		/*if (ssdwid != null) {
			fwKey.append(",ssdwid");
			fwVaule.append("," + ssdwid);
		}*/
		/*if(userInfo!=null){
			if(userInfo.get("ssdwid")!=null){
				fwKey.append(",ssdwid");
				fwVaule.append("," + userInfo.get("ssdwid"));				
			}
			if(userInfo.get("llr")!=null){
				fwKey.append(",llr");
				fwVaule.append(",'" + userInfo.get("llr") + "'");				
			}
		}*/
		if (file_catalog != null) {
			fwKey.append(",file_catalog");
			fwVaule.append(",'" + file_catalog + "'");
		}
		if (file_name != null) {
			fwKey.append(",file_name");
			fwVaule.append(",'" + file_name + "'");
		}
		if (mj != null) {
			fwKey.append(",mj");
			fwVaule.append(",'" + mj + "'");
		}
		if (ydr != null) {
			fwKey.append(",ydr");
			fwVaule.append(",'" + ydr + "'");
		}
		/*if (llr != null) {
			fwKey.append(",llr");
			fwVaule.append(",'" + llr + "'");
		}*/
		if (zt != null) {
			fwKey.append(",zt");
			fwVaule.append(",'" + zt + "'");
		}
		if (dzwjlx != null) {
			fwKey.append(",dzwjlx");
			fwVaule.append(",'" + dzwjlx + "'");
		}
		if (con_table != null) {
			fwKey.append(",con_table");
			fwVaule.append(",'" + con_table + "'");
		}
		if (ydr_id != null) {
			fwKey.append(",ydr_id");
			fwVaule.append("," + ydr_id);
		}
		if (con_table_id != null) {
			fwKey.append(",con_table_id");
			fwVaule.append("," + con_table_id);
		}
		if (ydzt != null) {
			fwKey.append(",ydzt");
			fwVaule.append("," + ydzt);
		}
		fwKey.append(")");
		fwVaule.append(")");

		int id = maxId;
		try {
			db.setAutoCommit(false);
			db.execute(fwKey.toString() + fwVaule.toString());
			db.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.rollback();
			id = -1;
			throw new Exception();
		} finally {
			db.release();
		}
		return id;
	}

	public void updateDzwj(Map m) throws Exception {
		DbSvr db = DbSvr.getDbService("gwsys");

		Object dzwj_id1 = m.get("dzwj_id");
		Object ssdwid = m.get("ssdwid");
		Object dzwjm = m.get("dzwjm");
		Object dzwjms = m.get("dzwjms");
		Object dzwjlx = m.get("dzwjlx");
		Object dzwjsx = m.get("dzwjsx");
		Object dzwj_size = m.get("dzwj_size");
		Object file_catalog = m.get("file_catalog");
		Object file_name = m.get("file_name");
		Object mj = m.get("mj");
		Object ydr = m.get("ydr");
		Object ydzt = m.get("ydzt");
		Object ydr_id = m.get("ydr_id");
		Object ksydsj = m.get("ksydsj");
		Object llr = m.get("llr");
		Object llsj = m.get("llsj");
		Object zt = m.get("zt");
		Object con_table = m.get("con_table");
		Object con_table_id = m.get("con_table_id");
		
		if(dzwj_id1!=null){
			String[] dzwj_id2=String.valueOf(dzwj_id1).split(",");
			for(int i=0;i<dzwj_id2.length;i++){
				String dzwj_id=dzwj_id2[i];
				Map dzwj=db.getOneRecorder("select * from jd_dzwj where dzwj_id=?", new Object[]{dzwj_id});
				if(dzwj!=null&&dzwj.get("con_table_id")!=null&&!String.valueOf(dzwj.get("con_table_id")).startsWith("-")){
				}else{
					StringBuffer sUpdate = new StringBuffer("UPDATE jd_dzwj SET ");
					
					int flag = -1;
					
					if (dzwj_size != null) {
						if (flag < 0) {
							sUpdate.append("dzwj_size='" + dzwj_size + "'");
						} else {
							sUpdate.append(",dzwj_size='" + dzwj_size + "'");
						}
						flag++;
					}
					if (ydr != null) {
						if (flag < 0) {
							sUpdate.append("ydr='" + ydr + "'");
						} else {
							sUpdate.append(",ydr='" + ydr + "'");
						}
						flag++;
					}
					if (ydzt != null) {
						if (flag < 0) {
							sUpdate.append("ydzt=" + ydzt);
						} else {
							sUpdate.append(",ydzt=" + ydzt);
						}
						flag++;
					}
					if (ydr_id != null) {
						if (flag < 0) {
							sUpdate.append("ydr_id=" + ydr_id);
						} else {
							sUpdate.append(",ydr_id=" + ydr_id);
						}
						flag++;
					}
					if (ksydsj != null) {
						if (flag < 0) {
							sUpdate.append("ksydsj=" + ksydsj);
						} else {
							sUpdate.append(",ksydsj=" + ksydsj);
						}
						flag++;
					}
					if (file_name != null) {
						if (flag < 0) {
							sUpdate.append("file_name='" + file_name + "'");
						} else {
							sUpdate.append(",file_name='" + file_name + "'");
						}
						flag++;
					}
					if (zt != null) {
						if (flag < 0) {
							sUpdate.append("zt='" + zt + "'");
						} else {
							sUpdate.append(",zt='" + zt + "'");
						}
						flag++;
					}
					if (con_table != null) {
						if (flag < 0) {
							sUpdate.append("con_table='" + con_table + "'");
						} else {
							sUpdate.append(",con_table='" + con_table + "'");
						}
						flag++;
					}
					if (con_table_id != null) {
						if (flag < 0) {
							sUpdate.append("con_table_id=" + con_table_id );
						} else {
							sUpdate.append(",con_table_id=" + con_table_id);
						}
						flag++;
					}
					
					sUpdate.append(" where dzwj_id=" + dzwj_id);
					try {
						db.setAutoCommit(false);
						db.execute(sUpdate.toString());
						db.commit();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						db.rollback();
						throw new Exception();
					} finally {
						db.release();
					}	
				}				
				
			}
		}
	}
	/**
	 * 给意见附件update con_table_id用
	 * @param m
	 * @throws Exception
	 */
	public void updateDzwj3(Map m) throws Exception {
		DbSvr db = DbSvr.getDbService("gwsys");		
		Object dzwj_id = m.get("dzwj_id");
		Object con_table_id = m.get("con_table_id");
		
		if(dzwj_id!=null){
			if (con_table_id != null) {
				StringBuffer sUpdate = new StringBuffer("UPDATE jd_dzwj SET con_table_id=").append(con_table_id);
				sUpdate.append(" where dzwj_id in (").append(dzwj_id).append(")");
				try {
					db.setAutoCommit(false);
					db.execute(sUpdate.toString());
					db.commit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					db.rollback();
					throw new Exception();
				} finally {
					db.release();
				}	
			}			
		}
	}
	/**
	 * upload2.jsp调用
	 */
	public void updateDzwj2(Map m) throws Exception {
		DbSvr db = DbSvr.getDbService("gwsys");
		
		Object dzwj_id = m.get("dzwj_id");
		Object ssdwid = m.get("ssdwid");
		Object dzwjm = m.get("dzwjm");
		Object dzwjms = m.get("dzwjms");
		Object dzwjlx = m.get("dzwjlx");
		Object dzwjsx = m.get("dzwjsx");
		Object dzwj_size = m.get("dzwj_size");
		Object file_catalog = m.get("file_catalog");
		Object file_name = m.get("file_name");
		Object mj = m.get("mj");
		Object ydr = m.get("ydr");
		Object ydzt = m.get("ydzt");
		Object ydr_id = m.get("ydr_id");
		Object ksydsj = m.get("ksydsj");
		Object llr = m.get("llr");
		Object llsj = m.get("llsj");
		Object zt = m.get("zt");
		Object con_table = m.get("con_table");
		Object con_table_id = m.get("con_table_id");
		
		if(dzwj_id!=null){
			StringBuffer sUpdate = new StringBuffer("UPDATE jd_dzwj SET ");
			
			int flag = -1;
			
			if (dzwj_size != null) {
				if (flag < 0) {
					sUpdate.append("dzwj_size='" + dzwj_size + "'");
				} else {
					sUpdate.append(",dzwj_size='" + dzwj_size + "'");
				}
				flag++;
			}
			if (ydr != null) {
				if (flag < 0) {
					sUpdate.append("ydr='" + ydr + "'");
				} else {
					sUpdate.append(",ydr='" + ydr + "'");
				}
				flag++;
			}
			if (ydzt != null) {
				if (flag < 0) {
					sUpdate.append("ydzt=" + ydzt);
				} else {
					sUpdate.append(",ydzt=" + ydzt);
				}
				flag++;
			}
			if (ydr_id != null) {
				if (flag < 0) {
					sUpdate.append("ydr_id=" + ydr_id);
				} else {
					sUpdate.append(",ydr_id=" + ydr_id);
				}
				flag++;
			}
			if (ksydsj != null) {
				if (flag < 0) {
					sUpdate.append("ksydsj=" + ksydsj);
				} else {
					sUpdate.append(",ksydsj=" + ksydsj);
				}
				flag++;
			}
			if (file_name != null) {
				if (flag < 0) {
					sUpdate.append("file_name='" + file_name + "'");
				} else {
					sUpdate.append(",file_name='" + file_name + "'");
				}
				flag++;
			}
			if (zt != null) {
				if (flag < 0) {
					sUpdate.append("zt='" + zt + "'");
				} else {
					sUpdate.append(",zt='" + zt + "'");
				}
				flag++;
			}
			if (con_table != null) {
				if (flag < 0) {
					sUpdate.append("con_table='" + con_table + "'");
				} else {
					sUpdate.append(",con_table='" + con_table + "'");
				}
				flag++;
			}
			if (con_table_id != null) {
				if (flag < 0) {
					sUpdate.append("con_table_id=" + con_table_id );
				} else {
					sUpdate.append(",con_table_id=" + con_table_id);
				}
				flag++;
			}
			
			sUpdate.append(" where dzwj_id=" + dzwj_id);
			try {
				db.setAutoCommit(false);
				db.execute(sUpdate.toString());
				db.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				db.rollback();
				throw new Exception();
			} finally {
				db.release();
			}	
		}
	}

	public List<Map> queryByBusByPage(DataMsgBus bus) {

		DbSvr db = DbSvr.getDbService("jdsys");
		
		Object dzwj_id = bus.get("dzwj_id");
		Object con_table = bus.get("con_table");
		Object con_table_id = bus.get("con_table_id");
		
		StringBuffer sql = new StringBuffer();
		List<Map> res = null;
		if(con_table_id!=null){
			sql.append("select dzwj_id,* from jd_dzwj t where 1=1");
			
			/*if (dzwj_id != null) {
				if(String.valueOf(dzwj_id).indexOf(",")!=-1){
					dzwj_id=recursion_sub(String.valueOf(dzwj_id));
					sql.append(" and dzwj_id in (").append(dzwj_id).append(")");
				}else{	
					sql.append(" AND $equal(dzwj_id,dzwj_id) ");
				}
			}*/
			sql.append(" AND $equal(con_table_id,con_table_id)");
			SqlInfo sqlinfo = new SqlInfo(sql.toString());
			res = db.getListResultByPage(bus, sqlinfo);
		}
		return res;
	}
	public List<Map> queryByBusByPage2(DataMsgBus bus) {
		
		DbSvr db = DbSvr.getDbService("gwsys");
		
		Object dzwj_id = bus.get("dzwj_id");
		Object ssdwid = bus.get("ssdwid");
		Object dzwjm = bus.get("dzwjm");
		Object dzwjms = bus.get("dzwjms");
		Object dzwjlx = bus.get("dzwjlx");
		Object dzwjsx = bus.get("dzwjsx");
		Object file_catalog = bus.get("file_catalog");
		Object file_name = bus.get("file_name");
		Object mj = bus.get("mj");
		Object ydr = bus.get("ydr");
		Object ydzt = bus.get("ydzt");
		Object ydr_id = bus.get("ydr_id");
		Object ksydsj = bus.get("ksydsj");
		Object llr = bus.get("llr");
		Object llsj = bus.get("llsj");
		Object con_table = bus.get("con_table");
		Object con_table_id = bus.get("con_table_id");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from jd_dzwj t where 1=1 and zt<>'-1' ");
		
		if (dzwj_id != null) {
			if(String.valueOf(dzwj_id).indexOf(",")!=-1){
				/*if(String.valueOf(dzwj_id).startsWith(",")){
					dzwj_id=String.valueOf(dzwj_id).substring(1,String.valueOf(dzwj_id).length());
				}*/
				dzwj_id=recursion_sub(String.valueOf(dzwj_id));
				sql.append(" and dzwj_id in (").append(dzwj_id).append(")");
			}else{	
				sql.append(" AND $equal(dzwj_id,dzwj_id) ");
			}
		}
		
		
		sql.append(" AND $equal(ksydsj,ksydsj)");
		sql.append(" AND $equal(llsj,llsj)");
		
		sql.append(" AND $like(dzwjm,dzwjm)");
		sql.append(" AND $like(dzwjms,dzwjms)");
		sql.append(" AND $equal(dzwjsx,dzwjsx)");
		sql.append(" AND $equal(ssdwid,ssdwid)");
		sql.append(" AND $like(file_catalog,file_catalog)");
		sql.append(" AND $like(file_name,file_name)");
		sql.append(" AND (mj <={mj} or mj is null)");
		sql.append(" AND $like(ydr,ydr)");
		sql.append(" AND $like(llr,llr)");
		sql.append(" AND $equal(dzwjlx,dzwjlx)");
		sql.append(" AND $equal(con_table,con_table)");
		sql.append(" AND $equal(ydr_id,ydr_id)");
		sql.append(" AND $equal(con_table_id,con_table_id)");
		sql.append(" AND $equal(ydzt,ydzt)");
		
		//sql.append(" and dzwjsx <>'审批件' ");
		sql.append(" order by decode(dzwjsx,'审批件',1,'来文内容',1,'正文',decode(dzwjm,'正式件(OFD)%.ofd',2,"
				+ "'正式件%.wps',3),4),dzwj_id ");
		SqlInfo sqlinfo = new SqlInfo(sql.toString());
		List<Map> res = db.getListResultByPage(bus, sqlinfo);
		
		return res;
	}
	/**
	 * 过滤掉“正式件*.wps”文件
	 * @param bus
	 * @return
	 */
	public List<Map> queryByBusByPage5(DataMsgBus bus) {
		
		DbSvr db = DbSvr.getDbService("gwsys");
		
		Object dzwj_id = bus.get("dzwj_id");
		Object ssdwid = bus.get("ssdwid");
		Object dzwjm = bus.get("dzwjm");
		Object dzwjms = bus.get("dzwjms");
		Object dzwjlx = bus.get("dzwjlx");
		Object dzwjsx = bus.get("dzwjsx");
		Object file_catalog = bus.get("file_catalog");
		Object file_name = bus.get("file_name");
		Object mj = bus.get("mj");
		Object ydr = bus.get("ydr");
		Object ydzt = bus.get("ydzt");
		Object ydr_id = bus.get("ydr_id");
		Object ksydsj = bus.get("ksydsj");
		Object llr = bus.get("llr");
		Object llsj = bus.get("llsj");
		Object con_table = bus.get("con_table");
		Object con_table_id = bus.get("con_table_id");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select dzwj_id,* from jd_dzwj t where 1=1 and zt<>'-1' ");
		
		if (dzwj_id != null) {
			if(String.valueOf(dzwj_id).indexOf(",")!=-1){
				/*if(String.valueOf(dzwj_id).startsWith(",")){
					dzwj_id=String.valueOf(dzwj_id).substring(1,String.valueOf(dzwj_id).length());
				}else if(String.valueOf(dzwj_id).endsWith(",")){
					dzwj_id=String.valueOf(dzwj_id).substring(0,String.valueOf(dzwj_id).length()-2);					
				}*/
				dzwj_id=recursion_sub(String.valueOf(dzwj_id));
				sql.append(" and dzwj_id in (").append(dzwj_id).append(")");
			}else{	
				sql.append(" AND $equal(dzwj_id,dzwj_id) ");
			}
		}
		
		
		sql.append(" AND $equal(ksydsj,ksydsj)");
		sql.append(" AND $equal(llsj,llsj)");
		
		sql.append(" AND $like(dzwjm,dzwjm)");
		sql.append(" AND $like(dzwjms,dzwjms)");
		sql.append(" AND $equal(dzwjsx,dzwjsx)");
		sql.append(" AND $equal(ssdwid,ssdwid)");
		sql.append(" AND $like(file_catalog,file_catalog)");
		sql.append(" AND $like(file_name,file_name)");
		sql.append(" AND (mj <={mj} or mj is null)");
		sql.append(" AND $like(ydr,ydr)");
		sql.append(" AND $like(llr,llr)");
		sql.append(" AND $equal(dzwjlx,dzwjlx)");
		sql.append(" AND $equal(con_table,con_table)");
		sql.append(" AND $equal(ydr_id,ydr_id)");
		sql.append(" AND $equal(con_table_id,con_table_id)");
		sql.append(" AND $equal(ydzt,ydzt)");
		
		//sql.append(" and (dzwjsx <> '审批件' or (dzwjlx='wps' and dzwjsx='正式件')) ");
		sql.append(" order by decode(dzwjsx,'审批件',1,'来文内容',1,'正文',decode(dzwjm,'正式件(OFD)%.ofd',2,"
				+ "'正式件%.wps',3),4),dzwj_id ");
		SqlInfo sqlinfo = new SqlInfo(sql.toString());
		List<Map> res = db.getListResultByPage(bus, sqlinfo);
		
		return res;
	}
	/**
	 * 发文查看页面用
	 * @param bus
	 * @return
	 */
	public List<Map> queryByBusByPage6(DataMsgBus bus) {
		
		DbSvr db = DbSvr.getDbService("gwsys");
		
		Object dzwj_id = bus.get("dzwj_id");
		Object ssdwid = bus.get("ssdwid");
		Object dzwjm = bus.get("dzwjm");
		Object dzwjms = bus.get("dzwjms");
		Object dzwjlx = bus.get("dzwjlx");
		Object dzwjsx = bus.get("dzwjsx");
		Object file_catalog = bus.get("file_catalog");
		Object file_name = bus.get("file_name");
		Object mj = bus.get("mj");
		Object ydr = bus.get("ydr");
		Object ydzt = bus.get("ydzt");
		Object ydr_id = bus.get("ydr_id");
		Object ksydsj = bus.get("ksydsj");
		Object llr = bus.get("llr");
		Object llsj = bus.get("llsj");
		Object con_table = bus.get("con_table");
		Object con_table_id = bus.get("con_table_id");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from jd_dzwj t where 1=1 and zt<>'-1' ");
		
		if (dzwj_id != null) {
			if(String.valueOf(dzwj_id).indexOf(",")!=-1){
				/*if(String.valueOf(dzwj_id).startsWith(",")){
					dzwj_id=String.valueOf(dzwj_id).substring(1,String.valueOf(dzwj_id).length());
				}*/
				dzwj_id=recursion_sub(String.valueOf(dzwj_id));
				sql.append(" and dzwj_id in (").append(dzwj_id).append(")");
			}else{	
				sql.append(" AND $equal(dzwj_id,dzwj_id) ");
			}
		}
		
		
		sql.append(" AND $equal(ksydsj,ksydsj)");
		sql.append(" AND $equal(llsj,llsj)");
		
		sql.append(" AND $like(dzwjm,dzwjm)");
		sql.append(" AND $like(dzwjms,dzwjms)");
		sql.append(" AND $equal(dzwjsx,dzwjsx)");
		sql.append(" AND $equal(ssdwid,ssdwid)");
		sql.append(" AND $like(file_catalog,file_catalog)");
		sql.append(" AND $like(file_name,file_name)");
		sql.append(" AND (mj <={mj} or mj is null)");
		sql.append(" AND $like(ydr,ydr)");
		sql.append(" AND $like(llr,llr)");
		sql.append(" AND $equal(dzwjlx,dzwjlx)");
		sql.append(" AND $equal(con_table,con_table)");
		sql.append(" AND $equal(ydr_id,ydr_id)");
		sql.append(" AND $equal(con_table_id,con_table_id)");
		sql.append(" AND $equal(ydzt,ydzt)");
		
		//sql.append(" and (dzwjsx <> '审批件' or (dzwjlx='wps' and dzwjsx='正式件')) ");
		sql.append(" order by decode(dzwjsx,'审批件',1,'来文内容',1,'正文',decode(dzwjm,'正式件(OFD)%.ofd',2,"
				+ "'正式件%.wps',3),4),dzwj_id ");
		SqlInfo sqlinfo = new SqlInfo(sql.toString());
		List<Map> res = db.getListResultByPage(bus, sqlinfo);
		
		return res;
	}

	public boolean addAll(DzwjListInfo list) {
		DbSvr db = DbSvr.getDbService(null);
		db.setAutoCommit(false);
		try {
			String filenames=" ";
			if (list != null && list.list != null && list.list.size() > 0) {
				List<DzwjInfo> l = list.list;
				for (int i = 0; i < l.size(); i++) {
					DzwjInfo d = l.get(i);
					if(i==l.size()-1){						
						filenames+=d.dzwjm;
					}else{
						filenames+=d.dzwjm+"，";						
					}
					StringBuffer fwKey = new StringBuffer(
							"INSERT INTO jd_dzwj(dzwj_id");
					StringBuffer fwVaule = new StringBuffer(
							" values(seq_dzwj.NEXTVAL");
					if (list.llsj != null) {
						fwKey.append(",llsj");
						fwVaule.append(",to_date('" + list.llsj
								+ "','yyyy-mm-dd HH24:mi:ss')");
					}
					if (d.dzwjm != null) {
						fwKey.append(",dzwjm");
						fwVaule.append(",'" + d.dzwjm + "'");
					}
					if (d.dzwjms != null) {
						fwKey.append(",dzwjms");
						fwVaule.append(",'" + d.dzwjms + "'");
					}
					if (list.dzwjsx != null) {
						fwKey.append(",dzwjsx");
						fwVaule.append(",'" + list.dzwjsx + "'");
					}
					if (d.dzwj_size != null) {
						fwKey.append(",dzwj_size");
						fwVaule.append(",'" + d.dzwj_size + "'");
					}
					if (list.ssdwid != null) {
						fwKey.append(",ssdwid");
						fwVaule.append("," + list.ssdwid);
					}
					if (list.file_catalog != null) {
						fwKey.append(",file_catalog");
						fwVaule.append(",'" + list.file_catalog + "'");
					}
					if (d.file_name != null) {
						fwKey.append(",file_name");
						fwVaule.append(",'" + d.file_name + "'");
					}
					if (d.mj != null) {
						fwKey.append(",mj");
						fwVaule.append(",'" + d.mj + "'");
					}
					if (d.ydr != null) {
						fwKey.append(",ydr");
						fwVaule.append(",'" + d.ydr + "'");
					}
					if (list.llr != null) {
						fwKey.append(",llr");
						fwVaule.append(",'" + list.llr + "'");
					}
					if (list.zt != null) {
						fwKey.append(",zt");
						fwVaule.append(",'" + list.zt + "'");
					}
					if (d.dzwjlx != null) {
						fwKey.append(",dzwjlx");
						fwVaule.append(",'" + d.dzwjlx + "'");
					}
					if (list.con_table != null) {
						fwKey.append(",con_table");
						fwVaule.append(",'" + list.con_table + "'");
					}
					if (d.ydrid > Integer.MIN_VALUE) {
						fwKey.append(",ydr_id");
						fwVaule.append("," + d.ydrid);
					}
					if (list.con_table_id > Integer.MIN_VALUE) {
						fwKey.append(",con_table_id");
						fwVaule.append("," + list.con_table_id);
					}
					if (list.zt != null) {
						fwKey.append(",ydzt");
						fwVaule.append("," + list.zt);
					}
					fwKey.append(")");
					fwVaule.append(")");

					db.execute(fwKey.toString() + fwVaule.toString());
					
				}
				String action="上传"+list.dzwjsx+" "+filenames;
				new FlowListService().insertFlowlist(String.valueOf(list.con_table_id),list.llr, action,db);
				db.commit();
				db.release();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.rollback();
			db.release();
			return false;
		}
		return false;
	}

	/**
	 * 获取指定文件的所有附件信息
	 * 
	 * @param wjbm
	 * @return
	 */
	public List<Map> queryDzwjByWjbm(String wjbm) {
		if (wjbm != null) {
			StringBuffer s = new StringBuffer("select * from jd_dzwj where zt!=-1 and con_table_id= ?");
			DbSvr dbSvr = DbSvr.getDbService(null);
			List<Map> list = dbSvr.executeQuery(s.toString(), new Object[]{wjbm});
			dbSvr.release();
			return list;
		} else {
			return null;
		}
	}

	public Map getDzwjById(String dzwjid) {
		StringBuffer s = new StringBuffer(
				"select * from jd_dzwj where dzwj_id=?");
		DbSvr dbSvr = DbSvr.getDbService(null);
		Map m = dbSvr.getOneRecorder(s.toString(), dzwjid);
		dbSvr.release();
		return m;
	}

	public Map getDzwjInfo(String con_table, String con_table_id, String dzwjsx) {
		if (con_table != null && con_table_id != null && dzwjsx != null) {
			StringBuffer s = new StringBuffer("");
			s.append("select * from jd_dzwj where zt!=-1 and con_table=? and con_table_id=? and dzwjsx like ?");
			Object[] par = new Object[3];
			par[0] = con_table;
			par[1] = con_table_id;
			par[2] = dzwjsx;
			DbSvr dbSvr = DbSvr.getDbService(null);
			Map m = dbSvr.getOneRecorder(s.toString(), par);
			dbSvr.release();
			return m;
		} else {
			return null;
		}
	}

	public Map queryDzwjByWjbmAndDzwjm(String con_table,String con_table_id, String dzwjm) {
		if (con_table_id != null && dzwjm != null) {
			StringBuffer s = new StringBuffer();
			s.append("select * from jd_dzwj where zt!=-1 and con_table=? and con_table_id=? ");
			s.append(" and dzwjm like ? order by dzwjm desc, dzwj_id desc ");
			Object[] par = new Object[3];
			par[0]=con_table;
			par[1]=con_table_id;
			par[2]=dzwjm;
			
			DbSvr dbSvr = DbSvr.getDbService(null);
			Map m = dbSvr.getOneRecorder(s.toString(), par);
			dbSvr.release();
			return m;
		} else {
			return null;
		}
	}
	
	/**
	 * 查询当前文件关联的内置附件信息
	 * @param con_table
	 * @param con_table_id
	 * @param dzwjsx
	 * @return
	 */
	public List<Map> queryNzfjs(String con_table,String con_table_id, String dzwjsx) {
		if (con_table_id != null && dzwjsx != null) {
			StringBuffer s = new StringBuffer();
			s.append("select * from jd_dzwj where zt!=-1 and con_table=? and con_table_id=? ");
			s.append(" and dzwjsx=? order by dzwjm desc, dzwj_id desc ");
			Object[] par = new Object[3];
			par[0]=con_table;
			par[1]=con_table_id;
			par[2]=dzwjsx;
			
			DbSvr dbSvr = DbSvr.getDbService(null);
			List<Map> res = dbSvr.executeQuery(s.toString(), par);
			dbSvr.release();
			return res;
		} else {
			return null;
		}
	}

	public List<Map> getDzwjInfos(String con_table, String con_table_id,
			String dzwjm) {
		if (con_table != null && con_table_id != null && dzwjm != null) {
			StringBuffer s = new StringBuffer("select * from jd_dzwj where zt!=-1 ");
			s.append(" and con_table=? and con_table_id=? and dzwjm like ? order by dzwjm desc,dzwj_id desc");
			
			Object[] par = new Object[3];
			par[0]=con_table;
			par[1]=con_table_id;
			par[2]=dzwjm;
			
			DbSvr dbSvr = DbSvr.getDbService(null);
			List<Map> list = dbSvr.executeQuery(s.toString(), par);
			dbSvr.release();
			return list;
		} else {
			return null;
		}
	}

	public void deleteById(DataMsgBus bus) {
		// 逻辑删除，将Dzwj记录ZT置为-1
		DbSvr dbSvr=DbSvr.getDbService(null);
		Map dzwj=dbSvr.getOneRecorder("select * from jd_dzwj where dzwj_id=?", new Object[]{bus.get("dzwj_id")});
		
		StringBuffer s = new StringBuffer(
				"update jd_dzwj set zt=-1 where dzwj_id=").append(bus
				.get("dzwj_id"));
		
		dbSvr.execute(String.valueOf(s));
		//删除时记入流程追踪
		bus.put("action", "删除"+dzwj.get("dzwjsx")+" "+dzwj.get("dzwjm"));
		bus.put("wjbm", dzwj.get("con_table_id"));
		//删除附件记录流程追踪时，将n_id置空，用于区分在流程追踪页签只显示流程记录过滤用（删除附件是用户操作，不是流程）
		bus.put("n_id", null);		
		new FlowListService().insertFlowlist(bus, dbSvr);
	}
	public void deleteById2(DataMsgBus bus) {
		// 逻辑删除，将Dzwj记录ZT置为-1
		DbSvr dbSvr=DbSvr.getDbService(null);
		Map dzwj=dbSvr.getOneRecorder("select * from jd_dzwj where dzwj_id=?", new Object[]{bus.get("dzwj_id")});
		
		StringBuffer s = new StringBuffer(
				"update jd_dzwj set zt=-1 where dzwj_id=").append(bus
						.get("dzwj_id"));
		
		dbSvr.execute(String.valueOf(s));
	}

	/**
	 * 设置文件占用状态
	 * 
	 * @param dzwj_id
	 * @throws Exception
	 */
	public void setDzwjIsWork(Map dzwj) throws Exception {
		StringBuffer s = new StringBuffer(
				"update jd_dzwj set ydzt=1").append(",ydr='").append(dzwj.get("ydr"))
				.append("',ydr_id=").append(dzwj.get("ydr_id")).append(",ksydsj=sysdate")
				.append(" where dzwj_id=").append(dzwj.get("dzwj_id"));
		DbSvr dbSvr = DbSvr.getDbService(null);
		try {
			dbSvr.setAutoCommit(false);
			dbSvr.execute(String.valueOf(s));
			dbSvr.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbSvr.rollback();
			throw new Exception();
		} finally {
			dbSvr.release();
		}
	}
	/**
	 * 取消文件占用状态
	 * 
	 * @param dzwj_id
	 * @throws Exception
	 */
	public void updateDzwjNoWork(String dzwj_id) throws Exception {
		StringBuffer s = new StringBuffer(
				"update jd_dzwj set ydzt=0,ydr=null,ydr_id=null,ksydsj=null where dzwj_id=")
				.append(dzwj_id);
		DbSvr dbSvr = DbSvr.getDbService(null);
		try {
			dbSvr.setAutoCommit(false);
			dbSvr.execute(String.valueOf(s));
			dbSvr.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbSvr.rollback();
			throw new Exception();
		} finally {
			dbSvr.release();
		}
	}

	public int getMaxId() {
		int id = 0;
		Map res = DbSvr.getDbService(null).getOneRecorder(
				"select seq_dzwj.NEXTVAL as id  from dual ", null);
		if (res != null) {
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}
		return id;
	}
	
	/**
	 * 将文件关联附件压缩成zip文件下载
	 * @param wjbm
	 * @return
	 */
	public String downLoadZip(String wjbm) {
		// TODO Auto-generated method stub
		List<Map> list=queryDzwjByWjbm(wjbm);
		String filepath="-1";
		if(list!=null&&list.size()>0){
			File[] files=new File[list.size()];
			for(int i=0;i<list.size();i++){
				Map m=list.get(i);
				String file_address=String.valueOf(m.get("file_catalog"));
				String file_name=String.valueOf(m.get("file_name"));
				//在linux环境下会出现"/opt/app/oa_up_file/\20151122145109-218132.wps"
				//导致"java.io.FileNotFoundException: /opt/app/oa_up_file/\20151122145109-218132.wps (没有那个文件或目录)"错误
				//File f=new File(file_address+"\\"+file_name);
				//File temp=new File(file_address+"\\"+String.valueOf(m.get("dzwjm")));
				File f=new File(file_address+file_name);
				File temp=new File(file_address+String.valueOf(m.get("dzwjm")));
				fileCopy(f,temp);
				files[i]=temp;
			}			
			String basePath=(String) RegServiceServlet.SYSCON.get("uploadPath");
			//File zipfile=new File(basePath+"\\"+"附件.zip");
			File zipfile=new File(basePath+"附件.zip");
			ZipUtil.zipFiles(files, zipfile);
			//filepath=basePath+"\\"+"附件.zip";
			filepath=basePath+"附件.zip";
		}
		return filepath;
	}
	/**
	 * 下载文件时，复制一个临时文件，将文件名从“时间戳”更改为真实名字
	 * @param s
	 * @param t
	 */
	public void fileCopy(File s,File t){
		FileInputStream fi=null;
		FileOutputStream fo=null;
		FileChannel in=null;
		FileChannel out=null;
		try{
			fi=new FileInputStream(s);
			fo=new FileOutputStream(t);
			in=fi.getChannel();//得到源文件通道
			out=fo.getChannel();//得到目标文件通道
			in.transferTo(0, in.size(), out);//连接两个通道，从in读取，写入到out
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				out.close();
				in.close();
				fo.close();
				fi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询当前签署意见的关联附件
	 * @param arg1
	 * @return
	 */
	public List<Map> queryByBusByPage3(DataMsgBus arg1) {
		// TODO Auto-generated method stub
		Object ids=arg1.get("ids");
		List<Map> res=null;
		if(ids!=null){
			String sIds=String.valueOf(ids);
			
			StringBuffer sql = new StringBuffer();
			sql.append("select * from jd_dzwj t where 1=1 and zt<>'-1' ");
			
			if (sIds != null) {
				if(sIds.indexOf(",")!=-1){
					if(sIds.startsWith(",")){
						sIds=sIds.substring(1,sIds.length());
					}
					sql.append(" and dzwj_id in (").append(sIds).append(")");
				}else{
					arg1.put("dzwj_id", sIds);
					sql.append(" AND $equal(dzwj_id,dzwj_id) ");
				}
			}
			
			SqlInfo sqlinfo = new SqlInfo(sql.toString());
			 res= DbSvr.getDbService("gwsys").getListResultByPage(arg1, sqlinfo);
		}
		
		return res;
	}
	/**
	 * 查询当前签署意见的关联附件(根据意见ID)
	 * @param arg1
	 * @return
	 */
	public List<Map> queryByBusByPage4(DataMsgBus arg1) {
		// TODO Auto-generated method stub
		List<Map> res=null;			
		StringBuffer sql = new StringBuffer();
		sql.append("select * from jd_dzwj t where 1=1 and zt<>'-1' and con_table='GW_LDPS' and con_table_id={ldps_id}");
		
		SqlInfo sqlinfo = new SqlInfo(sql.toString());
		res= DbSvr.getDbService("gwsys").getListResultByPage(arg1, sqlinfo);		
		return res;
	}
	/**
	 * 递归去掉如“,,,,2912,2913,,,,”字符串前后的“,”
	 * @param s
	 * @return
	 */
	private String recursion_sub(String s){
		if(String.valueOf(s).startsWith(",")){
			s=String.valueOf(s).substring(1,String.valueOf(s).length());
		}
		if(String.valueOf(s).endsWith(",")){
			s=String.valueOf(s).substring(0,String.valueOf(s).length()-1);					
		}
		if(String.valueOf(s).startsWith(",")||String.valueOf(s).startsWith(",")){
			s=recursion_sub(s);
		}
		return s;
	}
}