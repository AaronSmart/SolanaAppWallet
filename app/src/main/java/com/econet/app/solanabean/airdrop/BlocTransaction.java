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
public class BlocTransaction {

    private int total;
    private List<Transactions> transactions;
    public void setTotal(int total) {
         this.total = total;
     }
     public int getTotal() {
         return total;
     }

    public void setTransactions(List<Transactions> transactions) {
         this.transactions = transactions;
     }
     public List<Transactions> getTransactions() {
         return transactions;
     }

}