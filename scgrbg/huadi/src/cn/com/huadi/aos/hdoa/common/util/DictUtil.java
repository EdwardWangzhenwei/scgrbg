package cn.com.huadi.aos.hdoa.common.util;

import java.util.List;
import java.util.Map;



/**
 * 常用的字典项的group代码
 * @author hbj
 *
 */
public  class DictUtil {
	//密级字典group code;
	public static final String DICT_CODE_MJ = "mj";
	//会议类型字典;
	public static final String DICT_CODE_HYLX = "hylx";
	//紧急程度字典
	public static final String DICT_CODE_HJ = "hj";
	//期限类别字典
	public static final String DICT_CODE_QXLB = "qxlb";
	//公开形式字典
	public static final String DICT_CODE_GKXS = "gkxs";
	//文件类型字典
	public static final String DICT_CODE_WJLX = "wjlx";
	//交接原因字典
	public static final String DICT_CODE_JJYY = "jjyy";
	//取得方式字典
	public static final String DICT_CODE_QDFS = "qdfs";
	//资产状态字典
	public static final String DICT_CODE_ZCZT = "zczt";
	//产权形式字典
	public static final String DICT_CODE_CQXS = "cqxs";
	//权属性质字典
	public static final String DICT_CODE_QSXZ = "qsxz";
	//权属性质字典
	public static final String DICT_CODE_CGZZXS = "cgzzxs";
	//价值类型字典
	public static final String DICT_CODE_JZLX = "jzlx";
	//使用方向字典
	public static final String DICT_CODE_SYFX = "syfx";
	//单位分组字典
	public static final String DICT_CODE_DWFZ = "dwfz";
	//计量单位
	public static final String DICT_CODE_JLDW = "jldw";
	//15种文种元数据
	public static final String DICT_CODE_WZ = "wz";
	//点位类别
	public static final String DICT_CODE_JDDWLX = "jddwlx";
	//关键词（点位管理）
	public static final String DICT_CODE_KEYWORD = "keyWord";
	public static String getDictName(List<Map> dictList,String dictCode){
		String res =null;
		if(dictList!=null){
			for(Map data:dictList){
				String code =data.get("code").toString();
				if(code.equals(dictCode)){
					res = data.get("name").toString();
					break;
				}
			}
		}
		return res;
		
	}
	
}
