package cn.com.huadi.aos.hdoa.common.util;

public class Debug {

	// static int degree = 99999;
	public static int degree = 0;
	public static int debugConUseTime = 5000;

	public Debug() {
	}

	public static void setDegree(int i) {
		degree = i;
	}

	public static void debugMessage(int i, String s) {
		if (i >= degree)
			System.out.println(s);
	}

	public static int getDegree() {
		return degree;
	}

}
