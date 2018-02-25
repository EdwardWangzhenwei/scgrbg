package cn.com.huadi.aos.hdoa.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet;

import com.aisino.platform.db.DbSvr;

@SuppressWarnings("unused")
public class MenuService {

	public MenuService() {
		super();
		// TODO Auto-generated constructor stub
	}
  public String getMenuHtml(int userid,int subsystemid){
	  StringBuffer htmlString = new StringBuffer("");
	  //查询当前登录人的权限信息
	  List<Map> menus =  this.queryUserMenuList(userid,subsystemid);
	  //拼html�?菜单�?按照规范
	  if(menus!=null){
		  for(Map menu:menus){
			  String name = String.valueOf(menu.get("function_name"));
          
			  
			 int uperfunid = Integer.parseInt(String.valueOf(menu.get("uper_function_id")));
			  String url = null;
			  if(menu.get("function_discription")!=null){
				  url =  String.valueOf(menu.get("function_discription"));
			  }
			  if(url==null){
				  url="javascript:void(0)";
			  }
			  int funid =  Integer.parseInt(String.valueOf(menu.get("function_id")));
			  int level = Integer.parseInt(String.valueOf(menu.get("level")));
			  int cn = Integer.parseInt(String.valueOf(menu.get("cn")));
			  //获取下级子菜单信�?
			  if(uperfunid==0){
				 
				  htmlString.append("<li class=\"level1\"><a href=\""+url+"\"  target=ifrm>"+name+"</a>");
				  //如果没有有上级节点
				  List<Map> submenus = new ArrayList<Map>();
				  for(Map submenu:menus){
					  int subuperfunid = Integer.parseInt(String.valueOf(submenu.get("uper_function_id")));
					  if(subuperfunid!=0&&subuperfunid==funid){
						  submenus.add(submenu); 
					  }
			        }
				  //如果有下级子菜单
				  if(submenus.size()>0){
					  htmlString.append("<ul class=\"level2\">");
					  for(Map submenu:submenus){
						    String subname = String.valueOf(submenu.get("function_name"));
							int subuperfunid = Integer.parseInt(String.valueOf(submenu.get("uper_function_id")));
							String suburl = null;
							if(submenu.get("function_discription")!=null){
								suburl =  String.valueOf(submenu.get("function_discription"));
							 }
							 if(suburl==null){
								 suburl="javascript:void(0)";
							 }
							 int subfunid =  Integer.parseInt(String.valueOf(submenu.get("function_id")));
							 int sublevel = Integer.parseInt(String.valueOf(submenu.get("level")));
							 int subcn = Integer.parseInt(String.valueOf(submenu.get("cn")));
							  htmlString.append("<li><a href=\""+suburl+"\"  >"+subname+"</a></li>");
					  }
					  htmlString.append("</ul>"); 
				  }
				  htmlString.append("</li>");  
			
			  }
		  }
	  }
	  return htmlString.toString();
  }
  
  public String getMenuHtmlNew(int userid,int subsystemid){
	  StringBuffer htmlString = new StringBuffer("");
	  //查询当前登录人的权限信息
	  List<Map> menus =  this.queryUserMenuList(userid,subsystemid);
	  //拼html�?菜单�?按照规范
	  if(menus!=null){
		  for(Map menu:menus){
			  String name = String.valueOf(menu.get("function_name"));
          
			  
			 int uperfunid = Integer.parseInt(String.valueOf(menu.get("uper_function_id")));
			  String url = null;
			  if(menu.get("function_discription")!=null){
				  url =  String.valueOf(menu.get("function_discription"));
			  }
			  if(url==null){
				  url="javascript:void(0)";
			  }
			  int funid =  Integer.parseInt(String.valueOf(menu.get("function_id")));
			  int level = Integer.parseInt(String.valueOf(menu.get("level")));
			  int cn = Integer.parseInt(String.valueOf(menu.get("cn")));
			  //获取下级子菜单信�?
			  if(uperfunid==0){
				  htmlString.append("<dl><dt class=\"level1\" href=\""+url+"\"  target=ifrm>"+name+"<img src='images/select_xl01.png'></dt>");
				  //如果没有有上级节点
				  List<Map> submenus = new ArrayList<Map>();
				  for(Map submenu:menus){
					  int subuperfunid = Integer.parseInt(String.valueOf(submenu.get("uper_function_id")));
					  if(subuperfunid!=0&&subuperfunid==funid){
						  submenus.add(submenu); 
					  }
			        }
				  //如果有下级子菜单
				  if(submenus.size()>0){
					  for(Map submenu:submenus){
						    String subname = String.valueOf(submenu.get("function_name"));
							int subuperfunid = Integer.parseInt(String.valueOf(submenu.get("uper_function_id")));
							String suburl = null;
							if(submenu.get("function_discription")!=null){
								suburl =  String.valueOf(submenu.get("function_discription"));
							 }
							 if(suburl==null){
								 suburl="javascript:void(0)";
							 }
							 int subfunid =  Integer.parseInt(String.valueOf(submenu.get("function_id")));
							 int sublevel = Integer.parseInt(String.valueOf(submenu.get("level")));
							 int subcn = Integer.parseInt(String.valueOf(submenu.get("cn")));
							  htmlString.append("<dd href=\""+suburl+"\"  >"+subname+"</dd>");
					  }
				  }
				  htmlString.append("</dl>");  
			
			  }
		  }
	  }
	  System.out.println("###################"+htmlString);
	  return htmlString.toString();
  }
  private List<Map> queryUserMenuList(int userid,int subsystemid){
	 
	  Object[] params = new Object[4];
		params[0] = userid;
		params[1] = subsystemid;
		params[2] = userid;
		params[3] = subsystemid;
		
		DbSvr service = DbSvr.getDbService("acc_sys");
		List<Map> res = service.queryIdForList("privsys.queryUserFunctions", params);
		service.release();
	    return  res;
  }
  public List<Map> queryTopMenu(int userid,String subsystemid){
	  Object[] params = new Object[2];
		params[0] = userid;
		params[1] = subsystemid;
		DbSvr service = DbSvr.getDbService("acc_sys");
		List<Map> res = service.queryIdForList("privsys.queryUserTopFunctions", params);
		service.release();
	    return  res;
}

}
