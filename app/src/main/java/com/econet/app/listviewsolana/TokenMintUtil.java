package com.econet.app.listviewsolana;

import com.econet.app.beans.JsonBean;
import com.econet.app.solanabean.mintTokenList.TokenMintBean;
import com.econet.app.solanabean.mintTokenList.Tokens;
import com.econet.app.uitl.GetJsonDataUtil;
import com.econet.app.uitl.MyApplication;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TokenMintUtil {

    private static final String JsonData;

    static
    {
        //static load to improve the speed
        JsonData = new GetJsonDataUtil().getJson(MyApplication.getContext(), "tokenlist.json");
    }

    public static String getMintDecimal(String mint)
    {
        Gson gson = new Gson();
        TokenMintBean tokenMintBean = gson.fromJson(JsonData, TokenMintBean.class);
        List<Tokens> tokenList=tokenMintBean.getTokens();
        for(int i=0;i<tokenList.size();i++)
        {
            Tokens token=tokenList.get(i);
            if(mint.equals(token.getAddress()))
            {
                return token.getDecimals()+"";
            }
        }
        return "6"; // TODO update the decimal
    }
    public static String getJsonData(){
        return JsonData;
    }
    public static List<Tokens> searchTokens(String tokenName)
    {
        List<Tokens> searchResult=new ArrayList<>();
        Gson gson = new Gson();
        TokenMintBean tokenMintBean = gson.fromJson(JsonData, TokenMintBean.class);
        List<Tokens> tokenList=tokenMintBean.getTokens();
        for(int i=0;i<tokenList.size();i++)
        {
            Tokens token=tokenList.get(i);
            if(token.getName().toLowerCase().contains(tokenName.toLowerCase()))
            {
                searchResult.add(token);
            }
            else if(token.getSymbol().toLowerCase().contains(tokenName.toLowerCase()))
            {
                searchResult.add(token);
            }
            else if(token.getAddress().toLowerCase().contains(tokenName.toLowerCase()))
            {
                searchResult.add(token);
            }
        }
        return searchResult;
    }
}
