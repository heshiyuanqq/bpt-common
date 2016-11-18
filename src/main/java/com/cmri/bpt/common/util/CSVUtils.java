package com.cmri.bpt.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CSV操作(导出和导入)
 *
 * @author 范晓文
 * @version 20150907
 */
public class CSVUtils {

	
	
	
	
	public static boolean exportCsv(File file, List<String> dataList, boolean append) {
		
		boolean issuccess = false;

		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(file, append);
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);
			if (dataList != null && !dataList.isEmpty()) {
				for (String data : dataList) {
					bw.append(data).append("\r");
				}
			}
			issuccess = true;
		} catch (Exception e) {
			issuccess = false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return issuccess;
	}
	
	
	
	
	
	
	
	
	/**
	 * 导出
	 * 
	 * @param file
	 *            csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList
	 *            数据
	 * @return
	 */
	public static boolean exportCsv(File file, List<String> dataList) {
		boolean issuccess = false;

		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(file);
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);
			if (dataList != null && !dataList.isEmpty()) {
				for (String data : dataList) {
					bw.append(data).append("\r");
				}
			}
			issuccess = true;
		} catch (Exception e) {
			issuccess = false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return issuccess;
	}

	/**
	 * 导入
	 * 
	 * @param file
	 *            csv文件(路径+文件)
	 * @return
	 */
	public static List<List<String>> importCsv(File file) {
		InputStreamReader fr = null;
		BufferedReader br = null;
		List<List<String>> datalist = new ArrayList<List<String>>();
		try {
			fr = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(fr);
			String rec = null;
			String[] argsArr = null;
			while ((rec = br.readLine()) != null) {
				// System.out.println(rec);
				List<String> cells = new ArrayList<String>();
				argsArr = rec.split(",");
				for (int i = 0; i < argsArr.length; i++) {
					// System.out.println("num " + (i + 1) + ":" + argsArr[i]);
					cells.add(argsArr[i]);
				}
				datalist.add(cells);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null)
					fr.close();
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return datalist;
	}

	public static List<List<String>> importCsv(String str) {
		str = str.replace('\r', '\0');

		String[] lines = str.split("\n");

		List<List<String>> datalist = new ArrayList<List<String>>();

		String rec = null;
		String[] argsArr = null;
		for (String str_ : lines) {
			
			List<String> cells = new ArrayList<String>();
			argsArr = str_.split(",");
			
			for (int i = 0; i < argsArr.length; i++) {
				
				cells.add(argsArr[i]);
			}
			datalist.add(cells);
		}
		
		return datalist;

	}
}