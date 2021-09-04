package com.econet.app.uitl;

import java.text.DecimalFormat;

public class CopyCatTest {
    public static void main(String[]args)
    {
        int fee=5000;
        double feeForSol= (double)fee/1000000000L;
        DecimalFormat df=new DecimalFormat("#.#####");
        System.out.println(df.format(feeForSol));
    }
}
