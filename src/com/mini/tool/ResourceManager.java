package com.mini.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 资源操作类，与业务相关
 * @author zhukang
 *
 */
public class ResourceManager {

	public static int TEXT_TYPE = 0;
	public static int BINARY_TYPE = 1;

	public static HashMap<ResourceClass, ArrayList<ResourceClass>> proMap = new HashMap<ResourceClass, ArrayList<ResourceClass>>();
	public static ArrayList<File> fileList = new ArrayList<File>();
	public static HashMap<File, String> fileCache = new HashMap<File, String>();
	public static HashMap<String,String> strMap = new HashMap<String,String>();
	public static ArrayList<String> oldNameList = new ArrayList<String>();
	public static char[] endCh = {';',')','?','>','<','/','\\',' ','|','!','@','#','$','%','^','&','*','(','-','+','=','.','"',',',':',']','}','[','{'};
	/**
	 * 读取文件配置
	 * @param configPath
	 * @param encodeType
	 * @return
	 */
	public static ConfigClass readConfig(String configPath, String encodeType) {
		File file = new File(configPath);
		ConfigClass config = new ConfigClass();
		if (file.exists() && file.isFile()) {
			try {
				BufferedReader fread = new BufferedReader(
						new InputStreamReader(new FileInputStream(file),
								encodeType));
				String infoStr = null;

				while ((infoStr = fread.readLine()) != null) {
					String[] infoArray = infoStr.split("=");

					if (infoArray[0].equals("projectPath")) {
						config.projectPath = infoArray[1];
					} else if (infoArray[0].equals("saveResultPath")) {
						config.resultSavePath = infoArray[1];
					} else if (infoArray[0].equals("whitePicturePath")) {
						config.whitePicturePath = infoArray[1];
					} else if (infoArray[0].equals("whiteXmlPath")) {
						config.whiteXmlPath = infoArray[1];
					} else if (infoArray[0].equals("picPath")) {
						config.picPath = infoArray[1];
					}
				}
				fread.close();
				return config;
			} catch (IOException e) {
				e.printStackTrace();
				return config;
			}
		}

		return config;
	}

	/**
	 * 读取白名单
	 * @param path
	 * @param encodeType
	 * @return
	 */
	public static ArrayList<String> readWhiteList(String path, String encodeType) {
		ArrayList<String> whiteList = new ArrayList<String>();
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			try {
				BufferedReader fread = new BufferedReader(
						new InputStreamReader(new FileInputStream(file),
								encodeType));
				String infoStr = null;
				while ((infoStr = fread.readLine()) != null) {
					whiteList.add(infoStr);
				}

				fread.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("文件不存在");
			// return null;
		}

		return whiteList;
	}


	/**
	 * 得到所有的xml文件
	 * @param fileInfoList
	 * @param file
	 * @return
	 */
	public static ArrayList<ResourceClass> getAllXmlResource(
			ArrayList<ResourceClass> fileInfoList, File file) {
		if (file == null) {
			System.out.println("file is null");
			return null;
		}

		if (file.isDirectory()) {
			File[] mFileList = file.listFiles();
			for (int i = 0; i < mFileList.length; i++) {
				getAllXmlResource(fileInfoList, mFileList[i]);
			}
		} else {
			String mFileName = file.getName();
			if (mFileName.endsWith(".xml")) {
				ResourceClass res = new ResourceClass(".xml",
						mFileName.substring(0, mFileName.length() - 4),
						file.getAbsolutePath(), FileOperator.getFileSize(file),
						new ArrayList<ResourceClass>());
				fileInfoList.add(res);
			}
		}

		return fileInfoList;
	}

