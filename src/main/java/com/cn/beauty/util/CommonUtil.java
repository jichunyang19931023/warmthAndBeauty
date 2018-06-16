package com.cn.beauty.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	public static String subStringHTML(String strHtml, Integer length, String text) {
		// 剔出<html>的标签
		String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); 
		// 去除字符串中的空格,回车,换行符,制表符
		txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");
		if(txtcontent.length() < length){
			return txtcontent;
		}else {
			return txtcontent.substring(0, length) + text;
		}
	}
	
	public static String getImgStr(String htmlStr) {  
        String pic = "";  
        String img = "";  
        Pattern p_image;  
        Matcher m_image;  
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址  
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";  
        p_image = Pattern.compile  
                (regEx_img, Pattern.CASE_INSENSITIVE);  
        m_image = p_image.matcher(htmlStr);  
        while (m_image.find()) {  
            // 得到<img />数据  
            img = m_image.group();  
            // 匹配<img>中的src数据  
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);  
            while (m.find()) {  
            	pic = m.group(1);
            	break;
            }  
        }  
        return pic;
    }  
}
