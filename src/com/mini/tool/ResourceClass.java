package com.mini.tool;
import java.io.File;
import java.util.ArrayList;
/**
 * 资源实体
 * @author zhukang
 *
 */
public class ResourceClass {

	//资源类型
	public String resourceType = "";
	//名称
	public String resourceName = "";
	//路径
	public String resourcePath = "";
	//大小（字符串）
	public String resourceSize = "";
	//内容
	public String resourceContent = "";
	//大小（浮点）
	public float resourceSizeT;
	//是否有用
	public boolean isUsed = false;
	//依赖文件
	public ArrayList<ResourceClass> pictureDependence = new ArrayList<ResourceClass> ();
	
	public ResourceClass(String type ,String name , String path, float size,ArrayList<ResourceClass> dependence){
		resourceType = type;
		resourceName = name;
		resourcePath = path;
		resourceSizeT = size;
		resourceSize = String.format("[%.2fKB]", size);
		isUsed = true;
		pictureDependence = dependence;
		resourceContent = FileOperator.readFileContent(new File(resourcePath));
	}
	
	public ResourceClass(String name , String path){
		resourceName = name;
		resourcePath = path;
	}
	public ResourceClass(String name){
		resourceName = name;
	}
}
