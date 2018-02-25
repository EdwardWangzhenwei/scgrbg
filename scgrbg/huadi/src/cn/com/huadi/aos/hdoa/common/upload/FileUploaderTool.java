package cn.com.huadi.aos.hdoa.common.upload;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.aisino.platform.view.DataMsgBus;

import cn.com.huadi.aos.hdoa.common.service.FileUploaderService;
import cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet;

/**
 * 
 * 上午8:35:34
 * 
 * @auto <a href="582078483@qq.com">zhangjun</a>
 * 
 **/

public class FileUploaderTool {

	public FileUploaderTool() {
		super();
	}

	public  List<Map> uploade(Map<String, File> l) throws Exception {
		List<Map> list = new ArrayList<Map>();
		if (l == null || l.isEmpty())
			return null;
		for (String key : l.keySet()) {
			Map map = new HashMap();
			File is = (File) l.get(key);
			if (is.canRead()) {
				String H_fileAddress = (String)RegServiceServlet.SYSCON
						.get("uploadPath");
				String random = String.valueOf(Math.random() * 10000000.0D);
				String H_fileName = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date())
						+ "-"
						+ random.substring(0, 6)
						//如果是国产终端新建文本文档，没有后缀名
						+ (key.indexOf(".")!=-1? key.substring(key.indexOf(".")):"");
				String realFileName = H_fileAddress + H_fileName;
				String show_name = key;
				String file_address = H_fileAddress;
				String file_ext = show_name
						.substring(show_name.lastIndexOf(".") + 1);
				String file_name = H_fileName;
				int size = 0;
				size = (int) Math
						.ceil((is.length() / (8.0 * 1024 * 1024)));
				
						DataInputStream in = null;
						DataOutputStream out = null;
						in = new DataInputStream(new FileInputStream(is));
						out = new DataOutputStream(new FileOutputStream(
								new File(realFileName)));
						int i;
						while ((i = in.read()) != -1) {
							out.write(i);
						}
						in.close();
						out.close();
						map.put("fileshowname",show_name );
						map.put("filerealname", H_fileName);
						map.put("fileaddress", realFileName);
						map.put("filetype", file_ext);
						map.put("filesize", size);
						map.put("filecatalog", H_fileAddress);
						list.add(map);
				
				
			}
		}
		return list;
		
	}
	
    public boolean uploade(Map<String, File> l,String lrr,String table,Integer tableid){
		
		if(l==null||l.isEmpty())
			return false;
		for(String key:l.keySet()){//keySet()  获取所有的键值集合
			File is=l.get(key);
			
			if(is.canRead()){
				String H_fileAddress = (String) RegServiceServlet.SYSCON
						.get("uploadPath");
				File f = new File(H_fileAddress);
		        if(!f.exists()){
		            f.mkdirs();        
		        }
				String random = String.valueOf(Math.random() * 10000000.0D);
				String H_fileName = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date())
				+ "-"
				+ random.substring(0, 6)
				+ key.substring(key.indexOf("."));
				String realFileName = H_fileAddress + H_fileName;
				String show_name = key;
				String file_address = H_fileAddress;
				String dzwjlx = show_name
						.substring(show_name.indexOf(".") + 1);
				String file_name = H_fileName;

				String UNION_TABLE = table;
				Integer UNION_TABLE_ID = tableid;
				
				Map<String, Object> data = new HashMap();
				data.put("dzwjms", show_name);
				data.put("file_catalog", file_address);
				data.put("dzwjlx", dzwjlx);
				//data.put("dzwj_size", size);
				data.put("file_name", file_name);
				data.put("lrr", lrr);
				//data.put("unitId", unitId);
				data.put("CON_TABLE", UNION_TABLE);
				data.put("CON_TABLE_ID", UNION_TABLE_ID);
				String dzwj_id="";
				try {
					dzwj_id=String.valueOf(this.GetFileUploaderService().save(data));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				if(dzwj_id!=null || !dzwj_id.equals("")){
					try {
						DataInputStream in = null;
						DataOutputStream out = null;
						in = new DataInputStream(new FileInputStream(is));
						out = new DataOutputStream(new FileOutputStream(
								new File(realFileName)));
						int i;
						while ((i = in.read()) != -1) {
							out.write(i);
						}
						in.close();
						out.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}					
				}
			}
		}
		return true;
	}


public int uploade(Map<String, File> l,String llr,String table,String tableid,String unitId){
		
		if(l==null||l.isEmpty())
			return -1;
		if(l.size()>1)
			return 1;
		for(String key:l.keySet()){//keySet()  获取所有的键值集合
			File is=l.get(key);
			
			if(is.canRead()){
				String H_fileAddress = (String) RegServiceServlet.SYSCON
						.get("uploadPath");
				File f = new File(H_fileAddress);
		        if(!f.exists()){
		            f.mkdirs();        
		        }
				String random = String.valueOf(Math.random() * 10000000.0D);
				String H_fileName = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date())
				+ "-"
				+ random.substring(0, 6)
				+ key.substring(key.indexOf("."));
				String realFileName = H_fileAddress + H_fileName;
				String show_name = key;
				String file_address = H_fileAddress;
				String dzwjlx = show_name
						.substring(show_name.indexOf(".") + 1);
				String file_name = H_fileName;

				String UNION_TABLE = table;
				int UNION_TABLE_ID = Integer.valueOf(tableid);
				
				Map<String, Object> data = new HashMap();
				data.put("dzwjms", show_name);
				data.put("file_catalog", file_address);
				data.put("dzwjlx", dzwjlx);
				// data.put("size", size);
				data.put("file_name", file_name);
				data.put("llr", llr);
				data.put("unitId", unitId);
				data.put("CON_TABLE", UNION_TABLE);
				data.put("CON_TABLE_ID", UNION_TABLE_ID);
				int dzwj_id=0;
				try {
					dzwj_id=Integer.valueOf(String.valueOf(this.GetFileUploaderService().save(data)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -1;
				}
				if(dzwj_id > 0){
					try {
						DataInputStream in = null;
						DataOutputStream out = null;
						in = new DataInputStream(new FileInputStream(is));
						out = new DataOutputStream(new FileOutputStream(
								new File(realFileName)));
						int i;
						while ((i = in.read()) != -1) {
							out.write(i);
						}
						in.close();
						out.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return -1;
					}					
				}
			}
		}
		return 0;
	}

    public FileUploaderService GetFileUploaderService(){
		return new FileUploaderService();
	}
}
