package cn.com.huadi.aos.hdoa.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

	public static void deleteFiles(String dir, String filename) {
		File fileDir = new File(dir);
		File[] filelist = fileDir.listFiles();
		for (File f : filelist) {
			if (f.isDirectory() && f.getName().contains(filename)) {
				deleteFile(f);
				f.delete();
			}
		}
	}

	public static void createNewFileDirectory(String path) {
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(path+"//不存在");
			file.mkdirs();
		} else {
			System.out.println(path+"//目录存在");
		}
	}

	/**
	 * 创建临时文件
	 * 
	 * @return
	 */
	public static File createTempFile() {
		File tempPath = new File(File.separator + "temp");
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			tempPath.mkdir(); // 如果不存在，则创建该文件夹
		}
		System.out.println(tempPath.getAbsolutePath());
		return tempPath;
	}

	/**
	 * 验证文件是否存在
	 * 
	 * @param path
	 * @param filename
	 * @return
	 */
	public static boolean isFile(String path, String filename) {
		boolean flag = false;
		File file = new File(path + filename);
		flag = file.isFile();
		return flag;
	}

	public static boolean isFile(String path) {
		boolean flag = false;
		File file = new File(path);
		flag = file.isFile();
		return flag;
	}

	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] fls = file.listFiles();
			for (File f : fls) {
				deleteFile(f);
			}
		} else {
			file.delete();
			System.out.println("删除 【" + file.getName() + "】文件");
		}
	}

	public static synchronized String convertStreamToString(InputStream is) {
		BufferedReader reader;
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			while ((line = reader.readLine()) != null) {
				sb.append(line.trim());
				// sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {

	}

}
