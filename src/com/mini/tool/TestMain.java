package com.mini.tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TestMain {
	private static ArrayList<ResourceClass> javaList = new ArrayList<ResourceClass>();
	private static ArrayList<ResourceClass> xmlList = new ArrayList<ResourceClass>();
	private static ArrayList<ResourceClass> allPicList = new ArrayList<ResourceClass>();
	private static ArrayList<ResourceClass> allXmlList = new ArrayList<ResourceClass>();
	private static ArrayList<ResourceClass> picTargetList;
	private static ArrayList<String> projectList;
	private static ConfigClass cfg;
	private static String stringXmlContent;
	
	private static Comparator<ResourceClass> comparator = new Comparator<ResourceClass>(){
		@Override
		 public int compare(ResourceClass o1, ResourceClass o2) {
			 if ( o1.resourceSizeT < o2.resourceSizeT){
				 return 1;
			 }
			 return -1;		 
		 }
	};

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//参数输入判断
		int argv = 0;
		if (args == null) {
			return;
		}
		argv = args.length;
		if (argv == 2) {
				// find 
				if (args[0].equals("find")) {
					initConfig(args[1]);
				} else {
					// 参数有问题
					showUsageTip();
					return;
				}
		}
	
		//测试代码
//		 testDelete("C:\\Users\\zhkang\\Desktop\\Re3\\addressList\\noUsePicture.txt", 1, "C:\\Users\\zhouyuanyuan\\Desktop\\melon");
		mainStrReplaceEntrance(null);
//		System.out.print(File.separator);
//		 String temp = FileOperator.readFileContent(new File("C:\\Users\\zhukang\\Desktop\\Android Dev Src\\android\\new\\tbadkcore\\res\\layout\\account_forbid_activity.xml"));
//		 ResourceManager.processSourceFile(temp);
	}

	public static void mainStrReplaceEntrance(String path){
		 initConfig("C:\\Users\\zhukang\\Desktop\\config.txt");
		 initResource();
		 stringNameReplace();
	}
	public static void showUsageTip() {
		System.out.println("action:you can replace String in src.");
		System.out.println("cmd:find configPath");
		System.out.println("more infomition please read readme.txt");
		
	}
	
	/**
	 * 初始化从配置文件读取
	 * @param configPath
	 */
	public static void initConfig(String configPath){
		if(configPath == null ||configPath.isEmpty()){
			System.out.println("configpath is null");
			return ;
		}
		System.out.println("开始读取文件配置。。。");
		cfg = ResourceManager.readConfig(configPath, "UTF-8");
		if(cfg == null){
			System.out.println("config is null");
			return;
		}
		System.out.println("文件配置读取结束！");
	}
/**
 * 读取配置，初始化参数
 * @param configPath
 */
	public static void initResource(){
		projectList =  ResourceManager.readWhiteList(cfg.projectPath, "UTF-8");
		System.out.println("从工程依赖依次读取每个工程的xml和java文件。。。");
		for(String proName : projectList){
			File file1 = new File(proName + File.separator + "src");
			File file2 = new File(proName + File.separator + "res");
			File file3 = new File(proName + File.separator + "AndroidManifest.xml");
			System.out.println("得到" + proName +"工程的源文件。。。");
			ResourceManager.getAllXmlResource(xmlList, file3);
			ResourceManager.getAllXmlResource(xmlList, file2);
			ResourceManager.getAllJavaResource(javaList, file1);
			System.out.println("此时xml的数量:" +javaList.size());
			System.out.println("此时java的数量:" +javaList.size());
		}
		System.out.println("合并工程中的源文件。包括java和xml文件。。。");
		picTargetList = ResourceManager.merge2List(javaList, xmlList);
		Collections.sort(picTargetList,comparator);
		System.out.println("共" +picTargetList.size() + "个源文件！");
	}
