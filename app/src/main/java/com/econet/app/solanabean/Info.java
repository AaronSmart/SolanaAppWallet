/**
  * Copyright 2021 bejson.com 
  */
package com.econet.app.solanabean;

/**
 * Auto-generated: 2021-06-14 12:11:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Info {

    private boolean isNative;
    private String mint;
    private String owner;
    private String state;
    private TokenAmount tokenAmount;
    public void setIsNative(boolean isNative) {
         this.isNative = isNative;
     }
     public boolean getIsNative() {
         return isNative;
     }

    public void setMint(String mint) {
         this.mint = mint;
     }
     public String getMint() {
         return mint;
     }

    public void setOwner(String owner) {
         this.owner = owner;
     }
     public String getOwner() {
         return owner;
     }

    public void setState(String state) {
         this.state = state;
     }
     public String getState() {
         return state;
     }

    public void setTokenAmount(TokenAmount tokenAmount) {
         this.tokenAmount = tokenAmount;
     }
     public TokenAmount getTokenAmount() {
         return tokenAmount;
     }

}