package com.mini.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 字符串产生器
 * @author zhukang
 *
 */
public class StringNameGenerator {
	private static StringNameGenerator mInst = null;
	public static char[] lowerAlp={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private static char[] upperAlp={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private static char[] num={'1','2','3','4','5','6','7','8','9'};
	public static char[] allCh = (String.valueOf(lowerAlp) + String.valueOf(upperAlp) + String.valueOf(num)).toCharArray();
	private static ArrayList<String> nameList = new ArrayList<String>();
	private static HashMap<Integer,ArrayList<String>> nameMap = new HashMap<Integer,ArrayList<String>>();
	private static ArrayList<String> whiteNameList = new ArrayList<String>();
	/**
	 * 构造函数
	 */
	public StringNameGenerator(){
		super();
		init();
		initWhiteList();
	}
	/**
	 * 单例
	 * @return
	 */
	public static synchronized StringNameGenerator getInstance(){
		if(null == mInst){
			synchronized (StringNameGenerator.class) {
				if(null == mInst){
					mInst = new StringNameGenerator();
				}
			}	
		}
		return mInst;
	}
	/**
	 * 字符拼接字符串
	 * @param chList
	 * @return
	 */
	public String toString(char ...chList){
		String str = String.valueOf(chList);
		return str;		
	}
	/**
	 * 输出化，nameMap，位数为0，对应list里面存一个空字符串
	 */
	public void init(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("");
		nameMap.put(0, list);
	}
	/**
	 * 初始化白名单
	 */
	public void initWhiteList(){
		whiteNameList.add("if");
		whiteNameList.add("do");
	}
	/**
	 * 名称生成器
	 * @return
	 */
	public String generateName(){	
		int n;
		if(nameMap.size() == 1){
			n=1;
		}
		else{
			n=nameMap.size()-1;
		}
		String str = generateName(n);
		if( null!=str){
			return str;
		}else{
			return generateName(n+1);
		}
	}
	/**
	 * 生成n位的名称，前提是n-1为全都生成完毕
	 * @param n
	 * @return
	 */
	public String generateName(int n){
		ArrayList<String> shortList = nameMap.get(n-1);
		ArrayList<String> longList;
		if(!nameMap.containsKey(n)){
			longList = new ArrayList<String>();
		}else{
			longList = nameMap.get(n);
		}
		for(int i=0; i<shortList.size(); i++){
			for(int j=0;j<allCh.length;j++){
				String str="";
				str = shortList.get(i) + String.valueOf(allCh[j]);
				if(longList.contains(str) || whiteNameList.contains(str) || !isMatch(str)){
					continue;
				}else{
					longList.add(str);
					nameMap.put(n, longList);
					nameList.add(str);
					return str;
				}
			}
		}
		return null;
	}
	/**
	 * 当前生成串的位数
	 * @return
	 */
	public int getCurrentMaxLen(){
		return nameMap.size()-1;
	}
	/**
	 * 校验正则
	 * @param str
	 * @return
	 */
	public boolean isMatch(String str){
		Pattern p = Pattern.compile("[a-zA-Z_$][a-zA-Z0-9_$]*");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	
	
	
	

}