	/**
	 * 得到所有的图片资源
	 * @param fileInfoList
	 * @param file
	 * @return
	 */
	public static ArrayList<ResourceClass> getAllPictureResource(
			ArrayList<ResourceClass> fileInfoList, File file) {
		if (file == null) {
			System.out.println("file is null");
			return null;
		}

		if (file.isDirectory()) {

			File[] mFileList = file.listFiles();
			for (int i = 0; i < mFileList.length; i++) {
				getAllPictureResource(fileInfoList, mFileList[i]);
			}
		} else {
			String mFileName = file.getName();
			if (mFileName.endsWith(".9.png")) {
				ResourceClass res = new ResourceClass(".9.png",
						mFileName.substring(0, mFileName.length() - 6),
						file.getAbsolutePath(), FileOperator.getFileSize(file),
						new ArrayList<ResourceClass>());
				fileInfoList.add(res);
			} else if (mFileName.endsWith(".png")) {
				ResourceClass res = new ResourceClass(".png",
						mFileName.substring(0, mFileName.length() - 4),
						file.getAbsolutePath(), FileOperator.getFileSize(file),
						new ArrayList<ResourceClass>());
				fileInfoList.add(res);
			} else if (mFileName.endsWith(".jpg")) {
				ResourceClass res = new ResourceClass(".jpg",
						mFileName.substring(0, mFileName.length() - 4),
						file.getAbsolutePath(), FileOperator.getFileSize(file),
						new ArrayList<ResourceClass>());
				fileInfoList.add(res);
			}
		}

		return fileInfoList;
	}

	/**
	 * 的到所有的java文件资源
	 * @param fileInfoList
	 * @param file
	 * @return
	 */
	public static ArrayList<ResourceClass> getAllJavaResource(
			ArrayList<ResourceClass> fileInfoList, File file) {
		if (file == null) {
			System.out.println("file is null");
			return null;
		}

		if (file.isDirectory()) {
			File[] mFileList = file.listFiles();
			for (int i = 0; i < mFileList.length; i++) {
				getAllJavaResource(fileInfoList, mFileList[i]);
			}

		} else {
			String mFileName = file.getName();
			if (mFileName.endsWith(".java")) {
				ResourceClass res = new ResourceClass(".java",
						mFileName.substring(0, mFileName.length() - 5),
						file.getAbsolutePath(),FileOperator.getFileSize(file),
						new ArrayList<ResourceClass>());
				fileInfoList.add(res);
			}
		}

		return fileInfoList;
	}

