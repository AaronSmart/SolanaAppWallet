/**
  * Copyright 2022 bejson.com 
  */
package com.econet.app.solanabean.getTokensFromSolscan;

/**
 * Auto-generated: 2022-02-14 17:13:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private String tokenAddress;
    private TokenAmount tokenAmount;
    private String tokenAccount;
    private String tokenName;
    private String tokenIcon;
    private int rentEpoch;
    private long lamports;
    public void setTokenAddress(String tokenAddress) {
         this.tokenAddress = tokenAddress;
     }
     public String getTokenAddress() {
         return tokenAddress;
     }

    public void setTokenAmount(TokenAmount tokenAmount) {
         this.tokenAmount = tokenAmount;
     }
     public TokenAmount getTokenAmount() {
         return tokenAmount;
     }

    public void setTokenAccount(String tokenAccount) {
         this.tokenAccount = tokenAccount;
     }
     public String getTokenAccount() {
         return tokenAccount;
     }

    public void setTokenName(String tokenName) {
         this.tokenName = tokenName;
     }
     public String getTokenName() {
         return tokenName;
     }

    public void setTokenIcon(String tokenIcon) {
         this.tokenIcon = tokenIcon;
     }
     public String getTokenIcon() {
         return tokenIcon;
     }

    public void setRentEpoch(int rentEpoch) {
         this.rentEpoch = rentEpoch;
     }
     public int getRentEpoch() {
         return rentEpoch;
     }

    public void setLamports(long lamports) {
         this.lamports = lamports;
     }
     public long getLamports() {
         return lamports;
     }

}