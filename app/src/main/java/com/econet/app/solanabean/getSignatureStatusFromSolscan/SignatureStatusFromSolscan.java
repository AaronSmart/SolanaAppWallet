/**
  * Copyright 2022 bejson.com 
  */
package com.econet.app.solanabean.getSignatureStatusFromSolscan;
import java.util.List;

/**
 * Auto-generated: 2022-06-17 16:32:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SignatureStatusFromSolscan {

    private long blockTime;
    private long slot;
    private String txHash;
    private int fee;
    private String status;
    private int lamport;
    private List<String> signer;
    private List<String> logMessage;
    private List<InputAccount> inputAccount;
    private String recentBlockhash;
    private List<String> innerInstructions;
    private List<MainActions> mainActions;
    private List<String> tokenBalanes;
    private List<ParsedInstruction> parsedInstruction;
    private String txStatus;
    private String confirmations;
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

    public void setLogMessage(List<String> logMessage) {
         this.logMessage = logMessage;
     }
     public List<String> getLogMessage() {
         return logMessage;
     }

    public void setInputAccount(List<InputAccount> inputAccount) {
         this.inputAccount = inputAccount;
     }
     public List<InputAccount> getInputAccount() {
         return inputAccount;
     }

    public void setRecentBlockhash(String recentBlockhash) {
         this.recentBlockhash = recentBlockhash;
     }
     public String getRecentBlockhash() {
         return recentBlockhash;
     }

    public void setInnerInstructions(List<String> innerInstructions) {
         this.innerInstructions = innerInstructions;
     }
     public List<String> getInnerInstructions() {
         return innerInstructions;
     }

    public void setMainActions(List<MainActions> mainActions) {
         this.mainActions = mainActions;
     }
     public List<MainActions> getMainActions() {
         return mainActions;
     }

    public void setTokenBalanes(List<String> tokenBalanes) {
         this.tokenBalanes = tokenBalanes;
     }
     public List<String> getTokenBalanes() {
         return tokenBalanes;
     }

    public void setParsedInstruction(List<ParsedInstruction> parsedInstruction) {
         this.parsedInstruction = parsedInstruction;
     }
     public List<ParsedInstruction> getParsedInstruction() {
         return parsedInstruction;
     }

    public void setTxStatus(String txStatus) {
         this.txStatus = txStatus;
     }
     public String getTxStatus() {
         return txStatus;
     }

    public void setConfirmations(String confirmations) {
         this.confirmations = confirmations;
     }
     public String getConfirmations() {
         return confirmations;
     }

}