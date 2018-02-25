package cn.com.huadi.aos.hdoa.common.util;

import java.util.Calendar;

/**
 * 制作公文标识工具类
 * 
 * @author hd
 * 
 */
public class GwBsUtil {
	/**
	 * 获取指定文种对应代码值
	 * 
	 * @param WZ
	 * @return
	 */
	static String getWZ(String WZ) {
		String returnStr = "";
		switch (WZ) {
		case "决议":
			returnStr = "1";
			break;
		case "决定":
			returnStr = "2";
			break;
		case "命令（令）":
			returnStr = "3";
			break;
		case "公报":
			returnStr = "4";
			break;
		case "公告":
			returnStr = "5";
			break;
		case "通告":
			returnStr = "6";
			break;
		case "意见":
			returnStr = "7";
			break;
		case "通知":
			returnStr = "8";
			break;
		case "通报":
			returnStr = "9";
			break;
		case "报告":
			returnStr = "A";
			break;
		case "请示":
			returnStr = "B";
			break;
		case "批复":
			returnStr = "C";
			break;
		case "议案":
			returnStr = "D";
			break;
		case "函":
			returnStr = "E";
			break;
		case "纪要":
			returnStr = "F";
			break;
		case "其他":
			returnStr = "Z";
		}
		// 根据最新规范，文种代码在公文标识中占2位，前面补“0”
		return "0" + returnStr;
	}

	/**
	 * 将字符转换成数字
	 * 
	 * @param Str
	 * @return
	 */
	static int getNum(String Str) {
		int returnNum = 0;
		switch (Str) {
		case "0":
			returnNum = 0;
			break;
		case "1":
			returnNum = 1;
			break;
		case "2":
			returnNum = 2;
			break;
		case "3":
			returnNum = 3;
			break;
		case "4":
			returnNum = 4;
			break;
		case "5":
			returnNum = 5;
			break;
		case "6":
			returnNum = 6;
			break;
		case "7":
			returnNum = 7;
			break;
		case "8":
			returnNum = 8;
			break;
		case "9":
			returnNum = 9;
			break;
		case "A":
			returnNum = 10;
			break;
		case "B":
			returnNum = 11;
			break;
		case "C":
			returnNum = 12;
			break;
		case "D":
			returnNum = 13;
			break;
		case "E":
			returnNum = 14;
			break;
		case "F":
			returnNum = 15;
			break;
		case "G":
			returnNum = 16;
			break;
		case "H":
			returnNum = 17;
			break;
		case "I":
			returnNum = 18;
			break;
		case "J":
			returnNum = 19;
			break;
		case "K":
			returnNum = 20;
			break;
		case "L":
			returnNum = 21;
			break;
		case "M":
			returnNum = 22;
			break;
		case "N":
			returnNum = 23;
			break;
		case "O":
			returnNum = 24;
			break;
		case "P":
			returnNum = 25;
			break;
		case "Q":
			returnNum = 26;
			break;
		case "R":
			returnNum = 27;
			break;
		case "S":
			returnNum = 28;
			break;
		case "T":
			returnNum = 29;
			break;
		case "U":
			returnNum = 30;
			break;
		case "V":
			returnNum = 31;
			break;
		case "W":
			returnNum = 32;
			break;
		case "X":
			returnNum = 33;
			break;
		case "Y":
			returnNum = 34;
			break;
		case "Z":
			returnNum = 35;
			break;
		}
		return returnNum;
	}

	/**
	 * 将数字转换成字符
	 * 
	 * @param Num
	 * @return
	 */
	static String getStr(int Num) {
		String returnStr = "";
		switch (Num) {
		case 0:
			returnStr = "0";
			break;
		case 1:
			returnStr = "1";
			break;
		case 2:
			returnStr = "2";
			break;
		case 3:
			returnStr = "3";
			break;
		case 4:
			returnStr = "4";
			break;
		case 5:
			returnStr = "5";
			break;
		case 6:
			returnStr = "6";
			break;
		case 7:
			returnStr = "7";
			break;
		case 8:
			returnStr = "8";
			break;
		case 9:
			returnStr = "9";
			break;
		case 10:
			returnStr = "A";
			break;
		case 11:
			returnStr = "B";
			break;
		case 12:
			returnStr = "C";
			break;
		case 13:
			returnStr = "D";
			break;
		case 14:
			returnStr = "E";
			break;
		case 15:
			returnStr = "F";
			break;
		case 16:
			returnStr = "G";
			break;
		case 17:
			returnStr = "H";
			break;
		case 18:
			returnStr = "I";
			break;
		case 19:
			returnStr = "J";
			break;
		case 20:
			returnStr = "K";
			break;
		case 21:
			returnStr = "L";
			break;
		case 22:
			returnStr = "M";
			break;
		case 23:
			returnStr = "N";
			break;
		case 24:
			returnStr = "O";
			break;
		case 25:
			returnStr = "P";
			break;
		case 26:
			returnStr = "Q";
			break;
		case 27:
			returnStr = "R";
			break;
		case 28:
			returnStr = "S";
			break;
		case 29:
			returnStr = "T";
			break;
		case 30:
			returnStr = "U";
			break;
		case 31:
			returnStr = "V";
			break;
		case 32:
			returnStr = "W";
			break;
		case 33:
			returnStr = "X";
			break;
		case 34:
			returnStr = "Y";
			break;
		case 35:
			returnStr = "Z";
			break;
		default:
			returnStr = "0";
		}
		return returnStr;
	}

	/**
	 * 获取公文标识
	 * 
	 * @param OID
	 *            电子公文OID，不参与计算，如：1.2.156.10.
	 * @param ZZJGDM
	 *            组织机构代码，长度9位，如：400002195
	 * @param NSJGDM
	 *            内设机构及下属单位代码，长度3位，如：C01
	 * @param NF
	 *            年份，长度4位，如：2004
	 * @param ZLDM
	 *            文件种类代码，长度1位，参见getWZ(String WZ)
	 * @param LSH
	 *            流水号，长度6位，系统自定义唯一编号
	 * @return
	 */
	public static String getGWBS(String OID, String ZZJGDM, String NSJGDM,
			String NF, String ZLDM, String LSH) {

		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		int M = 36; // 标准中要求是36

		String JYW = "";// 校验位
		String BS = ""; // 电子公文标识
		BS = ZZJGDM + NSJGDM + NF + getWZ(ZLDM) + LSH;
		a = M;
		for (int i = 0; i < BS.length(); i++) {
			b = a + getNum(String.valueOf(BS.charAt(i)));
			// c = b % M;
			c = b % M == 0 ? M : b % M;
			d = c * 2;
			e = d % (M + 1);
			// System.out.println("a="+a+"\tb="+b+"\tc="+c+"\td="+d+"\te="+e);
			a = e;
		}
		JYW = getStr((M + 1 - e) % M);
		// BS = BS + JYW;
		BS = OID + "." + ZZJGDM + "-" + NSJGDM + "-" + NF + "-" + getWZ(ZLDM)
				+ "-" + LSH + "-" + JYW;
		return BS;
	}

	public static void main(String[] args) {
		// System.out.println(getGWBS("400002195", "C01", "2017", "纪要",
		// "000002"));
		System.out.println(getGWBS("1.2.156.10", "12100000400002195R", "000",
				"2016", "报告", "00056"));
		System.out.println(36 % 37);
	}
}
