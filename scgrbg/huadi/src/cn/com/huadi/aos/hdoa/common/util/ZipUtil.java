package cn.com.huadi.aos.hdoa.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    /**
     * 功能:压缩多个文件成一个zip文件
     * @param srcfile：源文件列表
     * @param zipfile：压缩后的文件
     */
    public static void zipFiles(File[] srcfile,File zipfile){
        byte[] buf=new byte[1024];
        try {
            //ZipOutputStream类：完成文件或文件夹的压缩
            //ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
        	//采用apache的ZipOutputStream解决个别终端解压缩时中文文件名乱码问题
        	org.apache.tools.zip.ZipOutputStream out=new org.apache.tools.zip.ZipOutputStream(new FileOutputStream(zipfile));
            out.setEncoding("GBK");
        	for(int i=0;i<srcfile.length;i++){
                FileInputStream in=new FileInputStream(srcfile[i]);
                //out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                out.putNextEntry(new org.apache.tools.zip.ZipEntry(srcfile[i].getName()));
                int len;
                while((len=in.read(buf))>0){
                    out.write(buf,0,len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 功能:解压缩
     * @param zipfile：需要解压缩的文件
     * @param descDir：解压后的目标目录
     */
    public static void unZipFiles(File zipfile,String descDir){
        try {
            ZipFile zf=new ZipFile(zipfile);
            for(Enumeration entries=zf.entries();entries.hasMoreElements();){
                ZipEntry entry=(ZipEntry) entries.nextElement();
                String zipEntryName=entry.getName();
                InputStream in=zf.getInputStream(entry);
                OutputStream out=new FileOutputStream(descDir+zipEntryName);
                byte[] buf1=new byte[1024];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
                System.out.println("解压缩完成.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //2个源文件
       // File f1=new File("D:\\huadiSoft\\oc4j-10j-jtqy\\uploadfile\\20160413163201-201818.txt");
       // File f2=new File("D:\\huadiSoft\\oc4j-10j-jtqy\\uploadfile\\20160321150915-404406.wps");
        //File[] srcfile={f1,f2};
        //压缩后的文件
        //File zipfile=new File("D:\\huadiSoft\\oc4j-10j-jtqy\\uploadfile\\test.zip");
        //ZipUtil.zipFiles(srcfile, zipfile);
        
        //需要解压缩的文件
        File file=new File("D:\\huadiSoft\\oc4j-10j-jtqy\\uploadfile\\test.zip");
        //解压后的目标目录
        String dir="D:\\huadiSoft\\oc4j-10j-jtqy\\uploadfile\\test\\";
        ZipUtil.unZipFiles(file, dir);
    }

}