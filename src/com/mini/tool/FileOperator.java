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
/**
 * 通用与业务无关的文件操作类
 * @author zhukang
 *
 */
public class FileOperator {
	/**
	 * 读取文件内容，放到一个字符串中
	 * @param file
	 * @return
	 */
		public static String readFileContent(File file){
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
					buf = buf + infoStr + "\n";	
				}
				fread.close();
//				System.out.println(buf);
				return buf;
			}catch (IOException e) {
				System.out.println("Exception file is :"+file.getPath());
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 是否为注释
		 * @param str
		 * @return
		 */
		public static boolean isNote(String str){
			if(str == null){
				return false;
			}
			int len = str.length();
			str = str.replace(" ", "");
			for(int i=0;i<len;i++){
				if(str.startsWith("//") || str.startsWith("/*") || str.startsWith("*")){
					return true;
				}
			}
			return false;
		}
		/**
		 * 字符串包含
		 * @param fileContent
		 * @param matchStr
		 * @return
		 */
		public static boolean checkStrInStr(String fileContent,String matchStr){
			if(fileContent == null || matchStr == null 
					|| fileContent.isEmpty() || matchStr.isEmpty()){
				return false;
			}
			if(fileContent.contains(matchStr)){
				return true;
			}
			return false;		
		}
		/**
		 * 移动文件 参数：路径
		 * @param srcPath
		 * @param desPath
		 * @throws IOException
		 */
		public static void moveBinaryResource(String srcPath, String desPath)
				throws IOException {
			// cv src des
			File srcFile = new File(srcPath);
			File desFile = new File(desPath);

			boolean moveSuccess = copyBinaryFile(srcFile, desFile);
			// rm src
			if (moveSuccess == true) {
				if (srcFile.exists()) {
					srcFile.delete();
				}
			} else {
				System.out.println(srcPath + "delete fail");
			}
		}
		/**
		 * 移动文件，参数：文件
		 * @param src
		 * @param dest
		 * @return
		 * @throws IOException
		 */
		public static boolean copyBinaryFile(File src, File dest)
				throws IOException {
			if (src.exists() && src.isFile()) {
				BufferedInputStream fSrc = new BufferedInputStream(
						new FileInputStream(src));
				BufferedOutputStream fDest = new BufferedOutputStream(
						new FileOutputStream(dest));
				int val = -1;
				while ((val = fSrc.read()) != -1) {

					fDest.write(val);
				}

				fDest.flush();
				fDest.close();
				fSrc.close();
				return true;
			} else {
				System.out.print("src file can't be null");
				return false;
			}

		}
		/**
		 * 拷贝文件
		 * @param srcPath
		 * @param desPath
		 * @throws IOException
		 */
		public static void moveTextResource(String srcPath, String desPath)
				throws IOException {
			// cv src des
			File srcFile = new File(srcPath);
			File desFile = new File(desPath);

			boolean moveSuccess = copyTextFile(srcFile, desFile, "UTF-8");
			// rm src
			if (moveSuccess == true) {
				if (srcFile.exists()) {
					srcFile.delete();
				}
			}

		}
		/**
		 * 拷贝文件
		 * @param src
		 * @param dest
		 * @param encode
		 * @return
		 * @throws IOException
		 */
		public static boolean copyTextFile(File src, File dest, String encode)
				throws IOException {
			if (src.exists() && src.isFile()) {
				BufferedWriter fWriter = new BufferedWriter(new FileWriter(dest,
						true));
				BufferedReader fread = new BufferedReader(new InputStreamReader(
						new FileInputStream(src), encode));
				String infoStr = null;
				while ((infoStr = fread.readLine()) != null) {
					fWriter.write(infoStr);
					fWriter.newLine();
				}
				fread.close();
				fWriter.flush();
				fWriter.close();
			} else {
				System.out.print("src file can't be null");
				return false;
			}

			return true;
		}
		/**
		 * 删除文件
		 * @param src
		 * @return
		 */
		public static boolean deleteFile(File src) {
			if (src.exists()) {
				return src.delete();
			}
			return true;
		}
		/**
		 * 计算文件大小
		 * @param file
		 * @return
		 */
		public static float getFileSize(File file) {
			float fileSize = (float) (file.length() / 1024.00);
			return fileSize;
		}
		
		public static boolean saveAsFile(String path,String fileContent){
			if(null == fileContent){
				System.out.println("fileContent is null，the path is:" +path);
				return false;
			}
			try {
				BufferedWriter fWriter = new BufferedWriter(new FileWriter(
						new File(path)));
				fWriter.write(fileContent);
				fWriter.flush();
				fWriter.close();
				return true;
			} catch (IOException e) {
				System.out.print("Exception file path:" +path );
				e.printStackTrace();
			}
			return true;
		}

}
