package com.econet.app.ServerBeans.login;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-06-02 15:57:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Result {

    @JsonProperty("authToken")
    private String authtoken;
    @JsonProperty("authId")
    private String authid;
    @JsonProperty("userInfo")
    private Userinfo userinfo;
    @JsonProperty("companyInfo")
    private Companyinfo companyinfo;
    public void setAuthtoken(String authtoken) {
         this.authtoken = authtoken;
     }
     public String getAuthtoken() {
         return authtoken;
     }

    public void setAuthid(String authid) {
         this.authid = authid;
     }
     public String getAuthid() {
         return authid;
     }

    public void setUserinfo(Userinfo userinfo) {
         this.userinfo = userinfo;
     }
     public Userinfo getUserinfo() {
         return userinfo;
     }

    public void setCompanyinfo(Companyinfo companyinfo) {
         this.companyinfo = companyinfo;
     }
     public Companyinfo getCompanyinfo() {
         return companyinfo;
     }

}