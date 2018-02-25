package cn.com.huadi.aos.hdoa.dbtx.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




import java.util.Map;

import com.aisino.platform.db.DbSvr;

import cn.com.huadi.aos.hdoa.dbtx.entity.Backlog;
import cn.com.huadi.aos.hdoa.dbtx.entity.DataBase;


public class BacklogService {

	/**
	 * @function  插入数据的方法(public_todolist)
	 * @param backlog
	 * @throws Exception
	 */
	public void insert(Backlog backlog,Connection conn) throws Exception{
		// 验证数据有效性的方法
		verifyBacklog(backlog);
			
		// 连接数据库，开启事物执行插入操作	 
		Statement stmt = null;
	        try {
	            // Statement里面带有很多方法，
	            stmt = conn.createStatement();
	            // 写SQL
	            String sql="INSERT INTO public_todolist (list_id,sender,sender_id,send_unit,send_unit_id,receiver,receiver_id,receive_unit,receive_unit_id,send_time,do_or_read,open_url,open_type,list_level,subsystem,function,title,number_of_doc,security,message,emergency_degree,list_type,state,main_table,primary_key_value) "
	            									+ "VALUES ("
	            									+ getMaxId(conn)
	            									+ ",'"
	            									+ backlog.getSender()
	            									+ "',"
	            									+ backlog.getSender_id()
	            									+ ",'"
	            									+ backlog.getSend_unit()
	            									+ "',"
	            									+ backlog.getSend_unit_id()
	            									+ ",'"
	            									+ backlog.getReceiver()
	            									+ "',"
	            									+ backlog.getReceiver_id()
	            									+ ",'"
	            									+ backlog.getReceive_unit()
	            									+ "',"
	            									+ backlog.getReceive_unit_id()
	            									// + ","
	            									// + backlog.getSend_time()
	            									+ ",to_date('" + backlog.getSend_time() + "','%Y-%m-%d %H:%M:%S')"
	            									+ ",'"
	            									+ backlog.getDo_or_read()
	            									+ "','"
	            									+ backlog.getOpen_url()
	            									+ "','"
	            									+ backlog.getOpen_type()
	            									+ "','"
	            									+ backlog.getList_level()
	            									+ "','"
	            									+ backlog.getSubsystem()
	            									+ "','"
	            									+ backlog.getFunction()
	            									+ "','"
	            									+ backlog.getTitle()
	            									+ "','"
	            									+ backlog.getNumber_of_doc()
	            									+ "','"
	            									+ backlog.getSecurity()
	            									+ "','"
	            									+ backlog.getMessage()
	            									+ "','"
	            									+ backlog.getEmergency_degree()
	            									+ "','"
	            									+ backlog.getList_type()
	            									+ "',"
	            									+ "待办,'"
	            									+ backlog.getMain_table()
	            									+ "','"
	            									+ backlog.getPrimary_key_value()
	            									+ "'"
	            									+ ")";
	        
	           
	            // 开启事物
	            conn.setAutoCommit(true);
	            stmt.execute(sql);        
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception("数据库操作失败！！！");
	        } finally {
	        	stmt.close();
	            conn.close();
	        }
		
	}




