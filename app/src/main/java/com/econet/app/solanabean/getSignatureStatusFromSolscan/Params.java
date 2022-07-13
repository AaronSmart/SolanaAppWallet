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
public class Params {

    private String clockSysvar;
    private String slotHashesSysvar;
    private String voteAccount;
    private String voteAuthority;
    private String voteHash;
    private List<Long> slots;
    private long timestamp;
    public void setClockSysvar(String clockSysvar) {
         this.clockSysvar = clockSysvar;
     }
     public String getClockSysvar() {
         return clockSysvar;
     }

    public void setSlotHashesSysvar(String slotHashesSysvar) {
         this.slotHashesSysvar = slotHashesSysvar;
     }
     public String getSlotHashesSysvar() {
         return slotHashesSysvar;
     }

    public void setVoteAccount(String voteAccount) {
         this.voteAccount = voteAccount;
     }
     public String getVoteAccount() {
         return voteAccount;
     }

    public void setVoteAuthority(String voteAuthority) {
         this.voteAuthority = voteAuthority;
     }
     public String getVoteAuthority() {
         return voteAuthority;
     }

    public void setVoteHash(String voteHash) {
         this.voteHash = voteHash;
     }
     public String getVoteHash() {
         return voteHash;
     }

    public void setSlots(List<Long> slots) {
         this.slots = slots;
     }
     public List<Long> getSlots() {
         return slots;
     }

    public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }
     public long getTimestamp() {
         return timestamp;
     }

}