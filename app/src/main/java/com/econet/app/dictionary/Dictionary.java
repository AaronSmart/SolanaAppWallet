package com.econet.app.dictionary;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    public static List<String> getDocumentType()
    {
        List<String> documentType=new ArrayList<>();
        documentType.add("ID Card");
        documentType.add("Passport");
        documentType.add("Motor vehicle driving license");
        return documentType;
    }
    public static List<String>getGender()
    {
        List<String> gender=new ArrayList<>();
        gender.add("Male");
        gender.add("Female");
        return gender;
    }
    public static String  getIdType(String str)
    {
        if("ID Card".equals(str))
        {
            return "Idcard";
        }
        else if("Passport".equals(str))
        {
            return "Passport";
        }
        else if("Motor vehicle driving license".equals(str))
        {
            return "Driving";
        }
        return "Idcard";
    }
    public static String getIdTypeLocal(String str)
    {
        if("Idcard".equals(str))
        {
            return "ID Card";
        }
        else if("Passport".equals(str))
        {
           return "Passport";
        }
        else if("Driving".equals(str))
        {
            return "Motor vehicle driving license";
        }
        return "ID Card";
    }

}
