package com.econet.app.solanaUtil;

public class ToolUtil {
    public static String getAbbrForMint(String mint)
    {
        int length=mint.length();
        String result=mint.substring(0,20)+"..."+mint.substring(length-10,length);
        return result;
    }

}
