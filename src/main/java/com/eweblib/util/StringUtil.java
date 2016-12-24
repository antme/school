package com.eweblib.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	public static String[] stringtoArray(String s,String split){
		if(s !=null&&!s.equals("")){
			if(split == null || split.equals("")){
				return s.split(",");
			}else{
				return s.split(split);
			}
		}
		return null;
	}
	public static String arraytoString(String[] str){
		if(str !=null){
			StringBuilder builder = new StringBuilder();
			for(int i=0;i<str.length;i++){
				if(i == str.length-1){
					builder.append(str[i]);
				}else{
					builder.append(str[i]+",");
				}
			}
			return builder.toString();
		}
		return "";
	}
	/**
	 * 返回非空白值或null
	 * @param str
	 * @return
	 */
    public static String getNonBlankValue(String str) {
        return StringUtils.isBlank(str)?null:str;
    }
}
