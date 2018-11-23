package org.jtest.app.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件处理操作
 * @author Administrator
 *
 */
public class FileUtil {
     public static void writeToFile(String data,String path,boolean isaddto){
    	 if(!new File(path).exists()){
    		 new File(path).getParentFile().mkdirs();
    	 }
    	 try {
			FileWriter fw=new FileWriter(new File(path),isaddto);
			fw.write(data);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     /**
      * 删除文件
      * @param path
      */
     public static void deleteFile(String path){
    	 if(new File(path).exists()){
    		 new File(path).delete();
    	 }
     }
}
