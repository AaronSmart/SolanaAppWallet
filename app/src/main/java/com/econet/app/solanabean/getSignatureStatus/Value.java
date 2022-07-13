/**
  * Copyright 2022 bejson.com 
  */
package com.econet.app.solanabean.getSignatureStatus;

/**
 * Auto-generated: 2022-06-17 9:24:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Value {

    private String confirmationStatus;
    private String confirmations;
    private String err;
    private long slot;
    private Status status;
    public void setConfirmationStatus(String confirmationStatus) {
         this.confirmationStatus = confirmationStatus;
     }
     public String getConfirmationStatus() {
         return confirmationStatus;
     }

    public void setConfirmations(String confirmations) {
         this.confirmations = confirmations;
     }
     public String getConfirmations() {
         return confirmations;
     }

    public void setErr(String err) {
         this.err = err;
     }
     public String getErr() {
         return err;
     }

    public void setSlot(long slot) {
         this.slot = slot;
     }
     public long getSlot() {
         return slot;
     }

    public void setStatus(Status status) {
         this.status = status;
     }
     public Status getStatus() {
         return status;
     }

}