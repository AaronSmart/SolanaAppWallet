package com.econet.app.uitl;

import java.io.UnsupportedEncodingException;

public class URLUtil
{
    public static String getURLEncoderString(String str)
    {
        String result="";
        if(null==str)
        {
            return "";
        }
        try
        {
            result=java.net.URLEncoder.encode(str,"UTF-8");
        }catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public static String URLDecoderString(String str)
    {
        String result="";
        if(null==str)
        {
            return "";
        }
        try
        {
            result=java.net.URLDecoder.decode(str,"UTF-8");
        }catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args)
    {

       String url2="%good%";
       String result=getURLEncoderString(url2);
       System.out.println("###########4444############");
       System.out.println(result);
       
       String my=result;
       System.out.println("++++++"+URLUtil.URLDecoderString("%E5%8F%B6%E5%BF%A0%E5%B9%B3"));
       
    }

}
