package com.mini.tool;
/**
 * 配置文件实体类，与config.txt对应
 * @author zhukang
 *
 */
public class ConfigClass {
	public String sourceProject;  //待检测工程路径
	public String resultSavePath; //存放检测结果的路径
	public String[] dependProject;//相关依赖工程
	public String whiteXmlPath; //xml白名单
	public String whitePicturePath; //picture白名单
	public String picPath;
	public String projectPath;
}
