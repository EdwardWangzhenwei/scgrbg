package cn.com.huadi.aos.hdoa.login.plugin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.com.huadi.aos.hdoa.common.service.HuadiLogService;
import cn.com.huadi.aos.hdoa.common.util.DESUtil;
import cn.com.huadi.aos.hdoa.login.service.LoginService;

import com.aisino.platform.core.Plugin;
import com.aisino.platform.exception.BusinessException;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.AbstractForm;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.listener.SubmitListener;

public class LoginPlugin extends Plugin implements SubmitListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void doSubmitAction(AbstractForm form, DataMsgBus bus) {
		String username = (String) bus.get("username");
		String userpwd = (String) bus.get("userpwd");
		// 用户基本信息
		Map userInfo = null;
		// 登录查询结果
		Map resultInfo = null;
		try {
			//密码解密
//			userpwd = DESUtil.encrypt(userpwd);
			// 按登录账号名密码
			
			//密码加密
			userpwd = DESUtil.encrypt(userpwd);
			
			resultInfo = this.getLoginService().queryUserByAccountName(
					username, userpwd);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException("数据库操作错误，请联络系统管理员!");
		}
		if (resultInfo != null) {
			if (resultInfo.get("result").toString().equals("ok")) {
				// 获得用户信息,保存在session中
				userInfo = (Map) resultInfo.get("data");
				int unit_flag = Integer.parseInt(String.valueOf(userInfo
						.get("unit_flag")));
				// 当前登录人直属部门ID和部门名称
				int unit_id = Integer.parseInt(String.valueOf(userInfo.get("unit_id")));
				/*userInfo.put("dept_id", userInfo.get("unit_id"));
				userInfo.put("dept_name", userInfo.get("unit_name"));*/
				int id=	Integer.parseInt(userInfo.get("unit_id").toString());
				
				Map upUnit = this.getLoginService().queryUnitByDeptid(id);
				if (upUnit != null) {
					userInfo.put("unit_id", upUnit.get("unit_id"));
					userInfo.put("unit_name", upUnit.get("unit_name"));
					userInfo.put("unit_name_short", upUnit.get("unit_name_short"));
					//在用户登录信息中添加单位统一社会信用代码、内设机构代码--20160407 lwf---start
					userInfo.put("credit_code", upUnit.get("credit_code"));
					userInfo.put("subunit_code", upUnit.get("subunit_code"));						
					//在用户登录信息中添加单位统一社会信用代码、内设机构代码--20160407 lwf---end
					userInfo.put("unit_jc", upUnit.get("unit_jc"));//单位简称
				}else{
					form.setReturn("当前用户无所属单位信息！");
					return;
				}
				
				
				
				upUnit = this.getLoginService().queryDeptByDeptid(id);
				if (upUnit != null) {
					userInfo.put("dept_id", upUnit.get("unit_id"));
					userInfo.put("dept_name", upUnit.get("unit_name"));
					userInfo.put("dept_name_short", upUnit.get("unit_name_short"));
					userInfo.put("dept_jc", upUnit.get("unit_jc"));//部门简称
				}
				else{
					userInfo.put("dept_id",userInfo.get("unit_id"));
					userInfo.put("dept_name",userInfo.get("unit_name"));
					userInfo.put("dept_name_short",userInfo.get("unit_name_short"));
					userInfo.put("dept_jc",userInfo.get("unit_jc"));
				}
				
				 upUnit = this.getLoginService().querySelfByDeptid(id);
				if (upUnit != null) {
					userInfo.put("group_id", upUnit.get("unit_id"));
					userInfo.put("group_name", upUnit.get("unit_name"));
					userInfo.put("group_name_short", upUnit.get("unit_name_short"));
					userInfo.put("group_jc", upUnit.get("unit_jc"));//部门简称
				}
				else{
					userInfo.put("group_id", upUnit.get("dept_id"));
					userInfo.put("group_name", upUnit.get("dept_name"));
					userInfo.put("group_name_short", upUnit.get("dept_name_short"));
					userInfo.put("group_jc", upUnit.get("dept_jc"));//部门简称
				}
				
				//=----------------------------------------------------------------------
				
					
					
				
				//-----------------------------------------------------------------
					
				HttpSession session = SessUtil.getRequest().getSession();
				session.putValue("USERINFO", userInfo);
				System.out.println(userInfo);
				
				//记录登录日志
				Map param= new HashMap();
				param.put("user_account_name", userInfo.get("user_account_name"));
				param.put("user_id", userInfo.get("user_id"));	
				param.put("unit_name", userInfo.get("unit_name"));
				param.put("unit_id", userInfo.get("unit_id"));
				param.put("ip",SessUtil.getRequest().getRemoteAddr());
				param.put("operate_name", "登录");
				param.put("operate_object",  userInfo.get("user_account_name")+"登录");
				param.put("subsystemname", "会议管理系统");	
				param.put("operate_description", userInfo.get("user_account_name")+"成功登录公文处理系统-人员ID为"+userInfo.get("user_id"));
				param.put("type", "会议管理系统");
				
				try {
					new HuadiLogService().privsysAndBusinessLoginLog(param);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
				form.setReturn("ok");
				
			} 
			else {
				String msg = resultInfo.get("message").toString();
				form.setReturn(msg);
			}
		}
	}

	public LoginService getLoginService() {
		return new LoginService();
	}

	@Override
	public void setValue(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

}
