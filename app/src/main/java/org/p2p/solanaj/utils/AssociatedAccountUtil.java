package org.p2p.solanaj.utils;

import io.github.novacrypto.hashing.Sha256;
import org.bitcoinj.core.Base58;
import java.io.UnsupportedEncodingException;

public class AssociatedAccountUtil {

    public static String createAssociatedAccountAddress(String account,String mint) throws UnsupportedEncodingException {
        String associatedAddress="";
        byte [] one = Base58.decode(account);
        byte [] two =Base58.decode("TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA");
        byte [] three =Base58.decode(mint);
        byte [] four = new byte[1];
        byte [] five =Base58.decode("ATokenGPvbdGVxr1b2hvZbsiqW5xWH25efTNsLJA8knL");
        byte [] six ="ProgramDerivedAddress".getBytes("utf-8");
        byte [] xt=new byte[one.length+two.length+three.length+four.length+five.length+six.length];
        for(int i=255;i>0;i--)
        {
            four[0] = (byte)(255L & i);
            int length=0;
            for(int t=0;t<one.length;t++)
            {
                xt[length++]=one[t];
                // System.four.print((one[t]& 0xff)+" ");
            }
            for(int t=0;t<two.length;t++)
            {
                xt[length++]=two[t];
                //System.four.print((two[t]& 0xff)+" ");
            }
            for(int t=0;t<three.length;t++)
            {
                xt[length++]=three[t];
                // System.four.print((three[t]& 0xff)+" ");
            }
            for(int t=0;t<four.length;t++)
            {
                xt[length++]=four[t];
                // System.four.print((four[t]& 0xff)+" ");
            }
            for(int t=0;t<five.length;t++)
            {
                xt[length++]=five[t];
                //System.four.print((five[t]& 0xff)+" ");
            }
            for(int t=0;t<six.length;t++)
            {
                xt[length++]=six[t];
                // System.four.print((six[t]& 0xff)+" ");
            }
            byte []hash=Sha256.sha256(xt);
            associatedAddress= Base58.encode(hash);
            int isOnCurve= TweetNaclFast.is_on_curve(hash);
            if(isOnCurve==0)//判断是否是在曲线上
            {
                break;
            }
        }
        return associatedAddress;
    }
    public static void main(String[]args) throws UnsupportedEncodingException {
        String res=createAssociatedAccountAddress("8gdimNnQB9XM7xWW5GSrvBFkdbRX17aL6AVcUV6BmAQR",
                "StepAscQoEioFxxWGnh2sLBDFp9d8rvKz2Yp39iDpyT");
        System.out.println(res);
    }

}
