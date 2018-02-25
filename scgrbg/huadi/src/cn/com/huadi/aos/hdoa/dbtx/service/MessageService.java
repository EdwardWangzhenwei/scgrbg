package cn.com.huadi.aos.hdoa.dbtx.service;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.aisino.platform.db.DbSvr;






import cn.com.huadi.aos.hdoa.dbtx.entity.Message;

public class MessageService {

	
	/**
	 * @function  插入数据的方法
	 * @param message
	 * @throws Exception
	 */
	public void insert(Message message,Connection conn) throws Exception{
		// 验证数据的有效性
		verifyMessage(message);
	 
		Statement stmt = null;
	        try {	           
	            // Statement里面带有很多方法，
	            stmt = conn.createStatement();
	            // 写SQL
	            String sql="INSERT INTO public_message (mess_id,sender,sender_id,send_unit,send_unit_id,receiver,receiver_id,receive_unit,receive_unit_id,send_time,open_url,open_type,subsystem,function,title,number_of_doc,security,message,emergency_degree,list_type,state,main_table,primary_key_value) "
	            									+ "VALUES ("
	            									+ getMaxId(conn)
	            									+ ",'"
	            									+ message.getSender()
	            									+ "',"
	            									+ message.getSender_id()
	            									+ ",'"
	            									+ message.getSend_unit()
	            									+ "',"
	            									+ message.getSend_unit_id()
	            									+ ",'"
	            									+ message.getReceiver()
	            									+ "',"
	            									+ message.getReceiver_id()
	            									+ ",'"
	            									+ message.getReceive_unit()
	            									+ "',"
	            									+ message.getReceive_unit_id()
	            									// + ","
	            									// + message.getSend_time()
	            									+ ",to_date('" + message.getSend_time() + "','%Y-%m-%d %H:%M:%S')"
	            									+ ",'" 									
	            									+ message.getOpen_url()
	            									+ "','"
	            									+ message.getOpen_type()
	            									+ "','"
	            									+ message.getSubsystem()
	            									+ "','"
	            									+ message.getFunction()
	            									+ "','"
	            									+ message.getTitle()
	            									+ "','"
	            									+ message.getNumber_of_doc()
	            									+ "','"
	            									+ message.getSecurity()
	            									+ "','"
	            									+ message.getMessage()
	            									+ "','"
	            									+ message.getEmergency_degree()
	            									+ "','"
	            									+ message.getList_type()
	            									+ "',"
	            									+ "未读,'"
	            									+ message.getMain_table()
	            									+ "','"
	            									+ message.getPrimary_key_value()
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
	 * @param message
	 * @throws Exception
	 */
	private void verifyMessage(Message message) throws Exception {
		// 判断参数是否有效
		// 1.单项数据的有效性
		    // state必须为 未读
		if(!("未读").equals(message.getState())){
			throw new Exception("输入数据不符合要求，请重新确认！");			
		}
		    // 发送时间不能为空
		if(message.getSend_time() != null){
			throw new Exception("输入数据不符合要求，请重新确认！");			
		}
		   // 判断打开方式是否为 页签 新窗口 页面跳转
		if(!(message.getOpen_type().equals("新窗口") || message.getOpen_type().equals("页面跳转") || message.getOpen_type().equals("页签") ||message.getOpen_type() == null)){
			throw new Exception("输入数据不符合要求，请重新确认！");
		}
			// 业务主表和业务表主键不能为空
		if(message.getMain_table() == null || message.getPrimary_key_value() == null){
			throw new Exception("输入数据不符合要求，请重新确认！");
		}
		
		// 2.判断逻辑有效性
		    // open_type不为空的时候,open_url不能为空
		if(message.getOpen_type() != null){
			if(message.getOpen_url() == null){
				throw new Exception("输入数据不符合要求，请重新确认！");
			}
		}
		    // open_url为空的时候,open_type必须为空
		if(message.getOpen_url() == null){
			if(message.getOpen_type() != null){
				throw new Exception("输入数据不符合要求，请重新确认！");
			}
		}
		    // 个人消息时， receiver_id不能为空
		if(("个人消息").equals(message.getList_type())){
			if(message.getReceiver_id() == null){
				throw new Exception("输入数据不符合要求，请重新确认！");
			}
		}		
		    // 部门待办时，receive_unit_id不能为空
		if(("部门消息").equals(message.getList_type())){
			if(message.getReceive_unit_id() == null){
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
	/*public void updateStateByKey(Message message) throws Exception{
		
		// 数据有效性校验
		if(message.getFinish_time() == null || message.getFinisher() == null || message.getFinisher_id() == null){			
			throw new Exception("输入数据不符合要求，请重新确认！");					
		}
		if(message.getMain_table() == null || message.getPrimary_key_value() == null){			
			throw new Exception("输入数据不符合要求，请重新确认！");					
		}
		
		
		
		
		Connection conn = null;
		 
        try {
            
            // 注册驱动
            Class.forName("com.gbase.jdbc.Driver");// 动态加载mysql驱动
            // 用户名
            String username="scdbtx";
            // 密码
            String password="123456";
            // 获取URL
            String url="jdbc:gbase://192.168.58.114:80/hddbtx";	 
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url,username,password);
            // Statement里面带有很多方法，
            Statement stmt = conn.createStatement();
            
            // 写SQL，执行SQL
            String sql="UPDATE public_todolist SET state='已办',"
            		+ "finisher='"+message.getFinisher()+"'"
            		+ "finish_time="+message.getFinish_time()
            		+ "finisher_id="+message.getFinisher_id()+"";
           //执行SQL
           stmt.execute(sql);
            
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            conn.close();
        }
		
		
		
	}*/
	
	
	
	
	/**
	 * @function 删除消息的方法
	 * @param  String main_table,String primary_key_value 
	 * @throws Exception 
	 * 
	 */ 
	public void deleteByKey(Message message,Connection conn) throws Exception{
				
		// 数据有效性校验
		if(message.getMain_table() == null || message.getPrimary_key_value() == null){			
			throw new Exception("输入数据不符合要求，请重新确认！");					
		}
		

		Statement stmt = null;
        try {
            
            // Statement里面带有很多方法，
            stmt = conn.createStatement();
            
            // 写SQL，执行SQL
            String sql="DELETE FROM public_message WHERE"
            		+ "main_table='"+message.getMain_table()+"'"
            		+ " AND primary_key_value='"+message.getPrimary_key_value()+"'";
            		
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
	 * @function 根据public_message序列获取ID方法
	 * 
	 */
	private int getMaxId(Connection conn) throws Exception{
		int id =0; 
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("select seq_public_message.NEXTVAL as id  from dual ");
		while(resultSet.next()){
			id=Integer.parseInt(resultSet.getString(1));			
			}
		/*Map res = DbSvr.getDbService(null).getOneRecorder("select seq_public_message.NEXTVAL as id  from dual ", null);
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

	
	

