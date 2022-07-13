/**
  * Copyright 2022 bejson.com 
  */
package com.econet.app.solanabean.getSignatureStatusFromSolscan;

/**
 * Auto-generated: 2022-06-17 16:32:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class InputAccount {

    private String account;
    private boolean signer;
    private boolean writable;
    private long preBalance;
    private long postBalance;
    public void setAccount(String account) {
         this.account = account;
     }
     public String getAccount() {
         return account;
     }

    public void setSigner(boolean signer) {
         this.signer = signer;
     }
     public boolean getSigner() {
         return signer;
     }

    public void setWritable(boolean writable) {
         this.writable = writable;
     }
     public boolean getWritable() {
         return writable;
     }

    public void setPreBalance(long preBalance) {
         this.preBalance = preBalance;
     }
     public long getPreBalance() {
         return preBalance;
     }

    public void setPostBalance(long postBalance) {
         this.postBalance = postBalance;
     }
     public long getPostBalance() {
         return postBalance;
     }

}