/**
 * 扫描pic
 */
	public static void getNoUsePic(){
		System.out.println("从配置路径获取所有的图片资源。。。");
		File picFile = new File(cfg.picPath + File.separator + "res");
		ResourceManager.getAllPictureResource(allPicList, picFile);
		System.out.println("对图片资源按照图片大小进行排序。。。");
		Collections.sort(allPicList,comparator);
		System.out.println("共" +allPicList.size() + "张图片！");
		System.out.println("存储所有的图片。。。");
		ResourceManager.saveAsTxt(allPicList, cfg.resultSavePath + File.separator + "allPicture.txt");
		
		System.out.println("从源文件中查找图片。。。");
		ResourceManager.splitResources(allPicList, picTargetList, null);
		System.out.println("筛选有用的图片。。。");
		ReturnData picRedata = ResourceManager.devideUsefulResources(allPicList);
		System.out.println("共" +allPicList.size() + "张图片！");
		System.out.println("不在简版的图片个数为:" + picRedata.noUsefulList.size());
		
		System.out.println("存储不在简版中的的图片。。。");
		if (picRedata.noUsefulList != null) {
			ResourceManager.saveAsTxt(picRedata.noUsefulList, cfg.resultSavePath + File.separator + "noUsePicture.txt");
			float allSize = 0;
			for(ResourceClass res : picRedata.noUsefulList){
				allSize += res.resourceSizeT;
			}
			System.out.println("不在简版中所有图片的大小为：" + allSize + "Kb");
		}else{
			System.out.println("在简版中的图片个数为空！");
		}
		
		System.out.println("存储在简版中的的图片。。。");
		if (picRedata.usefulList != null){
			ResourceManager.saveAsTxt(picRedata.usefulList, cfg.resultSavePath + File.separator + "UsefulPicture.csv");
			ResourceManager.saveAsTxt(picRedata.usefulList, cfg.resultSavePath + File.separator + "UsefulPicture.txt");
		}
		
		System.out.println("存储所有的源文件。。。");
		ResourceManager.saveProAsTxt(cfg.resultSavePath + File.separator + "allJavaAndXml.txt");
		System.out.println("结束。。。");
	}
	
	/**
	 * 扫描xml
	 */
	public static void getNoUseXml(){
		File picFile = new File(cfg.picPath + File.separator + "res");
		System.out.println("从配置路径获取所有的xml资源。。。");
		ResourceManager.getAllXmlResource(allXmlList, picFile);
		System.out.println("对xml资源按照xml大小进行排序。。。");
		Collections.sort(allXmlList,comparator);
		
		System.out.println("共" +allXmlList.size() + "个xml文件！");
		System.out.println("存储所有的xml文件。。。");
		ResourceManager.saveAsTxt(allXmlList, cfg.resultSavePath + File.separator + "allXml.txt");
		System.out.println("从源文件中查找xml文件。。。");
		ResourceManager.splitResources(allXmlList, picTargetList, null);
		System.out.println("筛选有用的xml。。。");
		ReturnData xmlRedata = ResourceManager.devideUsefulResources(allXmlList);
		System.out.println("共" +allXmlList.size() + "个xml文件！");
		System.out.println("不在简版的xml个数为:" + xmlRedata.noUsefulList.size());
		System.out.println("存储不在简版中的的xml文件。。。");
		if (xmlRedata.noUsefulList != null) {
			ResourceManager.saveAsTxt(xmlRedata.noUsefulList, cfg.resultSavePath + File.separator + "noUseXml.txt");
			float allSize = 0;
			for(ResourceClass res : xmlRedata.noUsefulList){
				allSize += res.resourceSizeT;
			}
			System.out.println("不在简版中所有图片的大小为：" + allSize + "Kb");
		}else{
			System.out.println("在简版中的图片个数为空！");
		}
		if (xmlRedata.usefulList != null){
			ResourceManager.saveAsTxt(xmlRedata.usefulList, cfg.resultSavePath + File.separator + "UsefulXml.txt");
		}
	}
	/**
	 * 读取strings.xml
	 * @param configPath
	 */
	public static void processStringXml(String configPath){
		File stringXml = new File(configPath);
		stringXmlContent =  ResourceManager.processStringXml(stringXml);
	}
	public static void stringNameReplace(){
		System.out.println("string.xml 参数替换..");
		processStringXml(cfg.picPath + File.separator +"res" +File.separator +"values"+File.separator +"strings.xml");
		System.out.println("string.xml 参数替换结束！");
		System.out.println("源文件字符串引用替换...");
		ResourceManager.saveNewSourceFileList(picTargetList);	
		System.out.println("源文件字符串引用替换结束！");
		boolean isSuccess = FileOperator.saveAsFile(cfg.picPath + File.separator +"res" +File.separator +"values"+File.separator +"strings.xml",stringXmlContent);
		if(isSuccess){
			System.out.print("strings.xml文件替换成功！");
		}else{
			System.out.print("strings.xml文件替换失败！");
		}
	}

}
