/**
  * Copyright 2021 bejson.com 
  */
package com.econet.app.solanabean.mintTokenList;
import java.util.List;
import java.util.Date;

/**
 * Auto-generated: 2021-07-29 15:43:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TokenMintBean {

    private String name;
    private String logoURI;
    private List<String> keywords;
    private Tags tags;
    private Date timestamp;
    private List<Tokens> tokens;
    private Version version;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setLogoURI(String logoURI) {
         this.logoURI = logoURI;
     }
     public String getLogoURI() {
         return logoURI;
     }

    public void setKeywords(List<String> keywords) {
         this.keywords = keywords;
     }
     public List<String> getKeywords() {
         return keywords;
     }

    public void setTags(Tags tags) {
         this.tags = tags;
     }
     public Tags getTags() {
         return tags;
     }

    public void setTimestamp(Date timestamp) {
         this.timestamp = timestamp;
     }
     public Date getTimestamp() {
         return timestamp;
     }

    public void setTokens(List<Tokens> tokens) {
         this.tokens = tokens;
     }
     public List<Tokens> getTokens() {
         return tokens;
     }

    public void setVersion(Version version) {
         this.version = version;
     }
     public Version getVersion() {
         return version;
     }

}