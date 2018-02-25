/**
 * 
 */
package cn.com.huadi.aos.hdoa.common.util;

import java.util.UUID;

/**
 * @author qinyalin
 *
 * 2017年7月18日
 */
public class UuidUtil {
	
	public static String  Uuid() {
		String uuid=UUID.randomUUID().toString().replace("-", ""); 
		return uuid;
	}

}
