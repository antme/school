package com.eweblib.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 文件读取类，处理文件的操作
 * @author huwenl
 *
 */
public class FileUtil {

    /**
     * 得到文件所有行的数据
     * @param path
     * @return
     */
	public static List<String> getLines(String path){
	    List<String> result = new ArrayList<>();
        BufferedReader br = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if(StringUtils.isNotEmpty(line)){
                    result.add(line);
                }
            }
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return result;
    
	}
}
