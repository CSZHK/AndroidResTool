package com.mini.tool;
/**
 * value字段实体
 * @author zhukang
 *
 */
public class ValueData {
	public String typeName;
	public String oldValue;
	public String newValue;
	public ValueData(String typeName,String oldValue ,String newValue){
		this.typeName = typeName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

}