	/**
	 * @param backlog
	 * @throws Exception
	 */
	private void verifyBacklog(Backlog backlog) throws Exception {
		// 判断参数的有效性:
		// 1.单项数据的有效性
			// 确认发送时间是否为空
		if(!(("待办").equals(backlog.getState()))){
			throw new Exception("输入数据不符合要求，请重新确认！");			
		}
		if(backlog.getSend_time() != null){
			throw new Exception("输入数据不符合要求，请重新确认！");			
		}
		   // 判断打开方式是否为 页签 新窗口 页面跳转
		if(!(("新窗口").equals(backlog.getOpen_type()) || ("页面跳转").equals(backlog.getOpen_type()) || ("页签").equals(backlog.getOpen_type()) || backlog.getOpen_type() == null)){
			throw new Exception("输入数据不符合要求，请重新确认！");
		}
		   // do_or_read只能为 待办  待阅
		if(!(("待办").equals(backlog.getDo_or_read()) || ("待阅").equals(backlog.getDo_or_read()))){
			throw new Exception("输入数据不符合要求，请重新确认！");
		}
		  // list_type只能为 个人待办  部门待办
		if(!(("个人待办").equals(backlog.getList_type()) || ("部门待办").equals(backlog.getList_type()))){
			throw new Exception("输入数据不符合要求，请重新确认！");
		}
		   // 业务主表和业务表主键不能为空
		if(backlog.getMain_table() == null || backlog.getPrimary_key_value() == null){
			throw new Exception("输入数据不符合要求，请重新确认！");
		}
		  // 插入待办时，不允许设置转为“已办”的信息
		if(!(backlog.getFinish_time() == null && backlog.getFinisher() == null && backlog.getFinisher_id() == null)){
			throw new Exception("输入数据不符合要求，请重新确认！");
		}
		
		// 2.判断逻辑有效性
			// open_type不为空时，open_url不能为空
		if(backlog.getOpen_type() != null){
			if(backlog.getOpen_url() == null){
				throw new Exception("输入数据不符合要求，请重新确认！");
			}
		}
		    // open_url为空时，open_type必须为空
		if(backlog.getOpen_url() == null){
			if(backlog.getOpen_type() != null){
				throw new Exception("输入数据不符合要求，请重新确认！");
			}
		}
		   // 个人待办时， receiver_id不能为空
		if(("个人待办").equals(backlog.getList_type())){
			if(backlog.getReceiver_id() == null){
				throw new Exception("输入数据不符合要求，请重新确认！");
			}
		}		
		   // 部门待办时，receive_unit_id不能为空
		if(("部门待办").equals(backlog.getList_type())){
			if(backlog.getReceive_unit_id() == null){
				throw new Exception("输入数据不符合要求，请重新确认！");
			}
		}
	}
	
	
	
	
	/**
	 * @throws Exception 
	 * @function 更新状态的方法:update state为已办及完成finish相关信息
	 * @param  String main_table,String primary_key_value
	 * 
	 */ 
	public void updateStateByKey(Backlog backlog,Connection conn) throws Exception{
		
		// 数据有效性校验：1.state为“已办”;2.finish信息不允许为空;3."key"信息必须不为空
		if(!("已办").equals(backlog.getState())){
			throw new Exception("输入数据不符合要求，请重新确认！");	
		}
		if(backlog.getFinish_time() == null || backlog.getFinisher() == null || backlog.getFinisher_id() == null){			
			throw new Exception("输入数据不符合要求，请重新确认！");					
		}
		if(backlog.getMain_table() == null || backlog.getPrimary_key_value() == null){			
			throw new Exception("输入数据不符合要求，请重新确认！");					
		}
			
		
		Statement stmt = null;
        try {         
            stmt = conn.createStatement();           
            // 写SQL，执行SQL
            String sql="UPDATE public_todolist SET state='已办',"
            		+ "finisher='"+backlog.getFinisher()+"'"
            		// + ",finish_time="+backlog.getFinish_time()
            		+ ",finish_time="+"to_date('" + backlog.getFinish_time() + "','%Y-%m-%d %H:%M:%S')"
            		+ ",finisher_id="+backlog.getFinisher_id()+"";
           //执行SQL
           stmt.execute(sql);          
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("数据库操作失败！！！");
        } finally {
        	stmt.close();
            conn.close();
        }		
	}
	
	
	
	
	/**
	 * @function 删除的方法
	 * @param  String main_table,String primary_key_value 
	 * @throws Exception 
	 * 
	 */ 
	public void deleteByKey(Backlog backlog,Connection conn) throws Exception{
				
		// 数据有效性校验
		if(backlog.getMain_table() == null || backlog.getPrimary_key_value() == null){			
			throw new Exception("输入数据不符合要求，请重新确认！");					
		}
			
		Statement stmt = null;
        try {           
            stmt = conn.createStatement();
            
            // 写SQL，执行SQL
            String sql="DELETE FROM public_todolist WHERE"
            		+ "main_table='"+backlog.getMain_table()+"'"
            		+ " AND primary_key_value='"+backlog.getPrimary_key_value()+"'";
            		
           //执行SQL
           stmt.execute(sql);
            
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("数据库操作失败！！！");
        } finally {
        	stmt.close();
            conn.close();
        }
		
	}
	
	
	
	/**
	 * @throws Exception 
	 * @function 根据序列获取ID方法
	 */
	private int getMaxId(Connection conn) throws Exception{
		Statement stmt = conn.createStatement();
		int id =0; 
		ResultSet resultSet = stmt.executeQuery("select seq_public_todolist.NEXTVAL as id  from dual ");
		while(resultSet.next()){
			id = Integer.parseInt(resultSet.getString(1));
		}
		/*Map res = DbSvr.getDbService(null).getOneRecorder("select seq_public_todolist.NEXTVAL as id  from dual ", null);
		//Map res = DbSvr.getDbService(null).getOneRecorder("select nvl(max(hy_id)+1,1) as id from hy_jbxx ", null);
		if(res!=null){
			id = Integer.parseInt(String.valueOf(res.get("id")));
		}*/
		resultSet.close();
		stmt.close();
		conn.close();
		return id;
	}

}
