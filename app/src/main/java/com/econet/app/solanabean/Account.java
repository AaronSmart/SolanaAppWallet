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
public class Account {

    private Data data;
    private boolean executable;
    private long lamports;
    private String owner;
    private int rentEpoch;
    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    public void setExecutable(boolean executable) {
         this.executable = executable;
     }
     public boolean getExecutable() {
         return executable;
     }

    public void setLamports(long lamports) {
         this.lamports = lamports;
     }
     public long getLamports() {
         return lamports;
     }

    public void setOwner(String owner) {
         this.owner = owner;
     }
     public String getOwner() {
         return owner;
     }

    public void setRentEpoch(int rentEpoch) {
         this.rentEpoch = rentEpoch;
     }
     public int getRentEpoch() {
         return rentEpoch;
     }

}