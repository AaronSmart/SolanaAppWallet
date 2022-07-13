/**
  * Copyright 2021 bejson.com 
  */
package com.econet.app.solanabean.getFees;

/**
 * Auto-generated: 2021-08-28 9:35:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Value {

    private String blockhash;
    private FeeCalculator feeCalculator;
    private int lastValidSlot;
    private int lastValidBlockHeight;
    public void setBlockhash(String blockhash) {
         this.blockhash = blockhash;
     }
     public String getBlockhash() {
         return blockhash;
     }

    public void setFeeCalculator(FeeCalculator feeCalculator) {
         this.feeCalculator = feeCalculator;
     }
     public FeeCalculator getFeeCalculator() {
         return feeCalculator;
     }

    public void setLastValidSlot(int lastValidSlot) {
         this.lastValidSlot = lastValidSlot;
     }
     public int getLastValidSlot() {
         return lastValidSlot;
     }

    public void setLastValidBlockHeight(int lastValidBlockHeight) {
         this.lastValidBlockHeight = lastValidBlockHeight;
     }
     public int getLastValidBlockHeight() {
         return lastValidBlockHeight;
     }

}