package cn.com.huadi.aos.hdoa.common.util;

public class GwBsUtil2 {
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
	
	public static String getGWBS(String OID, String ZZJGDM, String NSJGDM,
			String NF, String ZLDM, String LSH){
		int M = 36;
		int r = 2;
		String BS = ZZJGDM + NSJGDM + NF + getWZ(ZLDM) + LSH;
		int n= BS.length();
		int P = M;
		int S = 0;
		int j = 0;
		
		while(j++<n){
			int pj=getIntegerFromString(BS.toUpperCase(),j-1);
			S = P+pj;
			S = S%M;
			if(S==0)S=M;
			P = S*r;
			P = P%(M+1);
			if(P==0)P=M;
		}
		int result = M+1-P;
		if(P==1){
			if(M==11)return "X";
			if(M==36)return "*";
		}
		
		//if(result<10)return result+"";
			
		//char c = (char)((result-10)+'A');
		if(result<10)		
			BS=OID + "." + ZZJGDM + "-" + NSJGDM + "-" + NF + "-" + getWZ(ZLDM)
			+ "-" + LSH + "-" +result;
		else
			BS=OID + "." + ZZJGDM + "-" + NSJGDM + "-" + NF + "-" + getWZ(ZLDM)
			+ "-" + LSH + "-" +(char)((result-10)+'A');
		return BS;
	}
	private static int getIntegerFromString(String rawText,int index){
		char a = rawText.charAt(index);
		if(a>='0'&&a<='9')return Integer.valueOf(a+"");
		if(a>='A'&&a<='Z')return a-'A'+10;
		return -1;
	}
	public static void main(String[] args) {
		System.out.println(getGWBS("1.2.156.10", "113400000029860376", "001",
				"2017", "纪要", "00001"));
	}
}
