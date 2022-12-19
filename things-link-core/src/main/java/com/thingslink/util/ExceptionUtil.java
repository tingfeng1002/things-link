package com.thingslink.util;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常 util
 * 打印异常
 * @author wang xiao
 * date 2022/12/19
 */
public interface ExceptionUtil {


    /**
     * 获取异常信息
     * @param e 异常
     * @return String
     */
     static String getExceptionMsg(Exception e) {
        try (StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter)){
            e.printStackTrace(printWriter);
            printWriter.flush();
            stringWriter.flush();
            return stringWriter.toString();
        }catch (Exception exception){
            return  e.getMessage();
        }
    }
}
