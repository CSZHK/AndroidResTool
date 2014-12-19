package com.mini.tool;

import java.util.ArrayList;
/**
 * 资源分割
 * @author zhukang
 *
 */
public class ReturnData {

	public ArrayList<ResourceClass> usefulList;
	public ArrayList<ResourceClass> noUsefulList;

	public ReturnData() {
		usefulList = new ArrayList<ResourceClass>();
		noUsefulList = new ArrayList<ResourceClass>();
	}
}
