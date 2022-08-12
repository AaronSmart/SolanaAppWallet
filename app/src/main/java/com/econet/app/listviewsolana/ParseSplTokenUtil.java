package com.econet.app.listviewsolana;

import com.alibaba.fastjson.JSON;
import com.econet.app.solanabean.Account;
import com.econet.app.solanabean.Data;
import com.econet.app.solanabean.Info;
import com.econet.app.solanabean.Parsed;
import com.econet.app.solanabean.SolToken;
import com.econet.app.solanabean.SplToken;
import com.econet.app.solanabean.SplTokenBean;
import com.econet.app.solanabean.TokenAmount;
import com.econet.app.solanabean.Value;
import com.econet.app.solanabean.airdrop.BlocTransaction;
import com.econet.app.solanabean.airdrop.BlockInfo;
import com.econet.app.solanabean.airdrop.Transactions;
import com.econet.app.solanabean.getTokensFromSolscan.TokensFromSolScan;
import com.econet.app.uitl.URLUtil;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParseSplTokenUtil {

    public static SplToken parseJsonForSol(String result){
        if(result==null)
        {
            return null;
        }
        //there exist \n in the end of json string,it will crush,so handle it
        result=result.replace("\n","");
        result=result.replace("\r","");
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
            splToken.setTokenMint("solana chain native god token");
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
        //there exist \n in the end of json string,it will crush,so handle it
        result=result.replace("\n","");
        result=result.replace("\r","");
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

    //get the tokens from solscan for better speed up
    public static List<SplToken>  parseTokensFromSolscanJson(String result)
    {
        if(result==null) {
            return null;
        }
        result=result.replace("\n","");
        result=result.replace("\r","");
        TokensFromSolScan tokensFromSolScan =JSON.parseObject(result, TokensFromSolScan.class);
        //tokens from  solana account ,data is from solscan
        List<SplToken> splTokenList=new ArrayList<>();
        if(tokensFromSolScan.getData()!=null) {
            List<com.econet.app.solanabean.getTokensFromSolscan.Data> tokensFromSolscanData=tokensFromSolScan.getData();
            for(int i =0;i<tokensFromSolscanData.size();i++) {
                SplToken splToken=new SplToken();
                splToken.setTokenMint(tokensFromSolscanData.get(i).getTokenAddress());
                //get token symbol
                String tokenName=TokenMintUtil.getMintName(tokensFromSolscanData.get(i).getTokenAddress());
                if("".equals(tokenName)) {
                    tokenName=tokensFromSolscanData.get(i).getTokenName();
                }
                if("".equals(tokenName)) {
                    tokenName="Unknow Token";
                }
                splToken.setTokenName(tokenName);
                com.econet.app.solanabean.getTokensFromSolscan.TokenAmount tokenAmount=tokensFromSolscanData.get(i).getTokenAmount();
                if(tokenAmount!=null) {
                    splToken.setTokenAmount(tokenAmount.getUiAmountString());
                }
                splTokenList.add(splToken);
            }
        }
        return splTokenList;
    }
    // this function is used to get the total transactions for the Block
    public static int getBlockTransactionTotalCountForAirdrop(String result)
    {
        if(result==null) {
            return 0;
        }
        BlockInfo blockInfo=JSON.parseObject(result, BlockInfo.class);
        int transactionsCount=blockInfo.getTransactions_count();
        return transactionsCount;
    }
    //get the singer of one block
    public static List<String> getBlockTransactionSingerListForAirdrop(String result)
    {
        List<String> singerList=new ArrayList<>();
        if(result==null) {
            return singerList;
        }
        BlocTransaction blockSingers=JSON.parseObject(result, BlocTransaction.class);
        if(blockSingers!=null)
        {
            List<Transactions> transactionList=blockSingers.getTransactions();
            if(transactionList.size()!=0)
            {
                for(int i=0;i<transactionList.size();i++)
                {
                    singerList.add(transactionList.get(i).getSigner().get(0));
                }
            }
        }
        return singerList;
    }

    //solana merchant pay protocal standard
    public static HashMap<String,String> getMerchantPayMap(String scanResult)
    {
        //solana:mvines9iiHiQTysrwkJjGf2gb9Ex9jXJX8ns3qwf2kN?amount=0.01&spl-token=EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v&label=Michael&message=Thanks%20for%20all%20the%20fish&memo=OrderId5678
        HashMap<String,String>payMap=new HashMap<>();
        scanResult=scanResult.trim();
        String scheme="";
        String solanaAccount="";
        //todo this is the basic check for the solana pay standard account=44+scheme(7)
        if(scanResult.length()>51) {
            scheme= scanResult.substring(0,6);
            if("solana".equalsIgnoreCase(scheme)) {
                int accountEnd=scanResult.indexOf("?");
                solanaAccount=scanResult.substring(7,accountEnd);
                scanResult=scanResult.substring(accountEnd+1);
                payMap.put("solanaAccount",solanaAccount);
                payMap.put("scheme","solana");
                String[]params=scanResult.split("&");
                for(int i=0;i<params.length;i++) {
                    payMap.put(params[i].substring(0,params[i].indexOf("=")), URLUtil.URLDecoderString(params[i].substring(params[i].indexOf("=")+1)));
                }
            }
        }
        return payMap;
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
    public static String getSignatureStatus(String signature)
    {
        return "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"getSignatureStatuses\",\"params\":[[\""+signature+"\"]]}";
    }

    public static  class Solana
    {
        public static final String SOL="SolanaNativeToken";
        public static final String SOL_MINT="solana chain native god token";
        public static final String SOL_WRAP="So11111111111111111111111111111111111111112";
        public static final String USDC ="EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v";
        public static final String RAY="4k3Dyjzvzp8eMZWUXbBCjEvwSkkk59S5iCNLY3QrkX6R";
        public static final String USDT="Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB";
        public static final String SRM="SRMuApVNdxXokk5GT7XD5cUUgXMBCoAz2LHeuAoKWRt";
        public static final String ULA="EwHqbMUMX33JjWAhxSg9vsX3miWqncsgpnAbqn9nhJwZ";
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
