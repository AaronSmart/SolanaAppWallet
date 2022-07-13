/**
  * Copyright 2022 bejson.com 
  */
package com.econet.app.solanabean.airdrop;
import java.util.List;

/**
 * Auto-generated: 2022-03-06 9:32:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Transactions {

    private long blockTime;
    private long slot;
    private String txHash;
    private int fee;
    private String status;
    private int lamport;
    private List<String> signer;
    private List<ParsedInstruction> parsedInstruction;
    public void setBlockTime(long blockTime) {
         this.blockTime = blockTime;
     }
     public long getBlockTime() {
         return blockTime;
     }

    public void setSlot(long slot) {
         this.slot = slot;
     }
     public long getSlot() {
         return slot;
     }

    public void setTxHash(String txHash) {
         this.txHash = txHash;
     }
     public String getTxHash() {
         return txHash;
     }

    public void setFee(int fee) {
         this.fee = fee;
     }
     public int getFee() {
         return fee;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setLamport(int lamport) {
         this.lamport = lamport;
     }
     public int getLamport() {
         return lamport;
     }

    public void setSigner(List<String> signer) {
         this.signer = signer;
     }
     public List<String> getSigner() {
         return signer;
     }

    public void setParsedInstruction(List<ParsedInstruction> parsedInstruction) {
         this.parsedInstruction = parsedInstruction;
     }
     public List<ParsedInstruction> getParsedInstruction() {
         return parsedInstruction;
     }

}