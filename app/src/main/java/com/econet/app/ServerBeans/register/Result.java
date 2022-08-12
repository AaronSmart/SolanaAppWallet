package com.econet.app.ServerBeans.register;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

    @JsonProperty("authToken")
    private String authtoken;
    @JsonProperty("authId")
    private String authid;
    @JsonProperty("userInfo")
    private Userinfo userinfo;
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

}