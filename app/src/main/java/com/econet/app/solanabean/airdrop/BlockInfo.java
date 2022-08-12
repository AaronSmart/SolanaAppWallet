/**
  * Copyright 2022 bejson.com 
  */
package com.econet.app.solanabean.airdrop;

/**
 * Auto-generated: 2022-03-06 9:30:27
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BlockInfo {

    private long blockHeight;
    private long blockTime;
    private String blockhash;
    private long parentSlot;
    private String previousBlockhash;
    private long fee_rewards;
    private String validator;
    private int transactions_count;
    public void setBlockHeight(long blockHeight) {
         this.blockHeight = blockHeight;
     }
     public long getBlockHeight() {
         return blockHeight;
     }

    public void setBlockTime(long blockTime) {
         this.blockTime = blockTime;
     }
     public long getBlockTime() {
         return blockTime;
     }

    public void setBlockhash(String blockhash) {
         this.blockhash = blockhash;
     }
     public String getBlockhash() {
         return blockhash;
     }

    public void setParentSlot(long parentSlot) {
         this.parentSlot = parentSlot;
     }
     public long getParentSlot() {
         return parentSlot;
     }

    public void setPreviousBlockhash(String previousBlockhash) {
         this.previousBlockhash = previousBlockhash;
     }
     public String getPreviousBlockhash() {
         return previousBlockhash;
     }

    public void setFee_rewards(long fee_rewards) {
         this.fee_rewards = fee_rewards;
     }
     public long getFee_rewards() {
         return fee_rewards;
     }

    public void setValidator(String validator) {
         this.validator = validator;
     }
     public String getValidator() {
         return validator;
     }

    public void setTransactions_count(int transactions_count) {
         this.transactions_count = transactions_count;
     }
     public int getTransactions_count() {
         return transactions_count;
     }

}