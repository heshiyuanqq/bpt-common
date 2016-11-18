package com.cmri.bpt.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveStringToFile {
	public static String SaveJsonToFile(String data,String file_path,String file_name) throws IOException{
		File file=new File(file_path);
		if  (!file .exists()  && !file .isDirectory()){
			file.mkdirs();
		}
		
		File file1=new File(file_name);
		if(!file1.exists()){
			file1.createNewFile();
		}
	
		FileWriter filewriter=new FileWriter(file_name,true);//在文件末尾追加信息
	    filewriter.write(data);
		filewriter.flush();
		filewriter.close();
		
		return null;
		
	}
	
	
	
	public static String SaveJsonToFile1(String data,String file_path,String file_name) throws IOException{
		File file=new File(file_path);
		if (!file.exists()  && !file.isDirectory()){
			file.mkdirs();
		}
		
		File file1=new File(file_name);
		if(!file1.exists()){
			file1.createNewFile();
		}
	
		FileWriter filewriter=new FileWriter(file_name,false);//清楚原来的信息，重写信息
	    filewriter.write(data);
		filewriter.flush();
		filewriter.close();
		
		return null;
		
	}

}
