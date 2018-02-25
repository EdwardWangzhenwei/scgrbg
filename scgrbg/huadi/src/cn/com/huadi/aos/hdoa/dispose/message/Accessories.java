/**
 * 
 */
package cn.com.huadi.aos.hdoa.dispose.message;

import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/***
 * 附件集
 * 
 * @author nmj
 * @E-mail bjahqj@163.com
 * @date 2016年4月26日上午9:25:59
 * @return
 * 
 */

@XStreamAlias("附件集")
public class Accessories {
	public Accessories() {
	}

	@XStreamImplicit(itemFieldName = "附件")
	List<AccessoryType> accessorylist;

	/**
	 * @return the accessorylist
	 */
	public List<AccessoryType> getAccessorylist() {
		return accessorylist;
	}

	/**
	 * @param accessorylist2
	 *            the accessorylist to set
	 */
	public void setAccessorylist(List<AccessoryType> accessorylist2) {
		this.accessorylist = accessorylist2;
	}

}
