package cn.com.huadi.aos.hdoa.common.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static String byteToAryString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	private static String byteToNum(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	private static String byteToString(byte[] bByte) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			buffer.append(byteToAryString(bByte[i]));
		}
		return buffer.toString();
	}

	public static String getMD5Code(String obj) {
		String res = null;
		try {
			res = obj;
			MessageDigest md = MessageDigest.getInstance("MD5");
			res = byteToString(md.digest(obj.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
