package cn.com.huadi.aos.hdoa.common.util;

public class Computational3736 {
	public static int M=36;
	public static int S=0;
	public static int P=0;
	
	public void setModuli(int M){
		this.M=M;
	}
	public int getModuli(){
		return M;
	}

	/***
	 * 输入字符串s为本体码，根据算法【ISO/IEC 7064,MOD37,36】生成一个一位的校验码
	 * M=36
	 * M+1=37
	 * 公式：
	 * @param s
	 * @return
	 */
	public static String calculate(String s){
		System.out.println("算法【ISO/IEC 7064,MOD37,36】计算过程：");
		if(s==null||"".equals(s)){return null;}
		
		int sInt[]=string2IntArray(s);
		
		//计算过程
		//第一步：初始化
		P=M;
		System.out.println("初始化：模="+M);
		System.out.println("输出列：Sj - Pj+1 - Pj+1|(M+1)");
		//第二步：过程计算
		for(int j=0;j<sInt.length;j++){//从第一位开始循环，到最后一位。
			System.out.print("第"+(j+1)+"次运算值：");
			S=myMod(P,M+1)+sInt[j];
			System.out.print(S+"-");
			P=myMod(S,M)*2;
			System.out.print(P+"-");
			P=myMod(P,M+1);
			System.out.println(P);
		}
		
		//第三步：校验位计算
		int checkNum=(M+1-P);//因为P一定小于M+1
		System.out.println("校验码值："+checkNum);
		char check=switchNumber2Letter(checkNum);
		System.out.println("校验码："+check);
		String val=check+"";
		return val;
	}
	/***
	 * 将字符串s转换成int型的数组，字符串中的数字对应十进制的数字，字符对应关系为a-z对应10-35；
	 * 
	 * @param s
	 * @return
	 */
	private static int[] string2IntArray(String s){
		if(s==null||"".equals(s)){return null;}
		char sChar[]=s.toCharArray();
		int sInt[]=new int[sChar.length];
		for(int i=0;i<sChar.length;i++){
			sInt[i]=Character.getNumericValue(sChar[i]);//将给定的字符转换为数字,其中a-z对应10-35
			System.out.println("第"+(i+1)+"个字符："+"字符"+sChar[i]+"值为："+sInt[i]);
		}
		return sInt;
		
	}
	/***
	 * 将给定的数字转换为字符a-z对应10-35
	 * @param i
	 * @return
	 */
	private static char switchNumber2Letter(int i){
		if(i<10||i>35){return Character.toChars(42)[0];}
		//if(i==10){return "A";}
		return Character.toChars(i+55)[0];
	}
	
	
	/***
	 * "||M" 运算,求一个数模M后的余数，若果余数为零则返回其本身，否则返回余数。
	 * x%y表示x模y的值
	 * @param x
	 * @param y （模）
	 * @return
	 */
	public static int myMod(int x,int y){
		return x%y==0?x:x%y;
		
	}
	public static void main(String args[]){
		calculate("12100000400002195R00020160A00056");		
	}
}
