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
	private ArrayList<String> nameList = new ArrayList<String>();
	private HashMap<Integer,ArrayList<String>> nameMap = new HashMap<Integer,ArrayList<String>>();
	private ArrayList<String> whiteNameList = new ArrayList<String>();
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
		whiteNameList.add("DO");
		whiteNameList.add("IF");
		whiteNameList.add("aux");
	}
	/**
	 * 添加白名单
	 * @param word
	 */
	public void addWhiteName(String word){
		if(word == null){
			return;
		}
		whiteNameList.add(word);
	}
	/**
	 * 名称生成器
	 * @param beLower
	 * @return
	 */
	public String generateName(boolean beLower){	
		int n;
		if(nameMap.size() == 1){
			n=1;
		}
		else{
			n=nameMap.size()-1;
		}
		String str = generateName(n,beLower);
		if( null!=str){
			return str;
		}else{
			return generateName(n+1,beLower);
		}
	}
	/**
	 * 生成n位的名称，前提是n-1为全都生成完毕
	 * @param n
	 * @param beLower
	 * @return
	 */
	public String generateName(int n, boolean beLower){
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
				if(longList.contains(str) || whiteNameList.contains(str) 
						|| !isMatch(str) || nameList.contains(str)){
					continue;
				}
				if((beLower && isMatchLower(str))
						|| !beLower){
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
	 * 校验正则,是否匹配首字母不含字母
	 * @param str
	 * @return
	 */
	public static boolean isMatch(String str){
		Pattern p = Pattern.compile("[a-zA-Z_$][a-zA-Z0-9_$]*");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	/**
	 *  校验正则,是否匹配首字母不含字母,且字母均为小写
	 * @param str
	 * @return
	 */
	public static boolean isMatchLower(String str){
		Pattern p = Pattern.compile("[a-z_$][a-z0-9_$]*");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	
	
	
	

}
