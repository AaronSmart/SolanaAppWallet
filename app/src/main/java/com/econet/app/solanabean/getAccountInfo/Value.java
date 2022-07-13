/**
  * Copyright 2021 bejson.com 
  */
package com.econet.app.solanabean.getAccountInfo;
import java.util.List;

/**
 * Auto-generated: 2021-07-26 12:40:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Value {

    private List<String> data;
    private boolean executable;
    private long lamports;
    private String owner;
    private int rentEpoch;
    public void setData(List<String> data) {
         this.data = data;
     }
     public List<String> getData() {
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