	/**
	 * 合并资源list
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static ArrayList<ResourceClass> merge2List(
			ArrayList<ResourceClass> list1, ArrayList<ResourceClass> list2) {
		ArrayList<ResourceClass> mList = new ArrayList<ResourceClass>();
		if (list1 != null) {
			for (ResourceClass obj : list1) {
				mList.add(obj);
			}
		}
		if (list2 != null) {
			for (ResourceClass obj : list2) {
				mList.add(obj);
			}
		}

		return mList;

	}


	/**
	 * 文件中是否包含字符串
	 * @param file
	 * @param matchStr1
	 * @param matchStr2
	 * @return
	 */
	public static boolean checkSourceInProject(File file, String matchStr1,
			String matchStr2) {
		if (file.isDirectory() || !(file.exists()) || matchStr1 == null
				|| matchStr1.isEmpty() || matchStr2 == null
				|| matchStr2.isEmpty()) {
			System.out.println("file or matchStr is null");
			return false;
		}
		try {
			if (!fileCache.containsKey(file)) {
				BufferedReader fread = new BufferedReader(
						new InputStreamReader(new FileInputStream(file),
								"UTF-8"));
				String buf = null;
				String infoStr = null;
				while ((infoStr = fread.readLine()) != null) {
					if(FileOperator.isNote(infoStr)){
						continue;
					}
					buf = buf + infoStr;	
				}
				fread.close();
				fileCache.put(file, buf);
				return false;
			} 
			String buf = fileCache.get(file);
			if (buf.contains(matchStr1) || buf.contains(matchStr2)) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// public static void splitResources(ArrayList<String> mResourceNameList,
	// ArrayList<ResourceClass> targetResourceList){
	// if(mResourceNameList == null || targetResourceList == null){
	// System.out.println("picNameList and targetList can't be null");
	// return;
	// }
	// String resourceName = "";
	// int listSize = mResourceNameList.size();
	// for (int i = 0; i < listSize; i++) {
	// boolean resourceExist = false;
	// String res = mResourceNameList.get(i);
	// if (res.endsWith("_1")) {
	// resourceName = res.substring(0,
	// res.length() - 2);
	// } else {
	// resourceName = res;
	// }
	// for (ResourceClass target : targetResourceList) {
	// File file = new File(target.resourcePath);
	// if (checkSourceInProject(file, "." + resourceName, "/"
	// + resourceName)) {
	// System.out.println(res + "存在于"
	// + target.resourcePath);
	// res.isUsed = true;
	// resourceExist = true;
	// res.pictureDependence.add(target);
	// }
	// }
	// }
	// }

	/**
	 * 建立文件-图片资源映射表，1-n
	 * @param proRes文件
	 * @param picRes图片
	 */
	public static void addPicToProMap(ResourceClass proRes, ResourceClass picRes) {
		if (proRes == null || picRes == null) {
			return;
		}
		if (proMap.containsKey(proRes)) {
			proMap.get(proRes).add(picRes);
		} else {
			ArrayList<ResourceClass> picList = new ArrayList<ResourceClass>();
			picList.add(picRes);
			proMap.put(proRes, picList);
		}
	}

	/**
	 * 区分源资源是否在目标资源用到
	 * @param mResourceList
	 * @param targetResourceList
	 * @param whiteList
	 */
	public static void splitResources(ArrayList<ResourceClass> mResourceList,
			ArrayList<ResourceClass> targetResourceList,
			ArrayList<String> whiteList) {
		if (mResourceList == null || targetResourceList == null) {
			System.out.println("xmlList and targetList can't be null");
			return;
		}

		String resourceName = "";
		int listSize = mResourceList.size();
		// for (ResourceClass res : xmlResourceList) {
		for (int i = 0; i < listSize; i++) {
			boolean resourceExist = false;
			ResourceClass res = mResourceList.get(i);
//			if (res.resourceName.endsWith("_1")) {
//				resourceName = res.resourceName.substring(0,
//						res.resourceName.length() - 2);
//			} else {
//				resourceName = res.resourceName;
//			}
			resourceName = res.resourceName;
			if (whiteList == null) {
				System.out.println("whitePictureList为空");
				for (ResourceClass target : targetResourceList) {
					if (FileOperator.checkStrInStr(target.resourceContent,"." + resourceName)
							|| FileOperator.checkStrInStr(target.resourceContent, "/"
									+ resourceName)) {
						System.out.println(res.resourceName + "存在于"
								+ target.resourcePath);
						res.isUsed = true;
						resourceExist = true;
						res.pictureDependence.add(target);
						addPicToProMap(target, res);
					}
				}

			} else {
				if (whiteList.contains(resourceName)) {
					resourceExist = true;
					System.out.println(resourceName + "存在于白名单");
					res.isUsed = true;
					resourceExist = true;
				} else {
					for (ResourceClass target : targetResourceList) {
						if (FileOperator.checkStrInStr(target.resourceContent,"." + resourceName)
								|| FileOperator.checkStrInStr(target.resourceContent, "/"
										+ resourceName)) {
							System.out
									.println(res.resourceName + "存在于"
											+ target.resourcePath
											+ target.resourceSize);
							res.isUsed = true;
							resourceExist = true;
							res.pictureDependence.add(target);
							addPicToProMap(target, res);
						}
					}
				}
			}
			if (resourceExist == false) {
				System.out
						.println(res.resourceName + "未曾使用" + res.resourcePath);
				res.isUsed = false;
				mResourceList.get(i).isUsed = false;
			}

		}

	}

	/**
	 * 对结果进行划分，有用的和无用的，根据isUsed字段
	 * @param list
	 * @return
	 */
	public static ReturnData devideUsefulResources(ArrayList<ResourceClass> list) {
		if (list == null) {
			System.out.println("devided list is null");
			return null;
		}
		ReturnData reData = new ReturnData();
		for (ResourceClass obj : list) {
			if (obj.isUsed) {
				reData.usefulList.add(obj);
			} else {
				reData.noUsefulList.add(obj);
			}
		}

		return reData;
	}

	public static ArrayList<ResourceClass> readInfoFromTxt(String path,
			String encodeType) {
		ArrayList<ResourceClass> list = new ArrayList<ResourceClass>();
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			try {
				BufferedReader fread = new BufferedReader(
						new InputStreamReader(new FileInputStream(file),
								encodeType));
				String infoStr = null;

				while ((infoStr = fread.readLine()) != null) {
					String[] infoArray = infoStr.split(" ");
					if (infoArray != null && infoArray.length == 3) {
						ResourceClass res = new ResourceClass(infoArray[0],
								infoArray[2]);
						list.add(res);
					}

				}

				fread.close();

			} catch (IOException e) {
				e.printStackTrace();

			}
		}

		return list;
	}

	/**
	 * 删除无用资源
	 * @param list
	 * @param copyDir
	 * @param resourceType
	 * @throws IOException
	 */
	public static void deleteNouseResources(ArrayList<ResourceClass> list,
			String copyDir, int resourceType) throws IOException {
		if (list == null) {
			System.out.println("no unuseful resources");
			return;
		}
		if (resourceType == TEXT_TYPE) {
			for (ResourceClass res : list) {
				// System.out.println(res.resourceName);
				FileOperator.moveTextResource(res.resourcePath, copyDir + File.separator 
						+ res.resourceName);
			}
		} else if (resourceType == BINARY_TYPE) {
			for (ResourceClass res : list) {
				// System.out.println(res.resourceName);
				FileOperator.moveBinaryResource(res.resourcePath, copyDir + File.separator 
						+ res.resourceName);
			}
		}

	}

	/**
	 * 控制台打印资源list
	 * @param list
	 */
	public static void printResourceList(ArrayList<ResourceClass> list) {
		if (list == null) {
			System.out.print("Be printed list is null");
			return;
		}
		for (ResourceClass obj : list) {
			System.out.println(obj.resourceName + "\t" + obj.resourcePath
					+ "\t" + obj.isUsed + "\t" + obj.resourceSize);
		}
	}
	/**
	 *存储文件，格式为：xml或者图片文件名（不含类型后缀）+文件类型+文件大小+文件依赖
	 * @param list
	 * @param path
	 */
	public static void saveAsTxt(ArrayList<ResourceClass> list, String path) {
		if (list == null) {
			System.out.println("list in saveAsTxt is null");
			return;
		}
		try {
			BufferedWriter fWriter = new BufferedWriter(new FileWriter(
					new File(path)));
			for (ResourceClass info : list) {
				fWriter.write(info.resourceName + info.resourceType + "\t"
						+ "\t" + info.resourceSize + "\t");
				int size = 0;
				if (info.pictureDependence != null) {
					size = info.pictureDependence.size();
				}
				if (size > 0) {
					for (ResourceClass target : info.pictureDependence) {
						if (info.pictureDependence.get(0) == target) {
							fWriter.write("{ ");
						}
						fWriter.write(target.resourcePath
								.replace(
										"C:\\Users\\zhukang\\Desktop\\Android Dev Src\\android\\new\\",
										""));
						if (info.pictureDependence.get(size - 1) != target) {
							fWriter.write(" , ");
						} else {
							fWriter.write(" }");
						}
					}
				}
				fWriter.newLine();
			}
			fWriter.flush();
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 存储文件，格式为：java和xml文件名（包含后缀）+文件大小+包含的图片文件列表
	 * @param path
	 */
	public static void saveProAsTxt(String path) {
		if (proMap == null) {
			System.out.println("list in saveProAsTxt is null");
			return;
		}
		try {
			BufferedWriter fWriter = new BufferedWriter(new FileWriter(
					new File(path)));
			Iterator iter = proMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<ResourceClass, ArrayList<ResourceClass>> entry = (Map.Entry<ResourceClass, ArrayList<ResourceClass>>) iter
						.next();
				ResourceClass key = entry.getKey();
				ArrayList<ResourceClass> value = entry.getValue();
				fWriter.write(key.resourcePath
						.replace(
								"C:\\Users\\zhukang\\Desktop\\Android Dev Src\\android\\new\\",
								"")
						+ "\t" + "\t" + key.resourceSize + "\t");
				int size = 0;
				if (value != null) {
					size = value.size();
				}
				if (size > 0) {
					for (ResourceClass target : value) {
						if (value.get(0) == target) {
							fWriter.write("{ ");
						}
						fWriter.write(target.resourcePath
								.replace(
										"C:\\Users\\zhukang\\Desktop\\Android Dev Src\\android\\new\\",
										""));
						if (value.get(size - 1) != target) {
							fWriter.write(" , ");
						} else {
							fWriter.write(" }");
						}
					}
				}
				fWriter.newLine();
			}
			fWriter.flush();
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处理String.xml，替换string name
	 * @param file
	 * @return
	 */
	public static String processStringXml(File file){
		if(file.isDirectory() || !(file.exists())){
			System.out.print("file is not avilable!");
			return null;
		}
		try{
			BufferedReader fread = new BufferedReader(
					new InputStreamReader(new FileInputStream(file),
							"UTF-8"));
			String buf = "";
			String infoStr = "";
			while ((infoStr = fread.readLine()) != null) {
				if(infoStr.endsWith("</string>")){
					int start = infoStr.indexOf("\"");
					int end = infoStr.indexOf(">");
					String oldName = infoStr.substring(start+1, end-1);
					String newName = StringNameGenerator.getInstance().generateName();
					strMap.put(oldName, newName);
					oldNameList.add(oldName);
					infoStr = infoStr.replaceFirst("\""+oldName,"\""+newName);
				}
				buf = buf + infoStr + "\n";	
			}
			System.out.println(buf);
			fread.close();
			return buf;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 处理源文件文本内容，做字符串替换
	 * @param fileContent
	 * @return
	 */
	public static String processSourceFile(String fileContent){
		if(fileContent == null || fileContent == "" 
				|| (!fileContent.contains("R.string.")
				&& !fileContent.contains("@string/"))){
			return fileContent;
		}
		for(String oldName: oldNameList){
			if(!fileContent.contains("R.string."+oldName)&&!fileContent.contains("@string/"+oldName)){
				continue;
			}
			for(int i=0;i<endCh.length;i++){
				fileContent = fileContent.replace("R.string."+oldName+endCh[i], "R.string."+strMap.get(oldName)+endCh[i]);
				fileContent = fileContent.replace("@string/"+oldName+endCh[i], "@string/"+strMap.get(oldName)+endCh[i]);				
			}			
		}
//		System.out.println(fileContent);
		return fileContent;		
	}
	/**
	 * 处理存储文件列表
	 * @param resList
	 * @return
	 */
	public static boolean saveNewSourceFileList(ArrayList<ResourceClass> resList){
		if(resList == null){
			return false;
		}
		for(ResourceClass res : resList){
			if(res.resourceContent == null){
				System.out.println("file is null,path:" + res.resourcePath );
				continue;
			}
			String str = processSourceFile(res.resourceContent);
			if (!str.equals(res.resourceContent)){
				System.out.println("正在替换源文件:" + res.resourceName + res.resourceType + "  文件路径：" + res.resourcePath);
				FileOperator.saveAsFile(res.resourcePath, str);
			}		
		}
		return true;
	}
}
