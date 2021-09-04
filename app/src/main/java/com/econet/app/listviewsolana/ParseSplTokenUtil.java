package com.econet.app.listviewsolana;

import com.alibaba.fastjson.JSON;
import com.econet.app.ServerBeans.historydetail.HistoryDetailBean;
import com.econet.app.solanabean.Account;
import com.econet.app.solanabean.Data;
import com.econet.app.solanabean.Info;
import com.econet.app.solanabean.Parsed;
import com.econet.app.solanabean.SolToken;
import com.econet.app.solanabean.SplToken;
import com.econet.app.solanabean.SplTokenBean;
import com.econet.app.solanabean.TokenAmount;
import com.econet.app.solanabean.Value;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseSplTokenUtil {

    public static SplToken parseJsonForSol(String result){
        if(result==null)
        {
            return null;
        }
        SplToken splToken=new SplToken();
        splToken.setTokenName("***");
        splToken.setTokenAmount("***");
        splToken.setTokenAmount("***");
        SolToken solToken =JSON.parseObject(result, SolToken.class);
        if(solToken.getResult()!=null)
        {
            long amount=solToken.getResult().getValue();
            long lamport=1000000000;
            DecimalFormat df=new DecimalFormat("0.0000");//设置保留位数
            splToken.setTokenName("SOL");
            splToken.setTokenMint("So11111111111111111111111111111111111111112");
            splToken.setTokenAmount(df.format((double)amount/lamport)+"");
        }
        return splToken;
    }
    public static SplToken parseJson(String result)
    {
        if(result==null)
        {
            return null;
        }
        SplToken splToken=new SplToken();
        splToken.setTokenName("***");
        splToken.setTokenAmount("***");
        splToken.setTokenAmount("***");
        SplTokenBean splTokenBean =JSON.parseObject(result, SplTokenBean.class);
        if(splTokenBean.getResult()!=null)
        {
            List<Value> valueList=splTokenBean.getResult().getValue();
            if(valueList!=null&&valueList.size()!=0)
            {
                if(valueList.get(0)!=null)
                {
                    Account account=valueList.get(0).getAccount();
                    if(account!=null)
                    {
                        Data data =account.getData();
                        if(data!=null)
                        {
                            Parsed parsed=data.getParsed();
                            if (parsed!=null)
                            {
                                Info info=parsed.getInfo();
                                if(info!=null)
                                {
                                    splToken.setTokenMint(info.getMint());
                                    splToken.setTokenName(getTokenName(info.getMint()));
                                }
                                TokenAmount tokenAmount =info.getTokenAmount();
                                if(tokenAmount !=null)
                                {
                                    splToken.setTokenAmount(tokenAmount.getUiAmountString());
                                }
                            }
                        }
                    }
                }
            }

        }
        return splToken;
    }

    public static String getTokenInfoUrl(String solAccount,String tokenMint)
    {
        if(Solana.SOL.equals(tokenMint))
        {
            return "{\"jsonrpc\":\"2.0\", \"id\":1, \"method\":\"getBalance\", \"params\":[\""+solAccount+"\"]}";
        }
        return "{\"jsonrpc\": \"2.0\",     \"id\": 1,     \"method\": \"getTokenAccountsByOwner\",     \"params\": [       \""+solAccount+"\",       {           \"mint\":\""+tokenMint+"\"       },       {         \"encoding\": \"jsonParsed\"       }     ]   }";
    }
    public static String getAccountInfo(String solAccount)
    {
        return "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"getAccountInfo\",\"params\":[\""+solAccount+"\",{\"encoding\":\"base64\"}]}";
    }
    public static String getFees()
    {
        return "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"getFees\"}";
    }

    public static  class Solana
    {
        public static final String solMainNet="http://103.164.54.79:80";//64.120.114.146  //104.18.20.90
        public static final String SOL="So11111111111111111111111111111111111111112";
        public static final String USDC ="EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v";
        public static final String RAY="4k3Dyjzvzp8eMZWUXbBCjEvwSkkk59S5iCNLY3QrkX6R";
        public static final String USDT="Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB";
        public static final String SRM="SRMuApVNdxXokk5GT7XD5cUUgXMBCoAz2LHeuAoKWRt";
        public static final String ULA="3N4MaMn4fPm7puzE6oPEwAUody9h5pLUTxc6hZGFdpgM";
        public static final String STEP="StepAscQoEioFxxWGnh2sLBDFp9d8rvKz2Yp39iDpyT";
    }
    //个人列表token资产
    public static ArrayList<String> splTokenList=new ArrayList<String>();
    public static ArrayList<String> getDefaultSplTokenInfo(){
        return splTokenList;
    }
    public static ArrayList<String> addSpltoken(String token)
    {
        splTokenList.add(token);
        return splTokenList;
    }
    public static ArrayList<String> getSplTokenList()
    {
        return splTokenList;
    }

    //内部token字典
    public static HashMap<String,String> tokenMap=new HashMap<>();
    static
    {
        tokenMap.put(Solana.SOL,"SOL");
        tokenMap.put(Solana.USDC,"USDC");
        tokenMap.put(Solana.RAY,"RAY");
        tokenMap.put(Solana.USDT,"USDT");
        tokenMap.put(Solana.SRM,"SRM");
        tokenMap.put(Solana.ULA,"ULA");
        tokenMap.put(Solana.STEP,"STEP");
        splTokenList.add(Solana.SOL);
        splTokenList.add(Solana.USDT);
        splTokenList.add(Solana.USDC);
        splTokenList.add(Solana.ULA);
        splTokenList.add(Solana.RAY);
        splTokenList.add(Solana.SRM);
        splTokenList.add(Solana.STEP);
    }
    public static String getTokenName(String mint)
    {
        return tokenMap.get(mint);
    }
    public static void putToken(String mint,String name)
    {
        tokenMap.put(mint,name);
    }




    public static  class ContractProgram
    {
        public static final String SYSTEM_PROGRAM="11111111111111111111111111111111";
        public static final String TOKEN_PROGRAM="TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA";
        public static final String ASSOCIATED_TOKEN_PROGRAM="ATokenGPvbdGVxr1b2hvZbsiqW5xWH25efTNsLJA8knL";
    }

}
