package com.cmri.bpt.common.util;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmri.bpt.common.entity.FileNamesPO;
import com.cmri.bpt.common.entity.LogSequenceVO;
import com.cmri.bpt.common.user.UserContext;
import com.cmri.bpt.common.user.UserContextHolder;

public class GetFileName {

	static Logger logger = Logger.getLogger(GetFileName.class);

	/**
	 * 获取文件夹下所有文件的名字
	 * 
	 * @param path
	 * @return
	 */
	public static Map<String, Map<String, Integer>> maps;

	@Autowired

	public static List<FileNamesPO> getFileName(String path) {
		List<FileNamesPO> filenames = new ArrayList<FileNamesPO>();
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return null;
		}

		File fa[] = f.listFiles();

		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			if (fs.isDirectory()) {
				FileNamesPO getfilename = new FileNamesPO();
				getfilename.setFilename(fs.getName());
				filenames.add(getfilename);
			} else if (fs.isFile()) {
				FileNamesPO getfilename = new FileNamesPO();
				getfilename.setFilename(fs.getName());
				filenames.add(getfilename);
			}
		}
		return filenames;
	}

	/**
	 * 获取指定文件夹下所有文件数目
	 * 
	 * @param path
	 * @return
	 */
	public static int getFileCount(String path) {
		File f = new File(path);
		if (!f.exists()) {
			logger.debug(path + " not exists");
			return 0;
		}

		File fa[] = f.listFiles();
		return fa.length;
	}

	/**
	 * 获取指定文件夹下所有文件夹的名称
	 * 
	 * @param path
	 * @return
	 * @throws SQLException
	 */
	/*
	 * public List<FileNamesPO> getFolderName(String path) throws SQLException {
	 * List<FileNamesPO> filenames=new ArrayList<FileNamesPO>(); File f = new
	 * File(path); if (!f.exists()) { System.out.println(path + " not exists");
	 * return null; }
	 * 
	 * File fa[] = f.listFiles();
	 * 
	 * try{ for (int i = 0; i < fa.length; i++) { File fs = fa[i]; if
	 * (fs.isDirectory()) { FileNamesPO getfilename=new FileNamesPO(); String
	 * casename=path.split("/")[2]; LogSequenceVO logsequencevo=new
	 * LogSequenceVO(); logsequencevo.setCase_file_name(casename);
	 * logsequencevo.setLocation(fs.getName()); LogSequenceVO getlogvo=new
	 * LogSequenceVO();
	 * getlogvo=logsequenceservice.selectLogSequenceByCaseNameAndLocation(
	 * logsequencevo); if(getlogvo==null){ continue; }else{
	 * getfilename.setFilename(fs.getName()+"/"+getlogvo.getSequence_no());
	 * filenames.add(getfilename); } } } }catch(Exception e){
	 * e.printStackTrace(); } return filenames; }
	 */

	public List<FileNamesPO> getFolderName(String path) throws SQLException {
		List<FileNamesPO> filenames = new ArrayList<FileNamesPO>();
		// String userid="user1";
		UserContext ctx = UserContextHolder.getUserContext();
		String userid = ctx.getUserId().toString();
		Map<String, Integer> mymap = new HashMap<String, Integer>();
		mymap = maps.get(userid);
		try {
			for (Map.Entry<String, Integer> entry : mymap.entrySet()) {
				FileNamesPO getfilename = new FileNamesPO();
				getfilename.setFilename(entry.getKey() + "/" + entry.getValue());
				filenames.add(getfilename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filenames;
	}

	/**
	 * 文件夹中是否含有某个文件名
	 * 
	 * @param path
	 * @param filename
	 * @return
	 */
	public static boolean IsExistfile(String path, String filename) {

		File f = new File(path);
		if (!f.exists()) {
			logger.debug(path + " not exists");
			return false;
		}

		logger.debug(" GetFileName " + path + "/" + filename);

		File fa[] = f.listFiles();

		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			if (fs.isFile() && fs.getName().equals(filename)) {
				return true;
			}
		}
		return false;

	}

	public List<FileNamesPO> getFolderName1(String path) {
		List<FileNamesPO> filenames = new ArrayList<FileNamesPO>();
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return null;
		}

		File fa[] = f.listFiles();

		for (int i = fa.length - 1; i >= 0; i--) {
			File fs = fa[i];
			if (fs.isDirectory()) {
				FileNamesPO getfilename = new FileNamesPO();
				getfilename.setFilename(fs.getName());
				filenames.add(getfilename);
			}
		}
		return filenames;
	}

	/**
	 * 以location和sequenceNo生成键值对
	 * 
	 * @param logSequenceVOs
	 * @return
	 */
	public static Map<String, Integer> getLogSequenceMap(List<LogSequenceVO> logSequenceVOs) {

		Map<String, Integer> logSequenceMap = new HashMap<String, Integer>();
		if (logSequenceVOs != null && logSequenceVOs.size() > 0) {
			for (LogSequenceVO vo : logSequenceVOs) {
				logSequenceMap.put(vo.getLocation(), vo.getSequence_no());
			}
		}
		return logSequenceMap;
	}
	
	/**
	 * 删除子文件夹中无效文件夹
	 * 
	 * @param 文件路径path，子文件夹中有效文件夹序号fileNumber
	 * @return
	 */
	public static void deleteFile(String path,Integer fileNumber){
		File file = new File(path);
		if (file.exists()) {
			for (File sonFile : file.listFiles()) {
				if (sonFile != null&&sonFile.getName() != null) {
					Integer fileName = Integer.parseInt(sonFile.getName());
					if (fileName<fileNumber) {
						deleteDir(sonFile);
					}
				}
			}
		}
	}
	
	
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
