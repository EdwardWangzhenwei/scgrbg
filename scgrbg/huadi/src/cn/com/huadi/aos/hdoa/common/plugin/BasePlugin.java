package cn.com.huadi.aos.hdoa.common.plugin;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.com.huadi.aos.hdoa.common.service.HuadiLogService;




import com.aisino.platform.core.Plugin;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.DataMsgBus;

public class BasePlugin extends Plugin {
	public void insertLog(String operate_name, String operate_object,
			String operate_description) throws Exception {
		Map param = new HashMap();
		HttpSession session = SessUtil.getRequest().getSession();
		HttpServletRequest request = SessUtil.getRequest();
		Map userInfo = (Map) session.getAttribute("USERINFO");
		if (userInfo != null) {
			param.put("user_id", userInfo.get("user_id"));
			param.put("user_name", userInfo.get("user_name"));
			param.put("unit_id", userInfo.get("unit_id"));
			param.put("unit_name", userInfo.get("unit_name"));
//			param.put("ip", this.getRemortIP(request));
			param.put("ip", SessUtil.getClientIP());
			//param.put("mac_address", local.getLocalMac(ia));
			param.put("operate_name", operate_name);
			param.put("operate_object", operate_object);
			param.put("subsystemname", "电子公文处理系统");
			param.put("operate_description", operate_description);
			new HuadiLogService().businessLog(param);
		}
	}

	private String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 对页面录入值进行转义，过滤掉“'”防止sql执行错误
	 * @param bus
	 * @return
	 */
	public DataMsgBus transferBus(DataMsgBus bus) {
		Set<Map.Entry<String, Object>> entry = bus.entrySet();
		for (Map.Entry<String, Object> entry_ : entry) {
			System.out.println(String.valueOf(entry_.getKey()) + ":"
					+ String.valueOf(entry_.getValue()));
			if (entry_.getValue() instanceof String) {
				if (String.valueOf(entry_.getValue()).indexOf("'") >= 0) {
					entry_.setValue(entry_.getValue().toString()
							.replace("'", "''"));
				}
			}
		}
		return bus;
	}
	
	@Override
	public void setValue(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}
}
