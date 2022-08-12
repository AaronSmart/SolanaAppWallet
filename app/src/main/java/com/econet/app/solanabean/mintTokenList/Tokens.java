/**
  * Copyright 2021 bejson.com 
  */
package com.econet.app.solanabean.mintTokenList;
import java.util.List;

/**
 * Auto-generated: 2021-07-29 15:43:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Tokens {

    private int chainId;
    private String address;
    private String symbol;
    private String name;
    private int decimals;
    private String logoURI;
    private List<String> tags;
    private Extensions extensions;
    public void setChainId(int chainId) {
         this.chainId = chainId;
     }
     public int getChainId() {
         return chainId;
     }

    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

    public void setSymbol(String symbol) {
         this.symbol = symbol;
     }
     public String getSymbol() {
         return symbol;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setDecimals(int decimals) {
         this.decimals = decimals;
     }
     public int getDecimals() {
         return decimals;
     }

    public void setLogoURI(String logoURI) {
         this.logoURI = logoURI;
     }
     public String getLogoURI() {
         return logoURI;
     }

    public void setTags(List<String> tags) {
         this.tags = tags;
     }
     public List<String> getTags() {
         return tags;
     }

    public void setExtensions(Extensions extensions) {
         this.extensions = extensions;
     }
     public Extensions getExtensions() {
         return extensions;
     }

}